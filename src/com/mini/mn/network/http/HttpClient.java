package com.mini.mn.network.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import com.mini.mn.R;
import com.mini.mn.app.MiniApplication;
import com.mini.mn.constant.NetworkType;
import com.mini.mn.exception.HttpRequestException;
import com.mini.mn.exception.HttpResponseException;
import com.mini.mn.exception.HttpTimeoutException;
import com.mini.mn.exception.IllegalArgsException;
import com.mini.mn.exception.NetworkNotFoundException;
import com.mini.mn.exception.OutOfMemeryException;
import com.mini.mn.model.UploadFile;
import com.mini.mn.util.DebugUtils;
import com.mini.mn.util.DeviceUtils;
import com.mini.mn.util.StringUtils;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;

/**
 * ��װ�����������.
 * 
 * @version 1.0.0
 * @date 2012-9-12
 * @author DengZhaoyong
 */
public class HttpClient {

	// ������������ʱʱ��
	private static final int CONNECTION_TIMEOUT_MS = 8 * 1000;
	private static final int SOCKET_TIMEOUT_MS = 24 * 1000;
	// �ڷ����쳣ʱ�Զ����Դ���
	private static final int AUTO_RETRY_TIMES = 3;
	// ���������������(��ʱ���̳߳ص�������һ��)
	private static final int MAX_TOTAL_CONNECTIONS = 48;
	// �������Ŀ���ʱ��(��λ: ��)
	private static final Integer MAX_IDLE_TIME_OUT = 60;

	// ����APACHE HttpClientʵ��
	private DefaultHttpClient mHttpClient;

	// HTTP Cookies
	private static HashMap<String, String> mCookies = new HashMap<String, String>();

	/** ��������ַ */
	private static String mServerAddress;

	/** �ֻ��ͻ��������� */
	private static String mChannelId;
	/** Android�������� */
	private static String mSubChannelId;

	/** CMWAP(�ƶ�, ��ͨ)GPRS����, ���ô��� */
	private HttpHost mCmwapProxy = new HttpHost("10.0.0.172", 80, "http");
	/** CTWAP(����)GPRS����, ���ô��� */
	private HttpHost mCtwapProxy = new HttpHost("10.0.0.200", 80, "http");

	private Context mContext = null;

	// �ύ�汾��Ϣ�����ڶ���ͳ��
	private static String appVersion = "1.0.0";

	/**
	 * ����HttpClientʵ��
	 */
	public HttpClient(Context context) {
		this.mContext = context;
		initConfig();
		initHttpClient();
	}

