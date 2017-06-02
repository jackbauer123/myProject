package com.checkimg;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;



public class ImgCreate {
	
	
	
	String DOWNLOAD_DIR="G:\\imgTest";
	
	String TRAIN_DIR="G:\\imgTest\\train";
    
 // 2.去除图像干扰像素（非必须操作，只是可以提高精度而已）。
    public static BufferedImage removeInterference(BufferedImage image)  
            throws Exception {  
        int width = image.getWidth();  
        int height = image.getHeight();  
        for (int x = 0; x < width; ++x) {  
            for (int y = 0; y < height; ++y) {  
                if (isFontColor(image.getRGB(x, y))) {
                    // 如果当前像素是字体色，则检查周边是否都为白色，如都是则删除本像素。
                    int roundWhiteCount = 0;
                    if(isWhiteColor(image, x+1, y+1))
                        roundWhiteCount++;
                    if(isWhiteColor(image, x+1, y-1))
                        roundWhiteCount++;
                    if(isWhiteColor(image, x-1, y+1))
                        roundWhiteCount++;
                    if(isWhiteColor(image, x-1, y-1))
                        roundWhiteCount++;
                    if(roundWhiteCount == 4) {
                        image.setRGB(x, y, Color.WHITE.getRGB());  
                    }
                } 
            }  
        }  
        return image;  
     }
    
    // 取得指定位置的颜色是否为白色，如果超出边界，返回true
    // 本方法是从removeInterference方法中摘取出来的。单独调用本方法无意义。
    private static boolean isWhiteColor(BufferedImage image, int x, int y) throws Exception {
        if(x < 0 || y < 0) return true;
        if(x >= image.getWidth() || y >= image.getHeight()) return true;

        Color color = new Color(image.getRGB(x, y));
        
        return color.equals(Color.WHITE)?true:false;
    }
    
    
    
 // 3.判断拆分验证码的标准：就是定义验证码中包含的各数字的x、y坐标值，及它们的宽度（width）、高度（height）。
    private static List<BufferedImage> splitImage(BufferedImage image) throws Exception {
        final int DIGIT_WIDTH = 19;
        final int DIGIT_HEIGHT = 17;

        List<BufferedImage> digitImageList = new ArrayList<BufferedImage>();
        digitImageList.add(image.getSubimage(2, 2, DIGIT_WIDTH, DIGIT_HEIGHT));
        digitImageList.add(image.getSubimage(20, 2, DIGIT_WIDTH, DIGIT_HEIGHT));
        digitImageList.add(image.getSubimage(40, 2, DIGIT_WIDTH, DIGIT_HEIGHT));
        digitImageList.add(image.getSubimage(60, 2, DIGIT_WIDTH, DIGIT_HEIGHT));

        return digitImageList;
    }
    
    
 // 4.判断字体的颜色含义：正常可以用rgb三种颜色加起来表示，字与非字应该有显示的区别，找出来。
    private static boolean isFontColor(int colorInt) {
        Color color = new Color(colorInt);

        return color.getRed() + color.getGreen() + color.getBlue() == 340;
    }
    
    
 // 5.将下载的验证码图片全部拆分到另一个目录。
    public void generateStdDigitImgage() throws Exception {
        File dir = new File(DOWNLOAD_DIR);
        /*File[] files = dir.listFiles(new ImageFileFilter("png"));
        
        int counter = 0;
        for (File file : files) {
            BufferedImage image = ImageIO.read(file);
            removeInterference(image); 
            List<BufferedImage> digitImageList = splitImage(image);
            for (int i = 0; i < digitImageList.size(); i++) {
                BufferedImage bi = digitImageList.get(i);
                ImageIO.write(bi, "PNG", new File(TRAIN_DIR, "temp_" + counter++ + ".png"));
            }
        }*/
        System.out.println("生成供比对的图片完毕，请到目录中手工识别并重命名图片，并删除其它无关图片！");
    }
}
