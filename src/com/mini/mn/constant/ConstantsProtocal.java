package com.mini.mn.constant;

import junit.framework.Assert;

/**
 * 常用协议类型
 * 
 * @author S.Kei.Cheung
 * @date 2014.07.05
 * @version 1.0.0
 */
public final class ConstantsProtocal {

	private ConstantsProtocal() {

	}

	public static void setTestVersion(int malformVersion) {
		CLIENT_VERSION = malformVersion;
		IS_ALPHA_VERSION = checkAlphaVersion();
	}

	public static String DEVICE_TYPE = "android-" + android.os.Build.VERSION.SDK_INT;
	public static final String DEVICE_BRAND = android.os.Build.BRAND;
	public static final String DEVICE_MODEL = android.os.Build.MODEL + android.os.Build.CPU_ABI;
	public static final String OS_TYPE = "android-" + android.os.Build.VERSION.SDK_INT;
	public static final String OS_NAME = "android-" + android.os.Build.MANUFACTURER;
	public static final String OS_VERSION = "" + android.os.Build.VERSION.SDK_INT;

	public static int CLIENT_VERSION =  0x2405011A;

//网络模块灰度区间 [0x24050111,0x24050119]
	
	private static final int[] ALPHA_VERSIONS = {
			0x240000A1,
			0x240000A2,
			0x240100B4,
			0x240200B5,
			0x240200B7,
			0x240200B9,
			0x240200BB,
			0x240300D0, // 群灰度增量升级
			0x240300D1, // 外部用户增量升级
			0x240300D2,
			0x240300D3,
			0x240500F9,
			0x240500FA, };
	
	// 0x240500F8 ~ 0x240500FA // 白名单版本号区间

	private static boolean checkAlphaVersion() {
		for (int i = 0; i < ALPHA_VERSIONS.length; i++) {
			if (ALPHA_VERSIONS[i] == CLIENT_VERSION) {
				return true;
			}
		}
		return false;
	}

	public static boolean IS_ALPHA_VERSION = checkAlphaVersion();

	// 微信支持的最低版本的sdk
	public static final int SUPPORT_MIN_SDK_VERSION = 0x21010001;
	// public static final int SUPPORT_MAX_SDK_VERSION = 0x21010001;

	public static final String URL_SVR = "short.weixin.qq.com";
	public static final int PORT_SVR = 80;
	// mobileapi.dingfanhe.com
	public static final String URL_SVR2 = "112.124.106.42";
	public static final int PORT_SVR2 = 58891;

	// 通用初始/非法值定义
	public static final int MM_INVALID_RETCODE = -99;
	public static final int MM_INVALID_SYNCKEY = -99;
	public static final int MM_INVALID_UIN = 0;
	public static final int MM_INVALID_CLIENT_VERSION = 0;
	public static final byte[] MM_INVALID_KEYBUF = null;
	public static final long MM_INVALID_SELECTOR = 0;
	public static final byte[] MM_INVALID_NEWINIT_KEY = null;
	public static final byte[] MM_INVALID_MAX_NEWINIT_KEY = null;

	// Errors
	public static final int MM_OK = 0;
	public static final int MM_ERR_SYS = -1;
	public static final int MM_ERR_ARG = -2;
	public static final int MM_ERR_PASSWORD = -3;
	public static final int MM_ERR_NOUSER = -4;
	public static final int MM_ERR_ACCESS_DENIED = -5;
	public static final int MM_ERR_NEED_VERIFY = -6;
	public static final int MM_ERR_USEREXIST = -7;
	public static final int MM_ERR_EMAILEXIST = -8;
	public static final int MM_ERR_EMAILNOTVERIFY = -9;
	public static final int MM_ERR_USERRESERVED = -10;
	public static final int MM_ERR_NICKRESERVED = -11;
	public static final int MM_ERR_UINEXIST = -12;
	public static final int MM_ERR_SESSIONTIMEOUT = -13;
	public static final int MM_ERR_USERNAMEINVALID = -14;
	public static final int MM_ERR_NICKNAMEINVALID = -15;
	public static final int MM_ERR_CRITICALUPDATE = -16;
	public static final int MM_ERR_RECOMMENDEDUPDATE = -17;
	public static final int MM_ERR_NOUPDATEINFO = -18;
	public static final int MM_ERR_NOTOPENPRIVATEMSG = -19;
	public static final int MM_ERR_NOTMICROBLOGCONTACT = -20;
	public static final int MM_ERR_NOTCHATROOMCONTACT = -21;
	public static final int MM_ERR_BLACKLIST = -22;
	public static final int MM_ERR_MEMBER_TOOMUCH = -23;
	public static final int MM_ERR_SPAM = -24;
	public static final int MM_ERR_DOMAINVERIFIED = -25;
	public static final int MM_ERR_DOMAINMAXLIMITED = -26;
	public static final int MM_ERR_DOMAINDISABLE = -27;
	public static final int MM_ERR_BADEMAIL = -28;
	public static final int MM_ERR_OIDBTIMEOUT = -29;
	public static final int MM_ERR_NEEDREG = -30;
	public static final int MM_ERR_NEEDSECONDPWD = -31;
	public static final int MM_ERR_VERIFYCODE_UNMATCH = -32;
	public static final int MM_ERR_VERIFYCODE_TIMEOUT = -33;
	public static final int MM_ERR_FREQ_LIMITED = -34;
	public static final int MM_ERR_MOBILE_BINDED = -35;
	public static final int MM_ERR_MOBILE_UNBINDED = -36;
	public static final int MM_ERR_INVALID_BIND_OPMODE = -37;
	public static final int MM_ERR_INVALID_UPLOADMCONTACT_OPMODE = -38;
	public static final int MM_ERR_MOBILE_NULL = -39;
	public static final int MM_ERR_UNMATCH_MOBILE = -40;
	public static final int MM_ERR_MOBILE_FORMAT = -41;
	public static final int MM_ERR_USER_MOBILE_UNMATCH = -42;
	public static final int MM_ERR_USER_BIND_MOBILE = -43;
	public static final int MM_ERR_NEED_VERIFY_USER = -44;
	public static final int MM_ERR_BATCHGETCONTACTPROFILE_MODE = -45;
	public static final int MM_ERR_NOTQQCONTACT = -46;
	public static final int MM_ERR_TICKET_UNMATCH = -47;
	public static final int MM_ERR_TICKET_NOTFOUND = -48;
	public static final int MM_ERR_NEED_QQPWD = -49;
	public static final int MM_ERR_BINDUIN_BINDED = -50;
	public static final int MM_ERR_VERIFYCODE_NOTEXIST = -51;
	public static final int MM_ERR_INVALID_HDHEADIMG_REQ_TOTAL_LEN = -54;
	public static final int MM_ERR_NO_HDHEADIMG = -55;
	public static final int MM_ERR_NO_BOTTLECOUNT = -56;
	public static final int MM_ERR_SEND_VERIFYCODE = -57;
	public static final int MM_ERR_MOBILE_NOT_SUPPORT = -59;
	public static final int MM_ERR_GMAIL_PWD = -60; // gmail pwd wrong
	public static final int MM_ERR_GMAIL_ONLINELIMITE = -61; // gmail online limite
	public static final int MM_ERR_GMAIL_WEBLOGIN = -62; // gmail need web login
	public static final int MM_ERR_GMAIL_IMAP = -63; // gmail imap not open
	public static final int MM_ERR_PARSE_MAIL = -64; // mail parse fail
	public static final int MM_ERR_UNBIND_REGBYMOBILE = -65; // 手机注册的用户，不能解绑
	public static final int MM_ERR_IS_NOT_OWNER = -66; // 群聊踢人，不是群主返回
	public static final int MM_ERR_HAVE_BIND_FACEBOOK = -67; // facebook绑定 已绑定返回该值
	public static final int MM_FACEBOOK_ACCESSTOKEN_INVALID = -68; // facebook accesstoken不合法
	public static final int MM_ERR_BIGBIZ_AUTH = -69; // 大商家不给登录
	public static final int MM_ERR_GETMFRIEND_NOT_READY = -70; // facebook getmfriend 未准备好(AsyncMq还在拉数据)
	public static final int MM_ERR_TOLIST_LIMITED = -71; // MassSend to many users in ToList
	public static final int MM_ERR_NICEQQ_EXPIRED = -72; // QQ靓号过期
	public static final int MM_ERR_MOBILE_NEEDADJUST = -74; // 手机号码归属地和国家不一致
	public static final int MM_ERR_ALPHA_FORBIDDEN = -75; // 不在灰度版本的白名单内，不允许操作
	// 没有绑定QQ号码
	public static final int MM_ERR_NOTBINDQQ = -81;
	// 只剩下一种绑定关系，不能解绑
	public static final int MM_ERR_ONE_BINDTYPE_LEFT = -82;
	// 已经解绑，不能重复解绑
	public static final int MM_ERR_HAS_UNBINDED = -83;
	// 已经绑定，不能再绑
	public static final int MM_ERR_HAS_BINDED = -84;
	// 已经被别人绑定
	public static final int MM_ERR_BINDED_BY_OTHER = -85;
	// 绑定的email不能和绑定QQ的qmail一样
	public static final int MM_ERR_BIND_EMAIL_SAME_AS_QMAIL = -86;
	// 会议官号关注数已达最大
	public static final int MM_ERR_BIZ_FANS_LIMITED = -87;
	// 对方版本不支持
	public static final int MM_ERR_USER_NOT_SUPPORT = -94;

	public static final int MM_ERR_AUTH_ANOTHERPLACE = -100;

	public static final int MM_ERR_CERT_EXPIRED = -102;
	
	// 答题次数用完
	public static final int MM_ERR_ANSWER_COUNT = -150;
	// 提问次数用完
	public static final int MM_ERR_QUESTION_COUNT = -151;
	// 没有问题
	public static final int MM_ERR_NO_QUESTION = -152;
	// 关系不对劲
	public static final int MM_ERR_QA_RELATION = -153;

	// 帐号相关
	public static final int MM_ERR_OTHER_MAIN_ACCT = -204; // 需要其它主帐号登陆
	public static final int MM_ERR_QQ_OK_NEED_MOBILE = -205; // QQ验证OK，需要验证手机

	/* big chatroom */
	public static final int MM_ERR_BIGCHATROOM_NOQUOTA = -250;
	public static final int MM_ERR_BIGCHATROOM_NONEED = -251;
	public static final int MM_ERR_IDC_REDIRECT = -301;
	public static final int MM_ERR_IMG_READ = -1005;
	public static final int MM_ERR_APPINFO_INVALID = -1011; // 该appinfo根本不存在
	public static final int MM_ERR_LBSDATANOTFOUND = -2000;
	public static final int MM_ERR_LBSBANBYEXPOSE = -2001;
	public static final int MM_ERR_BOTTLEBANBYEXPOSE = -2002;
	public static final int MM_ERR_SHAKEBANBYEXPOSE = -2003;
	public static final int MM_ERR_QRCODEVERIFY_BANBYEXPOSE = -2004;

	public static final int MM_ERR_KILLSELF = -999999;

	public static final int MM_UINFOFLAG_USERNAME = 0x1;
	public static final int MM_UINFOFLAG_NICKNAME = 0x2;
	public static final int MM_UINFOFLAG_BINDUIN = 0x4;
	public static final int MM_UINFOFLAG_BINDEMAIL = 0x8;
	public static final int MM_UINFOFLAG_BINDMOBILE = 0x10;
	public static final int MM_UINFOFLAG_STATUS = 0x20;
	public static final int MM_UINFOFLAG_PHOTO = 0x40;
	public static final int MM_UINFOFLAG_PERSONALCARD = 0x80;
	public static final int MM_UINFOFLAG_BOTTLEIMG = 0x100;
	public static final int MM_UINFOFLAG_HDHEADIMGMD5SUM = 0x200;
	public static final int MM_UINFOFLAG_BOTTLEHDHEADIMGMD5SUM = 0x400;
	public static final int MM_UINFOFLAG_PLUGINFLAG = 0x800;

