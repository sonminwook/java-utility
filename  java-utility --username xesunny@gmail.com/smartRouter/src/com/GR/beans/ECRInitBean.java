package com.GR.beans;

public class ECRInitBean {

	private String POSID = null;
	private String TID = null;
	private String UseACK = null;
	private String UseLRC = null;
	private String MagCard = null;
	private String EMVCard = null;
	private int Decimals = 0;
	private String Prompt = null;
	private String TermPrint = null;
	private String DCC = null;
	private String TFS = null;
	private String ActivationCode = null;
	public String getPOSID() {
		return POSID;
	}
	public void setPOSID(String posid) {
		POSID = posid;
	}
	public String getTID() {
		return TID;
	}
	public void setTID(String tid) {
		TID = tid;
	}
	public String getUseACK() {
		return UseACK;
	}
	public void setUseACK(String useACK) {
		UseACK = useACK;
	}
	public String getUseLRC() {
		return UseLRC;
	}
	public void setUseLRC(String useLRC) {
		UseLRC = useLRC;
	}
	public String getMagCard() {
		return MagCard;
	}
	public void setMagCard(String magCard) {
		MagCard = magCard;
	}
	public String getEMVCard() {
		return EMVCard;
	}
	public void setEMVCard(String card) {
		EMVCard = card;
	}
	public int getDecimals() {
		return Decimals;
	}
	public void setDecimals(int decimals) {
		Decimals = decimals;
	}
	public String getPrompt() {
		return Prompt;
	}
	public void setPrompt(String prompt) {
		Prompt = prompt;
	}
	public String getTermPrint() {
		return TermPrint;
	}
	public void setTermPrint(String termPrint) {
		TermPrint = termPrint;
	}
	public String getDCC() {
		return DCC;
	}
	public void setDCC(String dcc) {
		DCC = dcc;
	}
	public String getTFS() {
		return TFS;
	}
	public void setTFS(String tfs) {
		TFS = tfs;
	}
	public String getActivationCode() {
		return ActivationCode;
	}
	public void setActivationCode(String activationCode) {
		ActivationCode = activationCode;
	}
	

}
