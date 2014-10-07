package com.mini.mn.platformtools;

import java.nio.ByteBuffer;


public class LVBuffer{
	
	public LVBuffer() {
	}
	
	public static final int LENGTH_ALLOC_PER_NEW = 4096;
	public static final int MAX_STRING_LENGTH = 2048;
	private static final byte BE = (byte)0x7b;
	private static final byte ED = (byte)0x7d;
	private int check(byte[] buf){
		if(buf == null || buf.length == 0){
			return -1;
		}
		if(buf[0]!= BE){
			return -2;
		}
		if(buf[buf.length -1] != ED){
			return -3;
		}
		return 0;
	}
	private ByteBuffer byteBuffer ;
	private boolean isBuildBuffer;
	
	public int initParse(byte [] buf) throws Exception{
		int ret = check(buf);
		if(ret != 0) {
			byteBuffer = null;
			//throw new Exception("Parse Buffer Check Failed :" + ret); 
			return -1;
		}
		byteBuffer = ByteBuffer.wrap(buf);
		byteBuffer.position(1);
		isBuildBuffer = false;
		return 0;
	}
	
	public int getInt() throws Exception{
		if(isBuildBuffer){
			throw new Exception("Buffer For Build");
		}
		return byteBuffer.getInt();
	}
	
	public long getLong() throws Exception{
		if(isBuildBuffer){
			throw new Exception("Buffer For Build");
		}
		return byteBuffer.getLong();
	}
	
	public String getString() throws Exception{
		if(isBuildBuffer){
			throw new Exception("Buffer For Build");
		}
		int len = byteBuffer.getShort();
		if(len > MAX_STRING_LENGTH){
			byteBuffer = null;
			throw new Exception("Buffer String Length Error");
		}
		if(len == 0){
			return "";
		}
		byte[] tmp = new byte[len];
		byteBuffer.get(tmp, 0, len);
		return new String(tmp);
	}
	
	public boolean checkGetFinish(){
		return (byteBuffer.limit() - byteBuffer.position() <= 1);
	}
	

	public int initBuild(){
		byteBuffer = ByteBuffer.allocate(LENGTH_ALLOC_PER_NEW);
		byteBuffer.put(BE);
		isBuildBuffer = true;
		return 0;
	}
	
	private int checkBuffer(int size){
		if(byteBuffer.limit() - byteBuffer.position() > size ){
			return 0;
		}
		ByteBuffer n = ByteBuffer.allocate(byteBuffer.limit() + LENGTH_ALLOC_PER_NEW);
		n.put(byteBuffer.array(), 0, byteBuffer.position());
		byteBuffer = n;
		return 0;
	}
	
	public int putInt(int value) throws Exception{
		if(!isBuildBuffer){
			throw new Exception("Buffer For Parse");
		}
		checkBuffer(4);
		byteBuffer.putInt(value);
		return 0;
	}
	
	public int putLong(long value) throws Exception{
		if(!isBuildBuffer){
			throw new Exception("Buffer For Parse");
		}
		checkBuffer(8);
		byteBuffer.putLong(value);
		return 0;
	}
	
	public int putString(String value) throws Exception{
		if(!isBuildBuffer){
			throw new Exception("Buffer For Parse");
		}
		byte []tmp = null;
		if( value != null ){
			tmp =  value.getBytes();
		}
		if(tmp == null){
			tmp = new byte[0];
		}
		if(tmp.length > MAX_STRING_LENGTH){
			throw new Exception("Buffer String Length Error");
		}
		
		checkBuffer(tmp.length + 2);
		byteBuffer.putShort((short)(tmp.length));
		if(tmp.length > 0){	
			byteBuffer.put(tmp);	
		}
		return 0;
	}
	
	public byte [] buildFinish() throws Exception{
		if(!isBuildBuffer){
			throw new Exception("Buffer For Parse");
		}
		checkBuffer(1);
		byteBuffer.put(ED);
		byte [] ret = new byte[byteBuffer.position()];
		System.arraycopy(byteBuffer.array(), 0, ret, 0, ret.length);
		return ret;
	}
}