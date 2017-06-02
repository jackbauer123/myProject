package piccode;

/**
 * ͼƬ�������棬�ʺ�����վ��֤��ķ�����
 * ���ȱ���������Ʒ���������������Һ���ɨ�裬�����������ľ��Զ���¼��
 * ��Ȼ�������ʺ���������Ψһ�ģ�Ҳ����˵Ҫʶ���ͼƬ�����Ż�������䶯�ͱ��α������޷�����������ʶ��
 * ���ͼƬ�е���ɫ�仯�ǳ��󣬴˳�����ܻ������⣬��Ȼ�������ѡ��һ����׼��ֵ��Ϊת����0,1����ı�׼��
 * 
 * �������������뽫����ת���ɻҶ�ģʽ��ֻ������ɫ��ã���Ȼ�˲�ת����Ҳ����ת���ˡ�
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
	// �����ľ���
	private static List<byte[][]> swatches = null;

	// ������ֵ
	private static List<String> swatcheValues = null;

	// ͼƬ�ļ��ľ���
	private byte[][] targetColors;

	public String getCode(InputStream streram) throws Exception {
		ImageParser parse = new ImageParser();
		return parse.parseValue(streram);
	}

	int count = 0;
	// ------------------------------------------------------------ Constructors

	/**
	 * ������������·������������Ӧ����ֵ
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
		// ֻ������������һ�μ���
		if (swatches == null && swatcheValues == null) {
			int fileslength = files.length;
			int valueslength = values.length;
			if (fileslength != valueslength) {
				System.out.println("�����ļ���������ֵ��ƥ�䣡���������ã�");
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
	 * ����ͼƬ��ֵ
	 * 
	 * @param parseFilePath
	 *            ����ͼƬ·��
	 * @return �����ַ���
	 * @throws Exception
	 */
	public String parseValue(InputStream streram) throws Exception {
		StringBuffer result = new StringBuffer();
		targetColors = imageToMatrix(streram);

		//��ӡ��ֵ����
		for(int ii =0 ; ii<targetColors.length ; ii++){
			for(int jj =0 ; jj<targetColors[ii].length; jj++){
				System.out.print(targetColors[ii][jj]);
			}
			System.out.println();
		}
		//����4���ַ�����~��λ��
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
		
		
		//����ָ�λ��
		List<Integer> left_right_split =new ArrayList<Integer>();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<left_right.size()-1;i++){
			if(left_right.get(i)+1 != left_right.get(i+1)){
				left_right_split.add(i);
			}
		}
		
		//����4���ַ�����~��λ��
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
		
		
		//���ַ���ʼλ��ת���ɶ�ά����
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
		
		//�ü���ֵ����
		for(int i=0 ; i<position.length/2 ; i++){
			int top = position[i][0];
			int botton = position[i][1];
			int left = position[i+4][0];
			int right = position[i+4][1];
			byte[][] temp = splitMatrix(targetColors, left, top, right-left+1, botton-top+1);
			
			//��ӡ�ü������ľ���
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
	 * �ж�һ�������Ƿ�������ľ�����
	 * 
	 * @param source
	 *            Դ����
	 * @param bigMatrix
	 *            ��ľ���
	 * @return ������ھͷ��� true
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
	 * �и����
	 * 
	 * @param source
	 *            Դ����
	 * @param x
	 *            X����
	 * @param y
	 *            Y����
	 * @param width
	 *            ����Ŀ��
	 * @param height
	 *            ����ĸ߶�
	 * @return �и��ľ���
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
	 * ͼƬת���ɾ�������
	 * 
	 * @param filePath
	 *            �ļ�·��
	 * @return ���ؾ���
	 * @throws Exception
	 *             ���ܻ��׳��쳣
	 */
	private byte[][] imageToMatrix(Image image) throws Exception {
		// �����ļ�
		// Image image = ImageIO.read(new File(filePath));
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		BufferedImage src = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		src.getGraphics().drawImage(image, 0, 0, null);

		byte[][] colors = new byte[h][w];
		
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int rgb = src.getRGB(j, i);
				// ���ؽ��лҶȴ���
				String sRed = Integer.toHexString(rgb).substring(2, 4);
				String sGreen = Integer.toHexString(rgb).substring(4, 6);
				String sBlank = Integer.toHexString(rgb).substring(6, 8);
				long ired = Math.round((Integer.parseInt(sRed, 16) * 0.3 + 0.5d));
				long igreen = Math.round((Integer.parseInt(sGreen, 16) * 0.59 + 0.5d));
				long iblank = Math.round((Integer.parseInt(sBlank, 16) * 0.11 + 0.5d));
				long al = ired + igreen + iblank;

				/* ��ͼ��ת����0,1 */
				// �˴���ֵ���Խ����޸ĳ�������Ҫ�жϵ�ֵ
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
