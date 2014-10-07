package com.mini.mn.db.message;

import java.util.HashMap;
import java.util.Map;

import com.mini.mn.db.storage.ContentProviderDB;

import android.content.Context;
import android.net.Uri;

public class RMsgInfoDB extends ContentProviderDB<RMsgInfoDB> {

	// known tables
	private static final Map<String, Uri> SUPPORTS = new HashMap<String, Uri>();
	
	public static final String TABLE = "message";
	
	static {
		SUPPORTS.put(TABLE, Uri.parse("content://" + RMsgInfoStorage.AUTHORITY + "/" + TABLE));
	}

	public RMsgInfoDB(Context context) {
		super(context);
	}

	@Override
	public Uri getUriFromTable(String table) {
		return SUPPORTS.get(table);
	}
	
	
	

}
