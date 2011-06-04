package com.javainsight.RnD;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;



public class TestEmail {
	
	public static void main(String[] args) {
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.setAuthenticator(new DefaultAuthenticator("xesunny", "2wsx3edc"));
			email.setTLS(true);
			email.setFrom("admin@javainsights.com");
			email.setSubject("TestMail");
			email.setHtmlMsg("<html><body><font class=\"Apple-style-span\" face=\"'Comic Sans'\">" +
					"Hi This is <b>sunny jain</b></font></body></html>");
			email.addTo("jovialjava.blogspot@gmail.com", "Sunny Jain");
			email.setTextMsg("Your email client does not support HTML messages");
			email.send();
			System.out.println("sent successfully");
			
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
