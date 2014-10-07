package com.mini.mn.db.storage;

import android.os.Environment;

public final class ConstantsStorage {

	private ConstantsStorage() {

	}

	// --------------------------------------------------------------------------------------------------
	/**
	 * Unique Configuration Keys
	 */
	// SYSTEM CONFIGURE
	public static final int DEVICE_ID = 0x100; // 256
	public static final int DEVICE_TYPE = 0x101; // 257
	public static final int DEVICE_IMEI = 0x102; // 258
	public static final int DEFAULT_UIN = 1;
	public static final int BUILTIN_SHORT_IPS = 2;
	public static final int BUILTIN_LONG_IPS = 3;
	public static final int BUILTIN_IP_SEQ = 4;
	// public static final int LAST_USERNAME = 5;
	public static final int NETWORK_CONTROL_PORTS = 6;
	public static final int NETWORK_CONTROL_TIMEOUTS = 7;
	public static final int PACK_RECOMMENED_UPDATE_IGNORE = 16;
	public static final int CORE_HOLD = 17; // used for multi-login
	public static final int LAST_KSID = 18;
	public static final int FB_CONNECT_COUNT_SUC = 19; // ���fb connect���е�¼����fb������¼�ɹ��Ĵ������ȼ�¼�ţ��ȵ�¼��ȥ���ϱ�
	public static final int FB_CONNECT_COUNT_FAIL = 20; // ���fb connect���е�¼����fb������¼ʧ�ܵĴ������ȼ�¼�ţ��ȵ�¼��ȥ���ϱ�
	// public static final int VOIP_DEVICE_INFO = 21;//�ӷ�������ȡ�ı���voipӲ��������Ϣ
	// public static final int VOIP_EXT_DEVICE_INFO = 22;//�ӷ�������ȡ�ı���voipӲ��������Ϣ
	public static final int LAST_BLUETOOTH_CONNECTED = 23;

	public static final int SHORT_WEIXIN_HOST_PCSUBSTITUTE = 24;
	public static final int LONG_WEIXIN_HOST_PCSUBSTITUTE = 25;

	public static final int HTC_ACCESSORY_CONNECTED = 26;
	public static final int MEDIARECORDER_NOT_SUPPORT_16K = 27;
	
	
// give up	
//	public static final int RSA_PUBLIC_KEYE = 28;
//	public static final int RSA_PUBLIC_KEYN = 29;
//	public static final int RSA_PUBLIC_KEY_VER = 30;
	

	
	public static final int DELETE_SHAKE_TRAN_IMG_FILE_IGNORE = 31;
	
	public static final int NEW_AUTOAUTH_TICKET = 32;
	
	public static final int NOOP_INTERVAL_MAX = 33;
	public static final int NOOP_INTERVAL_MIN = 34;
			
	//authTicket
	public static final String 	TICKET_SP_SUFFIX = "_auth_ticket";		
	public static final String AUTO_AUTH_TICKET_PREFS = "ticket_prefs";

	
	// long.weixin.qq.com 80/8080
	// net 112.64.200.218
	// tel 180.153.82.30
	// cmcc 117.135.130.187
	public static final String HARDCODE_BUILTIN_LONGS = "0,112.64.200.218,80|0,180.153.82.30,80|0,117.135.130.187,80";

	// weixin.qq.com 80
	// net 112.64.200.240
	// tel 180.153.82.27
	// cmcc 117.135.130.177
	public static final String HARDCODE_BUILTIN_SHORTS = "0,112.64.200.240,80|0,180.153.82.27,80|0,117.135.130.177,80";

	// -----------------------------BEGIN OF USERINFO (CONFIG STORAGE) -----------------------------//
	// -----------------------------BEGIN OF USERINFO (CONFIG STORAGE) -----------------------------//
	// -----------------------------BEGIN OF USERINFO (CONFIG STORAGE) -----------------------------//
	// USER CONFIGURE
	public static final int USERINFO_SESSIONKEY = 1;
	public static final int USERINFO_USERNAME = 2;
	public static final int USERINFO_PASSWORD = 3;
	public static final int USERINFO_NICKNAME = 4;
	public static final int USERINFO_BINDEMAIL = 5;
	public static final int USERINFO_BINDMOBILE = 6;
	public static final int USERINFO_STATUS = 7;
	// public static final int USERINFO_SYNCKEY = 8;
	public static final int USERINFO_BINDUIN = 9;
	// public static final int USERINFO_UPLOAD_FLOW = 10;
	// public static final int USERINFO_DOWNLOAD_FLOW = 11;
	// public static final int USERINFO_INITKEY = 12;
	public static final int USERINFO_INITBUFFER = 13;
	public static final int USERINFO_CLIENTVERSION = 14;
	public static final int USERINFO_INITDONE = 15;
	public static final int USERINFO_NEWUSER = 16;
	public static final int USERINFO_QQMAILSWITCH = 17;
	public static final int USERINFO_DEFAULT_CHATMODE = 18;
	public static final int USERINFO_PASSWORD2 = 19;
	public static final int USERINFO_DEBUG_SWITCH = 20; // DEBUG
	public static final int USERINFO_OFFICIAL_USER = 21; // OFFICIAL USER
	public static final int USERINFO_OFFICIAL_NICK = 22;
	public static final int USERINFO_OFFICIAL_WEIBO_USER = 23;
	public static final int USERINFO_OFFICIAL_WEIBO_NICK = 24;
	public static final int USERINFO_SENDCARD_BITFLAG = 25;
	public static final int USERINFO_IS_FORCE_SPEAK_OFF = 26;

