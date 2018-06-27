package com.team.app.dto;

import java.io.Serializable;

public class ApplicationDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String appId;
	
	private String appName;
	private String devEUI;
	private String devName;
	

	public String getDevName() {
		return devName;
	}

	public void setDevName(String devName) {
		this.devName = devName;
	}

	public String getDevEUI() {
		return devEUI;
	}

	public void setDevEUI(String devEUI) {
		this.devEUI = devEUI;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
