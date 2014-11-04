package com.mini.mn.compatible;

import java.util.Map;

import com.mini.mn.platformtools.KVConfig;

class DeviceInfoParser {

	private static final String TAG = "MiniMsg.DeviceInfoParser";

	public boolean parse(final String xml, CpuInfo cpuInfo, CameraInfo cameraInfo, AudioInfo audioInfo, CommonInfo commonInfo) {
		try {
			final Map<String, String> maps = KVConfig.parseXml(xml, "voip", null);
			if (maps == null) {
				return false;
			}
			// CPU
			if (maps.get(".voip.cpu.armv7") != null) {
				cpuInfo.enableArmv7 = Integer.parseInt(maps.get(".voip.cpu.armv7"));
				cpuInfo.hasCpuInfo = true;
			}
			if (maps.get(".voip.cpu.armv6") != null) {
				cpuInfo.enableArmv6 = Integer.parseInt(maps.get(".voip.cpu.armv6"));
				cpuInfo.hasCpuInfo = true;
			}

			// Camera
			if (maps.get(".voip.camera.num") != null) {
				cameraInfo.mCameraNum = Integer.parseInt(maps.get(".voip.camera.num"));
				cameraInfo.hasCameraNum = true;
			}
			if (maps.get(".voip.camera.surface") != null) {
				cameraInfo.mSurfaceType = Integer.parseInt(maps.get(".voip.camera.surface"));
				cameraInfo.hasSurfaceType = true;
			}
			if (maps.get(".voip.camera.format") != null) {
				cameraInfo.mOutputFormat = Integer.parseInt(maps.get(".voip.camera.format"));
				cameraInfo.hasOutputFormat = true;
			}

			// back camera
			if (maps.get(".voip.camera.back.enable") != null) {
				cameraInfo.mBackCameraInfo.enable = Integer.parseInt(maps.get(".voip.camera.back.enable"));
				cameraInfo.hasBackCamera = true;
			}
			if (maps.get(".voip.camera.back.fps") != null) {
				cameraInfo.mBackCameraInfo.fps = Integer.parseInt(maps.get(".voip.camera.back.fps"));
				cameraInfo.hasBackCamera = true;
			}
			if (maps.get(".voip.camera.back.orien") != null) {
				cameraInfo.mBackCameraInfo.orien = Integer.parseInt(maps.get(".voip.camera.back.orien"));
				cameraInfo.hasBackCamera = true;
			}
			if (maps.get(".voip.camera.back.rotate") != null) {
				cameraInfo.mBackCameraInfo.rotate = Integer.parseInt(maps.get(".voip.camera.back.rotate"));
				cameraInfo.hasBackCamera = true;
			}
			if (maps.get(".voip.camera.back.isleft") != null) {
				cameraInfo.mBackCameraInfo.isLeftRotate = Integer.parseInt(maps.get(".voip.camera.back.isleft"));
				cameraInfo.hasBackCamera = true;
			}
			if (maps.get(".voip.camera.back.width") != null) {
				cameraInfo.mBackCameraInfo.width = Integer.parseInt(maps.get(".voip.camera.back.width"));
				cameraInfo.hasBackCamera = true;
			}
			if (maps.get(".voip.camera.back.height") != null) {
				cameraInfo.mBackCameraInfo.height = Integer.parseInt(maps.get(".voip.camera.back.height"));
				cameraInfo.hasBackCamera = true;
			}

			// back camera
			if (maps.get(".voip.camera.front.enable") != null) {
				cameraInfo.mFrontCameraInfo.enable = Integer.parseInt(maps.get(".voip.camera.front.enable"));
				cameraInfo.hasFrontCamera = true;
			}
			if (maps.get(".voip.camera.front.fps") != null) {
				cameraInfo.mFrontCameraInfo.fps = Integer.parseInt(maps.get(".voip.camera.front.fps"));
				cameraInfo.hasFrontCamera = true;
			}
			if (maps.get(".voip.camera.front.orien") != null) {
				cameraInfo.mFrontCameraInfo.orien = Integer.parseInt(maps.get(".voip.camera.front.orien"));
				cameraInfo.hasFrontCamera = true;
			}
			if (maps.get(".voip.camera.front.rotate") != null) {
				cameraInfo.mFrontCameraInfo.rotate = Integer.parseInt(maps.get(".voip.camera.front.rotate"));
				cameraInfo.hasFrontCamera = true;
			}
			if (maps.get(".voip.camera.front.isleft") != null) {
				cameraInfo.mFrontCameraInfo.isLeftRotate = Integer.parseInt(maps.get(".voip.camera.front.isleft"));
				cameraInfo.hasFrontCamera = true;
			}
			if (maps.get(".voip.camera.front.width") != null) {
				cameraInfo.mFrontCameraInfo.width = Integer.parseInt(maps.get(".voip.camera.front.width"));
				cameraInfo.hasFrontCamera = true;
			}
			if (maps.get(".voip.camera.front.height") != null) {
				cameraInfo.mFrontCameraInfo.height = Integer.parseInt(maps.get(".voip.camera.front.height"));
				cameraInfo.hasFrontCamera = true;
			}

			// video record camera
			if (maps.get(".voip.camera.videorecord.frotate") != null) {
				cameraInfo.mVRFaceRotate = Integer.parseInt(maps.get(".voip.camera.videorecord.frotate"));
				cameraInfo.hasVRInfo = true;
			}
			if (maps.get(".voip.camera.videorecord.forientation") != null) {
				cameraInfo.mVRFaceDisplayOrientation = Integer.parseInt(maps.get(".voip.camera.videorecord.forientation"));
				cameraInfo.hasVRInfo = true;
			}
			if (maps.get(".voip.camera.videorecord.brotate") != null) {
				cameraInfo.mVRBackRotate = Integer.parseInt(maps.get(".voip.camera.videorecord.brotate"));
				cameraInfo.hasVRInfo = true;
			}
			if (maps.get(".voip.camera.videorecord.borientation") != null) {
				cameraInfo.mVRBackDisplayOrientation = Integer.parseInt(maps.get(".voip.camera.videorecord.borientation"));
				cameraInfo.hasVRInfo = true;
			}

			if (maps.get(".voip.camera.videorecord.num") != null) {
				cameraInfo.mVRCameraNum = Integer.parseInt(maps.get(".voip.camera.videorecord.num"));
				cameraInfo.hasVRCameraNum = true;
				cameraInfo.hasVRInfo = true;
			}

			if (maps.get(".voip.camera.videorecord.api20") != null) {
				cameraInfo.mCameraApi20 = Integer.parseInt(maps.get(".voip.camera.videorecord.api20"));
			}
			// camera SetFrameRate
			if (maps.get(".voip.camera.setframerate") != null) {
				cameraInfo.mSetFrameRate = Integer.parseInt(maps.get(".voip.camera.setframerate"));
			}

			if (maps.get(".voip.camera.videorecord.num") != null) {
				cameraInfo.mVRCameraNum = Integer.parseInt(maps.get(".voip.camera.videorecord.num"));
				cameraInfo.hasVRCameraNum = true;
				cameraInfo.hasVRInfo = true;
			}

			if (maps.get(".voip.camera.videorecord.api20") != null) {
				cameraInfo.mCameraApi20 = Integer.parseInt(maps.get(".voip.camera.videorecord.api20"));
			}
			// camera SetFrameRate
			if (maps.get(".voip.camera.setframerate") != null) {
				cameraInfo.mSetFrameRate = Integer.parseInt(maps.get(".voip.camera.setframerate"));
			}

			// Audio Info
			if (maps.get(".voip.audio.streamtype") != null) {
				audioInfo.streamtype = Integer.parseInt(maps.get(".voip.audio.streamtype"));
				audioInfo.hasAudioInfo = true;
			}
			if (maps.get(".voip.audio.smode") != null) {
				audioInfo.smode = Integer.parseInt(maps.get(".voip.audio.smode"));
				audioInfo.hasAudioInfo = true;
			}
			if (maps.get(".voip.audio.omode") != null) {
				audioInfo.omode = Integer.parseInt(maps.get(".voip.audio.omode"));
				audioInfo.hasAudioInfo = true;
			}
			if (maps.get(".voip.audio.ospeaker") != null) {
				audioInfo.ospeaker = Integer.parseInt(maps.get(".voip.audio.ospeaker"));
				audioInfo.hasAudioInfo = true;
			}
			if (maps.get(".voip.audio.operating") != null) {
				audioInfo.operating = Integer.parseInt(maps.get(".voip.audio.operating"));
				audioInfo.hasAudioInfo = true;
			}

			if (maps.get(".voip.audio.moperating") != null) {
				audioInfo.moperating = Integer.parseInt(maps.get(".voip.audio.moperating"));
				audioInfo.hasAudioInfo = true;
			}

			if (maps.get(".voip.audio.mstreamtype") != null) {
				audioInfo.mstreamtype = Integer.parseInt(maps.get(".voip.audio.mstreamtype"));
				audioInfo.hasAudioInfo = true;
			}

			if (maps.get(".voip.audio.recordmode") != null) {
				audioInfo.mVoiceRecordMode = Integer.parseInt(maps.get(".voip.audio.recordmode"));
			}

			if (maps.get(".voip.audio.playenddelay") != null) {
				audioInfo.playEndDelay = Integer.parseInt(maps.get(".voip.audio.playenddelay"));
			}

			if (maps.get(".voip.common.js") != null) {
				commonInfo.hasCommonInfo = true;
				commonInfo.disableJs = Integer.parseInt(maps.get(".voip.common.js"));
			}

			if (maps.get(".voip.audio.playenddelay") != null) {
				audioInfo.playEndDelay = Integer.parseInt(maps.get(".voip.audio.playenddelay"));
			}

			if (maps.get(".voip.common.js") != null) {
				commonInfo.js = Integer.parseInt(maps.get(".voip.common.js"));
			}
			if (maps.get(".voip.common.stopbluetoothbr") != null) {
				commonInfo.stopBluetoothInBR = Integer.parseInt(maps.get(".voip.common.stopbluetoothbr"));
			}
			if (maps.get(".voip.common.stopbluetoothbu") != null) {
				commonInfo.stopBluetoothInBU = Integer.parseInt(maps.get(".voip.common.stopbluetoothbu"));
			}

			if (maps.get(".voip.common.setbluetoothscoon") != null) {
				commonInfo.setBluetoothScoOn = Integer.parseInt(maps.get(".voip.common.setbluetoothscoon"));
			}
			if (maps.get(".voip.common.startbluetoothsco") != null) {
				commonInfo.startBluetoothSco = Integer.parseInt(maps.get(".voip.common.startbluetoothsco"));
			}

			if (maps.get(".voip.common.voicesearchfastmode") != null) {
				commonInfo.voiceSearchFastMode = Integer.parseInt(maps.get(".voip.common.voicesearchfastmode"));
			}
			if (maps.get(".voip.common.pcmreadmode") != null) {
				commonInfo.pcmReadMode = Integer.parseInt(maps.get(".voip.common.pcmreadmode"));
			}
			if (maps.get(".voip.common.pcmbufferrate") != null) {
				commonInfo.pcmBufferRate = Integer.parseInt(maps.get(".voip.common.pcmbufferrate"));
			}
			if (maps.get(".voip.common.app") != null) {
				commonInfo.audioPrePro = Integer.parseInt(maps.get(".voip.common.app"));
			}
			if (maps.get(".voip.common.vmfd") != null) {
				commonInfo.voicemsgfd = Integer.parseInt(maps.get(".voip.common.vmfd"));
			}
			if (maps.get(".voip.common.htcvoicemode") != null) {
				commonInfo.htcvoicemode = Integer.parseInt(maps.get(".voip.common.htcvoicemode"));
			}
			if (maps.get(".voip.common.speexbufferrate") != null) {
				commonInfo.speexBufferRate = Integer.parseInt(maps.get(".voip.common.speexbufferrate"));
			}
			if (maps.get(".voip.common.linespe") != null) {
				commonInfo.linespe = Integer.parseInt(maps.get(".voip.common.linespe"));
			}
			if (maps.get(".voip.common.extvideo") != null) {
				commonInfo.extvideo = Integer.parseInt(maps.get(".voip.common.extvideo"));
			}
			if (maps.get(".voip.common.extvideosam") != null) {
				commonInfo.extvideosam = Integer.parseInt(maps.get(".voip.common.extvideosam"));
			}
			if (maps.get(".voip.common.sysvideodegree") != null) {
				commonInfo.sysvideodegree = Integer.parseInt(maps.get(".voip.common.sysvideodegree"));
			}
			if (maps.get(".voip.common.mmnotify") != null) {
				commonInfo.mmnotify = Integer.parseInt(maps.get(".voip.common.mmnotify"));
			}
			
			if (maps.get(".voip.common.extsharevcard") != null) {
				commonInfo.extsharevcard = Integer.parseInt(maps.get(".voip.common.extsharevcard"));
			}
			if (maps.get(".voip.common.audioformat") != null) {
				commonInfo.audioformat = Integer.parseInt(maps.get(".voip.common.audioformat"));
			}
			if (maps.get(".voip.common.qrcam") != null) {
				commonInfo.qrcam = Integer.parseInt(maps.get(".voip.common.qrcam"));
			}
			if (maps.get(".voip.common.sysvideofdegree") != null) {
				commonInfo.sysvideofdegree = Integer.parseInt(maps.get(".voip.common.sysvideofdegree"));
			}
			commonInfo.dump();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