	// public static final int USERINFO_IS_NEW_VERSON_TAB = 27;//deprecated from 3.6
	// public static final int USERINFO_IS_NEW_VERSON_FRD = 28; // deprecated from 3.0
	public static final int USERINFO_FSURL = 29;
	public static final int USERINFO_UPDATE_NOTIFY_TAB = 30;
	public static final int USERINFO_UPDATE_NOTIFY_SETTING = 31;
	public static final int USERINFO_EXTPASSWORD = 32;
	public static final int USERINFO_EXTPASSWORD2 = 33;
	public static final int USERINFO_PLUGIN_FLAG = 34;
	public static final int USERINFO_DISPLAY_PLUGIN = 35;
	// public static final int USERINFO_IS_NEW_VERSON_SHAKE = 36; // deprecated from 3.5
	// public static final int USERINFO_IS_NEW_VERSON_BOTTLE = 37; // deprecated from 3.5
	public static final int USERINFO_IS_NEW_VERSON_SYSTEMNOTIFY = 38;
	public static final int USERINFO_IS_NEW_VERSON_SETTINGTAB = 39;
	public static final int USERINFO_PLUGINSWITCH = 40;
	public static final int USERINFO_WEIBO_FLAG = 41;
	public static final int USERINFO_ALIAS = 42; // ΢�ű���
	public static final int USERINFO_WEIBONICKNAME = 43;
	// public static final int USERINFO_IS_NEW_VERSON_QRCODE = 44; // deprecated from 3.6
	// public static final int USERINFO_SEND_RAW_IMAGE = 45;
	public static final int USERINFO_A2KEY = 46;
	public static final int USERINFO_KSID = 47;
	public static final int USERINFO_IS_NEW_VERSON_FRIEND_DINAIMC = 48;
	public static final int COPY_TO_RCONTACT = 49;
	public static final int COPY_TO_RCONVERSATION = 50;
	public static final int COPY_TO_RBOTTLECONVERSATION = 51;

	public static final int REG_INFO_REG_TYPE = 52;
	// public static final int REG_INFO_ALREADY_SET_PASSWD = 53; //�Ѿ��� queryhaspwd ���
	public static final int USERINFO_IS_NEW_VERSON_VOIP = 54;
	public static final int USERINFO_ABOUT_FUNTION_NEW = 55;
	public static final int USERINFO_ABOUT_NEW_VERSON = 56;
	public static final int USERINFO_NEED_SET_EMAIL_PASSWD = 57;
	public static final int USERINFO_LAST_NOTIFY_MSG_SVR_ID = 58;
	public static final int USERINFO_HAS_HEADIMG = 59;
	public static final int USERINFO_HAS_HEADIMG_BOTTLE = 60;
	public static final int USERINFO_HIDE_WAP_ADVISE = 61; // added in 4.3 patch2 for wap adviser
	public static final int USERINFO_IS_NEW_VERSON_VOIPAUDIO = 62; // added in 4.5
	public static final int COPY_TO_CHATROOM = 63; // added in 4.5
	public static final int USERINFO_SAFE_DEVICE_STATE = 64; // added in 4.5 for safe device status
	public static final int USERINFO_HIDE_WAP_ADVISE_EXIT_UI = 65; // added in 4.3 patch2 for wap adviser
	public static final int USERINFO_IS_NEW_VERSON_TALKROOM = 67;
	public static final int USERINFO_HIDE_BG_WIFI_LIMITED_ADVISE = 68; // added in 4.3 patch2 for wap adviser
	public static final int USERINFO_HAS_HTC_ACCESSORRY = 69; // has htc lite
	
	public static final int USERINFO_FORCE_ONCE_MANUAL_AUTH = 0x0100; // added in 4.3 for device id update

	public static final int USERINFO_BINDMOBILE_WAIT_CHECK = 0x1001;
	public static final int USERINFO_SYNCKEY_FORFRIEND = 0x1002;
	public static final int USERINFO_LAST_SYNC_FRIEND_TIME = 0x1003;
	public static final int USERINFO_LAST_SYNC_ADDRBOOK_TIME = 0x1004;
	public static final int USERINFO_VOLUMN_PANEL_SHOWED = 0x1005;
	public static final int USERINFO_MEDIA_PLAY_SPERKER_ON = 0x1006;
	public static final int USERINFO_IS_NEAR_FRIEND_INTRO_SHOWED = 0x1007;
	public static final int USERINFO_SHOW_LBS_OPEN_DIALOG = 0x1008;
	public static final int USERINFO_IGNORED_LBS_LOCATE_FAILED_DIALOG = 0x1009;

