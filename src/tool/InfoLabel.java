package tool;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class InfoLabel extends JLabel {
	
	/**
	 * 회색 글씨 라벨
	 * 1. 제목
	 * 2. x, y 위치값
	 * */
	public InfoLabel(String title, int x, int y) {
		this.setText(title);
		this.setBounds(x, y, 359, 19);
		this.setForeground(new Color(144, 144, 144));
		this.setFont(new Font("넥슨Lv1고딕 굵게", Font.BOLD, 18));
	}

}
