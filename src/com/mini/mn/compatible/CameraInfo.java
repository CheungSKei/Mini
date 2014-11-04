package com.mini.mn.compatible;

public class CameraInfo {

	public boolean hasCameraNum;
	public int mCameraNum;

	public boolean hasSurfaceType;
	public int mSurfaceType;

	public boolean hasOutputFormat;
	public int mOutputFormat;

	public boolean hasFrontCamera;
	public SubCameraInfo mFrontCameraInfo = new SubCameraInfo();

	public boolean hasBackCamera;
	public SubCameraInfo mBackCameraInfo = new SubCameraInfo();

	public boolean hasVRInfo;
	public int mVRFaceRotate;
	public int mVRFaceDisplayOrientation;
	public int mVRBackRotate;
	public int mVRBackDisplayOrientation;

	public int mSetFrameRate;

	public int mVRCameraNum;
	public boolean hasVRCameraNum;

	public int mCameraApi20;

	public class SubCameraInfo {
		public int enable;
		public int fps;
		public int orien;
		public int rotate;
		public int isLeftRotate;
		public int width;
		public int height;

		public void reset() {
			enable = 0;
			fps = 0;
			orien = 0;
			rotate = 0;
			isLeftRotate = 0;
			width = 0;
			height = 0;
		}
	}

	public CameraInfo() {
		reset();
	}

	public void reset() {
		hasCameraNum = false;
		mCameraNum = 0;

		hasSurfaceType = false;
		mSurfaceType = 0;

		hasOutputFormat = false;
		mOutputFormat = 0;

		hasFrontCamera = false;
		mFrontCameraInfo.reset();

		hasBackCamera = false;
		mBackCameraInfo.reset();

		hasVRInfo = false;
		mVRFaceRotate = -1;
		mVRFaceDisplayOrientation = -1;
		mVRBackRotate = -1;
		mVRBackDisplayOrientation = -1;

		mSetFrameRate = -1;

		mVRCameraNum = -1;
		hasVRCameraNum = false;

		mSetFrameRate = -1;

		mVRCameraNum = -1;
		hasVRCameraNum = false;
		
		mCameraApi20 =-1;

		mCameraApi20 = -1;
	}
}
