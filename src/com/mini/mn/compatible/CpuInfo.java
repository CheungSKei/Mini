package com.mini.mn.compatible;

public class CpuInfo {
	public boolean hasCpuInfo = false;

	public int enableArmv7;
	public int enableArmv6;

	public CpuInfo() {
		reset();
	}

	public void reset() {
		hasCpuInfo = false;
		enableArmv7 = 0;
		enableArmv6 = 0;
	}
}
