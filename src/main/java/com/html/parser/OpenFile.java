package com.html.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class OpenFile {

	private static String ENCODE = "utf-8";

	public static void message(String szMsg) {
		try {
			System.out.println(new String(szMsg.getBytes(ENCODE), System.getProperty("file.encoding")));
		} catch (Exception e) {

		}
	}

	public static String openFile(String szFileName) {
		try {
			BufferedReader bis = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(szFileName)), ENCODE));
			String szContent = "";
			szContent = bis.readLine();
			bis.close();
			return szContent;
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String openFile2(String szFileName) {
		try {
			BufferedReader bis = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(szFileName)), ENCODE));
			String szContent = "";
			String tmp="";
			while((tmp=bis.readLine()) != null){
				szContent+=tmp;
				
			}
			bis.close();
			return szContent;
		} catch (Exception e) {
			return "";
		}
	}
	

}
