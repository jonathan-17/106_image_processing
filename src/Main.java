public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImageHelper imageHelper = new ImageHelper();
		
		float small_gamma =0.9f, big_gamma = 2.0f; 
		float rate = 0.95f;
		int filter_size = 3;
		
		String fileName = "test.jpg";
		String gray_base = "gray.jpg";
		String small_gamma_base = "gamma" + small_gamma +".jpg";
		String big_gamma_base = "gamma" + big_gamma +".jpg";
		String contrase_base = "contrast.jpg";
		String laplacian_base = "laplacian.jpg";
		
		
		imageHelper.gray(fileName); //灰階
		
		imageHelper.neg(gray_base); //負片
		
		imageHelper.setGamma(gray_base, small_gamma); //setGamma
		
		imageHelper.setGamma(gray_base, big_gamma);//setGamma
		
		imageHelper.setBinary(big_gamma_base); //二值化
		
		imageHelper.setSaltAndPepper(small_gamma_base, rate);  //胡椒鹽雜訊
		
		imageHelper.filter("median", "SaltAndPepper.jpg", filter_size); //中值濾波器
		
		imageHelper.setFlatter(gray_base); // 對比
		
		imageHelper.laplacian(contrase_base);// laplacian
		
		imageHelper.filter("max",laplacian_base,filter_size);// 最大值濾波器
	}

}
