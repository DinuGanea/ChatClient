package client.camera;

import java.awt.Dimension;

import org.opencv.core.Core;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

public class Camera {
	
	private VideoCapture camera;
	private int rWidth;
	private int rHeight;

	public Camera(int rWidth, int rHeight, int cameraNr) {
		
		this.rWidth = rWidth;
		this.rHeight = rHeight;
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		camera = new VideoCapture(cameraNr);
		
    	    	
		setDefaultCameraResolution();
	}
	
	public VideoCapture getCamera() {
		return camera;
	}
	
	
	public void setCameraResolution(int rWidth, int rHeight) {
		camera.set(Highgui.CV_CAP_PROP_FRAME_WIDTH, rWidth);
	    camera.set(Highgui.CV_CAP_PROP_FRAME_HEIGHT, rHeight);	
	}
	
	
	public void setDefaultCameraResolution() {
		setCameraResolution(rWidth, rHeight);
	}
		
	public int getCameraWidth() {
		return (int) camera.get(Highgui.CV_CAP_PROP_FRAME_WIDTH);
	}
	
	public int getCameraHeight() {
		return (int) camera.get(Highgui.CV_CAP_PROP_FRAME_HEIGHT);
	}
	
	public Dimension getCameraResolution() {
		return new Dimension(getCameraWidth(), getCameraHeight());
	}
	
	public boolean isOpened() {
		return camera.isOpened();
	}
	
	public void stopCamera() {
		camera.release();
	}

}
