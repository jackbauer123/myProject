package com.login;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import com.ImageCheck.Tess4jImgCheck;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import piccode.ImageParser;

public class loginTest {
	
	@Test
	public void loginWuZhou(){
		LoginIn lg=new LoginIn("450329198901070321","198917");
		lg.loginWuZhou();
	}
	
	@Test
	public void loginLiuZhou(){
		LoginIn lg=new LoginIn("450205197302260747","4502051973pjb");
		lg.loginLiuZhou();
	}
	
	@Test
	public void loginGuiLin(){
		LoginIn lg=new LoginIn("450821198808181453","181453");
		lg.loginGuiLin();
	}
	
	
	@Test
	public void imgCheck() throws TesseractException{
		File imageFile = new File("G:\\testDecryption\\TestURL\\verifyCode.jpeg");  
	    Tesseract instance = new Tesseract();
	    //将验证码图片的内容识别为字符串 
	    String result = instance.doOCR(imageFile);
	    System.out.println(result);
	}
	
	
	@Test
	public void imgCheck_() throws TesseractException{
		File imageFile = new File("G:\\testDecryption\\TestURL\\verifyCode.jpeg");  
	    ITesseract instance = new Tesseract();
	    instance.setLanguage("eng");
	    File tessDataFolder = LoadLibs.extractTessResources("tessdata");
	    instance.setDatapath(tessDataFolder.getParent());
	    //将验证码图片的内容识别为字符串 
	    String result = instance.doOCR(imageFile);
	    System.out.println(result);
	}
	
	
	
	@Test
	public void imgCheck2() {  
		HttpClient client = HttpClients.createDefault();//实例化httpclient 
        HttpGet getVerifyCode = new HttpGet("http://www.gxwzgjj.com/GetCode1.asp");//验证码get  
        FileOutputStream fileOutputStream = null;  
        HttpResponse response;  
        try {  
            response = client.execute(getVerifyCode);//获取验证码 
            
            
            InputStream inStream = response.getEntity().getContent();
         // 处理内容
            // System.out.println(new String(responseBody));
            BufferedImage iag      = ImageIO.read(inStream);
            Tess4jImgCheck      imgIdent = new Tess4jImgCheck(iag);

            imgIdent.saveJPEG(iag, "ddd.jpg");
            String validate = imgIdent.getValidatecode(4);
            
            System.out.println("kkkkkk");
            System.out.println(validate);
            
            
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
	
	@Test
	public void imgCheck3() throws Exception{
		ImageParser parse = new ImageParser();
		HttpClient client = HttpClients.createDefault();//实例化httpclient 
        HttpGet getVerifyCode = new HttpGet("http://www.gxwzgjj.com/GetCode1.asp");//验证码get  
        FileOutputStream fileOutputStream = null;  
        HttpResponse response;  
		
        try {  
            response = client.execute(getVerifyCode);//获取验证码    
            InputStream inStream = response.getEntity().getContent();
            String a = parse.getCode(inStream);
            System.out.println("-------------"+a);
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
	
	
	
	
	
	
}
