package com.sunz.temp;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;

import org.xml.sax.*;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

public class BooksLibrary2 extends HandlerBase {
	protected static final String XML_FILE_NAME = "ECRInitReq.xml";

	public static void main(String argv[]) {
		// Use the default (non-validating) parser
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			// Set up output stream
			out = new OutputStreamWriter(System.out, "UTF8");

			// Parse the input
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new File(XML_FILE_NAME), new BooksLibrary2());

		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("Exiting");

		System.out.println("Exited");
		for (int j = 0; j < htarr.length; j++) {
			if (htarr[j] != null) {
				Enumeration e = htarr[j].keys();
				while (e.hasMoreElements()) {
					String key = (String) e.nextElement();
					System.out.println(key + ":" + htarr[j].get(key));
				}
				System.out.println("______");
			}
		}

		System.exit(0);
	}

	static private Writer out;

	// ===========================================================
	// Methods in SAX DocumentHandler
	// ===========================================================

	public void startDocument() throws SAXException {
		showData("<?xml version='1.0' encoding='UTF-8'?>");
		newLine();
	}

	public void endDocument() throws SAXException {
		try {
			newLine();
			out.flush();
		} catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}

	public void startElement(String name, AttributeList attrs)
			throws SAXException {
		if (name.equalsIgnoreCase("Request")) {

		} else if (name.equalsIgnoreCase("Req_id")) {
			ht = new Hashtable();
		} else {
			this.Key = name;
		}
		showData("**" + name + "**");
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); i++) {
				// showData (" ");
				ht.put(attrs.getName(i), attrs.getValue((i)));
				showData("**" + attrs.getName(i) + "=\"" + attrs.getValue(i)
						+ "\"**");
			}
		}
		// showData (">");
	}

	public void endElement(String name) throws SAXException {
		if (name.equalsIgnoreCase("Req_id")) {
			this.htarr[counter++] = ht;
			ht = null;
		}
		showData(name);
	}

	public void characters(char buf[], int offset, int len) throws SAXException {
		String s = new String(buf, offset, len);

		if (s.length() > 1) {
			System.out.println(this.Key + "@@" + s);
			ht.put(this.Key, s);
			this.Key = null;
			showData("^^" + s + "^^");
		}

	}

	// ===========================================================
	// Helpers Methods
	// ===========================================================

	// Wrap I/O exceptions in SAX exceptions, to
	// suit handler signature requirements
	private void showData(String s) throws SAXException {
		try {
			System.out.println("---" + s + "---");
			// out.write (s);
			out.flush();
		} catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}

	// Start a new line
	private void newLine() throws SAXException {
		String lineEnd = System.getProperty("line.separator");
		try {
			// System.out.println("---"+lineEnd+"---");
			out.write(lineEnd);
		} catch (IOException e) {
			throw new SAXException("I/O error", e);
		}
	}

	String Key = null;
	String Value = null;
	static Hashtable ht = null;
	static Hashtable htarr[] = new Hashtable[10];
	int counter = 0;
}