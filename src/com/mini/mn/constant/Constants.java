package com.mini.mn.constant;

/**
 * �̶�����
 * 
 * @version 1.0.0
 * @date 2014-01-29
 * @author S.Kei.Cheueng
 */
public class Constants {
	// �̳߳ط���
	public static final int DEFAULT_CORE_POOL_SIZE = 5;
	public static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
	public static final int DEFAULT_KEEP_ALIVE = 1;
	
	// "֪��"Ĭ���ļ���
	public static final String DEFAULT_IMAGE_FOLDER_NAME = "Zhiyin";
	// ͨ�Ž�����
	public static byte  MESSAGE_SEPARATE='\b';
	//������Ĭ�ϴ�С
	public static final int DEFAULT_BUFFER_SIZE = 320;
	
	/** 30���ʱ */
	public static final int IDELTIMEOUT = 30;
	/** 15�뷢��һ�������� */
	public static final int HEARTBEATRATE = 15;
	/** ���������� */
	public static final byte HEARTBEATREQUEST = 0x10;
	public static final byte HEARTBEATRESPONSE = 0x11;
	
	/**
	 * ����������״̬
	 */
	public static class ReturnCode{
		
		public static final String CODE_404 = "404";
		
		public static final String CODE_403 = "403";
		
		public static final String CODE_405 = "405";
		
		public static final String CODE_200 = "200";
		
		public static final String CODE_206 = "206";
		
		public static final String CODE_500 = "500";
		
		public static final String CODE_USER_OFFLINE = "9992";//�û�������
		
		public static final String CODE_JSON_FORMAT_ERROR = "9993";//data json��ʽ����
		
		public static final String CODE_DEVICE_ID_IS_EMPTY = "9994";//����idΪ��
		
		public static final String CODE_CMD_NOT_VALID = "9995";//����Ƿ�
		
		public static final String CODE_MESSAGE_NOT_VALID = "9996";//���ݰ��Ƿ�
		
		public static final String CODE_USER_INVALID = "9997";//�û��Ƿ�
		
		public static final String CODE_USERID_EMPTY = "9998";//userId����Ϊ��״̬��
		
		public static final String CODE_IN_BLACKLIST = "9999";//������״̬��
		
		public static final String MSG_IN_BLACKLIST = "�û�id�ں�������";
	}
	
	public static int  DEFAULT_MESSAGE_ORDER=1;
	
	/**
	 * Ϊ ����˴����Ӧ��handlers
	 */
   public static class RequestKey{

		public static String CLIENT_AUTH ="client_auth";
		
		public static String CLIENT_HEARTBEAT="client_heartbeat";
		
		public static String CLIENT_LOGOUT ="client_logout";
		
		public static String CLIENT_DIY ="client_diy";
		
		public static String CLIENT_OFFLINE_MESSAGE ="client_get_offline_message";
   }
   
   /**
    * ����ָ��
    */
   public static class IMCmd{
	   
	   public static final String IM_LOGIN_CMD ="imLogin";	//im��¼����
	   
	   public static final String IM_LOGOUT_CMD ="imLogout";	//im�ǳ�����
	   
	   public static final String IM_AUTH_CMD ="imAuth";	//im��Ȩ����
	   
	   public static final String IM_TEXT_CMD ="imText";	//�����ı���Ϣ����
	   
	   public static final String IM_VOICE_CMD ="imVoice";	//����������Ϣ����
	   
	   public static final String IM_VIDEO_CMD ="imVideo";	//������Ƶ��Ϣ����
	   
	   public static final String IM_IMAGE_CMD ="imImage";	//����ͼƬ��Ϣ����
	   
	   public static final String IM_STATUS_CHANGE_CMD ="imStatusChange";	//�û�״̬�ı�����
	   
	   public static final String IM_RESPONSE_CMD ="imResponse";	//�����״̬�ظ��ظ�����
	   
	   public static final String IM_HEARTBEAT_CMD ="imHeartBeat";	//������������

   }
   // cookie key
   public static final String COOKIE_VALUE_KEY = "cookieValue";
   // �豸ID
   public static final String DEVICE_ID_KEY = "deviceId";
   // client
   public final static String FROM_CLIENT = "client";
   // server
   public final static String FROM_SERVER = "server";
   // ��������
   public final static String REQUEST_TYPE = "request";
   // �����ظ�
   public final static String RESPONSE_TYPE = "response";
   
   // ���������Ӷ˿�
   public static final int MESSAGE_SERVER_PORT = 58891;
   // ��������ַ
   public static final String MESSAGE_SERVER_HOST = "mobileapi.dingfanhe.com";
   
}