	public static final int USERINFO_SHAKE_TIMES = 0x100A;
	public static final int USERINFO_SHAKE_DEFAULT_IMGID = 0x100B; // ��¼���ǵ�ǰĬ�ϵ�shake����ͼƬid
	public static final int USERINFO_SHAKE_HAS_VIEW_INFO = 0x100C;
	public static final int USERINFO_SHAKE_IMG_TOTALLEN = 0x100D;
	public static final int USERINFO_SHAKE_IS_DEFAULT_IMG = 0x100E; // shake����ͼƬ�Ƿ���Ĭ�ϵ�
	public static final int USERINFO_SHAKE_CUSTOM_IMG_PATH = 0x100F; // �û��Զ����shake����ͼƬ·��
	public static final int USERINFO_SHAKE_VOICE = 0x1010;
	public static final int USERINFO_SHAKE_HAD_SHOW_MUTE_TIPS = 0x1015;
	// public static final int USERINFO_SHAKE_LAST_STATS_INFO = 0x1010; // ���ֻ��������������ռ�ͳ��
	// public static final int USERINFO_SHAKE_ALGOTITHM_VERSION = 0x1011; // �ֻ�ҡ���㷨�汾
	public static final int USERINFO_SYNC_ADDRBOOK_NOW = 0x1012;

	public static final int USERINFO_FIRST_TIME_USE_VOICE = 0x1013; // ��ʷԭ��
	public static final int USERINFO_LAST_UPDATE_ART_EMOJI_TIME = 0x1014; // ��ʷԭ��

	public static final int USERINFO_PUSHSYSTEMMSG = 0x2001;
	public static final int USERINFO_PUSHSYSTEMMSG_DOWNTIME = 0x2101;
	public static final int USERINFO_PUSHMSG_BINDMOBILE_READ = 0x2002;
	public static final int USERINFO_KEYBUF = 0x2003;
	public static final int USERINFO_CONTINUEFLAG = 0x2004;
	public static final int USERINFO_NEWINIT_KEY = 0x2005;
	public static final int USERINFO_MAX_NEWINIT_KEY = 0x2006;
	// public static final int USERINFO_PUSHMAIL_NIGHT_NOTIFY = 0x2007;
	public static final int USERINFO_PLUGIN_NIGHT_NOTIFY = 0x2008;
	public static final int USERINFO_PLUGIN_NIGHT_NOTIFY_START = 0x2009;
	public static final int USERINFO_PLUGIN_NIGHT_NOTIFY_END = 0x2010;
	// public static final int USERINFO_APP_LANGUAGE = 0x2011;
	public static final int USERINFO_LBS_LOCATION_OUTDATE_TIME = 0x2012;
	public static final int USERINFO_UPLOAD_SCENETIME = 0x2013;
	// public static final int USERINFO_SHOWWEIBOICON = 0x2014; // display weibo icon in addressui
	// public static final int USERINFO_LAST_SHOWTIME = 0x2015; // last showtime in seconds long
	// public static final int USERINFO_SHOWTIME = 0x2016; // show time count during report period
	// /
	public static final int USERINFO_STAT_REPORT_COMBINE = 0x2017;

	public static final int USERINFO_PERSONALCARD = 0x3001;
	public static final int USERINFO_SEX = 0x3002;
	public static final int USERINFO_SIGNATURE = 0x3003;
	public static final int USERINFO_CITY = 0x3004;
	public static final int USERINFO_PROVINCE = 0x3005;
	public static final int USERINFO_TMESSAGE_LAST_READ_TIME = 0x3006;
	public static final int USERINFO_QMESSAGE_LAST_READ_TIME = 0x3007;
	public static final int USERINFO_FIRST_TIME_USE_SLIDE = 0x3008;
	public static final int USERINFO_IMGMD5SUM = 0x3009;
	public static final int USERINFO_IMGMD5SUM_BOTTLE = 0x3109;
	public static final int USERINFO_SYSNOTIFY_NEWCOUNT = 0x3010;
	public static final int USERINFO_SYSNOTIFY_VERSION = 0x3011;
	public static final int USERINFO_BOTTLE_MESSAGE_LAST_READ_TIME = 0x3012;
	public static final int USERINFO_WEIBO_URL = 0x3013;
	public static final int USERINFO_SOFTWARE_URL = 0x3014;
	// public static final int USERINFO_SHARE_WEIBO = 0x3015;

	public static final int USERINFO_PLUGIN_SWITCH = 0x3016;
	public static final int USERINFO_DEFAULT_CHATTING_BG_ID = 0x3017;
	// public static final int USERINFO_DEFAULT_CHATTING_BG_PATH = 0x3018;
	public static final int USERINFO_DEFAULT_CHATTING_GROUPTIP_COUNT = 0x3019;

	public static final int USERINFO_GROUPCARD_GIVEUP = 0x3019; // �Ƿ���Ҫ��GoupCard ת����Chatroom
	public static final int USERINFO_TIP_BIND_QQ = 0x3020; // �Ƿ���ʾbind qq
	public static final int USERINFO_UPLOAD_ADDR_LOGIN = 0x3021; // ��¼��ʾ�Ƿ��ϴ�ͨѶ¼
	public static final int USERINFO_UPLOAD_ADDR_LOOK_UP = 0x3022; // �鿴ͨѶ¼������ʾ�Ƿ��ϴ�ͨѶ¼
	public static final int USERINFO_UPLOAD_ADDR_LOGIN_CONFIRM = 0x3023; // �״ε�½����ʾ�Ƿ��ϴ�ͨѶ¼

	public static final int USERINFO_COUNTRY_CODE = 0x3024;
	public static final int USERINFO_PROVINCE_CODE = 0x3025;
	public static final int USERINFO_CITY_CODE = 0x3026;

