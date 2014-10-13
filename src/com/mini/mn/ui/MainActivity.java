package com.mini.mn.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.mini.mn.R;
import com.mini.mn.model.AbstractRequest;
import com.mini.mn.model.AbstractResponse;
import com.mini.mn.network.socket.MessageConnectorManager;
import com.mini.mn.network.socket.OnMessageListener;
import com.mini.mn.task.socket.AsyncCallBack;
import com.mini.mn.task.socket.IMMsgSocketTaskImpl;
import com.mini.mn.task.socket.LoginSocketTaskImpl;
import com.mini.mn.task.socket.LogoutSocketTaskImpl;
import com.mini.mn.ui.base.ScrollTextView;

public class MainActivity extends ActionBarActivity implements OnMessageListener{

	private static final String TAG = "LoginSocket";
	
	private Button sendBtn;
	private Button loginBtn;
	private Button logoutBtn;
	private Button mNewPagebtn;
	private ScrollTextView msgContent;
	private EditText mSendContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		init();
		
		super.onCreate(savedInstanceState);
		System.setProperty("java.net.preferIPv6Addresses", "false");
		
		setContentView(R.layout.welcome_activity_layout);
		
		sendBtn = (Button) findViewById(R.id.sendBtn);
		sendBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new IMMsgSocketTaskImpl(new AsyncCallBack() {
					
					@Override
					public void onReplyReceived_OK(Object replyMessage) {
						Log.i(TAG, "onReplyReceived_OK");
					}
					
					@Override
					public void onReplyReceived_ERROR(Object replyMessage) {
						Log.i(TAG, "onReplyReceived_ERROR");
					}
					
					@Override
					public void onMessageSentSuccessful(Object message) {
						Log.i(TAG, "onMessageSentSuccessful");
					}
					
					@Override
					public void onMessageSentFailed(Exception e, Object message) {
						Log.i(TAG, "onMessageSentFailed");
					}
					
					@Override
					public void onMessageReceived(Object receivedMessage) {
						Log.i(TAG, "onMessageReceived");
					}
				}).commit(1003, 1000,"wenhsh",1001,"wenhsh",mSendContent.getText().toString(),mSendContent.getText().toString());
			}
		});
		
		loginBtn = (Button) findViewById(R.id.LoginIn);
		loginBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new LoginSocketTaskImpl(new AsyncCallBack() {
					
					@Override
					public void onReplyReceived_OK(Object replyMessage) {
						Log.i(TAG, "onReplyReceived_OK");
					}
					
					@Override
					public void onReplyReceived_ERROR(Object replyMessage) {
						Log.i(TAG, "onReplyReceived_ERROR");
					}
					
					@Override
					public void onMessageSentSuccessful(Object message) {
						Log.i(TAG, "onMessageSentSuccessful");
					}
					
					@Override
					public void onMessageSentFailed(Exception e, Object message) {
						Log.i(TAG, "onMessageSentFailed");
					}
					
					@Override
					public void onMessageReceived(Object receivedMessage) {
						Log.i(TAG, "onMessageReceived");
					}
				}).commit(1001, "wenhsh","123456","123456");
			}
		});
		
		logoutBtn = (Button) findViewById(R.id.LoginOut);
		logoutBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new LogoutSocketTaskImpl(new AsyncCallBack() {
					
					@Override
					public void onReplyReceived_OK(Object replyMessage) {
						Log.i(TAG, "onReplyReceived_OK");
					}
					
					@Override
					public void onReplyReceived_ERROR(Object replyMessage) {
						Log.i(TAG, "onReplyReceived_ERROR");
					}
					
					@Override
					public void onMessageSentSuccessful(Object message) {
						Log.i(TAG, "onMessageSentSuccessful");
					}
					
					@Override
					public void onMessageSentFailed(Exception e, Object message) {
						Log.i(TAG, "onMessageSentFailed");
					}
					
					@Override
					public void onMessageReceived(Object receivedMessage) {
						Log.i(TAG, "onMessageReceived");
					}
				}).commit(1002);
			}
		});
		
		mNewPagebtn = (Button) findViewById(R.id.newPageBtn);
		mNewPagebtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =  new Intent(MainActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
		
		msgContent = (ScrollTextView) findViewById(R.id.msgTxt);
		mSendContent = (EditText) findViewById(R.id.sendContent);
		MessageConnectorManager.getManager().registerMessageListener(this);
	}
	
	// 判断版本格式,如果版本 > 2.3,就是用相应的程序进行处理,以便影响访问网络
	@TargetApi(9)
	private static void init() {
		String strVer = android.os.Build.VERSION.RELEASE; // 获得当前系统版本
		strVer = strVer.substring(0, 3).trim(); // 截取前3个字符 2.3.3转换成2.3
		float fv = Float.valueOf(strVer);
		if (fv > 2.3) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork()
					.penaltyLog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
					.build());
		}
	}

	@Override
	public void onConnectionClosed() {
		msgContent.append("连接关闭\n");
	}

	@Override
	public void onConnectionSuccessful() {
		Log.i(TAG, "onConnectionSuccessful");
	}

	@Override
	public void onConnectionFailed(Exception e) {
		Log.i(TAG, "onConnectionFailed");
	}

	@Override
	public void onExceptionCaught(Throwable throwable) {
		Log.i(TAG, "onExceptionCaught");
	}

	@Override
	public void onMessageReceived(Object message) {
		msgContent.append(((AbstractRequest)message).toString()+"\n");
	}

	@Override
	public void onReplyReceived(Object reply) {
		msgContent.append(((AbstractResponse)reply).toString()+"\n");
	}

	@Override
	public void onSentSuccessful(Object message) {
		Log.i(TAG, "onSentSuccessful");
	}

	@Override
	public void onSentFailed(Exception e, Object message) {
		msgContent.append("发送失败\n");
	}

	@Override
	public void onNetworkChanged(NetworkInfo info) {
		
	}
	
	@Override
	protected void onDestroy() {
		MessageConnectorManager.getManager().removeMessageListener(this);
		super.onDestroy();
	}

}