	// Type
	public static final int rtBROADCAST = -1;
	public static final int rtNULL = 0;
	// public static final int rtAUTH = 1;
	// public static final int rtREGISTER = 2; //delete by dk
	// public static final int rtSYNC = 3;
	public static final int rtSENDMSG = 4;
	public static final int rtSEARCHCONTACT = 5;
	// public static final int rtGETVERIFYIMG = 6;
	public static final int rtSENDVERIFYEMAIL = 7;
	// public static final int MMFunc_GetMsgImg = 8;
	// public static final int MMFunc_UploadMsgImg = 9;
	public static final int rtDIRECTSEND = 10;
	public static final int rtGETUPDATEINFO = 11;
	public static final int rtGETUPDATEPACK = 12;
	public static final int rtGETINVITEFRIEND = 13;
	public static final int rtSENDINVITEEMAIL = 14;
	// public static final int rtSYNCHECK = 15;
	public static final int rtCREATECHATROOM = 16;
	public static final int rtADDCHATROOMMEMBER = 17;
	// public static final int rtINIT = 18;
	public static final int rtBATCHGETHEADIMG = 19;
	public static final int rtSENDDOMAINEMAIL = 20;
	public static final int rtUPLOADVOICE = 21;
	public static final int rtDOWNLOADVOICE = 22;
	// public static final int rtREGISTER2 = 23;
	public static final int rtSWITCHPUSHMAIL = 24;
	// public static final int rtGETUSERNAME = 25; // delete by dk use regmode_qq2
	public static final int rtSENDCARD = 26;
	// public static final int rtBINDOPMOBLIE = 27; // delete by dk
	public static final int rtSYNCFRIEND = 28;
	public static final int rtUPLOADMCONTACT = 29;
	public static final int rtVERIFYUSER = 30;
	public static final int rtGETQQGROUP = 31;
	public static final int rtGETMFRIEND = 32;
	public static final int rtBINDQQ = 33;
	// public static final int rtRESETPWD = 34; // delete by dk
	public static final int rtGETPSMIMG = 35;
	// public static final int rtBINDMOBILEFORREG = 36; // BindMobileForReg
	public static final int rtNEWINIT = 37;
	public static final int rtNEWSYNC = 38;
	public static final int rtNEWSYNCCHECK = 39;
	public static final int rtDOWNLOADVIDEO = 40;
	public static final int rtUPLOADVIDEO = 41;
	public static final int rtBATCHGETCONTACTPROFILE = 42;
	public static final int rtLBSFIND = 43;
	public static final int rtADDGROUPCARD = 44;
	public static final int rtUPLOADHDHEADIMG = 45;
	public static final int rtGETHDHEADIMG = 46;
	public static final int rtGETBOTTLECOUNT = 47;
	public static final int rtOPENBOTTLE = 48;
	public static final int rtPICKBOTTLE = 49;
	public static final int rtTHROWBOTTLE = 50;
	public static final int rtSHAKEREPORT = 51;
	public static final int rtSHAKEGET = 52;
	public static final int rtSHAKEMATCH = 53;
	public static final int rtDOWNLOADTHEME = 54;
	public static final int rtGETTHEMELIST = 55;
	public static final int rtSHAKEIMG = 56;
	public static final int rtGETVUSERINFO = 57;
	public static final int rtEXPOSEUSER = 58;
	public static final int rtFEEDBACK = 59;
	public static final int rtGMAILOPER = 60;
	public static final int rtGETQRCODE = 61;
	public static final int rtUPLOADEMOJI = 62;
	public static final int rtDOWNLOADEMOJI = 63;
	public static final int rtGETPACKAGELIST = 64;
	public static final int rtDOWNLOADPACKAGE = 65;
	public static final int rtGETCONTACT = 66;
	public static final int rtGENERALSET = 67;
	public static final int rtDELCHATROOMMEMBER = 68;
	public static final int rtCOLLECTCHATROOM = 69;
	public static final int rtVOIPINVITE = 70;
	public static final int rtVOIPCANCELINVITE = 71;
	public static final int rtVOIPANSWER = 72;
	public static final int rtVOIPSHUTDOWN = 73;
	public static final int rtVOIPSYNC = 74;
	public static final int rtVOIPHEARTBEAT = 75;
	public static final int rtGetAlbumPhotoListFP = 76;
	public static final int rtBatchGetAlbumPhoto = 77;
	public static final int rtBatchGetAlbumPhotoAttr = 78;
	public static final int rtUploadAlbumPhoto = 79;
	public static final int rtDownloadAlbumPhoto = 80;
	public static final int rtDelAlbumPhoto = 81;
	public static final int rtAlbumPhotoComment = 82;
	public static final int rtGetTLPhotoListFP = 83;
	public static final int rtFaceBookAuth = 84;
	public static final int rtMASSEND = 85;
	public static final int rtGetAlbumPhotoListNP = 86;
	public static final int rtGetTLPhotoListNP = 87;
	public static final int rtGetLatestTLPhoto = 88;
	public static final int rtNewSyncAlbum = 89;
	public static final int rtAlbumLbs = 90;
	public static final int rtGetWeiBoURL = 91;
	public static final int rtVoiceAddr = 92;
	public static final int rtGetLocation = 93; // get location
	public static final int rtUploadAppAttach = 94;
	public static final int rtDownloadAppAttach = 95;
	public static final int rtSendAppMsg = 96;
	public static final int rtSnsUpload = 97; // sns上传buffer
	public static final int rtSnsDownload = 98; // sns下载buffer
	public static final int rtSnsPost = 99; // sns发布事件

	public static final int rtMAX = 100; // NEVER NEVER NEVER NEVER Update this count
	// public static final int MMFunc_MMSnsObjectDetail = 100; // sns事件详情页
	// public static final int MMFunc_MMSnsTimeLine = 101; // sns获取timeline
	// public static final int MMFunc_MMSnsUserPage = 102; // sns个人页面
	// public static final int MMFunc_MMSnsComment = 103; // sns评论
	// public static final int MMFunc_MMSnsSync = 104; // sns消息系统同步
	// public static final int MMFunc_MMSnsLbs = 105; // sns lbs
	// public static final int MMFunc_MMSnsObjectOp = 106;
	// public static final int MMFunc_GetAppInfo = 107;
	// public static final int MMFunc_GetRecommendAppList = 108;
	// public static final int MMFunc_GetA8Key = 109;
	// public static final int MMFunc_UploadMedia = 110;
	// public static final int MMFunc_StatReport = 111;

	// !!!!!警告!!!!!!!!!!!!!!!!!!!!!!!!!
	// 不要再使用rtType 区分netscene , 使用 MMFunc_id ,
	// 新写的get , set type 直接赋值为 MMFunc_XXX
	// 任何问题, 找dk
	// func dk --- by lyh
	static {
		Assert.assertTrue("giveup rtType now ! Use the funcid !", rtMAX == 100);
	}

	// scene status
	public static final int MM_SCENE_MANUALAUTH = 1;
	public static final int MM_SCENE_AUTOAUTH = 2;
	public static final int MM_SCENE_NORMALINIT = 3;
	public static final int MM_SCENE_UPDATEINIT = 4;
	public static final int MM_SCENE_SENDMSG = 5;
	public static final int MM_SCENE_RETRYSENDMSG = 6;
	public static final int MM_SCENE_REGINIT = 7;
	public static final int MM_SCENE_MANUALUSERNAME = 8;
	public static final int MM_SCENE_SYSTEMUSERNAME = 9;

	// MM_DATA_TYPE
	public static final int MM_DATA_TEXT = 1;
	// public static final int MM_DATA_HTML = 2;
	public static final int MM_DATA_IMG = 3; // 图片类型
	public static final int MM_DATA_PRIVATEMSG_TEXT = 11; // 私信文本
	// public static final int MM_DATA_PRIVATEMSG_HTML = 12;
	public static final int MM_DATA_PRIVATEMSG_IMG = 13; // 私信图片
	public static final int MM_DATA_CHATROOMMSG_TEXT = 21; // 被客户端误用，兼容
	// public static final int MM_DATA_CHATROOMMSG_HTML = 22;
	public static final int MM_DATA_CHATROOMMSG_IMG = 23; // abandon // 被客户端误用，兼容
	public static final int MM_DATA_EMAILMSG_TEXT = 31; // 未使用,已废弃
	// public static final int MM_DATA_EMAILMSG_HTML = 32; // 未使用,已废弃
	public static final int MM_DATA_EMAILMSG_IMG = 33; // 未使用,已废弃
	public static final int MM_DATA_VOICEMSG = 34; // 语音类型
	public static final int MM_DATA_PUSHMAIL = 35;
	public static final int MM_DATA_QMSG = 36; // QQ离线消息文本
	public static final int MM_DATA_VERIFYMSG = 37; // 好友验证类型
	public static final int MM_DATA_PUSHSYSTEMMSG = 38; // 广告消息类型
	public static final int MM_DATA_QQLIXIANMSG_IMG = 39; // QQ离线消息图片
	public static final int MM_DATA_POSSIBLEFRIEND_MSG = 40; // 好友推荐类型
	public static final int MM_DATA_PUSHSOFTWARE = 41; // 精品软件推荐类型
	public static final int MM_DATA_SHARECARD = 42; // 名片分享类型
	public static final int MM_DATA_VIDEO = 43; // 视频类型
	public static final int MM_DATA_VIDEO_IPHONE_EXPORT = 44; // 转发视频类型
	// public static final int MM_DATA_GMAIL_PUSHMAIL = 45; // Gmail pushmail类型
	// public static final int MM_DATA_EMPTY = 46; // 客户端要求，占坑
	public static final int MM_DATA_EMOJI = 47; // 自定义表情类型
	public static final int MM_DATA_LOCATION = 48; // 位置消息类型
	public static final int MM_DATA_APPMSG = 49; // AppMsg
	public static final int MM_DATA_VOIPMSG = 50; // Voip Msg
	public static final int MM_DATA_STATUSNOTIFY = 51; // web和手机状态同步消息
	public static final int MM_DATA_VOIPNOTIFY = 52; // client side use only
	public static final int MM_DATA_VOIPINVITE = 53; // client side use only
	public static final int MM_DATA_BRAND_QA_ASK = 55; // 公众平台问答 提问
	public static final int MM_DATA_BRAND_QA_MSG = 57; // 公众平台问答 普通消息

	public static final int MM_DATA_TALK_SYSMSG = 56;

	public static final int MM_EX_DATA_VOICE_REMIND_SYS = 0x90000001; // client side use only
	public static final int MM_EX_DATA_VOICE_REMIND_REMIND = 0x90000002; // client side use only
	public static final int MM_EX_DATA_VOICE_REMIND_CONFIRM = 0x90000003; // client side use only
	public static final int MM_EX_DATA_VOIP_VOICE_FINISH = 0x90000004; // client side use only
	public static final int MM_EX_DATA_VOIP_VOICE_CANCEL = 0x90000005; // client side use only

	@Deprecated
	public static final int MM_DATA_WEIBOPUSH = 1000000 + 52; // Weibo Push( MMReader ), never sent to client(iotazhang)
	@Deprecated
	public static final int MM_DATA_WEBWXVOIPNOTIFY = 1000000 + 53; // webwx voip notify , never sent to client(iotazhang)
	@Deprecated
	public static final int MM_DATA_WEIBOPUSH_SVR = 52; // Weibo Push( MMReader ) , never sent to client(iotazhang)
	@Deprecated
	public static final int MM_DATA_WEBWXVOIPNOTIFY_SVR = 53; // webwx voip notify , never sent to client(iotazhang)

	public static final int MM_DATA_SYSCMD_IPXX = 9998;
	public static final int MM_DATA_SYSNOTICE = 9999;
	public static final int MM_DATA_SYS = 10000;
	public static final int MM_DATA_SYSCMD_XML = 10001;
	public static final int MM_DATA_SYSCMD_NEWXML = 10002; // 系统命令XML消息，客户端只处理，不显示

	// ///////////////////SYSCMD_NEWXML_SUBTYPE define
	public static final String MM_DATA_SYSCMD_NEWXML_SUBTYPE_EQUIPMENTMSG = "equipmentmsg";
	public static final String MM_DATA_SYSCMD_NEWXML_SUBTYPE_APPCOMMENT = "appcomment";
	public static final String MM_DATA_SYSCMD_NEWXML_SUBTYPE_BIZSELFMENU = "bizselfmenu";
	public static final String MM_DATA_SYSCMD_NEWXML_SUBTYPE_BIZREMOVE = "brand_service_remove_advise";

	public static final int MM_DATA_VOIP = 12299999;
	public static final int MM_DATA_READERAPP = 12399999;
	public static final int MM_DATA_APPMSG_IMG = 0x10000000 | MM_DATA_APPMSG; // for local use
	public static final int MM_DATA_APPMSG_TEXT = 0x01000000 | MM_DATA_APPMSG; // for local use
	public static final int MM_DATA_APPMSG_EMOJI = 0x00100000 | MM_DATA_APPMSG; // for local use
	public static final int MM_DATA_APPMSG_BIZ = 0x11000000 | MM_DATA_APPMSG; // for local use
	public static final int MM_DATA_APPMSG_SHAKE_SHARE = 0x12000000 | MM_DATA_APPMSG; // for local use

	public static final int MM_NEWSYNC_PROFILE = 0x1;
	public static final int MM_NEWSYNC_MSG = 0x2;
	public static final int MM_NEWSYNC_WXCONTACT = 0x4;
	public static final int MM_NEWSYNC_PMCONTACT = 0x8;
	public static final int MM_NEWSYNC_QQCONTACT = 0x10;
	public static final int MM_NEWSYNC_FRIEND = 0x20;
	public static final int MM_NEWSYNC_BOTTLECONTACT = 0x40;
	public static final int MM_NEWSYNC_DEFAULT_SELECTOR = MM_NEWSYNC_PROFILE | MM_NEWSYNC_MSG | MM_NEWSYNC_WXCONTACT;
	// added 3.5.1 without MM_NEWSYNC_FRIEND
	public static final int MM_NEWSYNC_ALL = MM_NEWSYNC_PROFILE | MM_NEWSYNC_MSG | MM_NEWSYNC_WXCONTACT | MM_NEWSYNC_PMCONTACT | MM_NEWSYNC_QQCONTACT | MM_NEWSYNC_BOTTLECONTACT;
	public static final int MM_NEWSYNC_ALBUMSYNCKEY = 0x80;
	public static final int MM_NEWSYNC_SNSSYNCKEY = 0x100;
	public static final int MM_NEWSYNC_VOIPSNYC = 0x200;
	public static final int MM_NEWSYNC_VOIPINVITE = 0x400;
	public static final int MM_NEWSYNC_SUBMSGSYNCKEY = 0x800; // 客服信息同步

	// domain status
	public static final int MM_DOMAINEMAIL_NOTVERIFY = 1;
	public static final int MM_DOMAINEMAIL_VERIFIED = 2;

	// micro-blog
	public static final int MM_NOTIFY_CLOSE = 0;
	public static final int MM_NOTIFY_OPEN = 1;

	public static final int MM_DELFLAG_EXIST = 1;
	public static final int MM_DELFLAG_DEL = 2;

	public static final int MM_MICROBLOG_QQ = 1;
	// public static final int MM_MICROBLOG_SINA = 2;

	// function switch type
	public static final int MM_FUNCTIONSWITCH_PUSHMAIL = 1;
	public static final int MM_FUNCTIONSWITCH_QQ_SEARCH_CLOSE = 2;
	public static final int MM_FUNCTIONSWITCH_QQ_PROMOTE_CLOSE = 3;
	public static final int MM_FUNCTIONSWITCH_VERIFY_USER = 4;
	public static final int MM_FUNCTIONSWITCH_QQMSG = 5;
	public static final int MM_FUNCTIONSWITCH_QQ_PROMOTE_TOME_CLOSE = 6;
	public static final int MM_FUNCTIONSWITCH_PROMOTE_TOME_CLOSE = 7;
	public static final int MM_FUNCTIONSWITCH_MOBILE_SEARCH_CLOSE = 8;
	public static final int MM_FUNCTIONSWITCH_ADD_CONTACT_CLOSE = 9;
	public static final int MM_FUNCTIONSWITCH_BOTTLE_OPEN = 11;
	public static final int MM_FUNCTIONSWITCH_WEIXIN_ONLINE_OPEN = 12;
	public static final int MM_FUNCTIONSWITCH_MEDIANOTE_OPEN = 13;
	public static final int MM_FUNCTIONSWITCH_BOTTLE_CHART_OPEN = 14;
	public static final int MM_FUNCTIONSWITCH_GMAIL_OPEN = 15;
	public static final int MM_FUNCTIONSWITCH_TXWEIBO_OPEN = 16;
	public static final int MM_FUNCTIONSWITCH_UPLOADMCONTACT_CLOSE = 17;
	public static final int MM_FUNCTIONSWITCH_RECFBFRIEND_OPEN = 18;
	// public static final int MM_FUNCTIONSWITCH_READER_OPEN = 19;
	// public static final int MM_FUNCTIONSWITCH_READER_TXNEWS_OPEN = 20;
	public static final int MM_FUNCTIONSWITCH_READER_WB_OPEN = 21;
	public static final int MM_FUNCTIONSWITCH_TXWEIBO_ICON_OPEN = 22;
	public static final int MM_FUNCTIONSWITCH_MEISHI_CARD_OPEN = 23;
	public static final int MM_FUNCTIONSWITCH_ALBUM_NOT_FOR_STRANGER = 24;
	public static final int MM_FUNCTIONSWITCH_USERNAME_SEARCH_CLOSE = 25;
	public static final int MM_FUNCTIONSWITCH_NEWSAPP_TXNEWS_CLOSE = 26;
	public static final int MM_FUNCTIONSWITCH_WEBONLINE_PUSH_OPEN = 27;
	public static final int MM_FUNCTIONSWITCH_SAFEDEVICE_OPEN = 28; // safedevice

