package tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JButton;

public class SmallCheckButton extends JButton{
	public SmallCheckButton(String name, int x, int y) {
		super(name);
		
		setForeground(new Color(255, 219, 0));
		setBackground(new Color(16, 118, 200));
		setSize(80, 40);
		setLocation(x, y);
		setFont(new Font("넥슨Lv1고딕 굵게", Font.BOLD, 20));
		
	}
}
