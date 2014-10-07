package com.mini.mn.db.storage;

import java.util.Map;

import com.mini.mn.constant.ConstantsProtocal;
import com.mini.mn.db.message.RMsgInfo;
import com.mini.mn.platformtools.KVConfig;
import com.mini.mn.util.Log;
import com.mini.mn.util.Util;

import junit.framework.Assert;
import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;

public class MsgInfo extends RMsgInfo {
	private static final String TAG = "MicroMsg.MsgInfo";

	public MsgInfo() {
		super.field_status = STATE_SENDING;
	}

	public MsgInfo(final long msgId) {
		super.field_msgId = msgId;
		super.field_status = STATE_SENDING;
	}

	public MsgInfo(final String talker) {
		super.field_talker = talker;
		super.field_status = STATE_SENDING;
	}

	public boolean isVoice() {
		return field_type == ConstantsProtocal.MM_DATA_VOICEMSG;
	}

	public boolean isShakeShare() {
		return field_type == ConstantsProtocal.MM_DATA_APPMSG_SHAKE_SHARE;
	}

	public boolean isVoip() {
		return field_type == ConstantsProtocal.MM_DATA_VOIPMSG || field_type == ConstantsProtocal.MM_DATA_VOIPINVITE;
	}

	public boolean isVoipNotify() {
		return field_type == ConstantsProtocal.MM_DATA_VOIPNOTIFY;
	}

	public boolean isImage() {
		switch (field_type) {
		case ConstantsProtocal.MM_DATA_IMG:
		case ConstantsProtocal.MM_DATA_PRIVATEMSG_IMG:
		case ConstantsProtocal.MM_DATA_CHATROOMMSG_IMG:
		case ConstantsProtocal.MM_DATA_EMAILMSG_IMG:
		case ConstantsProtocal.MM_DATA_QQLIXIANMSG_IMG:
			return true;

		default:
			break;
		}

		return false;
	}

	public boolean isMail() {
		return field_type == ConstantsProtocal.MM_DATA_PUSHMAIL;
	}

	public boolean isFriendCard() {
		return field_type == ConstantsProtocal.MM_DATA_SHARECARD;
	}

	public boolean isLocation() {
		return field_type == ConstantsProtocal.MM_DATA_LOCATION;
	}

	public boolean isText() {
		switch (field_type) {
		case ConstantsProtocal.MM_DATA_TEXT:
		case ConstantsProtocal.MM_DATA_PRIVATEMSG_TEXT:
		case ConstantsProtocal.MM_DATA_CHATROOMMSG_TEXT:
		case ConstantsProtocal.MM_DATA_EMAILMSG_TEXT:
		case ConstantsProtocal.MM_DATA_QMSG:
			return true;

		default:
			break;
		}

		return false;
	}

	public boolean isSystem() {
		return field_type == ConstantsProtocal.MM_DATA_SYS;
	}

	public boolean isVideo() {
		return field_type == ConstantsProtocal.MM_DATA_VIDEO;
	}

	public boolean isEmoji() {
		return field_type == ConstantsProtocal.MM_DATA_EMOJI;
	}

	public boolean isAppMsgEmoji() {
		return field_type == ConstantsProtocal.MM_DATA_APPMSG_EMOJI;
	}

	public boolean isVoiceRemindSys() {
		return field_type == ConstantsProtocal.MM_EX_DATA_VOICE_REMIND_SYS;
	}

	public boolean isVoiceRemindRemind() {
		return field_type == ConstantsProtocal.MM_EX_DATA_VOICE_REMIND_REMIND;
	}

	public boolean isVoiceRemindConfirm() {
		return field_type == ConstantsProtocal.MM_EX_DATA_VOICE_REMIND_CONFIRM;
	}

	public boolean isBrandQAMsg() {
		switch (field_type) {
		case ConstantsProtocal.MM_DATA_BRAND_QA_ASK:
		case ConstantsProtocal.MM_DATA_BRAND_QA_MSG:
			return true;
		default:
			break;
		}

		return false;
	}

	/**
	 * @author kirozhao
	 */
	public static final class MailContent {
		private String title = "";
		private String content = "";
		private String sender = "";
		private String waplink = "";
		private boolean attach = false;
		private String mailId;

		private MailContent() {
		}