	// Update Mobile Contact OpCode
	public static final int MM_UPDATEMCONTACT_ADD = 1;
	public static final int MM_UPDATEMCONTACT_DEL = 2;

	// function switch code
	public static final int MM_FUNCTIONSWITCH_NOSUCHFUNCTION = 0;
	public static final int MM_FUNCTIONSWITCH_OPEN = 1;
	public static final int MM_FUNCTIONSWITCH_CLOSE = 2;

	// send card flags
	public static final int MM_SENDCARD_QQBLOG = 0x01;
	public static final int MM_SENDCARD_QQSIGN = 0x02;
	public static final int MM_SENDCARD_QZONE = 0x04;
	public static final int MM_SENDCARD_QQFRIEND = 0x08;
	public static final int MM_SENDCARD_SINABLOG = 0x10;
	public static final int MM_SENDCARD_MODIFYHEADIMG = 0x20;
	public static final int MM_SENDCARD_READER = 0x40;
	public static final int MM_SENDCARD_FACEBOOK = 0x80;

	// verify user opcode
	public static final int MM_VERIFYUSER_ADDCONTACT = 1;
	public static final int MM_VERIFYUSER_SENDREQUEST = 2;
	public static final int MM_VERIFYUSER_VERIFYOK = 3;
	public static final int MM_VERIFYUSER_VERIFYREJECT = 4;
	public static final int MM_VERIFYUSER_SENDERREPLY = 5;
	public static final int MM_VERIFYUSER_RECVERREPLY = 6;

	// add contact scene
	public static final int MM_ADDSCENE_SEARCH_QQ = 1; // 通过搜索QQ昵称
	public static final int MM_ADDSCENE_SEARCH_EMAIL = 2; // 通过搜索QQMail昵称
	public static final int MM_ADDSCENE_SEARCH_WEIXIN = 3; // 通过搜索微信号
	public static final int MM_ADDSCENE_PF_QQ = 4; // 通过可能认识的QQ好友
	public static final int MM_ADDSCENE_PF_EMAIL = 5; // 通过可能认识的QQMail好友
	public static final int MM_ADDSCENE_PF_CONTACT = 6; // 通过把我加到通讯录的人
	public static final int MM_ADDSCENE_PF_WEIXIN = 7; // 通过可能认识的微信好友(二度关系)
	public static final int MM_ADDSCENE_PF_GROUP = 8; // 通过可能认识的群好友
	public static final int MM_ADDSCENE_PF_UNKNOWN = 9; // “可能认识的好友”（无法区分来源）
	public static final int MM_ADDSCENE_PF_MOBILE = 10; // 手机通讯录
	public static final int MM_ADDSCENE_PF_MOBILE_EMAIL = 11; // 邮箱匹配
	public static final int MM_ADDSCENE_VIEW_QQ = 12; // 查看QQ好友
	public static final int MM_ADDSCENE_VIEW_MOBILE = 13; // 查看手机通讯录
	public static final int MM_ADDSCENE_CHATROOM = 14; // 群动作
	public static final int MM_ADDSCENE_SEARCH_PHONE = 15; // 搜索手机
	public static final int MM_ADDSCENE_CORP_EMAIL = 16; // 工作邮箱
	public static final int MM_ADDSCENE_SEND_CARD = 17; // 发名片
	public static final int MM_ADDSCENE_LBS = 18; // LBS加好友
	public static final int MM_ADDSCENE_PF_MOBILE_REVERSE = 21; // 手机通讯录反向好友
	public static final int MM_ADDSCENE_PF_SHAKE_PHONE_PAIR = 22; // 撸sever加好友单对单
	public static final int MM_ADDSCENE_PF_SHAKE_PHONE_GROUP = 23; // 撸sever加好友群加
	public static final int MM_ADDSCENE_PF_SHAKE_PHONE_OPPSEX = 24; // 撸sever加好友1000米外异性
	public static final int MM_ADDSCENE_BOTTLE = 25; // 漂流瓶打招呼加好友
	public static final int MM_ADDSCENE_SHAKE_SCENE1 = 26; // shake, 3s, <= 1km
	public static final int MM_ADDSCENE_SHAKE_SCENE2 = 27; // shake, <= 1km
	public static final int MM_ADDSCENE_SHAKE_SCENE3 = 28; // shake <= 10km
	public static final int MM_ADDSCENE_SHAKE_SCENE4 = 29; // shake, > 10km
	public static final int MM_ADDSCENE_QRCODE = 30;
	public static final int MM_ADDSCENE_FACEBOOK = 31; // facebook插件
	public static final int MM_ADDSCENE_SNS = 32; // 通过朋友圈推荐添加
	public static final int MM_ADDSCENE_WEB = 33; // from web -> js popup dialog (2012.12.19 =。=)
	public static final int MM_ADDSCENE_BRAND_QA = 34; // 公众账号问答
	public static final int MM_ADDSCENE_FUZZY_SEARCH = 35; // 通过搜昵称（模糊搜索）
	public static final int MM_ADDSCENE_LOGO_WALL = 36; // 通过点击logo墙
	public static final int MM_ADDSCENE_TIMELINE_BIZ = 37; // 在朋友圈中，点击公众账号名的方式查看关注了公众帐号
	public static final int MM_ADDSCENE_PROMOTE_MSG = 38; // 在会话中，点击公众帐号下发消息所带的公众帐号查看关注了公众帐号
	public static final int MM_ADDSCENE_SEARCH_BRAND = 39; // 通过“查找微信公众号”入口关注的公众号。
	public static final int MM_ADDSCENE_PROMOTE_BIZCARD = 41; // 表示“公众平台下发的名片”而加到的好友。
	public static final int MM_ADDSCENE_WEBPAGE_INSIDE = 42; // web 页面内点击进入profile页面
	public static final int MM_ADDSCENE_WEBPAGE_TOP_BUTTON = 43; // web 页面右上角按钮进入profile页面
	public static final int MM_ADDSCENE_LBSROOM = 44; // lbsroom 聊天窗口右上角进入成员列表，列表中用户的profile页面

	// expose scene
	public static final int MM_EXPOSE_LBS_SEARCH = 1; // 主动LBS搜到后举报
	public static final int MM_EXPOSE_LBS_RCVGREET = 2; // 收到LBS的打招呼后举报
	public static final int MM_EXPOSE_SHAKE_SEARCH = 3; // 主动摇一摇后举报
	public static final int MM_EXPOSE_SHAKE_RCVGREET = 4; // 收到摇一摇的打招呼后举报
	public static final int MM_EXPOSE_BOTTLE_PICK = 5; // 收到漂流瓶后举报
	public static final int MM_EXPOSE_BOTTLE_RCVGREET = 6; // 收到漂流瓶来的打招呼后举报
	public static final int MM_EXPOSE_QRCODE_RCVGREET = 7; // 收到二维码打招呼后举报

	// packet protocol(cmd id)
	public static final int MM_PKT_INVALID_REQ = 0;
	public static final int MM_PKT_AUTH_REQ = 1;
	public static final int MM_PKT_SENDMSG_REQ = 2;
	// public static final int MM_PKT_SYNC_REQ = 3;
	public static final int MM_PKT_TOKEN_REQ = 4;
	public static final int MM_PKT_NOTIFY_REQ = 5;
	public static final int MM_PKT_NOOP_REQ = 6;
	public static final int MM_PKT_QUIT_REQ = 7;
	public static final int MM_PKT_DIRECTSEND_REQ = 8;
	public static final int MM_PKT_UPLOADMSGIMG_REQ = 9;
	public static final int MM_PKT_GETMSGIMG_REQ = 10;
	public static final int MM_PKT_CONNCONTROL_REQ = 11;
	public static final int MM_PKT_REDIRECT_REQ = 12;
	// public static final int MM_PKT_SYNCHECK_REQ = 13;
	// public static final int MM_PKT_INIT_REQ = 14;
	public static final int MM_PKT_GETUSERIMG_REQ = 15;
	public static final int MM_PKT_GETUPDATEPACK_REQ = 16;
	public static final int MM_PKT_SEARCHFRIEND_REQ = 17;
	// public static final int MM_PKT_GETINVITEFRIEND_REQ = 18;
	public static final int MM_PKT_UPLOADVOICE_REQ = 19;
	public static final int MM_PKT_DOWNLOADVOICE_REQ = 20;
	// public static final int MM_PKT_UPLOADWEIBOIMG_REQ = 21;
	// public static final int MM_PKT_ASYNCDOWNLOADVOICE_REQ = 22;
	public static final int MM_PKT_NEWGETINVITEFRIEND_REQ = 23;
	public static final int MM_PKT_NEWNOTIFY_REQ = 24;
	public static final int MM_PKT_NEWSYNCCHECK_REQ = 25;
	public static final int MM_PKT_NEWSYNC_REQ = 26;
	public static final int MM_PKT_NEWINIT_REQ = 27;
	public static final int MM_PKT_BATCHGETCONTACTPROFILE_REQ = 28;
	public static final int MM_PKT_GETPSMIMG_REQ = 29;
	public static final int MM_PKT_SYNCHECK_REQ = 30; // fix large uin problem

	public static final int MM_PKT_REG_REQ = 31;
	public static final int MM_PKT_NEWREG_REQ = 32;
	public static final int MM_PKT_GETUSERNAME_REQ = 33;
	public static final int MM_PKT_SEARCHCONTACT_REQ = 34;
	public static final int MM_PKT_GETUPDATEINFO_REQ = 35;
	public static final int MM_PKT_ADDCHATROOMMEMBER_REQ = 36;
	public static final int MM_PKT_CREATEROOM_REQ = 37;
	public static final int MM_PKT_GETQQGROUP_REQ = 38;
	public static final int MM_PKT_UPLOADVIDEO_REQ = 39;
	public static final int MM_PKT_DOWNLOADVIDEO_REQ = 40;
	public static final int MM_PKT_SENDINVITEMAIL_REQ = 41;
	public static final int MM_PKT_SENDCARD_REQ = 42;
	public static final int MM_PKT_SENDVERIFYMAIL_REQ = 43;
	public static final int MM_PKT_VERIFYUSER_REQ = 44;
	public static final int MM_PKT_BINDOPMOBILE_REQ = 45;
	public static final int MM_PKT_UPLOADHDHEADIMG_REQ = 46;
	public static final int MM_PKT_GETHDHEADIMG_REQ = 47;
	public static final int MM_PKT_GETVERIFYIMG_REQ = 48;
	public static final int MM_PKT_GETBOTTLECOUNT_REQ = 49;
	public static final int MM_PKT_ADDGROUPCARD_REQ = 50;
	public static final int MM_PKT_GETTHEMELIST_REQ = 51;
	public static final int MM_PKT_DOWNLOADTHEME_REQ = 52;
	public static final int MM_PKT_THROWBOTTLE_REQ = 53;
	public static final int MM_PKT_PICKBOTTLE_REQ = 54;
	public static final int MM_PKT_OPENBOTTLE_REQ = 55;
	public static final int MM_PKT_SHAKEREPORT_REQ = 56;
	public static final int MM_PKT_SHAKEGET_REQ = 57;
	// public static final int MM_PKT_SHAKEMATCH_REQ = 58;
	public static final int MM_PKT_EXPOSE_REQ = 59;
	public static final int MM_PKT_GETVUSERINFO_REQ = 60;
	public static final int MM_PKT_VOIPNOTIFY_REQ = 61;
	public static final int MM_PKT_VOIPSYNC_REQ = 62;
	public static final int MM_PKT_VOIPINVITE_REQ = 63;
	public static final int MM_PKT_VOIPCANCELINVITE_REQ = 64;
	public static final int MM_PKT_VOIPANSWER_REQ = 65;
	public static final int MM_PKT_VOIPSHUTDOWN_REQ = 66;
	public static final int MM_PKT_GETQRCODE_REQ = 67;
	public static final int MM_PKT_SENDEMOJI_REQ = 68;
	public static final int MM_PKT_RECEIVEEMOJI_REQ = 69;
	// public static final int MM_PKT_GENERALSET_REQ = 70;
	public static final int MM_PKT_GETCONTACT_REQ = 71;
	public static final int MM_PKT_OUTOFBAND_REQ = 72;
	public static final int MM_PKT_UPLOADALBUMPHOTO_REQ = 73;
	public static final int MM_PKT_DOWNLOADALBUMPHOTO_REQ = 74;
	public static final int MM_PKT_BATCHGETALBUMPHOTO_REQ = 75;
	public static final int MM_PKT_GETLASTTLPHOTO_REQ = 76;
	public static final int MM_PKT_GETALBUMPHOTOLISTFP_REQ = 77;
	public static final int MM_PKT_DELALBUMPHOTO_REQ = 78;
	public static final int MM_PKT_ALBUMPHOTOCOMMENT_REQ = 79;
	public static final int MM_PKT_GETALBUMPHOTOLISTNP_REQ = 80;
	public static final int MM_PKT_VOIPHEARTBEAT_REQ = 81;
	public static final int MM_PKT_ALBUMSYNC_REQ = 83;
	// public static final int MM_PKT_VOIPSTAT_REQ = 82;
	public static final int MM_PKT_MASSSEND_REQ = 84;
	public static final int MM_PKT_GETTLPHOTOLISTFP_REQ = 86;
	public static final int MM_PKT_GETTLPHOTOLISTNP_REQ = 87;
	public static final int MM_PKT_SPEEDTEST_NOTIFY_REQ = 88;
	public static final int MM_PKT_SPEEDTEST_REQ = 89;
	public static final int MM_PKT_SPEEDTEST_REPORT_REQ = 90;
	public static final int MM_PKT_QUERYDNS_REQ = 91;
	public static final int MM_PKT_VOICEADDR_REQ = 94;
	public static final int MM_PKT_SNSUPLOAD_REQ = 95;
	public static final int MM_PKT_SNSDOWLOAD_REQ = 96;
	public static final int MM_PKT_SNSPOST_REQ = 97;
	public static final int MM_PKT_SNSTIMELINE_REQ = 98;
	public static final int MM_PKT_SNSUSERPAGE_REQ = 99;
	public static final int MM_PKT_SNSCOMMENT_REQ = 100;
	public static final int MM_PKT_SNSOBJECTDETAIL_REQ = 101;
	public static final int MM_PKT_SNSSYNC_REQ = 102;
	public static final int MM_PKT_SNSLBS_REQ = 103;
	public static final int MM_PKT_SNSOBJECTOP_REQ = 104;
	public static final int MM_PKT_UPLOADAPPATTACH_REQ = 105;
	public static final int MM_PKT_DOWNLOADAPPATTACH_REQ = 106;
	public static final int MM_PKT_SENDAPPMSG_REQ = 107;
	public static final int MM_PKT_LBSFIND_REQ = 110;
	public static final int MM_PKT_UPLOADMEDIA_REQ = 111;

