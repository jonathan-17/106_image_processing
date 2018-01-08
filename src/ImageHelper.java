import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;

import javax.imageio.ImageIO;

/*
 * create by Majaja
 * you should not copy it directly
 */

public class ImageHelper {
	
	public void gray(String fileName) {
		String newFileName = "gray.jpg";
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(fileName));
			BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());	
			for (int i = 0; i < bufferedImage.getWidth(); i++) {
				for (int j = 0; j < bufferedImage.getHeight(); j++) {
					int color = bufferedImage.getRGB(i, j);
					int r = (color >> 16) & 0xff;
					int g = (color >> 8) & 0xff;
					int b = color & 0xff;
					
					int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
					int newPixel = colorToRGB(255, gray, gray, gray);
					grayImage.setRGB(i, j, newPixel);
				}
			}
			File newFile = new File(newFileName);
			ImageIO.write(grayImage, "jpg", newFile);
			System.out.println(newFileName + "done");
		} catch (IOException e) {
			// TODO Auto-generated catch block m
			e.printStackTrace();
		}
	}
	public void neg(String fileName) {
		String newFileName = "neg.jpg";
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(fileName));
			BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());	
			for (int i = 0; i < bufferedImage.getWidth(); i++) {
				for (int j = 0; j < bufferedImage.getHeight(); j++) {
					int color = bufferedImage.getRGB(i, j);
					int r = (color >> 16) & 0xff;
					int g = (color >> 8) & 0xff;
					int b = color & 0xff;
					// 以灰階圖來做的話只要rgb取一個就可以做了
					int newPixel = colorToRGB(255, 255 - r,255 - g,255 - b);
					grayImage.setRGB(i, j, newPixel);
				}
			}
			File newFile = new File(newFileName);
			ImageIO.write(grayImage, "jpg", newFile);
			System.out.println(newFileName + "done");
		} catch (IOException e) {
			// TODO Auto-generated catch block a
			e.printStackTrace();
		}

	}
	public void setGamma(String fileName,float gamma) {
		String newFileName = "gamma" + gamma + ".jpg";
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(fileName));
			BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());	
			
			int[] arr = findMaxMin(fileName); 
			int r_max = arr[0] , g_max = arr[1] , b_max = arr[2];
			int r_min = arr[3] , g_min = arr[4] , b_min = arr[5];
			double r_ratio = r_max-r_min, g_ratio = g_max-g_min, b_ratio = b_max-b_min;
			
			for (int i = 0; i < bufferedImage.getWidth(); i++) {
				for (int j = 0; j < bufferedImage.getHeight(); j++) {
					int color = bufferedImage.getRGB(i, j);
					//System.out.println(color+"");
					int r = (color >> 16) & 0xff;
					int g = (color >> 8) & 0xff;
					int b = color & 0xff;
					
					
					r = roundding(((r-r_min) / r_ratio)*255);
					g = roundding(((g-g_min) / g_ratio)*255);
					b = roundding(((b-b_min) / b_ratio)*255);
					float f_r = r/255f;
					float f_g = g/255f;
					float f_b = b/255f;  //先正規化為0~1之間
					//roundding( 四捨五入
					//Math.pow(f_r,gamma) 取次方  
					//*255) 乘255回來
					
					//((p-min / max -min) *255 )^r
					
					int newPixel = colorToRGB(255,roundding( Math.pow(f_r,gamma)*255),roundding(Math.pow(f_g,gamma)*255),roundding(Math.pow(f_b,gamma)*255));
					grayImage.setRGB(i, j, newPixel);
				}
			}
			File newFile = new File(newFileName);
			ImageIO.write(grayImage, "jpg", newFile);
			System.out.println(newFileName + " done");
		} catch (IOException e) {
			// TODO Auto-generated catch block j
			e.printStackTrace();
		}

	}
	public void setBinary(String fileName) {
		String newFileName = "binary.jpg";
		BufferedImage bufferedImage;
		float count = 1.000f;
		float arr[] = {0.0000f,0.0000f,0.0000f};
		try {
			bufferedImage = ImageIO.read(new File(fileName));
			for (int i = 0; i < bufferedImage.getWidth(); i++) {
				for (int j = 0; j < bufferedImage.getHeight(); j++) {
					int color = bufferedImage.getRGB(i, j);
					int r = (color >> 16) & 0xff;
					int g = (color >> 8) & 0xff;
					int b = color & 0xff;
					
					arr[0] = arr[0]+ ((float)r/255.0000f );
					arr[1] = arr[0]+ ((float)g/255.0000f );
					arr[2] = arr[0]+ ((float)b/255.0000f );
					count+=1.000f;
				}
			}
			
			arr[0] /= count;
			arr[1] /= count;
			arr[2] /= count;
			// 以灰階圖來做的話只要rgb取一個就可以做了
			BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());	
			for (int i = 0; i < bufferedImage.getWidth(); i++) {
				for (int j = 0; j < bufferedImage.getHeight(); j++) {
					int color = bufferedImage.getRGB(i, j);
					int r = (color >> 16) & 0xff;
					int g = (color >> 8) & 0xff;
					int b = color & 0xff;
					int newPixel = colorToRGB(255, forBinary(r,roundding(arr[0]*255)), forBinary(g,roundding(arr[1]*255)), forBinary(b,roundding(arr[2]*255)));
					grayImage.setRGB(i, j, newPixel);
				}
			}
			File newFile = new File(newFileName);
			ImageIO.write(grayImage, "jpg", newFile);
			System.out.println(newFileName + " done");
		} catch (IOException e) {
			// TODO Auto-generated catch block a
			e.printStackTrace();
		}

	}
	public void setSaltAndPepper(String fileName, float rate) {
		String newFileName = "SaltAndPepper.jpg";
		BufferedImage bufferedImage;
		Random rand = new Random();
		try {
			bufferedImage = ImageIO.read(new File(fileName));
			BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());	
			for (int i = 0; i < bufferedImage.getWidth(); i++) {
				for (int j = 0; j < bufferedImage.getHeight(); j++) {
					int color = bufferedImage.getRGB(i, j);
					int r = (color >> 16) & 0xff;
					int g = (color >> 8) & 0xff;
					int b = color & 0xff;
	
					if( rand.nextFloat() > rate){
						if(rand.nextFloat() > 0.5f){
							int newPixel = colorToRGB(255, 255, 255, 255);
							grayImage.setRGB(i, j, newPixel);
						}else{
							int newPixel = colorToRGB(255, 0, 0, 0);
							grayImage.setRGB(i, j, newPixel);
						}
					}else{
						int newPixel = colorToRGB(255, r, g, b);
						grayImage.setRGB(i, j, newPixel);
					}
					// 以灰階圖來做的話只要rgb取一個就可以做了
				}
			}
			File newFile = new File(newFileName);
			ImageIO.write(grayImage, "jpg", newFile);
			System.out.println(newFileName + "done");
		} catch (IOException e) {
			// TODO Auto-generated catch block j
			e.printStackTrace();
		}

	}
	public void filter(String filterName,String fileName,int filter_size){
		int start = (filter_size-1)/2;
		
		String newFileName = "filter_with_" + filterName +".jpg";
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(fileName));
			BufferedImage newImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());	
			int end_width = bufferedImage.getWidth()-start;
			int end_height = bufferedImage.getHeight()-start; 
			for (int i = start ; i < end_width; i++) {
				for (int j = start ; j < end_height; j++) {

					int r_arr[] = new int[9];
					int g_arr[] = new int[9];
					int b_arr[] = new int[9];
					
					int index = 0;
					for (int k = -1 ; k <= start; k++){
						for (int l = -1 ; l <= start ; l++){
							int color = bufferedImage.getRGB(i+l, j+k);
							//System.out.println(color+"");
							r_arr[index] = (color >> 16) & 0xff;
							g_arr[index]  = (color >> 8) & 0xff;
							b_arr[index]  = color & 0xff;
							index++;
						}
					}
					
					int r = 0;
					int g = 0;
					int b = 0;
					
					if(filterName.equals("median")){
						r = returnMedian(r_arr);
						g = returnMedian(g_arr);
						b = returnMedian(b_arr);
					}
					if(filterName.equals("max")){
						r = returnMax(r_arr);
						g = returnMax(g_arr);
						b = returnMax(b_arr);
					}		
					// 以灰階圖來做的話只要rgb取一個就可以做了
					int newPixel = colorToRGB(255, r, g, b);
					newImage.setRGB(i, j, newPixel);
				}
			}
			File newFile = new File(newFileName);
			ImageIO.write(newImage, "jpg", newFile);
			System.out.println(newFileName + "done");
		} catch (IOException e) {
			// TODO Auto-generated catch block a
			e.printStackTrace();
		}		
		
	}
	public void setFlatter(String fileName) {
		String newFileName = "contrast" + ".jpg";
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(fileName));
			BufferedImage newImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());	

			int[] arr = findMaxMin(fileName); 
			int r_max = arr[0] , g_max = arr[1] , b_max = arr[2];
			int r_min = arr[3] , g_min = arr[4] , b_min = arr[5];
			double r_ratio = r_max-r_min, g_ratio = g_max-g_min, b_ratio = b_max-b_min;
			
			
			for (int i = 0; i < bufferedImage.getWidth(); i++) {
				for (int j = 0; j < bufferedImage.getHeight(); j++) {
					int color = bufferedImage.getRGB(i, j);
					int r = (color >> 16) & 0xff;
					int g = (color >> 8) & 0xff;
					int b = color & 0xff;
					
					r = roundding(((r-r_min) / r_ratio)*255);
					g = roundding(((g-g_min) / g_ratio)*255);
					b = roundding(((b-b_min) / b_ratio)*255);
					// 以灰階圖來做的話只要rgb取一個就可以做了
					int newPixel = colorToRGB(255, r, g, b);
					newImage.setRGB(i, j, newPixel);
				}
			}		
			
			File newFile = new File(newFileName);
			ImageIO.write(newImage, "jpg", newFile);
			System.out.println(newFileName + " done");
		} catch (IOException e) {
			// TODO Auto-generated catch block m
			e.printStackTrace();
		}

	}
	public void laplacian(String fileName){
		int start = 1;
		String newFileName = "laplacian" +".jpg";
		BufferedImage bufferedImage;
		try {
			bufferedImage = ImageIO.read(new File(fileName));
			BufferedImage newImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());	
			int end_width = bufferedImage.getWidth()-start;
			int end_height = bufferedImage.getHeight()-start; 
			for (int i = start ; i < end_width; i++) {
				for (int j = start ; j < end_height; j++) {
					int color = bufferedImage.getRGB(i, j);
					int r = (color >> 16) & 0xff;
					int g = (color >> 8) & 0xff;
					int b = color & 0xff;
					
					color = bufferedImage.getRGB(i-1, j);
					int r_top = (color >> 16) & 0xff;
					int g_top = (color >> 8) & 0xff;
					int b_top = color & 0xff;
					
					color = bufferedImage.getRGB(i, j-1);
					int r_left = (color >> 16) & 0xff;
					int g_left = (color >> 8) & 0xff;
					int b_left = color & 0xff;
					
					color = bufferedImage.getRGB(i, j+1);
					int r_right = (color >> 16) & 0xff;
					int g_right = (color >> 8) & 0xff;
					int b_right = color & 0xff;
					
					color = bufferedImage.getRGB(i+1, j);
					int r_bot = (color >> 16) & 0xff;
					int g_bot = (color >> 8) & 0xff;
					int b_bot = color & 0xff;
					
					int r_new = Math.abs( 4*r - r_top - r_left - r_right - r_bot);
					int g_new = Math.abs( 4*g - g_top - g_left - g_right - g_bot);
					int b_new = Math.abs( 4*b - b_top - b_left - b_right - b_bot);
					// 以灰階圖來做的話只要rgb取其中一個就可以做了
					int newPixel = colorToRGB(255, r_new, g_new, b_new);
					newImage.setRGB(i, j, newPixel);
				}
			}
			File newFile = new File(newFileName);
			ImageIO.write(newImage, "jpg", newFile);
			System.out.println(newFileName + "done");
		} catch (IOException e) {
			// TODO Auto-generated catch block j
			e.printStackTrace();
		}		
		
	}
	private int colorToRGB(int alpha, int red, int green, int blue) {
		int newPixel = 0;
		newPixel += alpha;
		newPixel = newPixel << 8;
		newPixel += red;
		newPixel = newPixel << 8;
		newPixel += green;
		newPixel = newPixel << 8;
		newPixel += blue;
		return newPixel;

	}
	public int roundding(double a){
		return new BigDecimal(a).setScale(1, BigDecimal.ROUND_HALF_UP).intValue();
	}
	public int forBinary(int data, int threshlod){
		int result;
		if (data > threshlod){
			result = 255;
		}else{
			result = 0;
		}
		return result;
	}
	public int returnMedian(int arr[]){
		int size = arr.length;
		for (int i = 0 ; i<size; i++){
			for (int j = i ; j< size ; j++){
				if(arr[i] > arr[j]){
					int temp = arr[j];
					arr[j] = arr[i];
					arr[i] = temp;
				}
			}
		}
		return arr[4]; 
	}
	public int returnMax(int arr[]){
		int size = arr.length;
		for (int i = 0 ; i<size; i++){
			for (int j = i ; j< size ; j++){
				if(arr[i] > arr[j]){
					int temp = arr[j];
					arr[j] = arr[i];
					arr[i] = temp;
				}
			}
		}
		return arr[8]; 
	}
	public int[] findMaxMin(String fileName){
		BufferedImage bufferedImage;
		int r_max = 0 , g_max = 0 , b_max = 0;
		int r_min = 255 , g_min = 255 , b_min = 255;
		try {
			bufferedImage = ImageIO.read(new File(fileName));
			int width = bufferedImage.getWidth();
			int height = bufferedImage.getHeight();
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					int color = bufferedImage.getRGB(i, j);
					//System.out.println(color+"");
					int r = (color >> 16) & 0xff;
					int g = (color >> 8) & 0xff;
					int b = color & 0xff;
					
					if(r > r_max){  // find max
						r_max = r;
					}
					if(g > g_max){
						g_max = g;
					}
					if(b > b_max){
						b_max = b;
					}
					if(r < r_min){  // find min
						r_min = r;
					}
					if(g < g_min){
						g_min = g;
					}
					if(b < b_min){
						b_min = b;
					}				
					
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block c
			e.printStackTrace();
		}
		int arr[] = {r_max,g_max,b_max,r_min,g_min,b_min};
		return arr;
	}

}
