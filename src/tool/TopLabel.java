package tool;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class TopLabel extends JLabel {
	// 상단 라벨 (화이트 버전)
	public TopLabel(String str) {
		setText(str);
		this.setBounds(119, 13, 166, 28);
		this.setBackground(new Color(0, 88, 163));
		this.setForeground(Color.WHITE);
		this.setHorizontalTextPosition(CENTER);
		this.setFont(new Font("Sofia Sans Regular", Font.BOLD, 24));
	}
}
