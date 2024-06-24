package tool;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BackButton extends JButton {
	
	private static final long serialVersionUID = 1L;

	// 뒤로가기 버튼
	public BackButton() {
		super(new ImageIcon("C:\\aiautomation_kdm\\repositories\\ikea\\res\\back.png"));
		
		setBackground(new Color(16, 118, 200));
		setBounds(295, 13, 26, 26);	
		setOpaque(false);
		setBorder(null);
	}
}