	public static final int USERINFO_READER_APP_FONT = 0x4000; // in 4.0 ��Ѷ�����Ķ���������
	public static final int USERINFO_PROCESS_LIMITED_SHOW = 0x4001; // in 4.0 ��̨��Ϣ������ʾ
	public static final int NEAR_BY_FRIEND_SEARCH_TYPE = 0x4002; // in 4.0 ����������������
	public static final int USERINFO_ENABLE_AUTO_PLAY = 0x4003; // �����Զ�����

	// voip plugin
	public static final int USERINFO_VOIP_NOT_WIFI_DELAY = 0x5000;
	// plugin independent config
	public static final int USERINFO_PLUGIN_CONFIG_BASE = 0x10000; // 0x20 for each plugin

	// qqsync
	public static final int USERINFO_PLUGIN_QQSYNC_REMIND_SYNCING = 0x10100;
	public static final int USERINFO_PLUGIN_QQSYNC_LAST_SYNC_TIME = 0x10101;
	public static final int USERINFO_PLUGIN_QQSYNC_LAST_REMIND_TIME = 0x10102;
	// public static final int USERINFO_PLUGIN_QQSYNC_ENABLED = 0x10103;
	public static final int USERINFO_PLUGIN_QQSYNC_FIRST_TIME_SYNC_DATA = 0x10104;
	public static final int USERINFO_PLUGIN_QQSYNC_FIRST_TIME_ENTER = 0x10105;
	// public static final int USERINFO_PLUGIN_QQSYNC_TOKEN = 0x10106; // do not use
	// public static final int USERINFO_QQ_FRIST_SEND_COUNT = 0x10107; // ��������QQ������Ϣ(chattingUI�б�Ϊ��ʱ)
	// public static final int USERINFO_QQ_FRIST_SEND_TIME = 0x10108; // ��������QQ����ͳ�Ƶ�ʱ��

	// facebook plugin
	public static final int USERINFO_FACEBOOK_USERID = 0x10121;
	public static final int USERINFO_FACEBOOK_USERNAME = 0x10122;
	public static final int USERINFO_FACEBOOK_FLAG = 0x10123;
	public static final int USERINFO_FACEBOOK_MD5 = 0x10124;
	public static final int USERINFO_FACEBOOK_NOT_FRISTTIME = 0x10125;
	public static final int USERINFO_FACEBOOK_TOKEN = 0x10126;
	public static final int USERINFO_FACEBOOK_LAST_REFRESH_TOKEN_TIME = 0x10127; // ��һ�γɹ�����facebook token��ʱ��
	public static final int USERINFO_FACEBOOK_EXPIRES = 0x10128;
	public static final int USERINFO_FACEBOOK_FIRST_LOGIN = 0x10129; // �Ƿ���ʹ��facebook�ʺŵ�һ�ε�¼

	public static final int USERINFO_VERIFY_FLAG = 0x10201;
	public static final int USERINFO_VERIFY_INFO = 0x10202;
	public static final int USERINFO_VERIFY_LAST_TIME = 0x10203;
	public static final int USERINFO_VERIFY_ALL_FILE_ID = 0x10204;
	public static final int USERINFO_VERIFY_VERSION = 0x10205;

	// crashUpload ��������
	public static final int USERINFO_CRASH_UPLOAD_TIME = 0x10301;
	// public static final int USERINFO_CRASH_UPLOAD_NUMBER = 0x10302;

	public static final int USERINFO_QRCODE_NOW_STYLE = 0x10401;
	public static final int USERINFO_QRCODE_FRIST_IN = 0x10402;

	public static final String DEFAULT_OFFICIAL_USER = "weixin";
	// public static final String DEFAULT_OFFICIAL_NICK = "΢���Ŷ�"; in string.xml
	public static final String DEFAULT_OFFICIAL_WEIBO_NICK = "weibo";
	// public static final String DEFAULT_OFFICIAL_WEIBO_NICK = "��ͼƬ��΢��"; in string.xml

	public static final int USERINFO_MEDIANOTE_LAST_STAT_TIME = 0x10501; // ��һ��medianoteͳ���ϱ���ʱ��
	public static final int USERINFO_CHATTING_BG_LAST_STAT_TIME = 0x10502; // ��һ�α���ͳ���ϱ���ʱ��
	public static final int USERINFO_FMESSAGE_CARD_LAST_STAT_TIME = 0x10503; // ��һ��Fmessage Cardͳ���ϱ�ʱ��
	public static final int USERINFO_LAST_SET_CHATTING_BG = 0x10504; // ���һ�����õ����챳��
	public static final int USERINFO_LAST_NETSTATWATCHDOG_UPLOAD_CHECKPOINT = 0x10505; // ����״̬�ϱ����(���������ϱ�)
	public static final int USERINFO_APPMSG_LAST_STAT_TIME = 0x10506; // ���һ���ϱ�appmsg kvstat��ʱ��

