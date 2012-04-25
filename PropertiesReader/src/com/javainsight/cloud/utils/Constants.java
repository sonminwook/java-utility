package com.javainsight.cloud.utils;


public class Constants {

	public static String GOOGLE_DOC_URL  = "https://docs.google.com/feeds/default/private/full/";
	public static String EXCEL 	  		 = "-/spreadsheet";
	public static String DOC 		     = "-/document";
	public static String PPT 		     = "-/presentation/mine";
	public static String FOLDER 	     = "-/folder";
	public static String SPREADSHEET_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";
	
	public static String EXIT = "exit";
	public static String CLOUD_SERVICE_NAME = "~JCache~";
	
	
	
	public static String SPREADSHEET_AUTH_TOKEN = "DQAAALgAAAChV8ASkpeiTaBra7DAEJ1p7PmrFFX6085RYEv-6ZXNiVkJzI8hTYi7zbZAJuHshHHLNz_b0K8hiUGk_p46Q3AjNj1Fup4BdbmVHyNHsjCJy8or5BRbZ1in6rJdPQy9BYF5PnSQbLFN-dTGia81vYmTnNb2r6aXSSy3XwyoU7TLuQH7KUxyh8Ow2jGvS-CoP0zc71NyKk1HTXELME0o9_gDaoebmp8cpWjCqQqT_xZmPV_Z9Q0uHyR1YbRpOHQ_NlU";

	public static  String MASTER_EMAIL_ADD = null; 
	public static String PASSWORD = null ;         
	public static final String LICENSE = "LicenseInfo";
	public static final String DISCLAIMER = "Disclaimer";
	public static final String EXP = "LicenseExpiry";
	public static final String _TOKEN = "AuthToken";
	
	public static final int WAITING_TIME_IN_SECONDS = 120;
	
	public static final String SEPARATOR_CHAR = "@~JCache~@";
	public static final String SEPARATOR = "separator";	
	public static final String SUFFIX = ".properties";
	public static final String XML_SUFFIX = ".xml";
	public static final String COMMENT_DATE_FORMAT = "dd-MMM-yyyy HH:mm:ss";
	public static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>";
	
	private static final String NEW_LINE = System.getProperty("line.separator");
	
	public static final boolean license_check = false;
	
	public static final String community_license_disclaimer = 	NEW_LINE+
			"by Using JCache, you have agreed to following Terms and Conditions" +NEW_LINE+
			"This license agreement is based on the popular GPLv2 license used by many open source projects. " +
			"For open source projects this is a good choice as it allows the JCache to be distributed as part of any application," +
			" provided that all source of the application is made available to the user base free of charge and the userbase is not charged yany kind of cost related to the use of application." +
			" Acceptance of this License Agreement is implied by download and use of the JCache."+ NEW_LINE+

			"Licensed Products: The Community License Agreement allows you to use JCache in your application," +
			"provided that all source of the application is made available to the user base free of charge."+
			"Licensed Range of Use:  This Community License Agreement is based on the popular GPL v2 (General Public License ver.2)" +
			"license used by many open source projects. It allows deployment of the JCache in applications whose complete source is made" +
			"available to the user base free of charge. Intended Use for Community License Agreement."+ NEW_LINE+

			"Open Source Projects: The JCache itself has a history as a popular open source application." +
			"thegoodcode Software intends to continue working with and supporting the open source community." +
			"The Community License is perfect for open source applications with GPL compatible licenses. As long" +
			"as the entire application solution does not violate any of the requirements of the GPL, the JCache can" +
			"be included under this license. If any component of the total application can not be released in a form which" +
			"does not violate the GPL, then it necessary to purchase a Premium License Agreement."+ NEW_LINE+

			"Non-GPL Open Source Projects:  thegoodcode Software has no desire to restrict the use of the JCache in open" +
			"source applications released under licenses not directly compatible with GPL. We will be happy to work with" +
			"the open source community to come up with a license agreement which works with your application as long as" +
			"redistribution of the JCache is protected in a way which follows the spirit of the GPL."+ NEW_LINE+

			"Closed Source Use:  The GPL does not restrict private software from being developed for internal" +
			"use which depends on software under the GPL as long as that software is never redistributed without" +
			"making the full source of the entire application available to all users. While we encourage corporate" +
			"and government users to make use of either a Premium License Agreement, the Community" +
			"License Agreement is acceptable as long as the application is for internal use or will be always be redistributed" +
			"along with its full source."+ NEW_LINE+NEW_LINE+
			
			"For Latest information, please visit: http://www.thegoodcode.com/p/license-overview.html" + NEW_LINE;
	
	
}
