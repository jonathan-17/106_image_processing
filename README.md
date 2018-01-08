# 106_image_processing

106(2017) image processing, You should not use this code directly.
		  
![details](https://imgur.com/WPZWtvE.jpg)
the issue above is from 鍾承軒 in NCHU


宣告函式名為gray，回傳方法為不回傳(void)，作用域為public(即為可從外部呼叫)，呼叫時需要給定一個String參數

	public void gray(String fileName) {
	}
宣告灰階的檔名

	String newFileName = "gray.jpg";

用BufferedImage這個物件去存ImageIO讀到的檔案，因為會有IO的問題(例如找不到檔案會發生錯誤)，所以要用try catch包起來

	try {
		BufferedImage bufferedImage;
		bufferedImage = ImageIO.read(new File(fileName));
	} catch (IOException e) {
		// TODO Auto-generated catch block 
		e.printStackTrace();
	}
	

宣告另一個BufferImage物件用來存灰階影像，參數為輸入的圖檔寬、長、型態

	BufferedImage grayImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bufferedImage.getType());

用雙層for迴圈去取出原圖的每一個pixel值
			
	for (int i = 0; i < bufferedImage.getWidth(); i++) {
		for (int j = 0; j < bufferedImage.getHeight(); j++) {
			int color = bufferedImage.getRGB(i, j);
		}
	}

這個color是rgb跟alpha加總起來的值，概念是bit位移，所以還原rgb的時候要個別位移，還原後的各值會是0~255之間

	int r = (color >> 16) & 0xff;
	int g = (color >> 8) & 0xff;
	int b = color & 0xff;
				
灰階公式

	int gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);
	
還原回color，這個公式在code裡有，概念是把rgb位移之後加總
	
	int newPixel = colorToRGB(255, gray, gray, gray);
	
把剛剛宣告用來存新的灰階影像的BufferedImage的第(i,j)個位置寫入由公式得到的新pixel值

	grayImage.setRGB(i, j, newPixel);
	
最後就是寫成檔案
	
	File newFile = new File(newFileName);
	ImageIO.write(grayImage, "jpg", newFile);




test picture source: http://catzgallery.blogspot.tw/2011/11/omg-wtf-catz-collection.html