	public static final int USERINFO_MEDIANOTE_STAT_TEXT_COUNT = 0x10601; // �ϴ�medianoteͳ�Ƶ�text���͵���Ϣ����
	public static final int USERINFO_MEDIANOTE_STAT_IMG_COUNT = 0x10602; // �ϴ�medianoteͳ�Ƶ�img���͵���Ϣ����
	public static final int USERINFO_MEDIANOTE_STAT_VOICE_COUNT = 0x10603; // �ϴ�medianoteͳ�Ƶ�voice���͵���Ϣ����
	public static final int USERINFO_MEDIANOTE_STAT_VIDEO_COUNT = 0x10604; // �ϴ�medianoteͳ�Ƶ�video���͵���Ϣ����

	public static final int USERINFO_ALBUM_MD5 = 0x10701; // ��¼�����ȡ��MD5
	public static final int USERINFO_ALBUM_HEAD = 0x10702; // ���������� ����

	// public static final int USERINFO_LOCATION_OPEN_GOOGLE = 0x10801;
	// public static final int USERINFO_LOCATION_OPEN_OTHERS = 0x10802;
	// public static final int USERINFO_LOCATION_TIME = 0x10803;
	public static final int USERINFO_IS_CAN_GOOGLEMAP = 0x10804;

	public static final int USERINFO_UPDATE_REMARKNAME = 0x10901;

	public static final int USERINFO_LAST_REPORT_EGG = 0x10A01; // ���һ���ϱ��ʵ�ʱ��
	public static final int USERINFO_STAT_SEND_EGG_TRAIN_COUNT = 0x10A02; // ���ʵ�--�� ����
	public static final int USERINFO_STAT_RECV_EGG_TRAIN_COUNT = 0x10A03; // �ղʵ�--�� ����

	public static final int USERINFO_STAT_SEND_EGG_PEACH_COUNT = 0x10A04; // ���ʵ�--�һ� ����
	public static final int USERINFO_STAT_RECV_EGG_PEACH_COUNT = 0x10A05; // �ղʵ�--�һ� ����

	public static final int USERINFO_STAT_SEND_EGG_MONEY_COUNT = 0x10A06; // ���ʵ�--Ǯ ����
	public static final int USERINFO_STAT_RECV_EGG_MONEY_COUNT = 0x10A07; // �ղʵ�--Ǯ ����

	public static final int USERINFO_STAT_SEND_EGG_FISH_COUNT = 0x10A08; // ���ʵ�--�� ����
	public static final int USERINFO_STAT_RECV_EGG_FISH_COUNT = 0x10A09; // �ղʵ�--�� ����

	public static final int USERINFO_STAT_SEND_EGG_BIRTHDAY_COUNT = 0x10A0A; // ���ʵ�--���տ��� ����
	public static final int USERINFO_STAT_RECV_EGG_BIRTHDAY_COUNT = 0x10A0B; // �ղʵ�--���տ��� ����

	public static final int USERINFO_LAST_GET_EGG_LIST = 0x10A0C; // ���һ���Ϸ�������ȡ�ʵ����õ�ʱ��

	public static final String EGG_CONFIG_FILE = "eggingfo.ini";
	public static final String EGG_REPORT_FILE = "eggresult.rep";

	public static final int USERINFO_ALBUM_SYNC_WEIBO = 0x10B01; // ΢��ͬ��
	public static final int USERINFO_ALBUM_BG_IMG = 0x10B10; // ����1
	public static final int USERINFO_ALBUM_STYLE = 0x10B11;
	public static final int USERINFO_ALBUM_FLAG = 0x10B12;
	public static final int USERINFO_ALBUM_BG_IMG2 = 0x10B13; // ����2

	//
	// public static final int USERINFO_SNS_SYNC_WEIBO = 0x10B14; // ΢��ͬ��
	// public static final int USERINFO_SNS_BG_IMG = 0x10B15; // ����1
	// public static final int USERINFO_SNS_STYLE = 0x10B16;
	// public static final int USERINFO_SNS_FLAG = 0x10B17;
	// public static final int USERINFO_SNS_BG_IMG2 = 0x10B18; // ����2
	public static final int USERINFO_SNSYNC_ACTION_COUNT = 0x10B18; // ����Ȧ��δ��̬����
	public static final int USERINFO_SNSSYNC_OBJECT_USERNAME = 0x10B19; // ����Ȧ���¶�̬
	public static final int USERINFO_SNSYNC_NOTIFY_OPEN = 0x10B20; // ����ȦNotify
	public static final int USERINFO_SNSWELCOME_DIALOG_CLOSE = 0x10B21; // ����ҳ�رմ���
	public static final int USERINFO_SNSWELCOME_DIALOG_KNOW = 0x10B22; // ����ҵIknow����
	public static final int USERINFO_SNS_LAST_PEOPLE = 0x10B23; // �ϴ�With����
	public static final int USERINFO_SNS_TL_INTO_SELFPAGE = 0x10B24; // ��timeline����鿴homepage
	public static final int USERINFO_SNS_SETTING_INTO_SELFPAGE = 0x10B25;// ������ҳ����鿴homepage
	public static final int USERINFO_SNS_LASTREPORT_INTO_SELFPAGE = 0x10B26;// SNS���ͳ�Ƶ�ʱ��
	public static final int USERINFO_SNS_CDNLOAD_BIGBM = 0x10B27; // cdn���ش�ͼ����
	public static final int USERINFO_SNS_TAKE_PICTURE = 0x10B28; // ����ͳ��
	public static final int USERINFO_SNS_SELECT_PICTURE = 0x10B29; // ѡͼͳ��
	public static final int USERINFO_SNSSYNC_OBJECT_CREATETIME = 0x10B30; // ����Ȧ���¶�̬��¼ʱ��

