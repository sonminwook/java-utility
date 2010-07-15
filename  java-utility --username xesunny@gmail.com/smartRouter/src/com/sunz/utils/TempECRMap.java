package com.sunz.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.xml.sax.SAXException;

import com.globalblue.util.StringToStream;
import com.globalblue.util.XMLToMap;

import com.GR.beans.ECRInitBean;

public class TempECRMap {
	
private String XMLMessage = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
								"<ECRInitReq>"+
								"<POSID>goon</POSID>"+
								"<TID>50899994</TID>"+
								"<UseACK>Y</UseACK>"+
								"<UseLRC>N</UseLRC>"+
								"<MagCard>Y</MagCard>"+
								"<EMVCard>N</EMVCard>"+
								"<Decimals>2</Decimals>"+
								"<Prompt>N</Prompt>"+
								"<TermPrint>N</TermPrint>"+
								"<DCC>Y</DCC>"+
								"<TFS>Y</TFS>"+
								"<ActivationCode>4A320-FF14B-FD4BD-29460</ActivationCode>"+
								"</ECRInitReq>";
	
public ECRInitBean getECRInitBean( ) throws    IllegalArgumentException,
																SAXException,
																IOException{

	InputStream is = new StringToStream().convert(XMLMessage);
	Map<String, String> valueMap = new XMLToMap().convertToMap(is);
	return prepareBean(valueMap);
	}

	private ECRInitBean prepareBean(Map<String, String> map){
		ECRInitBean myBean = new ECRInitBean();
		myBean.setPOSID(map.get("POSID"));
		myBean.setTID(map.get("TID"));
		myBean.setUseACK(map.get("UseACK"));
		myBean.setUseLRC(map.get("UseLRC"));
		myBean.setMagCard(map.get("MagCard"));
		myBean.setEMVCard(map.get("EMVCard"));
		int dec = Integer.parseInt(map.get("Decimals"));
		myBean.setDecimals(dec);
		myBean.setPrompt(map.get("Prompt"));
		myBean.setTermPrint(map.get("TermPrint"));
		myBean.setDCC(map.get("DCC"));
		myBean.setTFS(map.get("TFS"));
		myBean.setActivationCode(map.get("ActivationCode"));
		return myBean;
	}
	
	
}