	public static final int MM_PKT_GETCITY_REQ = 113;

	public static final int MM_PKT_SNSTAGOPTION_REQ = 114;
	public static final int MM_PKT_SNSTAGMEMBEROPTION_REQ = 115;
	public static final int MM_PKT_SNSTAGLIST_REQ = 116;
	public static final int MM_PKT_SNSTAGMEMMUTILSET_REQ = 117;

	public static final int MM_PKT_VOIP_REQ = 120; // voip请求

	public static final int MM_PKT_PUSH_SYNC_REQ = 121;
	public static final int MM_PKT_PUSH_DATA_REQ = 122;
	public static final int MM_PKT_VOIP_ACK_REQ = 123;
	public static final int MM_PKT_VOIPINVITEREMIND_REQ = 125;
	public static final int MM_PKT_VOIPGETDEVICEINFO_REQ = 126;

	public static final int MM_PKT_SHAKE_TRANIMG_REPORT_REQ = 127;
	public static final int MM_PKT_SHAKE_TRANIMG_GET_REQ = 128;
	public static final int MM_PKT_SHAKE_TRANIMG_BATCH_GET_REQ = 129;
	public static final int MM_PKT_SHAKE_TRANIMG_UNBIND_REQ = 130;

	public static final int MM_PKT_CHECKUNBIND_REQ = 131;
	public static final int MM_PKT_QUERYHASPSWD_REQ = 132;

	public static final int MM_PKT_BAKCHAT_UPLOAD_HEAD_REQ = 134;
	public static final int MM_PKT_BAKCHAT_UPLOAD_END_REQ = 135;
	public static final int MM_PKT_BAKCHAT_UPLOAD_MSG_REQ = 136;
	public static final int MM_PKT_BAKCHAT_UPLOAD_MEDIA_REQ = 137;
	public static final int MM_PKT_BAKCHAT_RECOVER_GETLIST_REQ = 138;
	public static final int MM_PKT_BAKCHAT_RECOVER_HEAD_REQ = 139;
	public static final int MM_PKT_BAKCHAT_RECOVER_DATA_REQ = 140;
	public static final int MM_PKT_BAKCHAT_DELETE_REQ = 141;
	public static final int MM_PKT_TALK_MIC_ACTION_REQ = 146;
	public static final int MM_PKT_ENTER_TALKROOM_REQ = 147;
	public static final int MM_PKT_EXIT_TALKROOM_REQ = 148;
	public static final int MM_PKT_TALK_NOOP_REQ = 149;
	public static final int MM_PKT_GETA8KEY_REQ = 155;
	public static final int MM_PKT_SETEMAILPWD_REQ = 156;
	public static final int MM_PKT_UPLOADVOICEREMIND_REQ = 157;
	public static final int MM_PKT_OPVOICEREMIND_REQ = 150;

	public static final int MM_PKT_CREATE_SUBUSER_REQ = 159;
	public static final int MM_PKT_UPDATE_SUBUSER_REQ = 160;
	public static final int MM_PKT_UNBIND_SUBUSER_REQ = 161;
	public static final int MM_PKT_GET_SUBUSER_LIST_REQ = 162;

	public static final int MM_PKT_GET_CARD_LIST_REQ = 163;
	public static final int MM_PKT_GET_CARD_INFO_REQ = 164;
	public static final int MM_PKT_INSERT_CARD_REQ = 165;
	public static final int MM_PKT_DELETE_CARD_REQ = 166;

	public static final int MM_PKT_CREATE_TALKROOM_REQ = 168;
	public static final int MM_PKT_ADD_TALKROOM_MEMBER_REQ = 169;
	public static final int MM_PKT_DEL_TALKROOM_REQ = 170;

	public static final int MM_PKT_Get_BRAND_LIST_REQ = 173;

	public static final int MM_PKT_TALK_INVITE_REQ = 174;

	public static final int MM_PKT_TRANSFER_CARD_REQ = 175;
	public static final int MM_PKT_CLICK_COMMAND_REQ = 176;

	public static final int MM_PKT_SHAKE_MUSIC_REQ = 177;

	public static final int MM_PKT_NEWAUTH_REQ = 178;
	public static final int MM_PKT_GETCERT_REQ = 179;
	public static final int MM_PKT_NEWSETPASSWD_REQ = 180;
	public static final int MM_PKT_NEWSETEMAILPWD_REQ = 181;
	public static final int MM_PKT_NEWVERIFYPASSWD_REQ = 182;
	public static final int MM_PKT_JOINLBSROOM_REQ = 183;
	public static final int MM_PKT_GETROOMMEMBER_REQ = 184;

	public static final int MM_PKT_PUSH_SYNC_NOTIFY_DATA = 1000000122; // 你没看错, 他是一个client的req

	public static final int MM_PKT_INVALID_RESP = 0;
	public static final int MM_PKT_AUTH_RESP = 1000000001;
	public static final int MM_PKT_SENDMSG_RESP = 1000000002;
	public static final int MM_PKT_SYNC_RESP = 1000000003;
	public static final int MM_PKT_TOKEN_RESP = 1000000004;
	public static final int MM_PKT_NOOP_RESP = 1000000006;
	public static final int MM_PKT_UPLOADMSGIMG_RESP = 1000000009;
	public static final int MM_PKT_GETMSGIMG_RESP = 1000000010;
	// public static final int MM_PKT_SYNCHECK_RESP = 1000000013;
	public static final int MM_PKT_INIT_RESP = 1000000014;
	public static final int MM_PKT_GETUSERIMG_RESP = 1000000015;
	public static final int MM_PKT_GETUPDATEPACK_RESP = 1000000016;
	public static final int MM_PKT_SEARCHFRIEND_RESP = 1000000017;
	// public static final int MM_PKT_GETINVITEFRIEND_RESP = 1000000018;
	public static final int MM_PKT_UPLOADVOICE_RESP = 1000000019;
	public static final int MM_PKT_DOWNLOADVOICE_RESP = 1000000020;
	// public static final int MM_PKT_UPLOADWEIBOIMG_RESP = 1000000021;
	// public static final int MM_PKT_ASYNCDOWNLOADVOICE_RESP = 1000000022;
	public static final int MM_PKT_NEWGETINVITEFRIEND_RESP = 1000000023;
	public static final int MM_PKT_NEWSYNCCHECK_RESP = 1000000025;
	public static final int MM_PKT_NEWSYNC_RESP = 1000000026;
	public static final int MM_PKT_NEWINIT_RESP = 1000000027;
	public static final int MM_PKT_BATCHGETCONTACTPROFILE_RESP = 1000000028;
	public static final int MM_PKT_GETPSMIMG_RESP = 1000000029;
	public static final int MM_PKT_SYNCHECK_RESP = 1000000030;
	public static final int MM_PKT_REG_RESP = 1000000031;
	public static final int MM_PKT_NEWREG_RESP = 1000000032;
	public static final int MM_PKT_GETUSERNAME_RESP = 1000000033;
	public static final int MM_PKT_SEARCHCONTACT_RESP = 1000000034;
	public static final int MM_PKT_GETUPDATEINFO_RESP = 1000000035;
	public static final int MM_PKT_ADDCHATROOMMEMBERRESP = 1000000036;
	public static final int MM_PKT_CREATEROOMRESP = 1000000037;
	public static final int MM_PKT_GETQQGROUPRESP = 1000000038;
	public static final int MM_PKT_UPLOADVIDEO_RESP = 1000000039;
	public static final int MM_PKT_DOWNLOADVIDEO_RESP = 1000000040;
	public static final int MM_PKT_SENDINVITEMAIL_RESP = 1000000041;
	public static final int MM_PKT_SENDCARD_RESP = 1000000042;
	public static final int MM_PKT_SENDVERIFYMAIL_RESP = 1000000043;
	public static final int MM_PKT_VERIFYUSER_RESP = 1000000044;
	public static final int MM_PKT_BINDOPMOBILE_RESP = 1000000045;
	public static final int MM_PKT_UPLOADHDHEADIMG_RESP = 1000000046;
	public static final int MM_PKT_GETHDHEADIMG_RESP = 1000000047;
	public static final int MM_PKT_GETVERIFYIMG_RESP = 1000000048;
	public static final int MM_PKT_GETBOTTLECOUNT_RESP = 1000000049;
	public static final int MM_PKT_ADDGROUPCARD_RESP = 1000000050;
	public static final int MM_PKT_GETTHEMELIST_RESP = 1000000051;
	public static final int MM_PKT_DOWNLOADTHEME_RESP = 1000000052;
	public static final int MM_PKT_THROWBOTTLE_RESP = 1000000053;
	public static final int MM_PKT_PICKBOTTLE_RESP = 1000000054;
	public static final int MM_PKT_OPENBOTTLE_RESP = 1000000055;
	public static final int MM_PKT_SHAKEREPORT_RESP = 1000000056;
	public static final int MM_PKT_SHAKEGET_RESP = 1000000057;
	// public static final int MM_PKT_SHAKEMATCH_RESP = 1000000058;
	public static final int MM_PKT_EXPOSE_RESP = 1000000059;
	public static final int MM_PKT_GETVUSERINFO_RESP = 1000000060;
	public static final int MM_PKT_VOIPNOTIFY_RESP = 1000000061;
	public static final int MM_PKT_VOIPSYNC_RESP = 1000000062;
	public static final int MM_PKT_VOIPINVITE_RESP = 1000000063;
	public static final int MM_PKT_VOIPCANCELINVITE_RESP = 1000000064;
	public static final int MM_PKT_VOIPANSWER_RESP = 1000000065;
	public static final int MM_PKT_VOIPSHUTDOWN_RESP = 1000000066;
	public static final int MM_PKT_GETQRCODE_RESP = 1000000067;
	public static final int MM_PKT_SENDEMOJI_RESP = 1000000068;
	public static final int MM_PKT_RECEIVEEMOJI_RESP = 1000000069;
	// public static final int MM_PKT_GENERALSET_RESP = 1000000070;
	public static final int MM_PKT_GETCONTACT_RESP = 1000000071;
	public static final int MM_PKT_UPLOADALBUMPHOTO_RESP = 1000000073;
	public static final int MM_PKT_DOWNLOADALBUMPHOTO_RESP = 1000000074;
	public static final int MM_PKT_BATCHGETALBUMPHOTO_RESP = 1000000075;
	public static final int MM_PKT_GETLASTTLPHOTO_RESP = 1000000076;
	public static final int MM_PKT_GETALBUMPHOTOLISTFP_RESP = 1000000077;
	public static final int MM_PKT_DELALBUMPHOTO_RESP = 1000000078;
	public static final int MM_PKT_ALBUMPHOTOCOMMENT_RESP = 1000000079;
	public static final int MM_PKT_GETALBUMPHOTOLISTNP_RESP = 1000000080;
	public static final int MM_PKT_VOIPHEARTBEAT_RESP = 1000000081;
	public static final int MM_PKT_ALBUMSYNC_RESP = 1000000083;
	public static final int MM_PKT_MASSSEND_RESP = 1000000084;
	public static final int MM_PKT_GETTLPHOTOLISTFP_RESP = 1000000086;
	public static final int MM_PKT_GETTLPHOTOLISTNP_RESP = 1000000087;
	public static final int MM_PKT_SPEEDTEST_RESP = 1000000089;
	public static final int MM_PKT_SPEEDTEST_REPORT_RESP = 1000000090;
	public static final int MM_PKT_QUERYDNS_RESP = 1000000091;
	public static final int MM_PKT_VOICEADDR_RESP = 1000000094;
	public static final int MM_PKT_SNSUPLOAD_RESP = 1000000095;
	public static final int MM_PKT_SNSDOWNLOAD_RESP = 1000000096;
	public static final int MM_PKT_SNSPOST_RESP = 1000000097;
	public static final int MM_PKT_SNSTIMELINE_RESP = 1000000098;
	public static final int MM_PKT_SNSUSERPAGE_RESP = 1000000099;
	public static final int MM_PKT_SNSCOMMENT_RESP = 1000000100;
	public static final int MM_PKT_SNSOBJECTDETAIL_RESP = 1000000101;
	public static final int MM_PKT_SNSSYNC_RESP = 1000000102;
	public static final int MM_PKT_SNSLBS_RESP = 1000000103;
	public static final int MM_PKT_SNSOBJECTOP_RESP = 1000000104;
	public static final int MM_PKT_LBSFIND_RESP = 1000000110;

	public static final int MM_PKT_UPLOADMEDIA_RESP = 1000000111;

	public static final int MM_PKT_GETCITY_RESP = 1000000113;

	public static final int MM_PKT_SNSTAGOPTION_RESP = 1000000114;
	public static final int MM_PKT_SNSTAGMEMBEROPTION_RESP = 1000000115;
	public static final int MM_PKT_SNSTAGLIST_RESP = 1000000116;
	public static final int MM_PKT_SNSTAGMEMMUTILSET_RESP = 1000000117;

