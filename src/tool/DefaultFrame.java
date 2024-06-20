package tool;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class DefaultFrame {
	// 기본 화면
	public static JFrame getDefaultFrame() {
		JFrame window = new JFrame();
		JLabel imgLabel = new JLabel();
		
		ImageIcon icon = new ImageIcon("imgs/ikea.png");
		imgLabel.setBounds(0, 6, 100, 38);
		imgLabel.setIcon(icon);
		
		
		JPanel jpanel = new JPanel();
		
		jpanel.setBounds(0, 0, 400, 50);
		jpanel.setBackground(new Color(0, 88, 163));
		
		window.add(imgLabel);
		
		window.setSize(410, 850);
		window.setLayout(null);

		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		return window;
	}
}

