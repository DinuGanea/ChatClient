package client.camera.video;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class VideoCanvas {
	
	private VideoCapture camera;
	private Mat photo;
	
	private int width;
	private int height;

	public VideoCanvas(VideoCapture camera, int width, int height) {
		this.camera = camera;
		this.width = width;
		this.height = height;
	}
	

	
}
