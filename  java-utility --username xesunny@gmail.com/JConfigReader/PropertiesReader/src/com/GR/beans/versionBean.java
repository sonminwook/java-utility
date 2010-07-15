package com.GR.beans;

import com.GR.interfaces.Bean;

public class versionBean implements Bean {
	
	private String project;
	private float version;
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public float getVersion() {
		return version;
	}
	public void setVersion(float version) {
		this.version = version;
	}

}
