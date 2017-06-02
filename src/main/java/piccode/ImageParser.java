package piccode;

/**
 * 图片解析引擎，适合做网站验证码的分析。
 * 首先必须载入样品，解析器将从左到右横向扫描，发现于样本的就自动记录。
 * 当然本程序不适合样本不是唯一的，也就是说要识别的图片被缩放或者坐标变动和变形本程序无法进行这样的识别。
 * 如果图片中的颜色变化非常大，此程序可能会有问题，当然了你可以选择一个标准的值做为转换成0,1矩阵的标准。
 * 
 * 样本的制作：请将样本转换成灰度模式，只含有两色最好，当然了不转换我也帮你转换了。
 * 
 */

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageParser {

	// ------------------------------------------------------------ Private Data
	// 样本的矩阵
	private static List<byte[][]> swatches = null;

	// 样本的值
	private static List<String> swatcheValues = null;

	// 图片文件的矩阵化
	private byte[][] targetColors;

	public String getCode(InputStream streram) throws Exception {
		ImageParser parse = new ImageParser();
		return parse.parseValue(streram);
	}

	int count = 0;
	// ------------------------------------------------------------ Constructors

	/**
	 * 载入所有样本路径与其样本对应的数值
	 * 
	 * @param files
	 */
	public ImageParser() {
		String[] files = new String[10];
		String[] values = new String[10];
		for (int i = 0; i < files.length; i++) {
			files[i] = i + ".bmp";
			values[i] = String.valueOf(i);
		}
		// 只允许样本创建一次即可
		if (swatches == null && swatcheValues == null) {
			int fileslength = files.length;
			int valueslength = values.length;
			if (fileslength != valueslength) {
				System.out.println("样本文件与样本数值不匹配！请重新设置！");
				return;
			}
			swatches = new ArrayList<byte[][]>(fileslength);
			swatcheValues = new ArrayList<String>(valueslength);
			int i = 2;
			try {
				for (; i < files.length; i++) {
					swatches.add(imageToMatrix(files[i]));
					swatcheValues.add(i, values[i]);
				}
			} catch (Exception e) {
				System.out.println(files[i] + " can not be parsed");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解析图片的值
	 * 
	 * @param parseFilePath
	 *            给出图片路径
	 * @return 返回字符串
	 * @throws Exception
	 */
	public String parseValue(InputStream streram) throws Exception {
		StringBuffer result = new StringBuffer();
		targetColors = imageToMatrix(streram);

		//打印二值矩阵
		for(int ii =0 ; ii<targetColors.length ; ii++){
			for(int jj =0 ; jj<targetColors[ii].length; jj++){
				System.out.print(targetColors[ii][jj]);
			}
			System.out.println();
		}
		//计算4个字符的左~右位置
		List<Integer> left_right =new ArrayList<Integer>();
		for(int j = 0 ; j<targetColors[0].length ; j++){
			boolean flag = false;
			for(int i = 0 ; i<targetColors.length ; i++){
				if(targetColors[i][j] == 1){
					for(int k=0 ; k<j ;k++){
						if(targetColors[i][j] == 1){
							flag = true;
							break;
						}
					}
				}
			}
			if(flag){
				left_right.add(j);
			}
		}
		
		
		//计算分割位置
		List<Integer> left_right_split =new ArrayList<Integer>();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<left_right.size()-1;i++){
			if(left_right.get(i)+1 != left_right.get(i+1)){
				left_right_split.add(i);
			}
		}
		
		//计算4个字符的上~下位置
		int begin = 0;
		int end = 0;
		List<Integer> top_botton = new ArrayList<Integer>();
		for(int x=0;x<4;x++){
			if(x == 3){
				begin = end ;
				end =left_right.size();
			}
			else {
				if(end == 0){
					end = left_right_split.get(x)+1;
				}else{
					begin = end;
					end = left_right_split.get(x)+1;
				}
			}
			
			for(int i=0 ; i<targetColors.length ; i++ ){
				boolean flag = false;
				for(int j=begin ; j<end ; j++){
					if(targetColors[i][left_right.get(j)] == 1){
						flag = true;
						break;
					}
				}
				if(flag){
					top_botton.add(i);
				}
			}
		}
		
		
		//把字符起始位置转换成二维数组
		int[][] position = new int[8][2];
		int sign = 0;
		for(int i=0;i<top_botton.size()-1;i++){
			if(i == 0){
				position[sign][0] = top_botton.get(i);
			}else if(i == top_botton.size()-2){
				position[sign][1] = top_botton.get(top_botton.size()-1);
				sign++;
			}else{
				if(top_botton.get(i)+1 != top_botton.get(i+1)){
					position[sign][1] = top_botton.get(i);
					sign++;
					position[sign][0] = top_botton.get(i+1);
				}
			}
		}
		
		for(int i=0;i<left_right.size()-1;i++){
			if(i == 0){
				position[sign][0] = left_right.get(i);
			}else if(i == left_right.size()-2){
				position[sign][1] = left_right.get(left_right.size()-1);
				sign++;
			}else{
				if(left_right.get(i)+1 != left_right.get(i+1)){
					position[sign][1] = left_right.get(i);
					sign++;
					position[sign][0] = left_right.get(i+1);
				}
			}
		}
		
		//裁剪二值矩阵
		for(int i=0 ; i<position.length/2 ; i++){
			int top = position[i][0];
			int botton = position[i][1];
			int left = position[i+4][0];
			int right = position[i+4][1];
			byte[][] temp = splitMatrix(targetColors, left, top, right-left+1, botton-top+1);
			
			//打印裁剪出来的矩阵
			for(int x=0;x<temp.length;x++){
				for(int y =0 ;y<temp[x].length;y++){
					System.out.print(temp[x][y]);
				}
				System.out.println();
			}
			
			Iterator<byte[][]> itx = swatches.iterator();
			int x=0;
			while (itx.hasNext()) {
				byte[][] bytes = (byte[][]) itx.next();
				if (isMatrixInBigMatrix(bytes, temp)) {
					result.append(swatcheValues.get(x));
					break;
				}
				x++;
			}
			
		}
		return result.toString();
	}

	/**
	 * 判断一个矩阵是否在另外的矩阵中
	 * 
	 * @param source
	 *            源矩阵
	 * @param bigMatrix
	 *            大的矩阵
	 * @return 如果存在就返回 true
	 */
	private static final boolean isMatrixInBigMatrix(byte[][] source, byte[][] bigMatrix) {
		if (source == bigMatrix)
			return true;
		if (source == null || bigMatrix == null)
			return false;

		if (source.length > bigMatrix.length)
			return false;

		try {
			for (int i = 0; i < source.length; i++) {
				if (source[i].length > bigMatrix[i].length)
					return false;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}

		int width = source.length;
		int height = source[0].length;
		int count = 0;

		int comparecount = height * width;

		for (int i=0 ; i<width; i++) {
			for (int j = 0; j < height; j++) {
				if ( (source[i][j] & bigMatrix[i][j]) == source[i][j] ) {
					count++;
				}else
					break;
			}
		}
		if (count > comparecount*0.95){
			for(int i =0; i<source.length;i++){
				for(int j =0; j<source[i].length;j++){
					System.out.print(source[i][j]);
				}
				System.out.println();
			}
			
			System.out.println();
			
			for(int i =0; i<bigMatrix.length;i++){
				for(int j =0; j<bigMatrix[i].length;j++){
					System.out.print(bigMatrix[i][j]);
				}
				System.out.println();
			}
			System.out.println();
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 切割矩阵
	 * 
	 * @param source
	 *            源矩阵
	 * @param x
	 *            X坐标
	 * @param y
	 *            Y坐标
	 * @param width
	 *            矩阵的宽度
	 * @param height
	 *            矩阵的高度
	 * @return 切割后的矩阵
	 */
	private static final byte[][] splitMatrix(byte[][] source, int x, int y, int width, int height) {
		byte[][] resultbytes = new byte[height][width];
		for (int i = y, k = 0; i < height + y; i++, k++) {
			for (int j = x, l = 0; j < width + x; j++, l++) {
				resultbytes[k][l] = source[i][j];
			}
		}
		return resultbytes;
	}

	/**
	 * 图片转换成矩阵数组
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 返回矩阵
	 * @throws Exception
	 *             可能会抛出异常
	 */
	private byte[][] imageToMatrix(Image image) throws Exception {
		// 读入文件
		// Image image = ImageIO.read(new File(filePath));
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		BufferedImage src = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		src.getGraphics().drawImage(image, 0, 0, null);

		byte[][] colors = new byte[h][w];
		
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int rgb = src.getRGB(j, i);
				// 像素进行灰度处理
				String sRed = Integer.toHexString(rgb).substring(2, 4);
				String sGreen = Integer.toHexString(rgb).substring(4, 6);
				String sBlank = Integer.toHexString(rgb).substring(6, 8);
				long ired = Math.round((Integer.parseInt(sRed, 16) * 0.3 + 0.5d));
				long igreen = Math.round((Integer.parseInt(sGreen, 16) * 0.59 + 0.5d));
				long iblank = Math.round((Integer.parseInt(sBlank, 16) * 0.11 + 0.5d));
				long al = ired + igreen + iblank;

				/* 将图像转换成0,1 */
				// 此处的值可以将来修改成你所需要判断的值
				colors[i][j] = (byte) (al > 127 ? 0 : 1);
			}
		}
		
		for(int i =0; i<colors.length;i++){
			for(int j =0; j<colors[i].length;j++){
				if(colors[i][j] == 1 && colors[i-1][j] == 0 && colors[i+1][j] == 0 && colors[i][j-1] == 0 & colors[i][j+1] ==0){
					colors[i][j] = 0;
				}
				
			}
		}
		return colors;
	}

	private byte[][] imageToMatrix(String filePath) throws Exception {
		return imageToMatrix(ImageIO.read(new File(filePath)));
	}

	private byte[][] imageToMatrix(InputStream stream) throws Exception {
		return imageToMatrix(ImageIO.read(stream));
	}
}
