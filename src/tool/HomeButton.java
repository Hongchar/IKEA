package tool;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class HomeButton extends JButton {
	// 홈버튼
	public HomeButton() {
		super(new ImageIcon("res/home.png"));
		
		setBounds(340, 7, 36, 36);
		setBackground(new Color(16, 118, 200));
		setOpaque(false);
		setBorder(null);
	}
	
}
