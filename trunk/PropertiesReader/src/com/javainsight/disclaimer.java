package com.javainsight;

import org.apache.log4j.Logger;


public class disclaimer {
	
	private static Logger logger = Logger.getLogger(disclaimer.class);
	private static boolean isPrinted = false;
	public static void print(){
		String disclaimer = "~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*\n" +
				"This SOFTWARE PRODUCT is provided by Java~Insight (http://javainsight.wordpress.com) " +
				"\"as is\" and \"with all faults.\" \n THE PROVIDER makes no representations" +
				" or warranties of any kind concerning the safety, suitability, lack of viruses, " +
				"inaccuracies, typographical errors, or other harmful components of this SOFTWARE PRODUCT.\n " +
				"There are inherent dangers in the use of any software, and you are solely responsible for " +
				"determining whether this SOFTWARE PRODUCT is compatible with your equipment and " +
				"other software installed on your equipment.\n You are also solely responsible for the protection " +
				"of your equipment and backup of your data, and THE PROVIDER will not be liable for any damages you" +
				" may suffer in connection with using, modifying, or distributing this SOFTWARE PRODUCT.\n"+
				"This SOFTWARE PRODUCT is FREE to use/distribute without any modification for Global Blue organization for its product Pay@Table.\n" +
				"For any modification please contact us at [" + "javainsights@gmail.com]"+
				"~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*\n"+
				
"This license agreement is based on the popular GPLv2 license used by many open source projects. " +
"For open source projects this is a good choice as it allows the JCache to be distributed as part of any application," +
" provided that all source of the application is made available to the user base free of charge and the userbase is not charged yany kind of cost related to the use of application." +
" Acceptance of this License Agreement is implied by download and use of the JCache."+

"Licensed Products: The Community License Agreement allows you to use JCache in your application," +
"provided that all source of the application is made available to the user base free of charge."+
"Licensed Range of Use:  This Community License Agreement is based on the popular GPL v2 (General Public License ver.2)" +
"license used by many open source projects. It allows deployment of the JCache in applications whose complete source is made" +
"available to the user base free of charge. Intended Use for Community License Agreement."+

"Open Source Projects: The JCache itself has a history as a popular open source application." +
"Javainsights Software intends to continue working with and supporting the open source community." +
"The Community License is perfect for open source applications with GPL compatible licenses. As long" +
"as the entire application solution does not violate any of the requirements of the GPL, the JCache can" +
"be included under this license. If any component of the total application can not be released in a form which" +
"does not violate the GPL, then it necessary to purchase a Server License or Development License Agreement."+

"Non-GPL Open Source Projects:  thegoodcode Software has no desire to restrict the use of the JCache in open" +
"source applications released under licenses not directly compatible with GPL. We will be happy to work with" +
"the open source community to come up with a license agreement which works with your application as long as" +
"redistribution of the JCache is protected in a way which follows the spirit of the GPL."+

"Closed Source Use:  The GPL does not restrict private software from being developed for internal" +
"use which depends on software under the GPL as long as that software is never redistributed without" +
"making the full source of the entire application available to all users. While we encourage corporate" +
"and government users to make use of either a Server License or Development License Agreement, the Community" +
"License Agreement is acceptable as long as the application is for internal use or will be always be redistributed" +
"along with its full source.";
	logger.fatal(disclaimer);
	}
	
	public static void print(String disclaimer){
		if(!isPrinted){
			logger.fatal(disclaimer);
			isPrinted = true;
		}
	}
	
	

}
