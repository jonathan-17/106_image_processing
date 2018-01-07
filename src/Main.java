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
		
		
		imageHelper.gray(fileName); //�Ƕ�
		
		imageHelper.neg(gray_base); //�t��
		
		imageHelper.setGamma(gray_base, small_gamma); //setGamma
		
		imageHelper.setGamma(gray_base, big_gamma);//setGamma
		
		imageHelper.setBinary(big_gamma_base); //�G�Ȥ�
		
		imageHelper.setSaltAndPepper(small_gamma_base, rate);  //�J���Q���T
		
		imageHelper.filter("median", "SaltAndPepper.jpg", filter_size); //�����o�i��
		
		imageHelper.setFlatter(gray_base); // ���
		
		imageHelper.laplacian(contrase_base);// laplacian
		
		imageHelper.filter("max",laplacian_base,filter_size);// �̤j���o�i��
	}

}
