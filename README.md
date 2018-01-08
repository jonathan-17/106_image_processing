# 106_image_processing

106(2017) image processing, You should not use this code directly.
		  
![details](https://imgur.com/WPZWtvE.jpg)
the issue above is from 鍾承軒 in NCHU

# 灰階

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

完整程式碼: https://github.com/majaja98/106_image_processing/blob/master/src/ImageToGray.java

# 負片
255-減掉rgb各值即是負片效果，在這個作業因為是已經轉成灰階了，所以只要rgb其中一個值就可以做了

	int newPixel = colorToRGB(255, 255 - r,255 - g,255 - b);
	
# Gamma <1 、 =1 、 >1

	Gamma公式: ((p-min) / (max-min) )^gamma*255
先做一次讀值，找出最大最小值之後再對原圖做計算，次方可以使用java的函式:
	
	Math.pow(x,y) // x的y次方

# 胡椒鹽雜訊
用隨機亂數模擬灑胡椒鹽雜訊的過程

	Random rand = new Random();
	float random_num = rand.nextFloat(); // 介於0~1之間的浮點數
	if ( random_num > 0.95){ // 如果是完全隨機亂數的話，意義上就是整張圖只灑5%的胡椒鹽雜訊
		if (rand.nextFloat() > 0.5) { //黑白機率各半
			int newPixel = colorToRGB(255, 255,255,255); //灑白
		}else{
			int newPixel = colorToRGB(255, 0,0,0); //灑黑
		}
	}

# 3x3中值濾波器
假設你目前的pixel位置式(i,j)，附近的相對位置就會如下圖所示

![position](https://imgur.com/BrphFfI.png)

讀取各值之後做排序，把目前的pixel值取代成這排序後的中位數，可以有效去除胡椒鹽雜訊，但是還是會破壞掉圖片

# Laplacian邊緣偵測
推薦大家去看這個網站:https://goo.gl/WmWKC2
，概念講得很清楚；
要做的就是把目前(i,j)的值取代成與Laplacian邊緣偵測內積的結果

![position](https://imgur.com/BrphFfI.png) X ![Laplacian](https://imgur.com/IXHxwwZ.png)

	p_new = 4*p(i,j) - p(i-1,j)  - p(i,j-1)  - p(i,j+1)  - p(i+1,j) 

# 3x3最大值濾波器

與3x3中值濾波器相同，改為取最大值

# 二值化

題目要求以平均值當門檻，所以先讀完圖值後計算平均值，大於平均值的就設255，小於就設0



# 圖片來源
test picture source: http://catzgallery.blogspot.tw/2011/11/omg-wtf-catz-collection.html