	/** ��һ���ϱ��������ֲ�������ʹ�ô�����ʱ�� */
	public static final int USERINFO_INNER_MUSIC_PLAY_COUNT_LAST_TIME = 0x10B31;
	/** ��Ҫ�ϱ���timeline������������ֲ�������ʹ�ô��� */
	public static final int USERINFO_INNER_MUSIC_PLAY_TIMELINE = 0x10B32;
	/** ��Ҫ�ϱ���chattingUI������������ֲ�������ʹ�ô��� */
	public static final int USERINFO_INNER_MUSIC_PLAY_CHATTING_UI = 0x10b33;
	/** ��Ҫ�ϱ���ֹͣ���ֲ������ļ����� */
	public static final int USERINFO_INNER_MUSIC_STOP = 0X10B35;
	/** ��һ���ϱ��������ֲ�������ֹͣ������ʱ�� */
	public static final int USERINFO_INNER_MUSIC_STOP_COUNT_LAST_TIME = 0X10B36;

	public static final int USERINFO_SNS_SYNCFLAGS = 0x10B34; // ����Ȧ syncflag ����

	public static final int USERINFO_SNS_COMMENT_LOG = 0X10B38;// ��ס��һ�εĻ�
	public static final int USERINFO_SNS_LAST_TAG = 0X10B39;// ��ס��һ��tag

	public static final int USERINFO_BAKCHAT_RECOVER = 0x10B40;

	public static final int USERINFO_SNS_LAST_TAG2 = 0X10B41;// ��ס��һ��tag update to tag id list
	// open api
	public static final int USERINFO_RECOMMEND_APP_LIST_TOTAL_COUNT = 0x10C01;

	// app msg
	// public static final int USERINFO_APPMSG_SWITCH_COUNT = 0x10D01; // ͳ��΢�Ž��������app������Ϣ������
	// public static final int USERINFO_APPMSG_2_WEIXIN_COUNT = 0x10D02; // ͳ�Ƶ�����app���͵�΢�ŵ���Ϣ����

	// app new icon
	public static final int USERINFO_APP_NEW_ICON = 0x10E01; // ���ڱ���ĸ�appid��Ӧ�����newͼ��
	public static final int USERINFO_APP_VOIP_NEW_ICON = 0x10E02; // ���ڱ���ĸ�appid��Ӧ�����newͼ��

	public static final int USERINFO_VOIP_SOUND_NOTIFYCATION = 0x11E01;

	public static final int USERINFO_VOIPAUDIO_SOUND_NOTIFYCATION = 0x11E02;

	/** for twitter **/
	public static final int USERINFO_TWITTER_ACCESSTOKEN = 0x10F01; // access token
	public static final int USERINFO_TWITTER_ACCESSTOKENSECRET = 0x10F02; // access token secret

	// ConstantsStorage.TXNEWS_BEGIN 868518888
	public static final int USERINFO_TXNEWSCATEGORY = 868518889;
	public static final int USERINFO_TXNEWSCATEGORY_IN = 868518890;

	// ConstantsStorage.SMILEY_BEGIN -29414085
	public static final int USERINFO_LAST_SMILEY_TYPE = -29414084;
	public static final int USERINFO_LAST_SMILEY_PAGE = -29414083;

	// ConstantsStorage.QQMAIL_BEGIN -1535680991
	public static final int USERINFO_QQMAIL_AUTH_KEY = -1535680990;

	// plugin.report
	public static final int UESRINFO_REPORT_TIMESTAMP_SAVE_ALL = 0xabc12344;
	public static final int USERINFO_REPORT_STRATEGY = 0xabc12345;
	public static final int USERINFO_REPORT_KVSTAT = 0xabc12346;
	public static final int USERINFO_REPORT_TIMESTAMP_USERACTION = 0xabc12347;
	public static final int USERINFO_REPORT_TIMESTAMP_KVSTAT = 0xabc12348;
	public static final int USERINFO_REPORT_TIMESTAMP_CLIENTPREF = 0xabc12349;

	// -2046825378
	public static final int USERINFO_NEW_SYSTEM_PLUG = -2046825377; // in 3.6
	// public static final int USERINFO_NEW_QQMAIL = -2046825376; //in 3.6
	// public static final int USERINFO_NEW_TNEWS = -2046825375; //in 3.6
	// public static final int USERINFO_NEW_TWEIBONEWS = -2046825374; //in 3.6
	// public static final int USERINFO_NEW_MASS = -2046825373; //in 3.6
	// public static final int USERINFO_NEW_FACE_BOOK = -2046825372; //in 3.6
	public static final int USERINFO_NEW_TAB_SETTING = -2046825371; // in 3.6
	public static final int USERINFO_NEW_MEISHIAPP = -2046825370; // in 4.0
	public static final int USERINFO_NEW_FEEDSAPP = -2046825369; // in 4.0
	public static final int USERINFO_NEW_VOIPAPP = -2046825368; // in 4.2
//	public static final int USERINFO_NEW_VOICE_REMINDER = -2046825367; // in 4.5
	public static final int USERINFO_NEW_CARD_PACKAGE = -2046825366; // in 4.5

//	public static final int USERINFO_NEW_VOIPAUDIOAPP = -2046825365;

