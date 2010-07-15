package com.GR.beans;

import com.GR.interfaces.Bean;

public class sunnyBean implements Bean {

	private String name;
	private String[] lastName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String[] getLastName() {
		return lastName;
	}
	public void setLastName(String[] lastName) {
		this.lastName = lastName;
	}
}
