package com.login;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class LoginIn {  
	    private String accout;  
	    private String password;  
	    HttpClient client = HttpClients.createDefault();//实例化httpclient  
	    HttpResponse response = null;  
	    String rawHtml;  
	      
	    public LoginIn(String accout, String password) {  
	        super();  
	        this.accout = accout;  
	        this.password = password;  
	    }  
	  
	    public boolean loginWuZhou() {  
	        HttpGet getLoginPage = new HttpGet("http://www.gxwzgjj.com/");//教务处登陆页面get  
	          
	        try {  
	            //打开教务处  
	            client.execute(getLoginPage);  
	            //获取验证码  
	            getVerifyingCode(client);  
	              
	            //提醒用户并输入验证码  
	            System.out.println("verifying code has been save as verifyCode.jpeg, input its content");  
	            String code;  
	            Scanner in = new Scanner(System.in);  
	            code = in.nextLine();  
	            in.close();  
	              
	            //设定post参数，和上图中的内容一致  
	            ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();   
	            postData.add(new BasicNameValuePair("sfno", accout));//学号  
	            postData.add(new BasicNameValuePair("pwd", password));//密码  
	            postData.add(new BasicNameValuePair("pSN1", code));//验证码  
	              
	            HttpPost post = new HttpPost("http://www.gxwzgjj.com/login_grgjj.asp");//构建post对象  
	            post.setEntity(new UrlEncodedFormEntity(postData));//捆绑参数  
	            response = client.execute(post);//执行登陆行为      
	            rawHtml = EntityUtils.toString(response.getEntity(), "gb2312");  
	            //System.out.println(rawHtml);  
	            
	          //http://www.gxwzgjj.com/dc_zg_grye.asp   http://www.gxwzgjj.com/dc_zg_main.asp  http://www.gxwzgjj.com/dc_zg_menu.asp
	            HttpGet gjjInfo1 = new HttpGet("http://www.gxwzgjj.com/dc_zg_main.asp");//教务处登陆页面get 
	            //打开教务处  
	            response = client.execute(gjjInfo1); 
	            rawHtml = EntityUtils.toString(response.getEntity(), "gb2312"); 
	            //System.out.println(rawHtml); 
	            HttpGet gjjInfo2 = new HttpGet("http://www.gxwzgjj.com/dc_zg_menu.asp");//教务处登陆页面get 
	            //打开教务处  
	            response = client.execute(gjjInfo2); 
	            rawHtml = EntityUtils.toString(response.getEntity(), "gb2312"); 
	           // System.out.println(rawHtml); 
	            HttpGet gjjInfo = new HttpGet("http://www.gxwzgjj.com/dc_zg_grye.asp");//教务处登陆页面get 
	            //打开教务处  
	            response = client.execute(gjjInfo); 
	            rawHtml = EntityUtils.toString(response.getEntity(), "gb2312"); 
	            System.out.println(rawHtml); 
	  
	        } catch (ClientProtocolException e) {  
	            System.out.println(e.getMessage());  
	        } catch (IOException e) {  
	            System.out.println(e.getMessage());  
	        }  
	        return true;
	    }  
	      
	    void getVerifyingCode(HttpClient client) {  
	        HttpGet getVerifyCode = new HttpGet("http://www.gxwzgjj.com/GetCode1.asp");//验证码get  
	        FileOutputStream fileOutputStream = null;  
	        HttpResponse response;  
	        try {  
	            response = client.execute(getVerifyCode);//获取验证码  
	            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jped*/  
	            fileOutputStream = new FileOutputStream(new File("verifyCode.jpeg"));  
	            response.getEntity().writeTo(fileOutputStream);  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                fileOutputStream.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	    
	    void getVerifyingCode1(HttpClient client) {  
	        HttpGet getVerifyCode = new HttpGet("http://www.lzzfgjj.com/captcha.svl");//验证码get  
	        FileOutputStream fileOutputStream = null;  
	        HttpResponse response;  
	        try {  
	            response = client.execute(getVerifyCode);//获取验证码  
	            /*验证码写入文件,当前工程的根目录,保存为verifyCode.jped*/  
	            fileOutputStream = new FileOutputStream(new File("verifyCode.jpeg"));  
	            response.getEntity().writeTo(fileOutputStream);  
	        } catch (ClientProtocolException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                fileOutputStream.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	    
	    
	    public boolean loginLiuZhou() {  
	        HttpGet getLoginPage = new HttpGet("http://www.lzzfgjj.com/login.jspx");//教务处登陆页面get  
	          
	        try {  
	            //打开教务处  
	            client.execute(getLoginPage);  
	            //获取验证码  
	            getVerifyingCode1(client);  
	              
	            //提醒用户并输入验证码  
	            System.out.println("verifying code has been save as verifyCode.jpeg, input its content");  
	            String code;  
	            Scanner in = new Scanner(System.in);  
	            code = in.nextLine();  
	            in.close();  
	              
	            //设定post参数，和上图中的内容一致  
	            ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();   
	            postData.add(new BasicNameValuePair("_gscu_408601686", "95797178rkuvcg18"));//学号  
	            postData.add(new BasicNameValuePair("_gscs_408601686", "95797178y36yrf18|pv:1"));//学号  
	            postData.add(new BasicNameValuePair("_gscbrs_408601686", "1"));//学号  
	            postData.add(new BasicNameValuePair("username", accout));//学号  
	            postData.add(new BasicNameValuePair("password", password));//密码  
	            postData.add(new BasicNameValuePair("captcha", code));//验证码  
	              
	            HttpPost post = new HttpPost("http://www.lzzfgjj.com/login.jspx");//构建post对象  
	            post.setEntity(new UrlEncodedFormEntity(postData));//捆绑参数  
	            response = client.execute(post);//执行登陆行为      
	            rawHtml = EntityUtils.toString(response.getEntity(), "gb2312");  
	            //System.out.println(rawHtml);  
	            
	            HttpGet gjjInfo1 = new HttpGet("http://www.lzzfgjj.com/grcx/grcx_grjbqk.jspx");//教务处登陆页面get 
	            //打开教务处  
	            response = client.execute(gjjInfo1); 
	            rawHtml = EntityUtils.toString(response.getEntity(), "gb2312"); 
	            System.out.println(rawHtml); 
	            /*HttpGet gjjInfo2 = new HttpGet("http://www.gxwzgjj.com/dc_zg_menu.asp");//教务处登陆页面get 
	            //打开教务处  
	            response = client.execute(gjjInfo2); 
	            rawHtml = EntityUtils.toString(response.getEntity(), "gb2312"); 
	            System.out.println(rawHtml); 
	            HttpGet gjjInfo = new HttpGet("http://www.gxwzgjj.com/dc_zg_grye.asp");//教务处登陆页面get 
	            //打开教务处  
	            response = client.execute(gjjInfo); 
	            rawHtml = EntityUtils.toString(response.getEntity(), "gb2312"); 
	            System.out.println(rawHtml); */
	  
	        } catch (ClientProtocolException e) {  
	            System.out.println(e.getMessage());  
	        } catch (IOException e) {  
	            System.out.println(e.getMessage());  
	        }  
	        return true;
	    }  
	    
	    
	    
	    public boolean loginGuiLin() {  
	    	String temp1;
		    String temp2;
		    String username="450821198808181453";
		    String password="181453";
		    BufferedReader in = null;
		    ArrayList<String> result = new ArrayList<String>();
	        HttpGet getLoginPage = new HttpGet("http://www.glzfgjj.cn:8136");//教务处登陆页面get  
	          
	        try {  
	            //打开教务处  
	            response=client.execute(getLoginPage); 
	            
	            HttpEntity entity= response.getEntity();
	            InputStream inputStream=entity.getContent();
	            in = new BufferedReader(new InputStreamReader(inputStream,"gb2312"));
	             String line;
	             while ((line = in.readLine()) != null) {
	            	 if(line.contains("__VIEWSTATE")){
	            		 temp1=line.split("value=")[1];
	            		 result.add(temp1.split("\"")[1]);
	            	 }
	            	 if(line.contains("__EVENTVALIDATION")){
	            		 temp2=line.split("value=")[1];
	            		 result.add(temp2.split("\"")[1]);
	            	 }
	             }
	             
	           /* //获取验证码  
	            getVerifyingCode1(client);  
	              
	            //提醒用户并输入验证码  
	            System.out.println("verifying code has been save as verifyCode.jpeg, input its content");  
	            String code;  
	            Scanner in = new Scanner(System.in);  
	            code = in.nextLine();  
	            in.close();  */
	            String tmp=URLEncoder.encode(result.get(0), "GB2312");  
	            //设定post参数，和上图中的内容一致  
	            ArrayList<NameValuePair> postData = new ArrayList<NameValuePair>();    
	            postData.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$TextBox1", username));  
	            postData.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$TextBox2", password));  
	            postData.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$btnSearch", ""));  
	            postData.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$TextBox3", ""));
	            postData.add(new BasicNameValuePair("ctl00$ContentPlaceHolder1$TextBox4", ""));  
	            postData.add(new BasicNameValuePair("ctl00$drpLink", "http://www.cngjj.cn/")); 
	            postData.add(new BasicNameValuePair("__EVENTTARGET", "")); 
	            postData.add(new BasicNameValuePair("__EVENTARGUMENT", ""));  
	            postData.add(new BasicNameValuePair("__LASTFOCUS", ""));
	            postData.add(new BasicNameValuePair("__EVENTVALIDATION", result.get(1)));  
	            postData.add(new BasicNameValuePair("__VIEWSTATE", result.get(0)));  
	              
	            HttpPost post = new HttpPost("http://www.glzfgjj.cn:8136/Default.aspx");//构建post对象  
	            post.setEntity(new UrlEncodedFormEntity(postData));//捆绑参数  
	            response = client.execute(post);//执行登陆行为      
	            rawHtml = EntityUtils.toString(response.getEntity(), "gb2312");  
	            System.out.println(rawHtml);  
	            
	  
	        } catch (ClientProtocolException e) {  
	            System.out.println(e.getMessage());  
	        } catch (IOException e) {  
	            System.out.println(e.getMessage());  
	        }  
	        return true;
	    }  
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	      	
}	
