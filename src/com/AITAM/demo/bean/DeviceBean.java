package com.AITAM.demo.bean;

public class DeviceBean {
	String DeviceID;
	int EmpID;
	String Token;
	String Platform;
	String Model;
	String Logged;

	public String getDeviceID() {
		return DeviceID;
	}

	public int getEmpID() {
		return EmpID;
	}

	public String getLogged() {
		return Logged;
	}

	public String getModel() {
		return Model;
	}

	public String getPlatform() {
		return Platform;
	}

	public String getToken() {
		return Token;
	}

	public void setDeviceID(String deviceID) {
		DeviceID = deviceID;
	}

	public void setEmpID(int empID) {
		EmpID = empID;
	}

	public void setLogged(String logged) {
		Logged = logged;
	}

	public void setModel(String model) {
		Model = model;
	}

	public void setPlatform(String platform) {
		Platform = platform;
	}

	public void setToken(String token) {
		Token = token;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
