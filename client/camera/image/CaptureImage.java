package client.camera.image;

import java.awt.Color;
import java.awt.image.BufferedImage;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class CaptureImage {

	private VideoCapture camera;
	private Mat photo;
	
	private int width;
	private int height;
	
	public CaptureImage(VideoCapture camera, int width, int height) {
		this.camera = camera;
		this.width = width;
		this.height = height;
		
		photo = new Mat();
	}
	
	public Mat takePhoto() {
		camera.read(photo);
		return photo;
	}
	
	public BufferedImage mat2BufferedImage(Mat mat) {
		BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
		 
        byte[] data = new byte[width * height * (int) mat.elemSize()];

        mat.get(0, 0, data);

        bImage.getRaster().setDataElements(0, 0, width, height, data);
        
        return bImage;
	}
	
	public BufferedImage bgr2Rgb(BufferedImage bgrImg) {
		BufferedImage rgbImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
        int[] dataRgb = new int[width * height];
        
        bgrImg.getRGB(0, 0, width, height, dataRgb, 0, width);
        
        for (int i = 0; i < dataRgb.length; i++) {
        	Color c = new Color(dataRgb[i]);
        	dataRgb[i] = (c.getAlpha() << 24) | (c.getBlue() << 16) | (c.getGreen() << 8) | c.getRed();
        			
        }
        rgbImg.getRaster().setDataElements(0, 0, width, height, dataRgb);
        
        return rgbImg; 
	}
	
}
