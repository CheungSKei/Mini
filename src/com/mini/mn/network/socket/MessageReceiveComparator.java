package com.mini.mn.network.socket;

import java.util.Comparator;

import com.mini.mn.constant.Constants;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;

/**
 * 消息接收activity的接收顺序排序，CIM_RECEIVE_ORDER倒序
 * 
 * @version 1.0.0
 * @date 2014-2-13
 * @author S.Kei.Cheung
 */
public class MessageReceiveComparator  implements Comparator<IMessageListener_AIDL>{

	Context mcontext;
	public MessageReceiveComparator(Context ctx)
	{
		mcontext = ctx;
	}
	
	@Override
	public int compare(IMessageListener_AIDL arg1, IMessageListener_AIDL arg2) {
		 
		Integer order1  = Constants.DEFAULT_MESSAGE_ORDER;
		Integer order2  = Constants.DEFAULT_MESSAGE_ORDER;
		ActivityInfo info;
		if (arg1 instanceof Activity ) {
			
			try {
				 info = mcontext.getPackageManager() .getActivityInfo(((Activity)(arg1)).getComponentName(), PackageManager.GET_META_DATA);
				 if(info.metaData!=null)
				 {
					 order1 = info.metaData.getInt("CIM_RECEIVE_ORDER");
				 }
				 
		     } catch (Exception e) {}
		}
		
		if (arg1 instanceof Activity ) {
			try {
				 info = mcontext.getPackageManager() .getActivityInfo(((Activity)(arg2)).getComponentName(), PackageManager.GET_META_DATA);
				 if(info.metaData!=null)
				 {
					 order2 = info.metaData.getInt("CIM_RECEIVE_ORDER");
				 }
				 
		     } catch (Exception e) {}
		}
		
		return order2.compareTo(order1);
	}
	 

}