	public static final int MM_PKT_PUSH_SYNC_RESP = 1000000121;
	public static final int MM_PKT_VOIP_ACK_RESP = 1000000123;
	public static final int MM_PKT_VOIPINVITEREMIND_RESP = 1000000125;
	public static final int MM_PKT_VOIPGETDEVICEINFO_RESP = 1000000126;

	public static final int MM_PKT_SHAKE_TRANIMG_REPORT_RESP = 1000000127;
	public static final int MM_PKT_SHAKE_TRANIMG_GET_RESP = 1000000128;
	public static final int MM_PKT_SHAKE_TRANIMG_BATCH_GET_RESP = 1000000129;
	public static final int MM_PKT_SHAKE_TRANIMG_UNBIND_RESP = 1000000130;

	public static final int MM_PKT_CHECKUNBIND_RESP = 1000000131;
	public static final int MM_PKT_QUERYHASPSWD_RESP = 1000000132;

	public static final int MM_PKT_BAKCHAT_UPLOAD_HEAD_RESP = 1000000134;
	public static final int MM_PKT_BAKCHAT_UPLOAD_END_RESP = 1000000135;
	public static final int MM_PKT_BAKCHAT_UPLOAD_MSG_RESP = 1000000136;
	public static final int MM_PKT_BAKCHAT_UPLOAD_MEDIA_RESP = 1000000137;
	public static final int MM_PKT_BAKCHAT_RECOVER_GETLIST_RESP = 1000000138;
	public static final int MM_PKT_BAKCHAT_RECOVER_HEAD_RESP = 1000000139;
	public static final int MM_PKT_BAKCHAT_RECOVER_DATA_RESP = 1000000140;
	public static final int MM_PKT_BAKCHAT_DELETE_RESP = 1000000141;

	public static final int MM_PKT_TALK_MIC_ACTION_RESP = 1000000146;
	public static final int MM_PKT_ENTER_TALKROOM_RESP = 1000000147;
	public static final int MM_PKT_EXIT_TALKROOM_RESP = 1000000148;
	public static final int MM_PKT_TALK_NOOP_RESP = 1000000149;

	public static final int MM_PKT_SETEMAILPWD_RESP = 1000000156;

	public static final int MM_PKT_UPLOADVOICEREMIND_RESP = 1000000157;
	public static final int MM_PKT_OPVOICEREMIND_RESP = 1000000150;
	public static final int MM_PKT_GETA8KEY_RESP = 1000000155;
	public static final int MM_PKT_CREATE_SUBUSER_RESP = 1000000159;
	public static final int MM_PKT_UPDATE_SUBUSER_RESP = 1000000160;
	public static final int MM_PKT_UNBIND_SUBUSER_RESP = 1000000161;
	public static final int MM_PKT_GET_SUBUSER_LIST_RESP = 1000000162;

	public static final int MM_PKT_CREATE_TALKROOM_RESP = 1000000168;
	public static final int MM_PKT_ADD_TALKROOM_MEMBER_RESP = 1000000169;
	public static final int MM_PKT_DEL_TALKROOM_RESP = 1000000170;

	public static final int MM_PKT_GET_BRAND_LIST_RESP = 1000000173;

	public static final int MM_PKT_TALK_INVITE_RESP = 1000000174;

	public static final int MM_PKT_HEADER_LEN = 16;
	public static final int MM_PKT_VERSION = 0x0001;

	public static final int MM_PKT_GET_CARD_LIST_RESP = 1000000163;
	public static final int MM_PKT_GET_CARD_INFO_RESP = 1000000164;
	public static final int MM_PKT_INSERT_CARD_RESP = 1000000165;
	public static final int MM_PKT_DELETE_CARD_RESP = 1000000166;

	public static final int MM_PKT_TRANSFER_CARD_RESP = 1000000175;
	public static final int MM_PKT_CLICK_COMMAND_RESP = 1000000176;

	public static final int MM_PKT_SHAKE_MUSIC_RESP = 1000000177;

	public static final int MM_PKT_LBSROOM_RESP = MM_PKT_INVALID_RESP;
	public static final int MM_PKT_LBSROOM_GET_MEMBER_RESP = MM_PKT_INVALID_RESP;

	public static final int MM_PKT_NEWAUTH_RESP = 1000000178;
	public static final int MM_PKT_GETCERT_RESP = 1000000179;
	public static final int MM_PKT_NEWSETPASSWD_RESP = 1000000180;
	public static final int MM_PKT_NEWSETEMAILPWD_RESP = 1000000181;
	public static final int MM_PKT_NEWVERIFYPASSWD_RESP = 1000000182;
	public static final int MM_PKT_JOINLBSROOM_RESP = 1000000183;
	public static final int MM_PKT_GETROOMMEMBER_RESP = 1000000184;

	// enMMStatus
	public static final int MM_STATUS_OPEN = 0x1;
	public static final int MM_STATUS_EMAILVERIFY = 0x2;
	public static final int MM_STATUS_MOBILEVERIFY = 0x4;
	public static final int MM_STATUS_QQ_SEARCH_CLOSE = 0x08;
	public static final int MM_STATUS_QQ_PROMOTE_CLOSE = 0x10;
	public static final int MM_STATUS_VERIFY_USER = 0x20;
	public static final int MM_STATUS_QMSG = 0x40;
	public static final int MM_STATUS_QQ_PROMOTE_TOME_CLOSE = 0x80;
	public static final int MM_STATUS_PROMOTE_TOME_CLOSE = 0x100;
	public static final int MM_STATUS_MOBILE_SEARCH_CLOSE = 0x200;
	public static final int MM_STATUS_ADD_CONTACT_CLOSE = 0x400;
	public static final int MM_STATUS_FLOATBOTTLE_OPEN = 0x1000;
	public static final int MM_STATUS_WEIXIN_ONLINE_OPEN = 0x2000;
	public static final int MM_STATUS_MEDIANOTE_OPEN = 0x4000;
	public static final int MM_STATUS_BOTTLE_CHART_OPEN = 0x8000;
	public static final int MM_STATUS_UPLOADMCONTACT_CLOSE = 0x20000;

	// enMMUserattrFlag 微博
	public static final int MM_USERATTR_WEIBO_SHOW = 0x1;
	public static final int MM_USERATTR_WEIBO_VERIFY = 0x2;
	public static final int MM_USERATTR_WEIBO_SHOWICON = 0x4;

	// enMMAlbumType
	public static final int MM_ALBUM_PHOTO = 1; // 照片
	public static final int MM_ALBUM_SIGN = 2; // 签名
	public static final int MM_ALBUM_BGIMG_UPLOAD = 4; // 背景图片

	// enAlbumPhotoType
	public static final int MM_ALBUM_PHOTO_SMALL = 1; // 小图
	public static final int MM_ALBUM_PHOTO_MIDDLE = 2; // 中图
	public static final int MM_ALBUM_PHOTO_BIG = 3; // 大图
	public static final int MM_ALBUM_BGIMG = 4; // 背景图片
	public static final int MM_ALBUM_BGIMG_INTL = 5; // 推送到别人timeline的背景图片

	public static final int MM_ABLUM_PHOTOATTR_ISPRIVATED = 0x1; // 是否私密照片
	//
	public static final int MM_ALBUM_PHOTO_NOT_PRIVATED = 0; //
	public static final int MM_ALBUM_PHOTO_PRIVATED = 1; //

	// FP NP 拉的类型
	public static final int MM_GETALBUM_PHOTO = 1;
	public static final int MM_GETALBUM_SIGN = 2;
	public static final int MM_GETALBUM_PHOTO_AND_SIGN_MIX = 3;
	public static final int MM_GETALBUM_PHOTO_AND_SIGN_NOT_MIX = 4;

	public static final int MM_ALBUM_NOT_LIKE = 0; // 未点赞
	public static final int MM_ALBUM_HAVE_LIKE = 1; // 已赞

	public static final int MM_ALBUM_SYNC2_TXWEIBO = 0x1; // 同步到腾讯微薄

	public static final int MM_ALBUM_COMMENT_LIKE = 1; // 标红心
	public static final int MM_ALBUM_COMMENT_WORD = 2; // 评论
	public static final int MM_ALBUM_COMMENT_REPLY = 3; // 回复评论
	public static final int MM_ALBUM_COMMENT_WITH = 4; // 提到的人
	public static final int MM_ALBUM_COMMENT_LIKE_BGIMG = 5; // 赞背景

	// enPluginFlag
	public static final int MM_QQMAIL_UNINSTALL = 0x1; // QQ邮箱卸载标志
	public static final int MM_PM_UNINSTALL = 0x2; // 私信卸载标志
	public static final int MM_FM_UNINSTALL = 0x4; // 好友推荐助手卸载标志
	public static final int MM_WEIBO_UNINSTALL = 0x8; // 微博发图助手卸载标志
	public static final int MM_MEDIANOTE_UNINSTALL = 0x10; // 语音记事本卸载标志
	public static final int MM_QMSG_UNINSTALL = 0x20; // QQ离线消息卸载标志
	public static final int MM_BOTTLE_UNINSTALL = 0x40; // 漂流瓶卸载标志
	public static final int MM_QSYNC_UNISTALL = 0x80; // QQ同步助手卸载标志
	public static final int MM_SHAKE_UNISTALL = 0x100; // 撸一撸卸载标志
	public static final int MM_LBS_UNISTALL = 0x200; // lbs卸载标志
	public static final int MM_BOTTLE_CHART_INSTALL = 0x400; //
	// public static final int MM_GMAIL_UNINSTALL = 0x800; // Gmail卸载标志
	public static final int MM_CHECKQQF_UNINSTALL = 0x1000; // 查看QQ好友卸载标志
	public static final int MM_FACEBOOK_UNINSTALL = 0x2000; // FaceBook卸载标志
	public static final int MM_READERAPP_UNINSTALL = 0x80000; // Reader卸载标志 MM_PLUGIN_NEWSAPP = 0x80000,
	public static final int MM_FEEDSAPP_UNINSTALL = 0x8000; // 个人相册
	public static final int MM_MASSSEND_UNINSTALL = 0x10000; // 群发
	public static final int MM_MEISHI_UNINSTALL = 0x20000; // 美食
	public static final int MM_BLOG_UNINSTALL = 0x40000; // 微博精选推选

	public static final int MM_VOIP_UNINSTALL = 0x100000; // voip

	public static final int MM_VOICEVOIP_UNINSTALL = 0x400000; // audio voip

	public static final int MM_VOICEREMINDER_UNINSTALL = 0x200000;
	public static final int MM_CARDPACKAGE_UNINSTALL = 0x1000000;

	// enMMImgStatus
	public static final int MM_IMGSTATUS_NOIMG = 1;
	public static final int MM_IMGSTATUS_HASIMG = 2;

	public static final int MM_MEMBER_OK = 0;
	public static final int MM_MEMBER_NOUSER = 1;
	public static final int MM_MEMBER_USERNAMEINVALID = 2;
	public static final int MM_MEMBER_BLACKLIST = 3;
	public static final int MM_MEMBER_NEEDVERIFYUSER = 4;

	// enMMSexType
	public static final int MM_SEX_UNKNOWN = 0;
	public static final int MM_SEX_MALE = 1;
	public static final int MM_SEX_FEMALE = 2;

	// BatchGetContactProfile mode
	public static final int MM_NEED_MODE = 0; // 下发头像
	public static final int MM_NOT_NEED_MODE = 1; // 不下发头像

	// BatchGetContactProfile retCode
	public static final int MM_RETCODE_CONTINUE = 2; // 包已满，塞不下 // 建议：客户端应该继续拿还没请求到的list上来
	public static final int MM_RETCODE_SHOULD_STOP = 1; // 表示整个list内部不存在对应contact // 客户端不应该再次上来，否则会死循环
	public static final int MM_RETCODE_SUCCESS = 0; // 表示成功 // 请求列表已完成返回，客户端不应该再上来
	public static final int MM_RETCODE_SERVER_ERR = -1; // 表示服务器发生内部错误 // 建议：服务内部有问题，建议客户端做一定次数重试，

	// update opcode
	public static final int MM_UPDATE_ALERT_YES = 0x1;
	public static final int MM_UPDATE_ALERT_NO = 0x2;
	public static final int MM_UPDATE_NEW_CLICK = 0x4;
	public static final int MM_UPDATE_UPDATE_CLICK = 0x8;
	public static final int MM_UPDATE_CHECK_UPDATE = 0x10;

	// enNewRegReturnFlag
	public static final int MM_NEWREG_RETURNFLAG_NEEDQFRAME = 0x1;

	// public static final int MM_MAX_CHATROOM_MEMBER_COUNT = 10;

	public static final int MM_HD_HEADIMG_WEIXIN = 1;
	public static final int MM_HD_HEADIMG_BOTTLE = 2;

	public static final int MM_BOTTLE_TEXT = 1;
	public static final int MM_BOTTLE_IMG = 2;
	public static final int MM_BOTTLE_VOICE = 3;
	public static final int MM_BOTTLE_VIDEO = 4;
	public static final int MM_BOTTLE_BRAND = 19990; // self defined
	public static final int MM_BOTTLE_PICKCOUNTINVALID = 19;

	public static final int MM_FRIENDTYPE_QQ = 0;
	public static final int MM_FRIENDTYPE_BLOG = 1;
	public static final int MM_FRIENDTYPE_EMAIL = 2;
	public static final int MM_FRIENDTYPE_WEIXIN = 3;
	public static final int MM_FRIENDTYPE_SMS = 4;
	public static final int MM_FRIENDTYPE_Facebook = 5;

	// gmail constants
	// gmail status
	public static final int MM_GMAIL_OK = 0;
	public static final int MM_GMAIL_PWD = 1;
	public static final int MM_GMAIL_ONLINELIMITE = 2;
	public static final int MM_GMAIL_WEBLOGIN = 3;
	public static final int MM_GMAIL_IMAP = 4;

