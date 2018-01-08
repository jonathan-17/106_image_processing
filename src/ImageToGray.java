import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ImageToGray {
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
}
