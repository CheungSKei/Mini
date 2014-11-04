package com.mini.mn.filter;

import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mini.mn.constant.Constants;
import com.mini.mn.model.AbstractRequest;
import com.mini.mn.model.AbstractResponse;
import com.mini.mn.model.HeartBeatRequest;
import com.mini.mn.model.HeartBeatResponse;

/**
 * �ͻ�����Ϣ�������.<br>
 * {@link CumulativeProtocolDecoder}
 * 
 * @version 1.0.0
 * @date 2014-2-12
 * @author S.Kei.Cheung
 */
public class ClientMessageDecoder extends CumulativeProtocolDecoder {

//	private final Charset charset = Charset.forName("UTF-8");
	private IoBuffer buff = IoBuffer.allocate(320).setAutoExpand(true);

	@Override
	public boolean doDecode(IoSession iosession, IoBuffer iobuffer,
			ProtocolDecoderOutput out) throws Exception {
		
//		// ����(1���ֽ�)
//		if(iobuffer.hasRemaining() && iobuffer.remaining()==1){
//			byte heartbeat = iobuffer.get();
//			out.write(heartbeat);
//			return true;
//		}
//		// ��������
//		else if(iobuffer.hasRemaining() && iobuffer.remaining()>=4){
//			iobuffer.mark();
//			int length = iobuffer.getInt();
//
//			if (length > iobuffer.remaining()) { // ��ǰbuffer�е����ݲ�����,����false,���෽���м����ȴ�����
//				iobuffer.reset();
//				System.out.println(" ��ǰbuffer�е����ݳ���:" + iobuffer.remaining()
//						+ ",�ڴ�����:" + length);
//				return false;
//			}
//
//			while (iobuffer.hasRemaining()) {
//				byte b = iobuffer.get();
//				if (b == '\b') {
//					break;
//				}
//				buff.put(b);
//			}
//
//			buff.flip();
//			byte[] bytes = new byte[buff.limit()];
//			buff.get(bytes);
//			String message = new String(bytes, charset);
//			buff.clear();
//
//			if (iobuffer.hasRemaining()) {
//				// ��ʼ��ȡ�ļ�
//				while (iobuffer.hasRemaining()) {
//					buff_files.put(iobuffer.get());
//				}
//				buff_files.flip();
//				// ����ļ�
//				JsonParser json = new JsonParser();
//				JsonElement je = json.parse(message);
//				JsonObject jsonobject = je.getAsJsonObject();
//				fileCounts = jsonobject.get("data").getAsJsonObject()
//						.get("filecount").getAsInt();
//				for (int i = 0; i < fileCounts; i++) {
//					int fileNameLength = buff_files.getInt();
//					byte[] fileNameBytes = new byte[fileNameLength];
//					buff_files.get(fileNameBytes);
//					String fileName = new String(fileNameBytes, charset);
//					System.out.println(" ��ǰ�ļ�������:" + fileNameLength + ",�ļ���:"
//							+ fileName);
//					int fileLength = buff_files.getInt();
//					System.out.println(" ��ǰ�ļ�����:" + fileLength);
//					byte[] pngfile = new byte[fileLength];
//					int k = 0;
//					while (buff_files.hasRemaining()) {
//						pngfile[k] = buff_files.get();
//						k++;
//						if (k == fileLength) {
//							k = 0;
//							break;
//						}
//					}
//					FileOutputStream fileOut = new FileOutputStream(new File(fileName));
//					fileOut.write(pngfile);
//					fileOut.flush();
//					fileOut.close();
//				}
//				buff_files.clear();
//			}
//			out.write(message);
//			return true;
//		}
//
//		return false;
		System.out.println("��ʼ�ͻ��˽���");
		boolean complete = false;
		while(iobuffer.hasRemaining()){
			byte b = iobuffer.get();
			if(Constants.MESSAGE_SEPARATE == b){
				complete = true;
				break;
			}else{
				buff.put(b);
			}
		}
		 
		if(complete){
			buff.flip();
			byte[] bytes = new byte[buff.limit()];
			buff.get(bytes);
			String receiveMessage = new String(bytes);
			buff.clear();
			
			System.out.println("�յ��������Ϣ��" + receiveMessage);
			
			Gson gson = new Gson();
			Map<String, Object> map = gson.fromJson(receiveMessage,  
	                new TypeToken<Map<String, Object>>() {  
	                }.getType());
			if(map != null){
				String cmd = map.get("cmd").toString();
				if(Constants.IMCmd.IM_RESPONSE_CMD.equals(cmd)){
					AbstractResponse response = new AbstractResponse();
					response = gson.fromJson(receiveMessage, AbstractResponse.class);
					out.write(response);
				}else if(Constants.IMCmd.IM_HEARTBEAT_CMD.equals(cmd)){
					if(Constants.REQUEST_TYPE.equals(map.get("type"))){
						HeartBeatRequest request = new HeartBeatRequest();
						request = gson.fromJson(receiveMessage, HeartBeatRequest.class);
						out.write(request);
					}else if(Constants.RESPONSE_TYPE.equals(map.get("type"))){
						HeartBeatResponse response = new HeartBeatResponse();
						response = gson.fromJson(receiveMessage, HeartBeatResponse.class);
						out.write(response);
					}
				}else{
					AbstractRequest request = new AbstractRequest();
					request = gson.fromJson(receiveMessage, AbstractRequest.class);
					out.write(request);
				}
			}
			
			System.out.println("��ɿͻ�����Ϣ����");
		}
		
		return complete;
	}
}
