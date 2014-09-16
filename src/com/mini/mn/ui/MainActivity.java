package com.mini.mn.ui;

import java.util.HashMap;
import java.util.Map;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mini.mn.R;
import com.mini.mn.constant.Constants;
import com.mini.mn.network.socket.MessageConnectorManager;
import com.mini.mn.network.socket.MessageConnectorService;
import com.mini.mn.task.socket.AsyncCallBack;
import com.mini.mn.task.socket.MessageSocketTaskImpl;
import com.mini.mn.util.StringUtils;

public class MainActivity extends ActionBarActivity {

	private Button btn;
	private Button mLinkSocketbtn;
	private Button mNewPagebtn;
	private TextView msgContent;
	private EditText mSendContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		init();
		
		super.onCreate(savedInstanceState);
		System.setProperty("java.net.preferIPv6Addresses", "false");
		
		setContentView(R.layout.welcome_activity_layout);
		
		btn = (Button) findViewById(R.id.sendBtn);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!StringUtils.isEmpty(mSendContent.getText().toString())){
//					AbstractRequest内容
//					FileRequest文件内容
//					FileRequest fileRequest = new FileRequest();
//					Map<String,Object> mapData = new HashMap<String,Object>();
//					mapData.put("filecount", 1);
//					mapData.put("msg", mSendContent.getText().toString());
//					fileRequest.setCmd(Constants.IMCmd.IM_VOICE_CMD);
//					fileRequest.setData(mapData);
//					MessageConnectorManager.getManager().send(fileRequest);
					Map<String, Object> mapData = new HashMap<String, Object>();
					mapData.put("username", "wenhsh");//假设其对应的userId为1000
					mapData.put("password", "123456");
					mapData.put("confirmPassword", "123456");
					new MessageSocketTaskImpl(new AsyncCallBack() {
						
						@Override
						public void onReplyReceived_OK(Object replyMessage) {
							
						}
						
						@Override
						public void onReplyReceived_ERROR(Object replyMessage) {
							
						}
						
						@Override
						public void onMessageSentSuccessful(Object message) {
							
						}
						
						@Override
						public void onMessageSentFailed(Exception e, Object message) {
							
						}
						
						@Override
						public void onMessageReceived(Object receivedMessage) {
							
						}
					}).commit(1001, mapData);
					
				}
			}
		});
		
		mLinkSocketbtn = (Button) findViewById(R.id.linkSocket);
		mLinkSocketbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//连接服务端
				Intent serviceIntent  = new Intent(MainActivity.this, MessageConnectorService.class);
				serviceIntent.putExtra(MessageConnectorManager.MESSAGE_SERVIER_HOST, Constants.MESSAGE_SERVER_HOST);
				serviceIntent.putExtra(MessageConnectorManager.MESSAGE_SERVIER_PORT, Constants.MESSAGE_SERVER_PORT);
				startService(serviceIntent);
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
		
		msgContent = (TextView) findViewById(R.id.msgTxt);
		mSendContent = (EditText) findViewById(R.id.sendContent);
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

}