	/**
	 * ��ʼ��������Ϣ
	 */
	private void initConfig() {
		// ��ʼ��������
		mChannelId = mContext.getResources().getString(R.string.channel_id);
		mSubChannelId = mContext.getResources().getString(
				R.string.sub_channel_id);
		try {
			appVersion = MiniApplication
					.getContext()
					.getPackageManager()
					.getPackageInfo(
							MiniApplication.getContext().getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// ignore
		}

		// ��ʼ����������ַ
		String publishEnvironment = mContext.getResources().getString(
				R.string.publish_environment);
		if ("formal".equals(publishEnvironment)) {
			mServerAddress = mContext.getResources().getString(
					R.string.server_address_formal);
		} else if ("personal".equals(publishEnvironment)) {
			mServerAddress = mContext.getResources().getString(
					R.string.server_address_personal);
		}
	}

	/**
	 * ��ʼ��HttpClient��������
	 */
	private void initHttpClient() {
		// ��ʼ��Http����
		BasicHttpParams basicHttpParams = new BasicHttpParams();
		ConnManagerParams.setMaxTotalConnections(basicHttpParams,
				MAX_TOTAL_CONNECTIONS);
		HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);

		// ע��Э���������
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));

		// ������ThreadSafeClientConnManager��HttpClientʵ��
		ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager(
				basicHttpParams, schemeRegistry);
		threadSafeClientConnManager.closeIdleConnections(MAX_IDLE_TIME_OUT,
				TimeUnit.SECONDS);
		// ��ʼ��HttpClientʵ��
		mHttpClient = new DefaultHttpClient(threadSafeClientConnManager,
				basicHttpParams);

		/*
		 * ������������Ӧ������. ʹ��GZIPѹ���ͽ�ѹ����, ���Ը��õĽ�ʡ��صĵ���.
		 */
		mHttpClient.addRequestInterceptor(gzipReqInterceptor);
		// Support GZIP
		mHttpClient.addResponseInterceptor(gzipResInterceptor);
	}

	/**
	 * ����WAP����������������.
	 * <p>
	 * �ƶ�,��ͨ: httpClient.setProxy("10.0.0.172", 80, "http");<br>
	 * ����: httpClient.setProxy("10.0.0.200", 80, "http");
	 * </p>
	 * 
	 * @param host
	 *            ����������IP��ַ
	 * @param port
	 *            �˿ں�
	 * @param scheme
	 *            Э��(http or https)
	 */
	public void setProxy(String host, int port, String scheme) {
		HttpHost proxy = new HttpHost(host, port, scheme);
		setProxy(proxy);
	}

	/**
	 * ����WAP����������������.
	 * 
	 * @param proxy
	 */
	public void setProxy(HttpHost proxy) {
		mHttpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
				proxy);
	}

	/**
	 * �Ƴ����������.
	 */
	public void removeProxy() {
		mHttpClient.getParams().removeParameter(ConnRoutePNames.DEFAULT_PROXY);
	}

	/**
	 * �ر���������.
	 */
	public void shutdown() {
		mHttpClient.getConnectionManager().shutdown();
	}

	/**
	 * ���Cookie.
	 * 
	 * @param cookieName
	 * @param cookieValue
	 */
	public void addCookie(String cookieName, String cookieValue) {
		mCookies.put(cookieName, cookieValue);
	}

	/**
	 * ���Cookie
	 */
	public void clearCookie() {
		mCookies.clear();
	}

	public Response get(String url) throws HttpRequestException,
			NetworkNotFoundException, IllegalArgsException,
			HttpTimeoutException, HttpResponseException, OutOfMemeryException {

		return get(url, null);
	}

	public Response get(String url, ArrayList<BasicNameValuePair> params)
			throws HttpRequestException, NetworkNotFoundException,
			IllegalArgsException, HttpTimeoutException, HttpResponseException,
			OutOfMemeryException {
		if (params == null) {
			params = new ArrayList<BasicNameValuePair>();
		}
		params.add(new BasicNameValuePair("channelId", mChannelId));
		params.add(new BasicNameValuePair("subChannelId", mSubChannelId));
		params.add(new BasicNameValuePair("versionNo", appVersion));

		if (url.indexOf("?") < 0) {
			url += "?" + encodeParams(params);
		} else {
			url += "&" + encodeParams(params);
		}

		return request(url, params, null, HttpGet.METHOD_NAME);
	}

	public Response post(String url) throws HttpRequestException,
			NetworkNotFoundException, HttpTimeoutException,
			HttpResponseException, NotFoundException, OutOfMemeryException {

		return post(url, null);
	}

	public Response post(String url, ArrayList<BasicNameValuePair> params)
			throws HttpRequestException, NetworkNotFoundException,
			HttpTimeoutException, HttpResponseException, OutOfMemeryException {

		return post(url, params, null);
	}

	public Response post(String url, ArrayList<BasicNameValuePair> params,
			UploadFile uploadFile) throws HttpRequestException,
			NetworkNotFoundException, HttpTimeoutException,
			HttpResponseException, OutOfMemeryException {
		if (params == null) {
			params = new ArrayList<BasicNameValuePair>();
		}

		params.add(new BasicNameValuePair("channelId", mChannelId));
		params.add(new BasicNameValuePair("subChannelId", mSubChannelId));
		params.add(new BasicNameValuePair("versionNo", appVersion));

		return request(url, params, uploadFile, HttpPost.METHOD_NAME);
	}

	/**
	 * ������������.
	 * 
	 * @param url
	 *            �����ַ
	 * @param params
	 *            �������
	 * @param uploadFileVo
	 *            �ϴ��ļ�ʵ��VO
	 * @param getOrPost
	 *            ����ʽ: GET or POST
	 * @return
	 * @throws NetworkNotFoundException
	 * @throws HttpRequestException
	 * @throws HttpTimeoutException
	 * @throws HttpResponseException
	 * @throws OutOfMemeryException
	 */
	public Response request(String url, ArrayList<BasicNameValuePair> params,
			UploadFile uploadFile, String getOrPost)
			throws NetworkNotFoundException, HttpRequestException,
			HttpTimeoutException, HttpResponseException, OutOfMemeryException {
		DebugUtils.debug("sending " + getOrPost + " request to [" + url
				+ "]...");

		// �����������
		setupNetworkProxy();

		HttpUriRequest uriRequest = null;
		HttpResponse httpResponse = null;
		Response result = null;

		URI uri = createURI(url);

		// ���� GET or POST uriRequest
		uriRequest = createUriRequest(uri, params, uploadFile, getOrPost);
		// ����HTTP�����������
		setupHTTPConnectionParams(uriRequest);
		try {
			httpResponse = mHttpClient.execute(uriRequest);
			result = new Response(httpResponse);
		} catch (ConnectTimeoutException e) {
			uriRequest.abort();
			DebugUtils.error(e.getMessage(), e);
			throw new HttpTimeoutException(mContext.getResources().getString(
					R.string.http_request_timeout_exception));
		} catch (SocketTimeoutException e) {
			uriRequest.abort();
			DebugUtils.error(e.getMessage(), e);
			throw new HttpTimeoutException(mContext.getResources().getString(
					R.string.http_request_timeout_exception));
		} catch (Exception e) {
			uriRequest.abort();
			DebugUtils.error(e.getMessage(), e);
			throw new HttpResponseException(mContext.getResources().getString(
					R.string.server_no_response), e);
		} catch (OutOfMemoryError e) {
			uriRequest.abort();
			OutOfMemeryException exception = new OutOfMemeryException(mContext
					.getResources()
					.getString(R.string.server_request_exception));
			DebugUtils.error(e.getMessage(), exception);
			throw exception;
		} catch (AssertionError e) {
			uriRequest.abort();
			OutOfMemeryException exception = new OutOfMemeryException(mContext
					.getResources()
					.getString(R.string.server_request_exception));
			DebugUtils.error(e.getMessage(), exception);
			throw exception;
		}

		if (httpResponse == null
				|| httpResponse.getStatusLine().getStatusCode() != 200) {
			uriRequest.abort();
			DebugUtils.error("http response code from [" + url + "] is: "
					+ httpResponse.getStatusLine().getStatusCode(), null);
			throw new HttpResponseException(mContext.getResources().getString(
					R.string.server_no_response));
		}

		// ǿ�ƹر�����
		mHttpClient.getConnectionManager().closeExpiredConnections();

		return result;
	}

	/**
	 * ����������, ��������֧��GZIP.
	 */
	private static HttpRequestInterceptor gzipReqInterceptor = new HttpRequestInterceptor() {
		@Override
		public void process(HttpRequest request, HttpContext context)
				throws HttpException, IOException {
			String gzipHead = "Accept-Encoding";
			if (!request.containsHeader(gzipHead)) {
				request.addHeader(gzipHead, "gzip");
			}
		}
	};

	/**
	 * ��Ӧ������, ���뾭��GZIPѹ������Ӧ���.
	 */
	private static HttpResponseInterceptor gzipResInterceptor = new HttpResponseInterceptor() {
		@Override
		public void process(HttpResponse response, HttpContext context)
				throws HttpException, IOException {
			HttpEntity entity = response.getEntity();
			Header header = entity.getContentEncoding();
			if (header != null) {
				HeaderElement[] codecs = header.getElements();
				for (int i = 0; i < codecs.length; i++) {
					if (codecs[i].getName().indexOf("gzip") >= 0) {
						response.setEntity(new GzipDecompressingEntity(response
								.getEntity()));
						return;
					}
				}
			}
		}
	};

	/**
	 * ����������������
	 * 
	 * @throws NetworkNotFoundException
	 */
	private void setupNetworkProxy() throws NetworkNotFoundException {
		// �ж���������
		switch (DeviceUtils.checkNetWorkType(mContext)) {
		case NetworkType.NET:
			// NET����ʱ�Ƴ�����
			removeProxy();
			break;
		case NetworkType.CMWAP:
			// CMWAP(�ƶ�, ��ͨ)GPRS����, ���ô���
			setProxy(mCmwapProxy);
			break;
		case NetworkType.CTWAP:
			// CTWAP(����)GPRS����, ���ô���
			setProxy(mCtwapProxy);
			break;
		default:
			// �׳��������쳣
			throw new NetworkNotFoundException(mContext.getResources()
					.getString(R.string.no_network_connected));
		}
	}

	private URI createURI(String url) throws HttpRequestException {
		URI uri = null;
		try {
			if (!url.startsWith("http://")) {
				url = mServerAddress + url;
			}
			uri = new URI(url);
		} catch (URISyntaxException e) {
			// ����������ʧ��
			throw new HttpRequestException(mContext.getResources().getString(
					R.string.server_request_exception));
		}
		return uri;
	}

	private HttpUriRequest createUriRequest(URI uri,
			ArrayList<BasicNameValuePair> params, UploadFile uploadFile,
			String getOrPost) throws HttpRequestException {
		HttpUriRequest uriRequest = null;

		if (HttpPost.METHOD_NAME.equalsIgnoreCase(getOrPost)) {
			HttpPost post = new HttpPost(uri);
			post.getParams().setBooleanParameter(
					"http.protocol.expect-continue", false);
			try {
				HttpEntity requestEntity = null;
				// ���ļ��ϴ�POST����
				if (uploadFile != null && uploadFile.file != null
						&& uploadFile.file.exists()) {
					requestEntity = (HttpEntity) createMultipartEntity(params,
							uploadFile);
				}
				// ��ͨ�ı�POST����
				else if (params != null) {
					requestEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
				}
				post.setEntity(requestEntity);
			} catch (Exception e) {
				throw new HttpRequestException(e.getMessage());
			}
			uriRequest = post;
		} else {
			uriRequest = new HttpGet(uri);
		}

		return uriRequest;
	}

	private MultipartEntityBuilder createMultipartEntity(
			ArrayList<BasicNameValuePair> params, UploadFile uploadFile)
			throws UnsupportedEncodingException {
		MultipartEntityBuilder multipartEntity = MultipartEntityBuilder
				.create();
		multipartEntity.addPart(uploadFile.parameterName, new FileBody(
				uploadFile.file));
		if (params != null) {
			for (BasicNameValuePair param : params) {
				multipartEntity.addTextBody(
						param.getName(),
						param.getValue());
			}
		}
		return multipartEntity;
	}

	/**
	 * ����HTTP�����������
	 */
	private void setupHTTPConnectionParams(HttpUriRequest uriRequest) {
		HttpConnectionParams.setConnectionTimeout(uriRequest.getParams(),
				CONNECTION_TIMEOUT_MS);
		HttpConnectionParams.setSoTimeout(uriRequest.getParams(),
				SOCKET_TIMEOUT_MS);

		mHttpClient.setHttpRequestRetryHandler(mRequestRetryHandler);

		uriRequest.addHeader("Accept-Encoding", "gzip, deflate");
		uriRequest.addHeader("Accept-Charset", "UTF-8,*;q=0.5");
		uriRequest.addHeader("Cache-Control", "no-cache");

		addCookieToHeader(uriRequest);
	}

	// ���cookies
	private void addCookieToHeader(HttpUriRequest uriRequest) {
		if (!mCookies.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> cookie : mCookies.entrySet()) {
				sb.append(cookie.getKey()).append("=")
						.append(cookie.getValue()).append(";");
			}
			if (!StringUtils.isEmpty(sb.toString())) {
				uriRequest.addHeader("Cookie", sb.toString());
			}
		} else {
			uriRequest.removeHeaders("Cookie");
		}
	}

	private String encodeParams(ArrayList<BasicNameValuePair> params)
			throws IllegalArgsException {
		if (params == null) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			if (i != 0) {
				sb.append("&");
			}
			try {
				sb.append(URLEncoder.encode(params.get(i).getName(), "UTF-8"));
				sb.append("=");
				sb.append(URLEncoder.encode(params.get(i).getValue(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				throw new IllegalArgsException(e.getMessage(), e);
			}
		}

		return sb.toString();
	}

	/**
	 * �����쳣�Զ��ָ��������
	 */
	private static HttpRequestRetryHandler mRequestRetryHandler = new HttpRequestRetryHandler() {
		@Override
		public boolean retryRequest(IOException exception, int executionCount,
				HttpContext context) {
			DebugUtils.warn("!!! occured [" + exception
					+ "], retryRequest times: " + executionCount);
			// Auto retry X times
			if (executionCount > AUTO_RETRY_TIMES) {
				return false;
			}
			// Retry if the server dropped connection on us
			if (exception instanceof NoHttpResponseException) {
				return true;
			}
			// fixed "java.net.SocketException: Broken pipe" bug
			else if (exception instanceof SocketException) {
				return true;
			} else if (exception instanceof ClientProtocolException) {
				return true;
			}
			// Do not retry on SSL handshake exception
			else if (exception instanceof SSLHandshakeException) {
				return false;
			}

			return false;
		}
	};

	/**
	 * GZIPʵ���ѹ��ʵ����
	 */
	private static class GzipDecompressingEntity extends HttpEntityWrapper {

		public GzipDecompressingEntity(final HttpEntity entity) {
			super(entity);
		}

		@Override
		public InputStream getContent() throws IOException,
				IllegalStateException {
			InputStream wrappedInputStream = wrappedEntity.getContent();
			return new GZIPInputStream(wrappedInputStream);
		}

		@Override
		public long getContentLength() {
			return -1;
		}
	}

	public String getServerAddress() {
		return mServerAddress;
	}

	public HashMap<String, String> getCookies() {
		return mCookies;
	}

}
