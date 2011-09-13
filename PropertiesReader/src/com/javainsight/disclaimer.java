package com.javainsight;

import org.apache.log4j.Logger;


public class disclaimer {
	
	private static Logger logger = Logger.getLogger(disclaimer.class);
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
				"~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*\n";
		logger.fatal(disclaimer);
	}

}
