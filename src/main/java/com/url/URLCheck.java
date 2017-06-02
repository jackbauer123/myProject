package com.url;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import com.asprise.ocr.Ocr;

public class URLCheck {
	
	public static ArrayList sendPostliuZhouReadImg(String url,String firstCookie)
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
	             conn.setRequestProperty("accept", "text/html, application/xhtml+xml, image/jxr, */*");
	             conn.setRequestProperty("connection", "Keep-Alive");
	             conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
	             conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	             conn.setRequestProperty("Cache-Control", "no-cache");
	             conn.setRequestProperty("Cookie", firstCookie);
	             
		         // 获取所有响应头字段  
		       //  Map<String, List<String>> map = conn.getHeaderFields();
		         //String cookieData=conn.getHeaderField("Set-Cookie");
		       //  result.add(cookieData);
		        
		         // 遍历所有的响应头字段  
		            //for (String key : map.keySet()) {  
		              //  System.out.println(key + "--->" + map.get(key));  
		           // }  
		        
	             //conn.setRequestProperty("Host", "www.glzfgjj.cn:8136");
	             //conn.setRequestProperty("Referer", "http://www.glzfgjj.cn:8136/Default.aspx");
	            /* String sessionId = "";  
		         String cookieVal = "";  
		         String key = null;  
		         //取cookie  
		         for(int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++){  
		             if(key.equalsIgnoreCase("set-cookie")){  
		                 cookieVal = conn.getHeaderField(i);  
		                 cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));  
		                 sessionId = sessionId + cookieVal + ";";  
		             }  
		         }  
	             */
	             result.add(firstCookie);
	             // 定义BufferedReader输入流来读取URL的响应
	             // in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		         
	             InputStream inputStream =conn.getInputStream();
	            
	             String picName = "G:\\img\\";  
	             File filepic=new File(picName);  
	             if(!filepic.exists())  
	                 filepic.mkdir();  
	             File filepicF=new File(picName+new Date().getTime() + ".jpg");  
	             //InputStream inputStream = getMethod.getResponseBodyAsStream();  
	             OutputStream outStream = new FileOutputStream(filepicF);  
	             IOUtils.copy(inputStream, outStream); 
	             outStream.close();  
	        
	             Ocr.setUp(); // one time setup  
	             Ocr ocr = new Ocr(); // create a new OCR engine  
	             ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English 
	             String s = ocr.recognize(new File[] {filepicF},Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);  
	             if(!s.isEmpty()){
	            	 result.add(s);
	             }
	             
	             System.out.println("Result: " + s); 
	            
	             
	             //System.out.println("图片文字为:" + s.replace(",", "").replace("i", "1").replace(" ", "").replace("'", "").replace("o", "0").replace("O", "0").replace("g", "6").replace("B", "8").replace("s", "5").replace("z", "2"));  
	             // ocr more images here ...  
	             ocr.stopEngine();  
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
	
	
	
	public String sendLiuZhouIndex(String url){
		
		 PrintWriter out = null;
	     BufferedReader in = null;
	     String sessionId = "";
	     try {
	    	 	 URL realUrl = new URL(url);
	             // 打开和URL之间的连接
	         	 URLConnection conn= realUrl.openConnection();
	             // 设置通用的请求属性
	             conn.setRequestProperty("accept", "text/html, application/xhtml+xml, image/jxr, */*");
	             conn.setRequestProperty("connection", "Keep-Alive");
	             conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
	             conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	             conn.setRequestProperty("Cache-Control", "no-cache");
	             
		         // 获取所有响应头字段  
		       //  Map<String, List<String>> map = conn.getHeaderFields();
		         //String cookieData=conn.getHeaderField("Set-Cookie");
		       //  result.add(cookieData);
		         
		          
		         String cookieVal = "";  
		         String key = null;  
		         //取cookie  
		         for(int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++){  
		             if(key.equalsIgnoreCase("set-cookie")){  
		                 cookieVal = conn.getHeaderField(i);  
		                 cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));  
		                 sessionId = sessionId + cookieVal + ";";  
		             }  
		         }  
		        
		         
		        
		        
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
		
		
		return sessionId ;
	}
	
	
	
	
	
	public void sendLiuZhouIndexflow(String url,String params){
		
		 PrintWriter out = null;
	     BufferedReader in = null;
	     String sessionId = "";
	     try {
	    	 	 URL realUrl = new URL(url);
	             // 打开和URL之间的连接
	         	 URLConnection conn= realUrl.openConnection();
	             // 设置通用的请求属性
	             conn.setRequestProperty("accept", "text/html, application/xhtml+xml, image/jxr, */*");
	             conn.setRequestProperty("connection", "Keep-Alive");
	             conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko");
	             conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	             conn.setRequestProperty("Cache-Control", "no-cache");
	             conn.setRequestProperty("Cookie", params);
		         // 获取所有响应头字段  
		       //  Map<String, List<String>> map = conn.getHeaderFields();
		         //String cookieData=conn.getHeaderField("Set-Cookie");
		       //  result.add(cookieData);
		         conn.getInputStream();
		          
		         /*String cookieVal = "";  
		         String key = null;  
		         //取cookie  
		         for(int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++){  
		             if(key.equalsIgnoreCase("set-cookie")){  
		                 cookieVal = conn.getHeaderField(i);  
		                 cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));  
		                 sessionId = sessionId + cookieVal + ";";  
		             }  
		         }  */
		        
		         
		        
		        
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
		
		
		//return sessionId ;
	}
	
	
	
	
	
	
}
