package client.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.Client;

public class Window extends JFrame implements ActionListener, WindowListener {

	private static final long serialVersionUID = 1L;
	
	final static Logger log = LoggerFactory.getLogger(Window.class);
	
	private Client client;
	
	private String username;
	
	private JTextPane history;
	private JTextField message;
<<<<<<< HEAD
	private JButton btnSend;
	private JButton btnVideo;
=======
>>>>>>> bb535b0f1df9429bca47590197ae25954e6e67f6
	
	private HTMLEditorKit html;
	private HTMLDocument doc;
	
	
	public Window(String username, String ipAddress, int port) {
		this.username = username;
		
		createWindow();
		client = new Client(this, username);

	}
	
	private void createWindow() {
		setTitle("Chat");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(750, 400);
		
		setMinimumSize(new Dimension(400, 225));
		
		setLocationRelativeTo(null);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.rowWeights = new double[]{1.0, 0.0};
<<<<<<< HEAD
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0};
=======
		gridBagLayout.columnWeights = new double[]{1.0, 0.0};
>>>>>>> bb535b0f1df9429bca47590197ae25954e6e67f6
		gridBagLayout.columnWidths = new int[]{675, 75};
		gridBagLayout.rowHeights = new int[]{370, 30};
		
		
		getContentPane().setLayout(gridBagLayout);
		
		history = new JTextPane();
		history.setEditable(false);
		history.setContentType("text/html");
		html = new HTMLEditorKit();
		doc = new HTMLDocument();
		history.setEditorKit(html);
		history.setDocument(doc);
		
		GridBagConstraints gbcHistory = new GridBagConstraints();
<<<<<<< HEAD
		gbcHistory.gridwidth = 3;
=======
		gbcHistory.gridwidth = 2;
>>>>>>> bb535b0f1df9429bca47590197ae25954e6e67f6
		gbcHistory.insets = new Insets(5, 5, 10, 5);
		gbcHistory.fill = GridBagConstraints.BOTH;
		gbcHistory.gridx = 0;
		gbcHistory.gridy = 0;
		getContentPane().add(history, gbcHistory);
		history.setText("");
		
		message = new JTextField();
		GridBagConstraints gbcMessage = new GridBagConstraints();
		gbcMessage.insets = new Insets(0, 5, 0, 5);
		gbcMessage.fill = GridBagConstraints.BOTH;
		gbcMessage.gridx = 0;
		gbcMessage.gridy = 1;
		getContentPane().add(message, gbcMessage);
		
		
<<<<<<< HEAD
		btnSend = new JButton("Send");
=======
		JButton btnSend = new JButton("Send");
>>>>>>> bb535b0f1df9429bca47590197ae25954e6e67f6
		btnSend.addActionListener(this);
		GridBagConstraints gbcSend = new GridBagConstraints();
		gbcSend.insets = new Insets(0, 0, 0, 5);
		gbcSend.fill = GridBagConstraints.BOTH;
		gbcSend.gridx = 1;
		gbcSend.gridy = 1;
		getContentPane().add(btnSend, gbcSend);
		
<<<<<<< HEAD
		btnVideo = new JButton("Video");
		btnVideo.addActionListener(this);
		GridBagConstraints gbcVideo = new GridBagConstraints();
		gbcVideo.insets = new Insets(0, 0, 0, 5);
		gbcVideo.fill = GridBagConstraints.BOTH;
		gbcVideo.gridx = 2;
		gbcVideo.gridy = 1;
		getContentPane().add(btnVideo, gbcVideo);
		
=======
>>>>>>> bb535b0f1df9429bca47590197ae25954e6e67f6
		setVisible(true);
		requestFocus();
		message.requestFocusInWindow();
		
		addWindowListener(this);
	}

	public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
		if (e.getSource() == btnSend) {
			if (!getClientMessage().equals("")) {
				client.sendMessage(message.getText());
				clearMessageField();
				message.requestFocusInWindow();
			}
		}
		
		if (e.getSource() == btnVideo) {
			client.startVideoChat();
		}
		
=======
		if (!getClientMessage().equals("")) {
			client.sendMessage(message.getText());
			clearMessageField();
			message.requestFocusInWindow();
		}
>>>>>>> bb535b0f1df9429bca47590197ae25954e6e67f6
	}
	
	private String getClientMessage() {
		String message = this.message.getText().trim();
		return message;
	}
	
	private void clearMessageField() {
		message.setText("");
	}
	
	public void addToHistory(String message) {
		try {
			html.insertHTML(doc, doc.getLength(), message, 0, 0, null);
		} catch (BadLocationException | IOException e) {
			log.error("Could not write chat history document. {}", e.getMessage());
		}
		//history.setText(history.getText() + "\n" + message);
	}
	
	public void displayMessage(String message, String title) {
		JLabel messageLbl = new JLabel(message);
		JOptionPane.showMessageDialog(this, messageLbl, title, JOptionPane.ERROR_MESSAGE);
	}

	public void windowOpened(WindowEvent e) {	
	}
	public void windowClosing(WindowEvent e) {
		client.disconnect();
	}
	public void windowClosed(WindowEvent e) {
	}
	public void windowIconified(WindowEvent e) {	
	}
	public void windowDeiconified(WindowEvent e) {
	}
	public void windowActivated(WindowEvent e) {
	}
	public void windowDeactivated(WindowEvent e) {
	}

}