	// enPluginSwitch 插件状态（新），旧的在UserStatus里
	public static final int MM_STATUS_GMAIL_OPEN = 0x1; // gmail开关
	public static final int MM_STATUS_TXWEIBO_OPEN = 0x2; // 微博url开关
	public static final int MM_STATUS_RECFBFRIEND_OPEN = 0x4; // facebook好友推荐开关
	// public static final int MM_STATUS_READAPP_PUSH_OPEN = 0x8; // readerapp 推送开关
	// public static final int MM_STATUS_READAPP_TXNEWS_OPEN = 0x10; // readerapp 腾讯新闻推送开关
	public static final int MM_STATUS_READAPP_WB_OPEN = 0x20; // readerapp 微博精选推送开关
	public static final int MM_STATUS_TXWEIBO_ICON_OPEN = 0x40; // 是否显示腾讯微博图标开关
	public static final int MM_STATUS_MASSSEND_SHOW_IN_CHATLIST_OPEN = 0x80; // 群发主界面显示开关
	public static final int MM_STATUS_MEISHI_CARD_OPEN = 0x100; // QQ美食特惠插件开关
	public static final int MM_STATUS_USERNAME_SEARCH_CLOSE = 0x200; // search contact 中微信号开关
	public static final int MM_STATUS_NEWSAPP_TXNEWS_CLOSE = 0x400; // newsapp 腾讯新闻推送开关, 注意这个位置上后表示关闭
	public static final int MM_STATUS_PRIVATEMSG_RECV_CLOSE = 0x800; // 私信推送开关，注意这个位置上后表示关闭
	public static final int MM_STATUS_PUSHMAIL_NOTIFY_CLOSE = 0x1000; // pushmail 推送开关， 注意这个位置上后表示关闭
	public static final int MM_STATUS_WEBONLINE_PUSH_OPEN = 0x2000; // webwx在线，是否打开PUSH
	public static final int MM_STATUS_SAFEDEVICE_OPEN = 0x4000; // for safedevice function switch

	public static final int MM_EMOJIGame_CONTENT_J = 1;
	public static final int MM_EMOJIGame_CONTENT_S = 2;
	public static final int MM_EMOJIGame_CONTENT_B = 3;
	public static final int MM_EMOJIGame_CONTENT_1 = 4;
	public static final int MM_EMOJIGame_CONTENT_2 = 5;
	public static final int MM_EMOJIGame_CONTENT_3 = 6;
	public static final int MM_EMOJIGame_CONTENT_4 = 7;
	public static final int MM_EMOJIGame_CONTENT_5 = 8;
	public static final int MM_EMOJIGame_CONTENT_6 = 9;

	public static final int MM_EMOJI_NOTGAME = 0;
	public static final int MM_EMOJI_JSB = 1;
	public static final int MM_EMOJI_DICE = 2;

	public static final int MM_EMOJI_GAME_REPORT_CODE = 56;

	// enum emMMPackageType
	public static final int MM_PACKAGE_THEME = 0;
	public static final int MM_PACKAGE_SESSION_BACKGROUND = 1;
	public static final int MM_PACKAGE_EMOJIART = 2;
	public static final int MM_PACKAGE_RESOURCE = 3;
	public static final int MM_PACKAGE_ALBUM = 4;
	// TODO 5 is not used
	public static final int MM_PACKAGE_EGG = 6;
	public static final int MM_PACKAGE_CONFIG_LIST = 7;
	public static final int MM_PACKAGE_REGION_CODE = 8;
	public static final int MM_PACKAGE_PATTERN = 9;
	public static final int MM_PACKAGE_MASS_SEND = 10;

	public static final int MM_EMOJI_TYPE_PNG = 1;
	public static final int MM_EMOJI_TYPE_GIF = 2;
	public static final int MM_EMOJI_TYPE_JPG = 3;
	public static final int MM_APPEMOJI_TYPE_GIF = 4;
	public static final int MM_APPEMOJI_TYPE_JPG = 5;

	// enum enKVStatKey
	public static final int MM_GETDATA_TIMECOST = 1; // 收取耗时
	public static final int MM_DISCONNECT_WIFI = 2; // wifi下 无连接的次数
	public static final int MM_DISCONNECT_NOTWIFI = 3; // 非wifi下 无连接的次数
	public static final int MM_IPXX = 4;
	public static final int MM_LINKCHANGE_COUNT = 5; // 3¤á??ó′ó?μ·±3￠ê?±??ˉ?a1·??óò?′?3￠ê? μ?′?êy
	public static final int MM_QQSYNC_OPER = 6;
	public static final int MM_ANDROID_REQ_TIMECOST = 7;
	public static final int MM_IPHONE_CRASH_INFO = 8;
	public static final int MM_BACKGROUND_USED = 9; // 背景使用
	public static final int MM_CLICK_FRIEND_SCENE = 10; // 点击好友推荐卡片的场景次数统计
	public static final int MM_SYMBIAN_REQ_TIMECOST = 11; // 塞班请求耗时
	public static final int MM_SEND_PIC_2_QZONE = 12;
	public static final int MM_CRASHREPORT_ONCE = 13;
	public static final int MM_CRASHREPORT_MORE = 14;
	public static final int MM_SHARELOCATION_DETAIL_GOOGLE = 15; // 分享地理位置通过google查看详细位置
	public static final int MM_SHARELOCATION_DETAIL_OTHER = 16; // 分享地理位置通过其他查看详细位置
	public static final int MM_NEWYEAR_EGG_SEND = 17; // 彩蛋发送上报oplog.key
	public static final int MM_NEWYEAR_EGG_RECV = 18; // 彩蛋接收上报oplog.key
	public static final int MM_WEIXIN_SHOW = 19; // show count
	// public static final int MM_SYMBIAN_ERROR = 20;
	public static final int MM_DEVICE_PLATFORM = 21;
	public static final int MM_MEDIA_TRANSFER = 23;
	/** < 图片视频支持转发 （转发次数、人数） */
	public static final int MM_SHOW_PWD_PLAIN_TXT = 24;
	/** < 输入密码时可以选择显示明文 （选择显示明文的人数、次数） */
	public static final int MM_ADJUST_FONT_SIZE = 25;
	/** < 在设置中添加调整字体大小的选项 （每个字体号的选择人数） */
	public static final int MM_QQ_MOBILE_ADD_FRIEND = 26;
	/** < QQ好友列表、手机通讯录好友列表里快捷添加好友按钮 （点击此按钮的人数） */
	public static final int MM_APPMSG_SWITCH = 27; /* 由微信进入第3方APP发送消息 */
	public static final int MM_SNS_CHECK_BIGPIC = 28; // sns查看大图
	// public static final int MM_SNS_CHECK_SELFPAGE = 29; //sns查看自己相册

	// Facebook
	public static final int MM_FACEBOOK_CONNECT = 31; // 点击facebook Connect的用户数据
	public static final int MM_FACEBOOK_LINK = 32; // 点击facebook link的用户数据
	public static final int MM_FACEBOOK_INVITE = 33; // 点击facebook invite的用户数据

	public static final int MM_APP_UPDATE_STATE = 36; // 程序升级状态（成功/失败）
	public static final int MM_APP_EMERGENCY_TRIGGER = 37; // 程序应急启动

	public static final int MM_OPEN_MAIL_STAT = 10071; // 每天访问邮箱
	public static final int MM_APPMSG2WEIXIN = 38; // 第3方appmsg发送到微信的消息

	public static final int MM_SNS_TAKE_OR_SELECT_PIC = 10072; // 拍照或者选图统计
	public static final int MM_FIRST_SEND_MSG_TO_QQ_CONTACT = 10076; // 主动发起QQ离线消息(chattingUI列表为空时)
	public static final int SnsCheckSelfPage = 10077; // sns查看自己相册

	public static final int MM_EMBEDMUSICPLAYCOUNT = 10090; // 点击内置播放器统计

	public static final int MM_EMBUDMUSICSTOPCOUNT = 10231; // 停止音乐播放器统计

	/** 是否“全选”上传（限成功上传情况下） 1.全选， 0.非全选 */
	public static final int MM_BAKCHAT_IS_SELECT_ALL = 10319;

	/** 备份下载次数 */
	public static final int MM_BAKCHAT_DOWNLOAD_SUM = 10320;

	/**
	 * 备份成功下载结果,
	 * Size 下载内容大小 unsigned int 0
	 * Interval 上传成功到开始下载时间间隔（单位小时） unsigned int 0
	 * Time 备份下载和合并时间（单位秒） unsigned int 0
	 * DownloadTime 备份下载(不含合并)时间（单位秒） unsigned int 0
	 * UploadDeviceType 上传该备份数据的设备类型 unsigned int 0 1：iphone；2：android 3：s60v3 4：s60v5；5：wp7；-1：其他
	 * NetworkType 备份时客户端的网络类型 unsigned int 0 1：edge； 2:3g； 3：wifi
	 * ErrCount 下载过程中出现的错误次数 unsigned int 0
	 * IsSuccess 是否成功下载 unsigned int 0 1 成功 2 失败
	 * DownloadSize 已下载的大小 unsigned int 0
	 * IsCrypted 是否加密 unsigned int 0 1 是 2 否
	 * PauseCount 暂停次数 unsigned int 0
	 */
	public static final int MM_BAKCHAT_DOWNLOAD_SUCCESS_RESULT = 10321;

	/**
	 * 备份上传结果（新版
	 * Size 已上传内容大小 unsigned int 0
	 * Time 备份上传时间（单位秒） unsigned int 0
	 * NetworkType 备份时客户端的网络类型 unsigned int 0 1：edge； 2:3g； 3：wifi
	 * ErrCount 下载过程中出现的错误次数 unsigned int 0
	 * IsSuccess 是否成功下载 unsigned int 0 1 成功 2 失败
	 * IsCovered 是否覆盖上传 unsigned int 0 1 是 2 否
	 * IsSelectAll 是否全选上传 unsigned int 0 1 是 2 否
	 * IsCrypted 是否加密 unsigned int 0 1 是 2 否
	 * PauseCount 暂停次数 unsigned int 0
	 */
	public static final int BakchatUploaddResultNew = 10339;

	/**
	 * Mode 上报时机 unsigned int 0 1 上传， 2 下载
	 * ErrInfo 错误信息 char[1024] 0 "errType / errCode"
	 */
	public static final int BakchatErrInfo = 10341;
	public static final int BAKCHAT_MODE_UPLOAD = 1;
	public static final int BAKCHAT_MODE_DOWNLOAD = 2;

	public static final int MM_MAIN_FRAME_CREATECHAT = 10108; // 魔法棒点击发起聊天
	public static final int MM_MAIN_FRAME_EAR = 10109; // 魔法棒点击切换扬声器模式
	public static final int MM_MAIN_FRAME_WEBWX = 10110; // 魔法棒点击连接键盘
	public static final int MM_MAIN_FRAME_QRCODE = 10111; // 魔法棒点击扫描二维码
	public static final int MM_VOIP_TENMINS = 10166; // 十分钟逻辑统计

	public static final int MM_GET_LBS_IMG_FROM_CDN = 10073;
	public static final int MM_GET_SNS_IMG_FROM_CDN = 10074;
	public static final int MM_GET_SNS_HDIMG_FROM_CDN = 10075;
	public static final int MM_LANGUAGE_DOWNLOAD_TIMECOST = 10095;
	public static final int MM_APP_INSTALL = 10165;
	public static final int MM_CHAT_CREATE_CHATROOM_CLICK = 10170;
	public static final int MM_CHATROOM_ADD_CONTACT_CLICK = 10169;
	public static final int MM_CONTACTLIST_CREATE_CHAT_CLICK = 10168;

	// voice search
	public static final int MM_VOICE_SEARCH_RESULT_TO_CONTACT_INFO = 10234;

	// 二维码
	public static final int MM_IMPORT_QR_FROM_PHOTO_ALBUM = 10237;
	public static final int MM_ADD_NEW_CONTACT_FROM_QR_SCAN = 10238;
	public static final int MM_ADD_TO_EXISTED_CONTACT_FROM_QR_SCAN = 10239;

	public static final int MM_LOCATION_NAVI = 10458;
	public static final int MM_LOCATION_SDK_TYPE = 10475;
	// PHONEUSE
	public static final int PHONEUSE_CALL = 10112;
	public static final int PHONEUSE_ADD_NEWCONTACT = 10113;
	public static final int PHONEUSE_ADD_EXISTCONTACT = 10114;
	public static final int PHONEUSE_COPY = 10115;
	public static final int PHONEUSE_TIME = 10116;

	public static final int MM_ENTRY_SHAKE_VIEW = 10221;
	public static final int MM_SHARE_SHAKE_PHOTO = 10222;
	public static final int MM_SHAKE_TRAN_IMG_REPORT = 10224;
	// ADD FRIEND ENTRANCE
	public static final int ENTRY_PAGE_FROM_CONTACT = 10240;
	public static final int ENTRY_PAGE_FROM_CONTACTLIST = 10241;
	public static final int MM_TRY_BIND_TWITTER_COUNT = 10250;
	public static final int MM_BIND_TWITTER_RESULT = 10251;
	public static final int MM_SHAKE_PAGE_FROM_SHARE = 10259;

	public static final int MM_BRAND_USER_PROFILE_VISIT = 10298;
	public static final int MM_ADD_3rdAPP_BUTTON_CLICK = 10305;
	public static final int MM_Client_Logo_Wall_Visit = 10350;

	// patch update
	public static final int MM_INCREMENTAL_UPDATE = 10328; // 增量升级

	public static final int MM_SETALIAS_ALERT_SHOW_COUNT = 10356; // 设置别名提醒的出现次数
	public static final int MM_SETALIAS_ALERT_CLICK_COUNT = 10357; // 设置别名提醒的点击次数
	public static final int MM_SETALIAS_ALERT_SUCC_COUNT = 10358; // 别名提醒设置别名成功

	public static final int MM_TALK_CONNECTION_INFO = 10402; // TalkConnectionInfo 对讲模式客户端数据统计T
	public static final int MM_TALK_CLIENT_STAT_REPORT = 10404; // 对讲模式客户端数据统计T

	public static final int MM_MASS_SEND_PUSH = 10395;
	public static final int MM_MASS_SEND_TOP = 10425;
	public static final int MM_CONNECTOR_MSG_FORWARD = 10424;

	public static final int WEBVIEW_SECURITY_CONTROL = 10417; // jsapi调用时上报

	public static final int MM_MEDIA_CDN_TRANS = 10421;
	public static final int MM_MEDIA_OLD_TRANS = 10420;

	public static final int MM_USER_SAVE_EMOTIONICON = 10431; // 保存自定义表情来源，0-from chattin，1-from album

	public static final int MM_STRANGER_REMARK = 10448; // 修改陌生人备注名
	
	public static final int MM_VIEW_SEARCH_RESULE = 10450; //查看搜索聊天记录的结果
	public static final int MM_INPUT_WORD_SEARCH_CHAT_CONTENT = 10451; //输入字符搜索聊天记录
	public static final int MM_ENTER_SEARCH_CHAT_CONTENT_UI = 10455; //进入查找聊天记录的界面
	public static final int MM_CLICK_SEARCH_BUTTON = 10456; //点击了搜索按钮
	
