package com.login;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

public class CheckTest {
	
	
	String DOWNLOAD_DIR="G:\\imgTest";
	
	String TRAIN_DIR="G:\\imgTest\\train";
	
	// 1.下载验证码：将多个验证码图片下载到指定目录，要求各种可能的验证码（单个数字）都应该有，比如：0-9。
    private void downloadImage() throws Exception {
        HttpClient httpClient = new DefaultHttpClient();
        for (int i = 0; i < 10; i++) {
            String url = "http://www.yoursite.com/yz.php";
            HttpGet getMethod = new HttpGet(url);
            try {
                HttpResponse response = httpClient.execute(getMethod, new BasicHttpContext());
                HttpEntity entity = response.getEntity();
                InputStream instream = entity.getContent(); 
                OutputStream outstream = new FileOutputStream(new File(DOWNLOAD_DIR, i + ".png"));
                int l = -1;
                byte[] tmp = new byte[2048]; 
                while ((l = instream.read(tmp)) != -1) {
                    outstream.write(tmp);
                } 
                outstream.close();
            } finally {
                getMethod.releaseConnection();
            }
        }

        System.out.println("下载验证码完毕！");
    }
    
    
    // 7.测试判断效果：运行方法，可以调整rgb三值，以达到高的分辨率。
    // 目前此方法提供在输出判断结果的同时，在目标目录生成以判断结果命名的新验证码图片，以批量检查效果。
    public void testDownloadImage() throws Exception {
        File dir = new File(DOWNLOAD_DIR);
       // File[] files = dir.listFiles(new ImageFileFilter("png"));
        
       // for (File file : files) {
         //   String validateCode = getValidateCode(file);
           // System.out.println(file.getName() + "=" + validateCode);
       // }
        
        System.out.println("判断完毕，请到相关目录检查效果！");
    }
	
}
