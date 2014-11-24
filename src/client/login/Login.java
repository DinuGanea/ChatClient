package client.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import client.gui.Window;

public class Login extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField username;
	private JButton btnLogin;
	
	final static Logger log = LoggerFactory.getLogger(Login.class);

	public Login() {
		setTitle("Login");
		
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException| IllegalAccessException | UnsupportedLookAndFeelException e) {
			log.error("Can't set look and feel UIManager.");
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setSize(232, 171);
		
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		username = new JTextField();
		username.setBounds(54, 53, 131, 19);
		contentPane.add(username);
		username.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(78, 33, 83, 15);
		contentPane.add(lblUsername);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(54, 84, 131, 25);
		btnLogin.addActionListener(this);
		contentPane.add(btnLogin);
		
		setResizable(false);
	}
	
	public String getLoginName() {
		String username;
		if (this.username.getText().trim().equals("")) {
			username = "User";
		} else {
			username = this.username.getText().trim();
		}
		return username;
	}
	
	public void actionPerformed(ActionEvent e) {
		log.info("\"" + getLoginName() + "\" is trying to connect!");
		
		this.dispose();
		
		new Window(getLoginName(), "localhost", 7000);
	}
	
	public static void main(String[] args) {
		Login frame = new Login();
		frame.setVisible(true);
	}


}