	public static final int USERINFO_LBS_SAYHI_SHOULD_SHOW = 0x12001;
	public static final int USERINFO_SHAKE_SAYHI_SHOULD_SHOW = 0x12002;

	public static final int USERINFO_SERVER_CONFIG_INFO = 0x13001;
	public static final int USERINFO_TENMINS_COUNT = 0x13005; // ʮ���ӵ���ͳ��
	public static final int USERINFO_VOIP_TIME = 0x13004;

	public static final int USERINFO_ACCOUNT_TICKET = 0x13006;
	public static final int REG_MOBILE_TICKET = 0x13007;
	public static final int REG_FACEBOOK_TICKET = 0x13008;

	// PHONEUSE---------------------------------------------------------------------------------------
	// public static final int PHONEUSE_CALL = 0x14001;
	// public static final int PHONEUSE_ADD_NEWCONTACT = 0x14002;
	// public static final int PHONEUSE_ADD_EXISTCONTACT = 0x14003;
	// public static final int PHONEUSE_COPY = 0x14004;
	// public static final int PHONEUSE_TIME = 0x14005;

	public static final int USERINFO_LAST_UPDATE_REGION_CODE_TIME = 0x14011; // �ϴε��������ʱ��
	public static final int USERINFO_LAST_UPDATE_CONFIG_LIST_TIME = 0x14012; // �ϴ�configlist����ʱ��

	public static final int USERINFO_LAST_UPLOAD_SPEEX_CONFIG_LIST_TIME = 0x14018;

	public static final int USERINFO_LAST_UPLOAD_SPEEX_COUNT = 0xfe0001;

	public static final int USERINFO_LAST_VISIT_SETALIAS_PAGE = 0x14014; // �ϴ�ȥ����ҳ���ʱ��

	public static final int USERINFO_CONTACT_COPY_VERIFYFLAG = 0x15001;

	public static final int USERINFO_UPDATE_HARDCODE_AVATAR = 0x16001;

	public static final int USERINFO_TAG1_CHOOSE_FILE_DIR = 0x20001;
	public static final int USERINFO_TAG2_CHOOSE_FILE_DIR = 0x20002;

	public static final int USERINFO_SMILEY_GRID_NEW = 0x20003;

	public static final int USERINFO_IMPORT_QR_FROM_PHOTO_ALBUM = 0x19001; // ����ᵼ���ά��
	public static final int USERINFO_LAST_IMPORT_QR_FROM_PHOTO_ALBUM_TIME = 0X19002;
	public static final int USERINFO_ADD_NEW_CONTACT_FROM_QRSCAN = 0x19003; // ɨ���ά���������ϵ��
	public static final int USERINFO_LAST_ADD_NEW_CONTACT_TIME = 0x19004;
	public static final int USERINFO_ADD_TO_EXISTED_CONTACT_FROM_QRSCAN = 0x19005; // ɨ���ά�������������ϵ��
	public static final int USERINFO_LAST_ADD_TO_EXISTED_CONTACT_TIME = 0x19006;

	public static final int USERINFO_TEMP_RANDOM_PASSWORD = 0x19007;
	
	public static final int USERINFO_HAD_SET_MASS_SEND_TOP = 0x19008;
	public static final int USERINFO_CHECK_MASS_SEND_TOP_TIME = 0x19009;
	public static final int USERINFO_LAST_UPDATE_MASS_SEND_TOP_TIME = 0x19020;

	public static final int USERINFO_GRANT_ROOMINFO_SIZE = 0x21007;
	public static final int USERINFO_GRANT_ROOMINFO_QUOTA = 0x21008;
	public static final int USERINFO_GRANT_ROOMINFO_INVITE = 0x21009;
	public static final int USERINFO_GRANT_ROOMINFO_NEW = 0x21010;

	public static final int USERINFO_CARD_LIST_BUF = 0x22001;
	public static final int USERINFO_CREATE_CARD_BUF = 0x22002;
	public static final int USERINFO_LAST_TIME_GET_LIST = 0x22013;

	public static final int USERINFO_SYNC_ADDRBOOK_ONCE_FOR_OVERSEA = 0x23001; // ��¼��½��ͬ����ϵ��Ϊ�����û�����һ�Σ�Ϊ�˽��һ��bug

	// public static final int USERINFO_FMESSAGE_MSGINFO_UNREAD_TAB = 0x23101; // ������ͨѶ¼tab��ʾ�����Ƽ���Ϣδ����
	public static final int USERINFO_FMESSAGE_MSGINFO_UNREAD_HEADER = 0x23102; // ������ͨѶ¼tab���б�header���鿴���С�������ʾ�����Ƽ���Ϣδ����
	
	public static final int USREINFO_LBSROOM_LAST_ROOM = 0x23201;
	public static final int USREINFO_LBSROOM_LAST_JOIN_DATE = 0x23202;
	public static final int USREINFO_LBSROOM_STILL_IN_CHATTINGUI = 0x23203; //�Ƿ���һ��lbsroom chattingui
	public static final int USREINFO_BRAND_SERVICE_IS_NOT_NEW = 0x23301;
	
	public static final int USERINFO_CDN_GRAYSCALE_FLAG = 0x23401; // ���� �� ����ϵbit set ; 0x1 ��cdn
	
