package com.wuzhou;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpClientImg {
	
	
	public void doGetImg(String url){
		 CloseableHttpClient httpclient = HttpClients.createDefault();
	        HttpGet httpget = new HttpGet(url); 
	        CloseableHttpResponse response=null;
	        InputStream inputStream=null;
	        HttpEntity entity=null;
	        try {
				response = httpclient.execute(httpget);
				entity = response.getEntity();
				inputStream = entity.getContent(); 
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	      //  OutputStream outStream = new FileOutputStream(picName);  
	}
}
