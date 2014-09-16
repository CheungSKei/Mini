package com.mini.mn.filter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import android.os.Environment;

import com.mini.mn.constant.Constants;
import com.mini.mn.model.AbstractRequest;
import com.mini.mn.model.Entity;
import com.mini.mn.model.FileContent;
import com.mini.mn.model.FileRequest;

/**
 * �ͻ�����Ϣ�������.<br>
 * {@link ProtocolEncoderAdapter}
 * @version 1.0.0
 * @date 2014-2-12
 * @author S.Kei.Cheung
 */
public class ClientMessageEncoder extends ProtocolEncoderAdapter {

	private static final Charset CHARSET = Charset.forName("utf-8");

	@Override
	public void encode(IoSession session, Object message,ProtocolEncoderOutput out) throws Exception {
		IoBuffer buff = IoBuffer.allocate(Constants.DEFAULT_BUFFER_SIZE).setAutoExpand(true);
		if(message instanceof FileRequest){
			FileRequest fileRequest = (FileRequest)message;
			// ��ʼ����С:0
			buff.putInt(0);
		    buff.putString(((AbstractRequest)message).toString(),CHARSET.newEncoder());
			buff.put(Constants.MESSAGE_SEPARATE);
			List<FileContent> files = new ArrayList<FileContent>();
			// �ļ�1
			FileContent fileContent = new FileContent();
			File file1 = new File(Environment.getExternalStorageDirectory().getPath()+"/yuanlai_avatar.png");
			fileContent.setFileContent(file1);
			fileContent.setFileLength((int)file1.length());
			fileContent.setFileName(file1.getName());
			fileContent.setFileNameLength(file1.getName().length());
			files.add(fileContent);
			fileRequest.setFiles(files);
			// ����ļ�
			for(FileContent file : fileRequest.getFiles()){
				buff.putInt(file.getFileNameLength());
				buff.putString(file.getFileName(),CHARSET.newEncoder());
				buff.putInt(file.getFileLength());
				buff.put(inputStreamToByte(new FileInputStream(file.getFileContent())));
			}
			buff.putInt(0,buff.position()-4);
		}else if(message instanceof AbstractRequest){
			System.out.println("��ʼ�ͻ��˱���");
			System.out.println("�ͻ��˷��͵���Ϣ����Ϊ��" + ((AbstractRequest)message).toString());
			buff.putString(((AbstractRequest)message).toString(), CHARSET.newEncoder());
			buff.put(Constants.MESSAGE_SEPARATE);
		}else if(message instanceof Entity){
			System.out.println("������ʼ�ͻ��˱���");
			System.out.println("�������͵���Ϣ����Ϊ��" + ((Entity)message).toString());
			buff.putString(((Entity)message).toString(), CHARSET.newEncoder());
			buff.put(Constants.MESSAGE_SEPARATE);
		}else if(message instanceof Byte){	// ����һ���ֽ�
			buff.put(((Byte) message).byteValue());
		}else if(message instanceof String){
		    buff.putString((String)message, CHARSET.newEncoder());
		}
		buff.flip();
		out.write(buff);
		System.out.println("�����ͻ��˱���");
	}
	
	/**
	 * ת�����ֽ�����
	 * @param inStream	������
	 * @return �����ļ��ֽ�����
	 * @throws IOException
	 */
	private byte[] inputStreamToByte(InputStream inStream) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		int rc;
		while ((rc = inStream.read()) != -1) {
			  swapStream.write(rc);
		}
		byte[] in2b = swapStream.toByteArray();
		return in2b;
	}
}