	public static final int MM_NEW_VOICE_SEARCH_CLICK = 10453;
	public static final int MM_NEW_VOICE_SEARCH_RESULT = 10452;

	public static final int MM_TXNEWS_CATEGORY_NEWS = 0x1; // 新闻
	public static final int MM_TXNEWS_CATEGORY_SPORTS = 0x2; // 体育
	public static final int MM_TXNEWS_CATEGORY_ENT = 0x4; // 娱乐
	public static final int MM_TXNEWS_CATEGORY_FINANCE = 0x8; // 财经
	public static final int MM_TXNEWS_CATEGORY_TECH = 0x10; // 科技
	public static final int MM_TXNEWS_CATEGORY_GAMES = 0x20; // 游戏
	public static final int MM_TXNEWS_CATEGORY_LADY = 0x40; // 女性
	public static final int MM_TXNEWS_CATEGORY_DIGI = 0x80; // 数码
	public static final int MM_TXNEWS_CATEGORY_MIL = 0x100; // 军事
	public static final int MM_TXNEWS_CATEGORY_STOCK = 0x200; // 证券
	public static final int MM_TXNEWS_CATEGORY_HOUSE = 0x400; // 房产
	public static final int MM_TXNEWS_CATEGORY_AUTO = 0x800; // 汽车
	public static final int MM_TXNEWS_CATEGORY_USSTOCK = 0x1000; // 美股
	public static final int MM_TXNEWS_CATEGORY_HKSTOCK = 0x2000; // 港股

	public static final int MM_VIDEO_FUNCFLAG_SELF = 1; // NO USE!!!
	public static final int MM_VIDEO_FUNCFLAG_EXPORT = 2;

	// enum enMsgImgCompressType
	public static final int MM_MSGIMG_WITH_COMPRESS = 0;
	public static final int MM_MSGIMG_WITHOUT_COMPRESS = 1;

	// enum mm func id
	public static final int MMFunc_Invalid = 0;
	// public static final int MMFunc_Auth = 101; // 验证
	public static final int MMFunc_Reg = 102; // 注册
	public static final int MMFunc_Sync = 103; // 同步
	public static final int MMFunc_SendMsg = 104; // 发送消息
	public static final int MMFunc_IphoneReg = 105; // Iphone注册token
	public static final int MMFunc_SearchContact = 106; // 搜索Contact
	public static final int MMFunc_GetVerifyImg = 107; // 获取验证码
	public static final int MMFunc_SendVerifyEmail = 108; // 验证注册邮箱
	public static final int MMFunc_GetMsgImg = 109; // 取消息中的大图片
	public static final int MMFunc_UploadMsgImg = 110; // 客户端上传消息中的大图片
	public static final int MMFunc_ChatStatus = 111; // 发送或取消聊天状态
	public static final int MMFunc_IphoneUnReg = 112; // Iphone注销token
	public static final int MMFunc_GetUpdateInfo = 113; // 获取版本升级信息
	public static final int MMFunc_GetUpdatePack = 114; // 获取版本文件
	public static final int MMFunc_GetInviteFriend = 115; // 获取邀请好友
	public static final int MMFunc_SendInviteEmail = 116; // 发送邀请信
	public static final int MMFunc_SendPrivateMsg = 117; // 发送微博私信
	public static final int MMFunc_UploadPrivateMsgImg = 118; // 发送微博图片
	public static final int MMFunc_CreateChatRoom = 119; // 创建聊天群组
	public static final int MMFunc_AddChatRoomMember = 120; // 新增聊天群成员
	public static final int MMFunc_Init = 121; // Init
	public static final int MMFunc_SendDomainEmail = 122; // Init
	public static final int MMFunc_BatchGetHeadImg = 123; // 批量拉去头像
	public static final int MMFunc_SearchFriend = 124; // 搜索好友
	// public static final int MMFunc_GetUserName = 125; // 通过NickName生成UserName // delete by dk
	public static final int MMFunc_NewReg = 126; // 新注册协议
	public static final int MMFunc_UploadVoice = 127;
	public static final int MMFunc_DownloadVoice = 128;
	public static final int MMFunc_SwithPushMail = 129;
	public static final int MMFunc_UploadWeiboImg = 130; // 往微博发送照片
	public static final int MMFunc_SendCard = 131; // 往微博发自己的名片
	public static final int MMFunc_BindOpMobile = 132; // 绑定. 解绑;
	public static final int MMFunc_UploadMContact = 133; // 上传手机联系人
	public static final int MMFunc_ModifyHeadImg = 134; // 修改用户头像
	public static final int MMFunc_NewGetInviteFriend = 135; // 拉取全部好友的cgi
	public static final int MMFunc_SyncFriend = 136; // 看看谁在用
	public static final int MMFunc_VerifyUser = 137; // 好友验证
	public static final int MMFunc_NewSync = 138; // newsync
	public static final int MMFunc_NewInit = 139; // newsync
	public static final int MMFunc_BatchGetContactProfile = 140;
	public static final int MMFunc_GetPSMImg = 141; // 获取PushSystemMsg里面的图片
	public static final int MMFunc_GetMFriend = 142; // 获取手机好友消息
	public static final int MMFunc_GetQQGroup = 143; // 获取QQ好友
	public static final int MMFunc_BindQQ = 144; // 绑定QQ号
	public static final int MMFunc_BindMobileForReg = 145; // 手机号注册 验证
	public static final int MMFunc_ShareFriendCard = 146; // 分享好友名片
	// public static final int MMFunc_GetResetPwdUrl = 147; // 下发重置密码url到用户手机 delete by dk
	public static final int MMFunc_LbsFind = 148; // LBS查找周围联系人
	public static final int MMFunc_UploadVideo = 149;
	public static final int MMFunc_DownloadVideo = 150;
	public static final int MMFunc_AddGroupCard = 151; // 添加群名片
	public static final int MMFunc_GetBottleCount = 152; // 获取瓶子数
	public static final int MMFunc_SendFeedback = 153; // 发送用户反馈
	public static final int MMFunc_ThrowBottle = 154; // 扔瓶子
	public static final int MMFunc_PickBottle = 155; // 捡瓶子
	public static final int MMFunc_OpenBottle = 156; // 开瓶子
	public static final int MMFunc_UploadHDHeadImg = 157; // 上传高清头像
	public static final int MMFunc_GetHDHeadImg = 158; // 获取高清头像
	public static final int MMFunc_GetPackageList = 159; // 获取主题列表
	public static final int MMFunc_DownloadPackage = 160; // 获取主题
	public static final int MMFunc_ShakeReport = 161; // 上报 Shake 信息
	public static final int MMFunc_ShakeGet = 162; // 获取 Shake 结果
	public static final int MMFunc_ShakeMatch = 163; // 请求 Shake 匹配
	public static final int MMFunc_Echo = 164; // 拨测专用CGI
	public static final int MMFunc_ShakeImg = 165; // 请求 shake 背景图片
	public static final int MMFunc_Expose = 166; // 举报
	public static final int MMFunc_GetVUserInfo = 167; // 获取认证用户信息
	public static final int MMFunc_GetQRCode = 168; // 获取用户二维码
	public static final int MMFunc_GmailOper = 169; // gmail帐号绑定与解绑
	public static final int MMFunc_VoipInvite = 170; // Voip邀请
	public static final int MMFunc_VoipCancelInvite = 171; // Voip取消邀请
	public static final int MMFunc_VoipAnswer = 172; // Voip应答
	public static final int MMFunc_VoipShutDown = 173; // Voip挂断
	public static final int MMFunc_VoipSync = 174; // Voip Sync
	public static final int MMFunc_SendEmoji = 175; // 发送自定义表情
	public static final int MMFunc_ReceiveEmoji = 176; // 下载自定义表情
	public static final int MMFunc_GeneralSet = 177; // 通用设置; 满足阻塞的需求，避免总是增加cgi
	public static final int MMFunc_VoipHeartBeat = 178; // Voip HeartBeat
	public static final int MMFunc_DelChatRoomMember = 179; // 群主踢人
	public static final int MMFunc_GetMailOAuthUrl = 180; // 获取邮件OAuth的URL
	public static final int MMFunc_CollectChatRoom = 181; // 群名片转化成群
	public static final int MMFunc_GetContact = 182; // get contact
	public static final int MMFunc_FaceBookAuth = 183; // facebook
	public static final int MMFunc_GetAlbumPhotoListFP = 184; // 拉取第一页个人相册photoinfo
	public static final int MMFunc_BatchGetAlbumPhoto = 185; // 批量拉取相册缩略图
	public static final int MMFunc_BatchGetAlbumPhotoAttr = 186; // 批量拉取相册图片附属信息
	public static final int MMFunc_UploadAlbumPhoto = 187; // 上传个人相册图片
	public static final int MMFunc_DownloadAlbumPhoto = 188; // 上传个人相册图片
	public static final int MMFunc_DelAlbumPhoto = 189; // 删除个人相册图片
	public static final int MMFunc_AlbumPhotoComment = 190; // 标红心
	public static final int MMFunc_GetTLPhotoListFP = 191; // 拉取timeline第一页
	public static final int MMFunc_VoipStat = 192; // voip通话质量数据上报
	public static final int MMFunc_MassSend = 193; // 群发消息
	public static final int MMFunc_AlbumSync = 194; // 个人相册Sync
	public static final int MMFunc_SearchQRCode = 195; // 二维码搜索
	public static final int MMFunc_GetAlbumPhotoListNP = 196; // 拉取下一页个人相册photoinfo
	public static final int MMFunc_GetTLPhotoListNP = 197; // 拉取下一页timeline photoinfo
	public static final int MMFunc_GetLatestTLPhoto = 198; // 拉取最新的timelinet photoid
	public static final int MMFunc_GetPhotoCommentList = 199; // 获取一个照片的评论
	public static final int MMFunc_AlbumLbs = 200; // 给相册获取他们的lbs信息
	public static final int MMFunc_WinphoneReg = 201; // winphonereg 配置长连
	public static final int MMFunc_WinphoneUnReg = 202; // winphoneunreg 配置长连
	public static final int MMFunc_AlbumOperation = 203; // 相册照片的一些操作
	public static final int MMFunc_ShakeSync = 204; // 摇一摇传图绑定
	public static final int MMFunc_GetWeiBoURL = 205; // 获取微博链接，带登陆态
	public static final int MMFunc_VoiceAddr = 206; // 语音通讯录

	public static final int MMFunc_MMSnsUpload = 207; // sns上传buffer
	public static final int MMFunc_MMSnsDownload = 208; // sns下载buffer
	public static final int MMFunc_MMSnsPost = 209; // sns发布事件
	public static final int MMFunc_MMSnsObjectDetail = 210; // sns事件详情页
	public static final int MMFunc_MMSnsTimeLine = 211; // sns获取timeline
	public static final int MMFunc_MMSnsUserPage = 212; // sns个人页面
	public static final int MMFunc_MMSnsComment = 213; // sns评论
	public static final int MMFunc_MMSnsSync = 214; // sns消息系统同步
	public static final int MMFunc_MMSnsLbs = 216; // sns地理位置信息
	public static final int MMFunc_MMSnsObjectOp = 218; // sns事件修改操作
	public static final int MMFunc_MMSnsTagOption = 290; // sns tag 修改操作
	public static final int MMFunc_MMSnsTagMemberOption = 291; // sns tag member 修改操作
	public static final int MMFunc_MMSnsTagList = 292; // sns list 获取、推荐操作
	public static final int MMFunc_MMSnsTagMemMutilSet = 293; // 将用户设置到许多分组
	// sns end
	public static final int MMFunc_GetLocation = 215; // get location
	// public static final int MMFunc_SetPassWd = 217; // 手机注册设置密码

	// appmsg
	public static final int MMFunc_UploadAppAttach = 220; // upload app attach
	public static final int MMFunc_DownloadAppAttach = 221; // download app attach
	public static final int MMFunc_SendAppMsg = 222; // send app msg

	public static final int MMFunc_ImportFriends = 230; // add or recommend friends after reg by facebook

	public static final int MMFunc_GetAppInfo = 231; // get app info, by iver
	public static final int MMFunc_GetRecommendAppList = 232; // get recommend app list, by iver

	public static final int MMFunc_GetA8Key = 233; // a2 key to a8 key

	public static final int MMFunc_UploadMedia = 240;

	public static final int MMFunc_GetQuestion = 243; // 公众平台问答 获取问题
	public static final int MMFunc_GetQACount = 244; // 公众平台问答 获取提问or问题剩余次数

	public static final int MMFunc_StatReport = 250;

	public static final int MMFunc_StatusNotify = 251;

	public static final int MMFunc_QueryHasPasswd = 255;
	// public static final int MMFunc_VerifyPassWd = 219; // 验证密码
	public static final int MMFunc_CheckUnBind = 254; // 判断能否解绑
	public static final int MMFunc_UnBindQQ = 253; // 解除绑定QQ号
	public static final int MMFunc_BindEmail = 256; // 绑定或者解绑Email
	public static final int MMFunc_UnbindMobileByQQ = 300;
	public static final int MMFunc_SnsGetCity = 301;
	public static final int MMFunc_GetProfile = 302;

	public static final int MMFunc_LogOutWebWx = 281;

	public static final int MMFunc_VoipAck = 305;
	public static final int MMFunc_VoipRemind = 306;
	public static final int MMFunc_VoipGetDeviceInfo = 307;

	// for device stat report
	public static final int MMFunc_ReportStrategyCtrl = 308;
	public static final int MMFunc_ReportClntPerf = 309;
	public static final int MMFunc_ReportKV = 310;
	public static final int MMFunc_ReportAction = 311;
	public static final int MMFunc_ReportCgiAccess = 312;
	public static final int MMFunc_ReportCrash = 313;
	public static final int MMFunc_BlackBerryReg = 314; // BlackBerryreg 配置长连
	public static final int MMFunc_BlackBerryUnReg = 315; // BlackBerryunreg 配置长连

	// 摇一摇传图
	public static final int MMFunc_ShakeTranImgReport = 316;
	public static final int MMFunc_ShakeTranImgGet = 317;
	public static final int MMFunc_BatchGetShakeTranImg = 318;
	public static final int MMFunc_ShakeTranImgUnBind = 319;