	public static final int USERINFO_TALKROOM_WELCOME_DIALOG_CLOSE = 0x23501;	// talkroom welcomeҳ�Ĺرմ���
	
	
	public static final int USERINFO_NEWAUTOAUTHTICKET = 0x30001;
	
	// -----------------------------END OF USERINFO (CONFIG STORAGE) -----------------------------//
	// -----------------------------END OF USERINFO (CONFIG STORAGE) -----------------------------//
	// -----------------------------END OF USERINFO (CONFIG STORAGE) -----------------------------//

	// STORAGE PATH
	public static final String FAKE_SDCARD_ROOT = "/sdcard";	// NOTE: ֻ������ʾ�û������������߼�
	public static String DATAROOT_MOBILEMEM_PATH = "/data/data/com.tencent.mm/MicroMsg/";
	public static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static final String DATAROOT_SDCARD_PATH = SDCARD_ROOT + "/tencent/MicroMsg/";

	public static final String DATAROOT_SDCARD_DOWNLOAD_PATH = SDCARD_ROOT + "/tencent/MicroMsg/Download/";
	public static final String DATAROOT_SDCARD_CAMERA_PATH = SDCARD_ROOT + "/tencent/MicroMsg/Camera/";
	public static final String DATAROOT_SDCARD_VIDEO_PATH = SDCARD_ROOT + "/tencent/MicroMsg/Video/";
	public static final String DATAROOT_SDCARD_VUSER_ICON_PATH = SDCARD_ROOT + "/tencent/MicroMsg/vusericon/";

	public static final String DATAROOT_SDCARD_VOICE_REMIND_DOWNLOAD_PATH = SDCARD_ROOT + "/tencent/MicroMsg/Download/VoiceRemind";

	// public static final String ERRLOG_FILENAME = "errLog.cfg";
	public static final String SYSTEM_INFO_FILENAME = "systemInfo.cfg";
	public static final String CHAT_STATE_FILENAME = "chatstate.cfg";
	public static final String DB_NAME = "MicroMsg.db";
	// public static final String SNS_DB_NAME = "MicroMsgSns.db";
	public static final String ENDB_NAME = "EnMicroMsg.db";
	public static final String SNSDB_NAME = "SnsMicroMsg.db";
	public static final String ENSNSDB_NAME = "EnSnsMicroMsg.db";
	public static final String NO_MEDIA_FILENAME = ".nomedia";
	public static final String CRASH_RECORD_FILE = "crash_record_file";

	public static final String COMMONONEDB_NAME = "CommonOneMicroMsg.db";

	// TAGs
	// �Զ����TAGS
	public static final String TAG_ALL = "@all.android";
	public static final String TAG_ALL_WEIXIN = "@all.weixin.android";
	public static final String TAG_BLACKLIST = "@black.android";
	public static final String TAG_DOMAINMAIL = "@domain.android";
	public static final String TAG_CHATROOM_ALL = "@all.chatroom";
	public static final String TAG_VERIFY_BIZ_CONTACT = "@verify.contact";
	public static final String TAG_BIZ_CONTACT = "@biz.contact";
	public static final String TAG_WEIXIN_WITH_ALL_BIZ = "@micromsg.with.all.biz.qq.com";
	public static final String TAG_WEIXIN_NO_BIZ = "@micromsg.no.verify.biz.qq.com";

	// ϵͳ���ص�TAGS
	public static final String TAG_CHATROOM = "@chatroom";
	public static final String TAG_CHATROOM_EXCLUSIVE = "@chatroom_exclusive"; //for htc, kengdie
	public static final String TAG_TALKROOM = "@talkroom";
	public static final String TAG_LBSROOM = "@lbsroom";
	public static final String TAG_GROUPCARD = "@groupcard";
	public static final String TAG_WEIXIN = "@micromsg.qq.com";
	public static final String TAG_MICROBLOG_TENCENT = "@t.qq.com";
	public static final String TAG_MICROBLOG_SINA = "@t.sina.com";
	public static final String TAG_QQ = "@qqim";
	public static final String TAG_BOTTLE = "@bottle";
	public static final String TAG_QRCODE = "@qr";
	public static final String TAG_FACEBOOK = "@fb";
	public static final String URL_MM_QRCODE_HEAD_WEIXIN = "weixin://qr/";
	public static final String URL_MM_QRCODE_HEAD_HTTP = "http://weixin.qq.com/r/";
	public static final String URL_MM_WEB_LOGIN_CGI = "login.weixin.qq.com";

	// ��Ѷ΢��
	public static final String TAG_TWEIBOHTTP = "http://t.qq.com/";
	public static final String TAG_TWEIBO = "t.qq.com/";
	public static final String WEIBO_HEAD = "@";

	// SEPERATOR
	public static final String DOMAIN_END_SEPERATOR = ";";
	public static final String DOMAIN_FORMAT_SEPERATOR = "@";
	public static final String EMPTY_STRING = "";

	public static final String TMESSAGE_OR_QMESSAGE_CONTENT_SEPERATOR = ":";

	// crashUpload
	public static final long CRASH_UPLOAD_TIME = 120; // 2min
	public static final int CRASH_UPLOAD_NUMBER = 2; // numbers
	
	// stranger
	public static final String ENCRYPT_USERNAME_SUFFIX = "@stranger";
}
