package com.test.wuzhou;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.junit.Test;

import com.wuzhou.ChaoJiYing;

public class imgCheck {
	
	// 创建CookieStore实例
	//static CookieStore cookieStore = null;
	//static HttpClientContext context = null;
	
	
	@Test
	public void imgTest() {
		
		String url="http://www.gxwzgjj.com/GetCode1.asp";
		String imgValue=doGetImg(url);
		System.out.println(imgValue);
	}
	
	public String doGetImg(String url){
		CookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpClients = HttpClients.custom()  
	             .setDefaultCookieStore(cookieStore)  
	             .build(); 
		
		 CloseableHttpClient httpclient = HttpClients.createDefault();
	        HttpGet httpget = new HttpGet(url); 
	        CloseableHttpResponse response=null;
	        InputStream inputStream=null;
	        HttpEntity entity=null;
	        String imgValue=null;
	        try {
				response = httpclient.execute(httpget);
				//System.out.println("context cookies:"+ context.getCookieStore().getCookies());
				entity = response.getEntity();
				inputStream = entity.getContent(); 
				 String value=ChaoJiYing.PostPic("jackbauer", "189@yuan", "893502", "1902", "0",toByteArray(inputStream));
	             JSONObject jb=new JSONObject(value);
	             imgValue=jb.getString("pic_str");
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}  
	      //  OutputStream outStream = new FileOutputStream(picName);  
	        return imgValue;
	}
	
	public static byte[] toByteArray(InputStream input) throws IOException {
	    ByteArrayOutputStream output = new ByteArrayOutputStream();
	    byte[] buffer = new byte[4096];
	    int n = 0;
	    while (-1 != (n = input.read(buffer))) {
	        output.write(buffer, 0, n);
	    }
	    return output.toByteArray();
	}
	
}