	// voip stat report
	public static final int MMFunc_VoipStatReport = 320;

	// 聊天记录备份与迁移
	public static final int MMFunc_BakChatUploadHead = 321;
	public static final int MMFunc_BakChatUploadEnd = 322;
	public static final int MMFunc_BakChatUploadMsg = 323;
	public static final int MMFunc_BakChatUploadMedia = 324;
	public static final int MMFunc_BakChatRecoverGetList = 325;
	public static final int MMFunc_BakChatRecoverHead = 326;
	public static final int MMFunc_BakChatRecoverData = 327;
	public static final int MMFunc_BakChatDelete = 328;

	public static final int MMFunc_UploadVoiceRemind = 329;
	public static final int MMFunc_VoiceAddrReport = 330;

	public static final int MMFunc_OpVoiceReminder = 331;
	public static final int MMFunc_EnterTalkRoom = 332;
	public static final int MMFunc_ExitTalkRoom = 333;
	public static final int MMFunc_TalkMicAction = 334;
	public static final int MMFunc_TalkNoop = 335;
	public static final int MMFunc_GetTalkRoomMember = 336;

	public static final int MMFunc_BakChatUploadDB = 337;
	public static final int MMFunc_BakChatRecoverDB = 338;

	public static final int MMFunc_GrantBigChatRoom = 339;
	public static final int MMFunc_SendQRCodeByEmail = 340;

	public static final int MMFunc_BindSafeMobile = 341;
	public static final int MMFunc_AddSafeDevice = 342;
	public static final int MMFunc_GetSetableMainAcct = 343;
	public static final int MMFunc_SetMainAcct = 344;
	// public static final int MMFunc_SetEmailPwd = 345;

	public static final int MMFunc_CreateTalkRoom = 346;
	public static final int MMFunc_AddTalkRoomMember = 347;
	public static final int MMFunc_DelTalkRoomMember = 348;

	public static final int MMFunc_UploadInputVoice = 349;

	// 客服号相关
	public static final int MMFunc_CreateSubUser = 350;
	public static final int MMFunc_UpdateSubUser = 351;
	public static final int MMFunc_UnbindSubUser = 352;
	public static final int MMFunc_GetSubUserList = 353;

	public static final int MMFunc_GetCardList = 354;
	public static final int MMFunc_GetCardInfo = 355;
	public static final int MMFunc_InsertCard = 356;
	public static final int MMFunc_DeleteCard = 357;
	public static final int MMFunc_GetCardImg = 358;

	public static final int MMFunc_ClickCommand = 359;

	public static final int MMFunc_MMSubmsgSync = 360;

	public static final int MMFunc_MMUpdateSafeDevice = 361;
	public static final int MMFunc_MMDelSafeDevice = 362;

	public static final int MMFunc_TalkStatReport = 373;

	public static final int MMFunc_RegEquipment = 374;
	public static final int MMFunc_ResetEquipment = 375;
	public static final int MMFunc_MMGetBrandList = 363;

	public static final int MMFunc_TalkInvite = 364;

	public static final int MMFunc_TransferCard = 365;
	public static final int MMFunc_ParseCard = 366;

	public static final int MMFunc_ShakeMusic = 367;

	public static final int MMFunc_JoinLbsRoom = 376;
	public static final int MMFunc_GetRoomMember = 377;

	public static final int MMFunc_AppComment = 378; // AppStore 用户推荐评分 // iphone ???
	public static final int MMFunc_GetCDNDns = 379; // 获取上传图片到cdn的 ip和key

	public static final int MMFunc_NewAuth = 380; // 新验证
	public static final int MMFunc_GetCert = 381; // 获取证书
	public static final int MMFunc_NewSetEmailPwd = 382;
	public static final int MMFunc_NewSetPassWd = 383;
	public static final int MMFunc_NewVerifyPassWd = 384;

	public static final int MMFunc_ClientdefDataPush = 0x0FFF0001;
	public static final int MMFunc_ClientdefDataPushNotify = 0x0FFF0002;

	// openapi
	public static final String DEFAULT_APPID = "wx4310bbd51be7d979";
	public static final String DEFAULT_PACKAGE_NAME = "com.tencent.mm.openapi";
	public static final String DEFAULT_APP_NAME = "weixinfile";

	// enum enMMAppMsgInnerType
	public static final int MM_APP_TEXT = 1;
	public static final int MM_APP_IMG = 2;
	public static final int MM_APP_MUSIC = 3;
	public static final int MM_APP_VIDEO = 4;
	public static final int MM_APP_URL = 5;
	public static final int MM_APP_FILE = 6;
	public static final int MM_APP_OPEN = 7;
	public static final int MM_APP_EMOJI = 8;
	public static final int MM_APP_VOICE_REMIND = 9;

	// enum enMMAppInfoIconType
	public static final int MMAPPINFO_ICONTYPE_SD = 1; // iphone 低清
	public static final int MMAPPINFO_ICONTYPE_HD = 2; // android 低清
	public static final int MMAPPINFO_ICONTYPE_MDPI = 3; // 高清
	// public static final int MMAPPINFO_ICONTYPE_HDPI =4;
	// public static final int MMAPPINFO_ICONTYPE_LDPI = 5;

	// media type for UploadMedia
	// enum enMMMeidaType
	public static final int MM_MEDIA_TYPE_IMAGE = 1;
	public static final int MM_MEDIA_TYPE_VIDEO = 2;
	public static final int MM_MEDIA_TYPE_AUDIO = 3;
	public static final int MM_MEDIA_TYPE_ATTACHMENT = 4;

	// openapi url lang
	public static final String OPENAPI_URL_LANG_ZH_CN = "zh_CN";
	public static final String OPENAPI_URL_LANG_ZH_TW = "zh_TW";
	public static final String OPENAPI_URL_LANG_EN_US = "en_US";

	// openapi url from
	public static final String OPENAPI_URL_FROM_MESSAGE = "message";
	public static final String OPENAPI_URL_FROM_TIMELINE = "timeline";

	public static final int MM_USERATTR_REG_QQ = 1;
	public static final int MM_USERATTR_REG_MOBILE = 2;
	public static final int MM_USERATTR_REG_EMAIL = 3;
	public static final int MM_USERATTR_REG_BIZ = 4;
	public static final int MM_USERATTR_REG_FACEBOOK = 5;

	// enum enAppMsgSouce 用于统计sendappmsg的来源
	public static final int MM_APPMSG_WEIXIN = 1; // 微信跳到第三方app
	public static final int MM_APPMSG_3RD = 2; // 第三方app主动发送到微信
	public static final int MM_APPMSG_FORWARD = 3; // 转发

	// enum enMMAppMsgShowType
	public static final int MM_APPMSG_SHOW_DEFAULT = 0;
	public static final int MM_APPMSG_SHOW_READER = 1;
	public static final int MM_APPMSG_SHAKETRANIMG_RESULT = 2;
	public static final int MM_APPMSG_VOICEREMIND_CONFIRM = 3;
	public static final int MM_APPMSG_VOICEREMIND_REMIND = 4;
	public static final int MM_APPMSG_VOICEREMIND_SYS = 5;

	/**
	 * enum enMMAppMsgItemShowType
	 */
	public static final int MM_APPMSG_ITEM_SHOW_BIG_PIC_TEXT = 0; // 大图展示的图文消息Item
	public static final int MM_APPMSG_ITEM_SHOW_SMALL_PIC_TEXT = 1; // 小图展示的图文消息Item
	public static final int MM_APPMSG_ITEM_SHOW_ALL_TEXT = 2; // 全文本展示的消息Item, 仅仅显示摘要
	public static final int MM_APPMSG_ITEM_SHOW_TABLE_TEXT = 3; // 表格展示的消息Item,每个显示标题和摘要

	/**
	 * emMMUserAttrVerifyFlag
	 */
	public static final int MM_USERATTRVERIFYFALG_BIZ = 0x1; // 小商家
	public static final int MM_USERATTRVERIFYFALG_FAMOUS = 0x2;
	public static final int MM_USERATTRVERIFYFALG_BIZ_BIG = 0x4; // 大商家
	public static final int MM_USERATTRVERIFYFALG_BIZ_BRAND = 0x8; // 品牌商家
	public static final int MM_USERATTRVERIFYFALG_BIZ_VERIFIED = 0x10; // 认证

	// 文件上传大小显示
	public static final int MAX_ATTACH_SIZE = 10 * 1024 * 1024; // 10MB

	public static final int MMBRAND_SUBSCRIPTION_BLOCK_MESSAGE_NOTIFY = 0x1; // 不接收新消息提醒
	public static final int MMBRAND_SUBSCRIPTION_HIDE_FROM_PROFILE = 0x2; // 不在我的profile中显示
	public static final int MMBRAND_SUBSCRIPTION_REPORT_LOCATION = 0x4; // 上报地理位置信息

	public static final int MM_STATUSNOTIFY_MARKMSGREAD = 1;
	public static final int MM_STATUSNOTIFY_ENTERSESSION = 2;
	public static final int MM_STATUSNOTIFY_CHATLISTREQ = 3;
	public static final int MM_STATUSNOTIFY_CHATLISTRESP = 4;
	public static final int MM_STATUSNOTIFY_QUITSESSION = 5;

	// APP Intall or APP Unstall

	public static final int APP_USE = 0x27b5;

	// chatroom
	public static final int MM_CHATROOMFLAG_DISPALYNAME = 0x1;

	// lbsroom opcode
	public static final int MM_JOIN_LBSROOM = 1;
	public static final int MM_LEAVE_LBSROOM = 2;
	// lbsroom exit scene
	public static final int MM_EXIT_NORMAL = 1; // 主动退出
	public static final int MM_EXTI_PROFILE = 2; // 发起其他对话后退出
	public static final int MM_EXIT_EXCEPTION = 3; // 异常情况，没有正常退出

	// enum enJSAPIPermissionBitSet
	public static final int MM_JSAPI_PERMISSION_BITSET_LOG = 0x1; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_IMAGE_PREVIEW = 0x2; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_PROFILE = 0x4; // msghandler, webviewui
	public static final int MM_JSAPI_PERMISSION_BITSET_SHARE_WEIBO = 0x8; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_SHARE_TIMELINE = 0x10; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_ADD_CONTACT = 0x20; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_SEND_APP_MSG = 0x40; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_SCAN_QRCODE = 0x80; // webviewui
	public static final int MM_JSAPI_PERMISSION_BITSET_ADD_EMOTICON = 0x100; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_HAS_EMOTICON = 0x200; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_CANCEL_ADD_EMOTION = 0x400; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_HASH_CHANGE = 0x800;
	public static final int MM_JSAPI_PERMISSION_BITSET_HIDE_TOOLBAR = 0x1000; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_SHOW_TOOLBAR = 0x2000; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_HIDE_OPTION_MENU = 0x4000; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_SHOW_OPTION_MENU = 0x8000; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_GET_NEWWORK_TYPE = 0x10000; // msghandler
	public static final int MM_JSAPI_PERMISSION_BITSET_GET_CLOSE_WINDOW = 0x20000; // msghandler, webviewui
	public static final int MM_JSAPI_PERMISSION_BITSET_FONT_MENU = 0x40000; // webviewui
	public static final int MM_JSAPI_PERMISSION_BITSET_PROFILE_MENU = 0x80000; // webviewui
	public static final int MM_JSAPI_PERMISSION_BITSET_ADD_CONTACT_MENU = 0x100000; // webviewui
	public static final int MM_JSAPI_PERMISSION_BITSET_SEND_APP_MSG_MENU = 0x200000; // webviewui
	public static final int MM_JSAPI_PERMISSION_BITSET_SHARE_WEIBO_MENU = 0x400000; // webviewui
	public static final int MM_JSAPI_PERMISSION_BITSET_SHARE_TIMELINE_MENU = 0x800000; // webviewui
	public static final int MM_JSAPI_PERMISSION_BITSET_DOWNLOAD_IN_WEBVIEW = 0x1000000; // webviewui
	public static final int MM_JSAPI_PERMISSION_BITSET_GET_INSTALL_STATE = 0x2000000; // msghandler

	// enum enGeneralControlBitSet
	public static final int MM_GENERAL_CONTROL_BITSET_SHOW_INPUT_ALERT_TIPS = 0x1;

	// sns black Type
	public static final int MODSNSBLACKLIST_ADD = 1;
	public static final int MODSNSBLACKLIST_DEL = 2;

	// ReportLocationInfoErrCode
	public static final int MM_CLICKCOMMAND_REPORT_LOCATION_OK = 0; // 成功
	public static final int MM_CLICKCOMMAND_REPORT_LOCATION_USER_BAN = 1; // 用户不允许
	public static final int MM_CLICKCOMMAND_REPORT_LOCATION_DEVICE_BAN = 2; // 设备不允许
	public static final int MM_CLICKCOMMAND_REPORT_LOCATION_UNAVAILABLE = 3; // 无法获取地理位置信息

	// enReportLocationType
	public static final int MM_REPORT_LOCATION_TYPE_NONE = 0; // 不需要用户上报
	public static final int MM_REPORT_LOCATION_TYPE_ENTER_CONVERSATION = 1; // 用户点击公众帐号进入对话时上报一次
	public static final int MM_REPORT_LOCATION_TYPE_CONTINUOUS = 2; // 持续上报

	// click command type
	public static final int MM_CLICKCOMMAND_TYPE_BIZMENU_UNRECOGNIZED = 0; // 客户端不识别的菜单类型(都可以通过这个类型上报上来)
	public static final int MM_CLICKCOMMAND_TYPE_BIZMENU_CLICKNOTIFY = 1; // 商家菜单之点击通知类型
	public static final int MM_CLICKCOMMAND_TYPE_EVENT_CLICKBIZ = 10; // 用户点击公众账号
	public static final int MM_CLICKCOMMAND_TYPE_EVENT_REPORTLOCATION = 11; // 用户上报地理位置

	// 消息协议msgSource中用于内容区分的头部串
	public static final String MM_MSGSOURCE_HEADERSTR_BIZMENU = "#bizmenu#"; // 针对商家自定义菜单的消息回复

	public static final int MM_VOICE_FORMAT_UNKNOWN = -1;
	public static final int MM_VOICE_FORMAT_AMR = 0;
	public static final int MM_VOICE_FORMAT_SPEEX = 1;
	public static final int MM_VOICE_FORMAT_MP3 = 2;
	public static final int MM_VOICE_FORMAT_WAVE = 3;
}
