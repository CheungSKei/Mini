package com.mini.mn.network.http;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mini.mn.R;
import com.mini.mn.app.MiniApplication;
import com.mini.mn.exception.HttpResponseException;
import com.mini.mn.util.DebugUtils;
import com.mini.mn.util.StreamUtils;
import com.mini.mn.util.StringUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * ��װ����������Ӧ���.
 * 
 * @version 1.0.0
 * @date 2012-9-12
 * @author DengZhaoyong
 */
public class Response {
	
	private HttpResponse mResponse = null;

	/**
	 * ����Responseʵ��.
	 * 
	 * @param response
	 */
	public Response(HttpResponse response) {
		this.mResponse = response;
	}
	
	/**
	 * ��������ת��Ϊ������.
	 * 
	 * @return
	 * @throws HttpResponseException
	 */
	public InputStream asStream() throws HttpResponseException {
		try {
			final HttpEntity entity = getHttpEntity();
			if (entity != null) {
				return entity.getContent();
			}
			return null;
		} catch (IllegalStateException e) {
			throw new HttpResponseException(e.getMessage());
		} catch (IOException e) {
			throw new HttpResponseException(e.getMessage());
		}
	}
	
	/**
	 * ��������ת��Ϊ�ַ���.
	 * 
	 * @return
	 * @throws HttpResponseException
	 */
	public String asString() throws HttpResponseException {
		InputStream is = null;
		try {
			is = asStream();
			String result = StreamUtils.convertStreamToString(is);
			if (is == null || StringUtils.isEmpty(result)) {
				throw new HttpResponseException(MiniApplication.getContext().getString(R.string.server_no_response));
			}
			return result;
		} catch (IllegalStateException e) {
			throw new HttpResponseException(e.getMessage());
		} finally {
			try {
				getHttpEntity().consumeContent();
			} catch (IOException e) {}
			
			StreamUtils.closeInputStream(is);
			mResponse = null;
		}
	}
	
	/**
	 * ��������ת��ΪJSONObject.
	 * 
	 * @return
	 * @throws HttpResponseException
	 */
	public JSONObject asJSONObject() throws HttpResponseException {
		String result = asString();
		try {
			return new JSONObject(result);
		} catch (JSONException jsonException) {
			DebugUtils.error("JSON��ʽ����: " + result, jsonException);
			throw new HttpResponseException(jsonException.getMessage());
		} finally {
			mResponse = null;
		}
	}
	
	/**
	 * ��������ת��ΪJSONArray.
	 * 
	 * @return
	 * @throws HttpResponseException
	 */
	public JSONArray asJSONArray() throws HttpResponseException {
		String result = asString();
		try {
			return new JSONArray(asString());
		} catch (JSONException jsonException) {
			DebugUtils.error("JSON��ʽ����: " + result, jsonException);
			throw new HttpResponseException(jsonException.getMessage());
		} finally {
			mResponse = null;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws HttpResponseException
	 */
	public Bitmap asBitmap() throws HttpResponseException {
		try {
			return BitmapFactory.decodeStream(asStream());
		} catch (HttpResponseException e) {
			throw e;
		} finally {
			try {
				getHttpEntity().consumeContent();
			} catch (IOException e) {}
			
			mResponse = null;
		}
	}
	
	/**
	 * ��ȡorg.apache.http.HttpEntity.
	 * 
	 * @return
	 * @throws HttpResponseException
	 */
	public HttpEntity getHttpEntity() throws HttpResponseException {
		try {
			HttpEntity entity = mResponse.getEntity();
			if (entity == null) {
				throw new HttpResponseException("��ȡ����ʧ��");
			}
			return entity;
		} catch (Exception e) {
			throw new HttpResponseException(e.getMessage());
		}
	}
	
}
