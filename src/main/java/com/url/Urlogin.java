package com.url;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Urlogin {
	
	public static ArrayList sendPostliuZhouLogin(String url,String msg,String sessionId)
	{
	     PrintWriter out = null;
	     BufferedReader in = null;
	     ArrayList<String> result = new ArrayList<String>();
	     String temp1;
	     String temp2;
	     try {
	    	 	 URL realUrl = new URL(url);
	             // 打开和URL之间的连接
	         	 URLConnection conn= realUrl.openConnection();
	             // 设置通用的请求属性
	             conn.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	             conn.setRequestProperty("connection", "Keep-Alive");
	             conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
	             conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	             conn.setRequestProperty("Cache-Control", "no-cache");
	             conn.setRequestProperty("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5");
	             conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
	             conn.setRequestProperty("Host", "www.lzzfgjj.com");
	             conn.setRequestProperty("Referer", "http://www.lzzfgjj.com");
	             conn.setRequestProperty("Cookie", sessionId);
	             //conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
	             System.out.println("yyyyyyyyyyyyyyyyyyyy");
	             System.out.println(sessionId);
	          // 发送POST请求必须设置如下两行
	             conn.setDoOutput(true);
	             conn.setDoInput(true);
	            
	             // 获取URLConnection对象对应的输出流
	             out = new PrintWriter(conn.getOutputStream());
	             // 发送请求参数
	             out.print(msg);
	             // flush输出流的缓冲
	             out.flush();
	             
	             // 定义BufferedReader输入流来读取URL的响应
	             in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
	             String line;
	             
	             while ((line = in.readLine()) != null) {
	            	 System.out.println(line);
	             }
	             
	             String sessionId2 = "";  
		         String cookieVal = "";  
		         String key = null;  
		         //取cookie  
		         for(int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++){  
		             if(key.equalsIgnoreCase("set-cookie")){  
		                 cookieVal = conn.getHeaderField(i);  
		                 cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));  
		                 sessionId2 = sessionId2 + cookieVal + ";";  
		             }  
		         } 
	             //String cookieData=conn.getHeaderField("Set-Cookie");
	             System.out.println(sessionId2);
	              
	     } catch (Exception e) {
	             System.out.println("获取验证码请求出现异常！"+e);
	             e.printStackTrace();
	     }finally{
	    	 
	             try{
	                 if(out!=null){
	                         out.close();
	                 }
	                 if(in!=null){
	                         in.close();
	                 }
	                
	                 
	             }catch(IOException ex){
	                 ex.printStackTrace();
	             }
	     }
	     return result;
	} 
	
}
