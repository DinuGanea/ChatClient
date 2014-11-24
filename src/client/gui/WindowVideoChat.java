package client.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowVideoChat extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;

	private Display display;
	
	public WindowVideoChat(String name, int width, int height) {
		super(name);
		
		this.width = width;
		this.height = height;
		
		display = new Display(width, height);

		prepareGUI();
	}
	
	private void prepareGUI(){
				
		setSize(width, height);
		setLayout(new GridLayout(1, 1));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.add(display);
		
		setVisible(true);
   }
	
	public void updateDisplay(BufferedImage image) {
		display.updateImage(image);
		repaint();
	}
	

	class Display extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		private int width;
		private int height;
		
		private BufferedImage image;
		
		public Display(int width, int height) {
			this.width = width;
			this.height = height;
			setSize(width, height);
			setPreferredSize(new Dimension(width, height));
		}
		
		public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.setColor(Color.RED);
	        Graphics2D g2 = (Graphics2D) g;
	        if (image != null) {
	        	g2.drawImage(image, 0, 0, null);
	        }
		}
		
		public void updateImage(BufferedImage image) {
			this.image = image;
			repaint();
		}

	}	


	
}