		public static MailContent parse(final String xml) {
			final MailContent mail = new MailContent();
			final Map<String, String> maps = KVConfig
					.parseXml(xml, "msg", null);
			if (maps != null) {
				try {
					mail.setTitle(maps.get(".msg.pushmail.content.subject"));
					mail.setContent(maps.get(".msg.pushmail.content.digest"));
					mail.setSender(maps.get(".msg.pushmail.content.sender"));
					mail.setWaplink(maps.get(".msg.pushmail.waplink"));
					mail.setAttach(Util.nullAsNil( maps.get(".msg.pushmail.content.attach")).equalsIgnoreCase("true"));
					mail.setMailId(maps.get(".msg.pushmail.mailid"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return mail;
		}

		public void setTitle(final String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

		public void setContent(final String content) {
			this.content = content;
		}

		public String getContent() {
			return content;
		}

		public void setSender(final String sender) {
			this.sender = sender;
		}

		public String getSender() {
			return sender;
		}

		public void setWaplink(final String waplink) {
			this.waplink = waplink;
		}

		public String getWaplink() {
			return waplink;
		}

		public void setAttach(final boolean attach) {
			this.attach = attach;
		}

		public boolean isAttach() {
			return attach;
		}

		public String getMailId() {
			return mailId;
		}

		public void setMailId(String mailId) {
			this.mailId = mailId;
		}
	};

	public static final class VerifyContent {
		private String fromUsername = "";
		private String alias = "";
		private String nickname = "";
		private String quanPin = "";
		private String pyInitial = "";
		private String content = "";
		private int imageFlag = 0;
		private int scene = 0;
		private String phoneNumMD5 = "";
		private String fullPhoneNumMD5 = "";
		private long qqNum = 0;
		private String qqNickname = "";
		private String qqRemark = "";
		private int sex;
		private String signature;
		private int snsflag = 0;
		private int snsbgId;
		private String snsbgIdUrl;
		private String verifyTicket;
		private String bigHeadImgUrl = "";
		private String smallHeadImgUrl = "";
		private int opcode;
		private String encryptusername;

		private VerifyContent() {
		}

		public static VerifyContent parse(final String xml) {
			VerifyContent verify = new VerifyContent();
			Map<String, String> maps = KVConfig.parseXml(xml, "msg", null);

			if (maps != null) {
				try {
					verify.setFromUsername(maps.get(".msg.$fromusername"));
					verify.setAlias(maps.get(".msg.$alias"));
					verify.setNickname(maps.get(".msg.$fromnickname"));
					verify.setQuanPin(maps.get(".msg.$fullpy"));
					verify.setPyInitial(maps.get(".msg.$shortpy"));
					verify.setContent(maps.get(".msg.$content"));
					verify.setImageFlag(Integer.valueOf(maps.get(".msg.$imagestatus")));
					verify.setScene(Integer.valueOf(maps.get(".msg.$scene")));
					verify.setPhoneNumMD5(maps.get(".msg.$mhash"));
					verify.setFullPhoneNumMD5(maps.get(".msg.$mfullhash"));
					if ((maps.get(maps.get(".msg.$qqnum")) != null) && (maps.get(maps.get(".msg.$qqnum")).length() > 0)) {
						verify.setQQNum(Long.valueOf(maps.get(".msg.$qqnum")));
					}
					verify.setQQNickname(maps.get(".msg.$qqnickname"));
					verify.setQQRemark(maps.get(".msg.$qqremark"));
					verify.setSignature(maps.get(".msg.$sign"));
					if (maps.get(".msg.$sex") != null && maps.get(".msg.$sex").length() > 0) {
						verify.setSex(Integer.valueOf(maps.get(".msg.$sex")));
					}
					if (maps.get(".msg.$snsflag") != null) {
						verify.setSnsflag(Integer.valueOf(maps.get(".msg.$snsflag")));
						// verify.setSnsbgId(maps.get(".msg.$country"));
						verify.setSnsbgIdUrl(maps.get(".msg.$snsbgimgid"));
					}
					verify.setVerifyTicket(maps.get(".msg.$ticket"));
					Log.d(TAG, "dkverify ticket:%s", verify.getVerifyTicket());
					verify.setBigHeadImgUrl(Util.nullAsNil(maps.get(".msg.$bigheadimgurl")));
					verify.setSmallHeadImgUrl(Util.nullAsNil(maps.get(".msg.$smallheadimgurl")));
					verify.setOpcode(Integer.valueOf(Util.nullAs(maps.get(".msg.$opcode"), "0")));
					verify.setEncryptusername(Util.nullAsNil(maps.get(".msg.$encryptusername")));
					Log.d(TAG, "dkavatar VerifyContent user:[%s] big:[%s] sm:[%s]", verify.getFromUsername(), verify.getBigHeadImgUrl(), verify.getSmallHeadImgUrl());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return verify;
		}

		public String getBigHeadImgUrl() {
			return this.bigHeadImgUrl;
		}

		public void setBigHeadImgUrl(String url) {
			this.bigHeadImgUrl = url;
		}

		public String getSmallHeadImgUrl() {
			return this.smallHeadImgUrl;
		}

		public void setSmallHeadImgUrl(String url) {
			this.smallHeadImgUrl = url;
		}

		public String getVerifyTicket() {
			return verifyTicket;
		}

		public void setVerifyTicket(String verifyTicket) {
			this.verifyTicket = verifyTicket;
		}

		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}

		public String getFromUsername() {
			return fromUsername;
		}

		public void setFromUsername(String fromUsername) {
			this.fromUsername = fromUsername;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getDisplayName() {
			if (nickname != null && nickname.length() > 0) {
				return nickname;
			}
			Log.f(TAG, "username is nullOrNil");// Assert.assertTrue(fromUsername.length()
												// > 0);
			return fromUsername;
		}

		public String getQuanPin() {
			return quanPin;
		}

		public void setQuanPin(String quanPin) {
			this.quanPin = quanPin;
		}

		public String getPyInitial() {
			return pyInitial;
		}

		public void setPyInitial(String pyInitial) {
			this.pyInitial = pyInitial;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public int getImageFlag() {
			return imageFlag;
		}

		public void setImageFlag(int imageFlag) {
			this.imageFlag = imageFlag;
		}

		public int getScene() {
			return scene;
		}

		public void setScene(int scene) {
			this.scene = scene;
		}

		public String getPhoneNumMD5() {
			return phoneNumMD5;
		}

		public void setPhoneNumMD5(String phoneNumMD5) {
			this.phoneNumMD5 = phoneNumMD5;
		}

		public String getFullPhoneNumMD5() {
			return this.fullPhoneNumMD5;
		}

		public void setFullPhoneNumMD5(String md5) {
			this.fullPhoneNumMD5 = md5;
		}

		public long getQQNum() {
			return this.qqNum;
		}

		public void setQQNum(long qqNum) {
			this.qqNum = qqNum;
		}

		public String getQQNickname() {
			return this.qqNickname;
		}

		public void setQQNickname(String qqNickname) {
			this.qqNickname = qqNickname;
		}

		public String getQQRemark() {
			return this.qqRemark;
		}

		public void setQQRemark(String qqRemark) {
			this.qqRemark = qqRemark;
		}

		public String getQQDisplayName() {
			if (qqRemark != null && qqRemark.length() > 0) {
				return qqRemark;
			}

			if (qqNickname != null && qqNickname.length() > 0) {
				return qqNickname;
			}

			return Long.toString(qqNum);
		}

		public void setSex(int sex) {
			this.sex = sex;
		}

		public int getSex() {
			return sex;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

		public String getSignature() {
			return signature;
		}

		public int getSnsflag() {
			return snsflag;
		}

		public void setSnsflag(int snsflag) {
			this.snsflag = snsflag;
		}

		public int getSnsbgId() {
			return snsbgId;
		}

		public void setSnsbgId(int snsbgId) {
			this.snsbgId = snsbgId;
		}

		public String getSnsbgIdUrl() {
			return snsbgIdUrl;
		}

		public void setSnsbgIdUrl(String snsbgIdUrl) {
			this.snsbgIdUrl = snsbgIdUrl;
		}

		public void setOpcode(int opcode) {
			this.opcode = opcode;
		}

		public int getOpcode() {
			return opcode;
		}
		
		public String getEncryptusername() {
			return encryptusername;
		}
		
		public void setEncryptusername(String encryptusername) {
			this.encryptusername = encryptusername;
		}
	}

	public static final class FriendContent {
		private String fromUsername = "";
		private String nickname = "";
		private String alias = "";
		private String quanPin = "";
		private String pyInitial = "";
		private String source = "";
		private int imageFlag = 0;
		private int scene = 0;
		private String phoneNumMD5 = "";
		private String fullPhoneNumMD5 = "";
		private long qqNum = 0;
		private String qqNickname = "";
		private String qqRemark = "";
		private int sex;
		private String signature;
		private int verifyFlag = 0;
		private String verifyInfo = "";
		private String brandIconUrl = "";
		private String bigHeadImgUrl = "";
		private String smallHeadImgUrl = "";

		private FriendContent() {
		}

		public static FriendContent parse(final String xml) {
			final FriendContent friend = new FriendContent();

			final Map<String, String> maps = KVConfig.parseXml(xml, "msg", null);
			if (maps != null) {

				try {
					if (maps.get(".msg.$fromusername") == null) {
						friend.setFromUsername(maps.get(".msg.$username"));
					} else {
						friend.setFromUsername(maps.get(".msg.$fromusername"));
					}
					if (maps.get(".msg.$fromnickname") == null) {
						friend.setNickname(maps.get(".msg.$nickname"));
					} else {
						friend.setNickname(maps.get(".msg.$fromnickname"));
					}
					friend.setAlias(maps.get(".msg.$alias"));
					friend.setQuanPin(maps.get(".msg.$fullpy"));
					friend.setPyInitial(maps.get(".msg.$shortpy"));
					friend.setSource(maps.get(".msg.$source"));
					friend.setImageFlag(Integer.valueOf(maps.get(".msg.$imagestatus")));
					friend.setScene(Integer.valueOf(maps.get(".msg.$scene")));
					friend.setPhoneNumMD5(maps.get(".msg.$mobileidentify"));
					friend.setFullPhoneNumMD5(maps.get(".msg.$mobilelongidentify"));
					if (maps.get(".msg.$qqnum") != null && maps.get(".msg.$qqnum").length() > 0) {
						friend.setQQNum(Long.valueOf(maps.get(".msg.$qqnum")));
					}
					friend.setSignature(maps.get(".msg.$sign"));
					if (maps.get(".msg.$sex") != null && maps.get(".msg.$sex").length() > 0) {
						friend.setSex(Integer.valueOf(maps.get(".msg.$sex")));
					}
					friend.setQQNickname(maps.get(".msg.$qqnickname"));
					friend.setQQRemark(maps.get(".msg.$qqremark"));

					friend.setVerifyFlag(Integer.valueOf(TextUtils.isEmpty(maps.get(".msg.$certflag")) ? "0" : maps.get(".msg.$certflag")));
					friend.setVerifyInfo(Util.nullAsNil(maps.get(".msg.$certinfo")));
					friend.setBrandIconUrl(Util.nullAsNil(maps.get(".msg.$brandIconUrl")));
					friend.setBigHeadImgUrl(Util.nullAsNil(maps.get(".msg.$bigheadimgurl")));
					friend.setSmallHeadImgUrl(Util.nullAsNil(maps.get(".msg.$smallheadimgurl")));
					Log.d(TAG, "dkavatar FriendContent user:[%s] big:[%s] sm:[%s]",
							friend.getFromUsername(),
							friend.getBigHeadImgUrl(),
							friend.getSmallHeadImgUrl());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return friend;
		}

		public String getBigHeadImgUrl() {
			return this.bigHeadImgUrl;
		}

		public void setBigHeadImgUrl(String url) {
			this.bigHeadImgUrl = url;
		}

		public String getSmallHeadImgUrl() {
			return this.smallHeadImgUrl;
		}

		public void setSmallHeadImgUrl(String url) {
			this.smallHeadImgUrl = url;
		}

		public String getAlias() {
			return alias;
		}

		public void setAlias(String alias) {
			this.alias = alias;
		}

		public String getFromUsername() {
			return fromUsername;
		}

		public void setFromUsername(String fromUsername) {
			this.fromUsername = fromUsername;
		}

		public String getNickname() {
			return nickname;
		}

		public void setNickname(String nickname) {
			this.nickname = nickname;
		}

		public String getDisplayName() {
			if (!TextUtils.isEmpty(nickname)) {
				return nickname;
			}
			if (!TextUtils.isEmpty(alias)) {
				return alias;
			}
			Log.f(TAG, "username is nullOrNil");// Assert.assertTrue(fromUsername.length()
												// > 0);
			return Util.nullAsNil(fromUsername);
		}

		public String getQuanPin() {
			return quanPin;
		}

		public void setQuanPin(String quanPin) {
			this.quanPin = quanPin;
		}

		public String getPyInitial() {
			return pyInitial;
		}

		public void setPyInitial(String pyInitial) {
			this.pyInitial = pyInitial;
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public int getImageFlag() {
			return imageFlag;
		}

		public void setImageFlag(int imageFlag) {
			this.imageFlag = imageFlag;
		}

		public int getScene() {
			return scene;
		}

		public void setScene(int scene) {
			this.scene = scene;
		}

		public String getPhoneNumMD5() {
			return phoneNumMD5;
		}

		public void setPhoneNumMD5(String phoneNumMD5) {
			this.phoneNumMD5 = phoneNumMD5;
		}

		public long getQQNum() {
			return this.qqNum;
		}

		public void setQQNum(long qqNum) {
			this.qqNum = qqNum;
		}

		public String getQQNickname() {
			return this.qqNickname;
		}

		public void setQQNickname(String qqNickname) {
			this.qqNickname = qqNickname;
		}

		public String getQQRemark() {
			return this.qqRemark;
		}

		public void setQQRemark(String qqRemark) {
			this.qqRemark = qqRemark;
		}

		public String getQQDisplayName() {
			if (qqRemark != null && qqRemark.length() > 0) {
				return qqRemark;
			}

			if (qqNickname != null && qqNickname.length() > 0) {
				return qqNickname;
			}

			return Long.toString(qqNum);
		}

		public void setFullPhoneNumMD5(String fullPhoneNumMD5) {
			this.fullPhoneNumMD5 = fullPhoneNumMD5;
		}

		public String getFullPhoneNumMD5() {
			return fullPhoneNumMD5;
		}

		public int getSex() {
			return sex;
		}

		public void setSex(int sex) {
			this.sex = sex;
		}

		public String getSignature() {
			return signature;
		}

		public void setSignature(String signature) {
			this.signature = signature;
		}

		public int getVerifyFlag() {
			return verifyFlag;
		}

		public void setVerifyFlag(int verifyFlag) {
			this.verifyFlag = verifyFlag;
		}

		public String getVerifyInfo() {
			return verifyInfo;
		}

		public void setVerifyInfo(String verifyInfo) {
			this.verifyInfo = verifyInfo;
		}

		public String getBrandIconUrl() {
			return brandIconUrl;
		}

		public void setBrandIconUrl(String brandIconUrl) {
			this.brandIconUrl = brandIconUrl;
		}
	}

	public static final class LocationContent {
		private String fromUsername = "";
		private double slat = 0;
		private double slong = 0;
		private int scale = 0;
		private String label = "";
		private String maptype = "";
		private String localLocationcn = null;
		private String localLocationtw = null;
		private String localLocationen = null;

		public LocationContent() {

		}

		public static LocationContent parse(final String xml) {
			LocationContent location = new LocationContent();
			Map<String, String> maps = KVConfig.parseXml(xml, "msg", null);

			if (maps != null) {
				location.setFromUsername(maps.get(".msg.location.$fromusername"));
				location.setSlat(Double.parseDouble(maps.get(".msg.location.$x")));
				location.setSlong(Double.parseDouble(maps.get(".msg.location.$y")));
				location.setLabel(maps.get(".msg.location.$label"));
				location.setMaptype(maps.get(".msg.location.$maptype"));
				location.setScale(Integer.valueOf(maps.get(".msg.location.$scale")));
				location.setLocalLocationen(maps.get(".msg.location.$localLocationen"));
				location.setLocalLocationcn(maps.get(".msg.location.$localLocationcn"));
				location.setLocalLocationtw(maps.get(".msg.location.$localLocationtw"));
			}
			return location;
		}

		public String generyContent(boolean isChatRoom, final String user) {
			// 防止把null写到xml文件
			if (localLocationen == null || localLocationen.equals("")) {
				localLocationen = "";
			}
			if (localLocationtw == null || localLocationtw.equals("")) {
				localLocationen = "";
			}
			if (localLocationcn == null || localLocationcn.equals("")) {
				localLocationcn = "";
			}

			if (fromUsername == null || fromUsername.equals("")) {
				fromUsername = "";
			}
			if (label == null || label.equals("")) {
				label = "";
			}

			if (maptype == null || maptype.equals("")) {
				maptype = "";
			}
			// <msg><location x="xxx" y="xxx" scale="xxx" label="xxx"
			// maptype="xxx" /></msg>
			String xml = "<msg><location" + " x=\"" + slat + "\"" + " y=\""
					+ slong + "\"" + " scale=\"" + scale + "\"" + " label=\""
					+ label + "\"" + " maptype=\"" + maptype + "\""
					+ "  fromusername=\"" + fromUsername + "\" /></msg>";
			if (isChatRoom && !user.equals("")) {
				xml = user + ":\n" + xml;
			}
			return xml;
		}

		public double getSlat() {
			return slat;
		}

		public void setSlat(double slat) {
			this.slat = slat;
		}

		public double getSlong() {
			return slong;
		}

		public void setSlong(double slong) {
			this.slong = slong;
		}

		public int getScale() {
			return scale;
		}

		public void setScale(int scale) {
			this.scale = scale;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getMaptype() {
			return maptype;
		}

		public void setMaptype(String maptype) {
			this.maptype = maptype;
		}

		public String getLocalLocationen() {
			return localLocationen;
		}

		public void setLocalLocationen(String localLocationen) {
			this.localLocationen = localLocationen;
		}

		public String getLocalLocationcn() {
			return localLocationcn;
		}

		public void setLocalLocationcn(String localLocationcn) {
			this.localLocationcn = localLocationcn;
		}

		public String getLocalLocationtw() {
			return localLocationtw;
		}

		public void setLocalLocationtw(String localLocationtw) {
			this.localLocationtw = localLocationtw;
		}

		public String getFromUsername() {
			return fromUsername;
		}

		public void setFromUsername(String fromUsername) {
			this.fromUsername = fromUsername;
		}
	}

	private int flag = FLAG_NULL_ID;

	// 标记此消息为本机发送，还是本机接收
	public static final int RECEIVER = 0;
	public static final int SENDER = 1;
	public static final int HARD_CODE = 2; // hardCode 的系统消息

	public static final int STATE_UNKNOWN = 0;
	public static final int MM_SENDMSG_SENDING = 1;
	public static final int MM_SENDMSG_SENDED = 2;
	public static final int MM_SENDMSG_DELIVER = 3;
	public static final int MM_SENDMSG_READ = 4;
	public static final int MM_SENDMSG_FAILED = 5;
	public static final int MM_SENDMSG_READED = 6;// voip 未接通点击取消红点

	public static final int STATE_SENDING = MM_SENDMSG_SENDING;
	public static final int STATE_SENT = MM_SENDMSG_SENDED;
	public static final int STATE_REACH = MM_SENDMSG_DELIVER;
	public static final int STATE_READ = MM_SENDMSG_READ;
	public static final int STATE_FAILED = MM_SENDMSG_FAILED;
	public static final int STATE_READED = MM_SENDMSG_READED;

	public static final int INDEX_ID = 0;
	public static final int INDEX_ID_SVR = 1;
	public static final int INDEX_TYPE = 2;
	public static final int INDEX_STATE = 3;
	public static final int INDEX_IS_SEND = 4;
	public static final int INDEX_IS_SHOWTIMER = 5;
	public static final int INDEX_CREATE_TIME = 6;
	public static final int INDEX_TALKER = 7;
	public static final int INDEX_CONTENT = 8;
	public static final int INDEX_IMG_PATH = 9;
	public static final int INDEX_RESERVED = 10;
	public static final int INDEX_LVBUFFER = 11;

	public static final int FLAG_ID = 1 << INDEX_ID;
	public static final int FLAG_ID_SVR = 1 << INDEX_ID_SVR;
	public static final int FLAG_TYPE = 1 << INDEX_TYPE;
	public static final int FLAG_STATE = 1 << INDEX_STATE;
	public static final int FLAG_IS_SEND = 1 << INDEX_IS_SEND;
	public static final int FLAG_IS_SHOWTIMER = 1 << INDEX_IS_SHOWTIMER;
	public static final int FLAG_CREATE_TIME = 1 << INDEX_CREATE_TIME;
	public static final int FLAG_TALKER = 1 << INDEX_TALKER;
	public static final int FLAG_CONTENT = 1 << INDEX_CONTENT;
	public static final int FLAG_IMG_PATH = 1 << INDEX_IMG_PATH;
	public static final int FLAG_RESERVED = 1 << INDEX_RESERVED;

	public static final int FLAG_LVBUFFER = 1 << INDEX_LVBUFFER;
	// ///////这里不懂问dk
	public static final int FLAG_COMENTURL = FLAG_LVBUFFER;
	// /////这里不懂问dk

	public static final int FLAG_NULL_ID = ~1;
	public static final int FLAG_ALL = 0xffffffff;

	public static final int MM_MSG_EMOJI_QQ = 0x1;
	public static final int MM_MSG_EMOJI_EMOJI = 0x2;
	public static final int MM_MSG_EMOJI_ART = 0x4;

	public static final int MSG_FLAG_FORWARD_FLAG_MASK = 0x001;

	// enum enMMVoiceForwardFlag
	public static final int MM_VOICE_UPLOAD_ORIGINAL = 0;// 代表原有正常语音消息
	public static final int MM_VOICE_UPLOAD_FORWARD = 1;// 代表转发的语音消息

	public static MsgInfo copyTo(final MsgInfo msg) {
		if (msg == null) {
			Log.d(TAG, "convertFrom msg is null ");
			return null;
		}

		MsgInfo newMsg = new MsgInfo();
		newMsg.setMsgId(msg.getMsgId());
		newMsg.setMsgSvrId(msg.getMsgSvrId());
		newMsg.setType(msg.getType());
		newMsg.setStatus(msg.getStatus());
		newMsg.setIsSend(msg.getIsSend());
		newMsg.setIsShowTimer(msg.getIsShowTimer());
		newMsg.setCreateTime(msg.getCreateTime());
		newMsg.setTalker(msg.getTalker());
		newMsg.setContent(msg.getContent());
		newMsg.setImgPath(msg.getImgPath());
		newMsg.setReserved(msg.getReserved());
		newMsg.field_lvbuffer = msg.field_lvbuffer;

		newMsg.commentUrl = msg.commentUrl;
		newMsg.msgFlag = msg.msgFlag;

		return newMsg;
	}
	
	@Override
	public void convertFrom(final Cursor cu) {
		super.convertFrom(cu);
		if (this.field_content == null) {
			this.field_content = "";
		}
	}

	// 第一列为本地id不用置
	@Override
	public ContentValues convertTo() {
		final ContentValues value = new ContentValues();

		if ((flag & FLAG_ID) != 0) {
			value.put(COL_MSGID, getMsgId());
		}
		if ((flag & FLAG_ID_SVR) != 0) {
			value.put(COL_MSGSVRID, this.getMsgSvrId());
		}
		if ((flag & FLAG_TYPE) != 0) {
			value.put(COL_TYPE, this.getType());
		}
		if ((flag & FLAG_STATE) != 0) {
			value.put(COL_STATUS, this.getStatus());
		}
		if ((flag & FLAG_IS_SEND) != 0) {
			value.put(COL_ISSEND, this.getIsSend());
		}
		if ((flag & FLAG_IS_SHOWTIMER) != 0) {
			value.put(COL_ISSHOWTIMER, this.getIsShowTimer());
		}
		if ((flag & FLAG_CREATE_TIME) != 0) {
			value.put(COL_CREATETIME, this.getCreateTime());
		}
		if ((flag & FLAG_TALKER) != 0) {
			value.put(COL_TALKER, this.getTalker());
		}
		if ((flag & FLAG_CONTENT) != 0) {
			value.put(COL_CONTENT, this.getContent());
		}
		if ((flag & FLAG_IMG_PATH) != 0) {
			value.put(COL_IMGPATH, this.getImgPath());
		}
		if ((flag & FLAG_RESERVED) != 0) {
			value.put(COL_RESERVED, this.getReserved());
		}
		if ((flag & FLAG_LVBUFFER) != 0) {
			buildBuff();
			value.put(COL_LVBUFFER, this.field_lvbuffer);
		}

		return value;
	}

	public void setConvertFlag(final int flag) {
		this.flag = flag;
	}

	public long getMsgId() {
		return field_msgId;
	}

	public void setMsgId(final long msgId) {
		this.field_msgId = msgId;
	}

	public int getMsgSvrId() {
		return field_msgSvrId;
	}

	public void setMsgSvrId(final int msgSvrId) {
		this.field_msgSvrId = msgSvrId;
	}

	public int getType() {
		return field_type;
	}

	public void setType(final int type) {
		this.field_type = type;
	}

	public int getStatus() {
		return field_status;
	}

	public void setStatus(final int status) {
		this.field_status = status;
	}

	public int getIsSend() {
		return field_isSend;
	}

	public void setIsSend(final int isSend) {
		this.field_isSend = isSend;
	}

	public int getIsShowTimer() {
		return field_isShowTimer;
	}

	public void setIsShowTimer(final int isShowTimer) {
		this.field_isShowTimer = isShowTimer;
	}

	public long getCreateTime() {
		return field_createTime;
	}

	public void setCreateTime(final long createTime) {
		this.field_createTime = createTime;
	}

	public String getTalker() {
		return field_talker;
	}

	public void setTalker(final String talker) {
		this.field_talker = talker;
	}

	public String getContent() {
		return field_content;
	}

	public void setContent(final String content) {
		this.field_content = content;
	}

	public String getCommentUrl() {

		return this.commentUrl;
	}

	public void setCommentUrl(final String commentUrl) {
		this.commentUrl = commentUrl;
	}

	public String getImgPath() {
		return field_imgPath;
	}

	public void setImgPath(final String imgPath) {
		this.field_imgPath = imgPath;
	}

	// 语音提醒占用，有问题找tiger
	public String getReserved() {
		return field_reserved;
	}

	public void setReserved(final String reserved) {
		this.field_reserved = reserved;
	}

	public int getForwardFlag() {
		return (MSG_FLAG_FORWARD_FLAG_MASK & msgFlag);
	}

	public void setForwardFlag(int forwardflag) {
		switch (forwardflag) {
		case MM_VOICE_UPLOAD_ORIGINAL:
		case MM_VOICE_UPLOAD_FORWARD:
			this.msgFlag |= forwardflag;
			break;
		default:
			Log.w(TAG, "Illgeal forwardflag !!!");
			break;
		}
	}

	public static final int SEQUENCE_INCREMENT_FOR_MMSG = 1000000;
	public static final int SEQUENCE_INCREMENT_FOR_NOT_MMSG = 500000;
	public static final int BASE_INCREMENT = 1;

	public static final String MMSG_TABLE = "message";
	// private static final int MMSG_TABLE_ID = BASE_INCREMENT;
	public static final long MMSG_ID_MIN_VALUE = BASE_INCREMENT;
	public static final long MMSG_ID_MAX_VALUE = SEQUENCE_INCREMENT_FOR_MMSG;

	public static final String QMSG_TABLE = "qmessage";
	// private static final int QMSG_TABLE_ID = BASE_INCREMENT + MMSG_TABLE_ID;
	public static final long QMSG_ID_MIN_VALUE = BASE_INCREMENT + MMSG_ID_MAX_VALUE;
	public static final long QMSG_ID_MAX_VALUE = SEQUENCE_INCREMENT_FOR_NOT_MMSG + MMSG_ID_MAX_VALUE;

	public static final String TMSG_TABLE = "tmessage";
	// private static final int TMSG_TABLE_ID = BASE_INCREMENT + QMSG_TABLE_ID;
	public static final long TMSG_ID_MIN_VALUE = BASE_INCREMENT + QMSG_ID_MAX_VALUE;
	public static final long TMSG_ID_MAX_VALUE = SEQUENCE_INCREMENT_FOR_NOT_MMSG + QMSG_ID_MAX_VALUE;

	public static final String BOTTLE_MSG_TABLE = "bottlemessage";
	public static final long BOTTLE_MSG_ID_MIN_VALUE = BASE_INCREMENT + TMSG_ID_MAX_VALUE;
	public static final long BOTTLE_MSG_ID_MAX_VALUE = SEQUENCE_INCREMENT_FOR_NOT_MMSG + TMSG_ID_MAX_VALUE;

	public static String getTableByTalker(String username) {
		Assert.assertTrue(username != null && username.length() > 0);
		if (username.endsWith(ConstantsStorage.TAG_MICROBLOG_TENCENT)) {
			return TMSG_TABLE;
		}
		if (username.endsWith(ConstantsStorage.TAG_QQ)) {
			return QMSG_TABLE;
		}

//		if (Contact.isBottleContact(username)) {
//			return BOTTLE_MSG_TABLE;
//		}

		return MMSG_TABLE;
	}
}
