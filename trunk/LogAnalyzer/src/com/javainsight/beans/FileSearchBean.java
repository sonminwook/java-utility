package com.javainsight.beans;

import com.javainsight.interfaces.Bean;

public class FileSearchBean implements Bean {
	
	private String[] fileNamesRegex = null;
	private int cutOffDate = 0;
	private double cutOffTime = 0;
	private boolean isAnd = true;
	private String directory = null;
	private boolean isSubdirectory = false;
	
	public String[] getFileNamesRegex() {
		return fileNamesRegex;
	}
	public int getCutOffDate() {
		return cutOffDate;
	}
	public double getCutOffTime() {
		return cutOffTime;
	}
	public boolean isAnd() {
		return isAnd;
	}
	public String getDirectory() {
		return directory;
	}
	public boolean isSubdirectory() {
		return isSubdirectory;
	}
	public void setFileNamesRegex(String[] fileNamesRegex) {
		this.fileNamesRegex = fileNamesRegex;
	}
	public void setCutOffDate(int cutOffDate) {
		this.cutOffDate = cutOffDate;
	}
	public void setCutOffTime(double cutOffTime) {
		this.cutOffTime = cutOffTime;
	}
	public void setAnd(boolean isAnd) {
		this.isAnd = isAnd;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public void setSubdirectory(boolean isSubdirectory) {
		this.isSubdirectory = isSubdirectory;
	}

}
