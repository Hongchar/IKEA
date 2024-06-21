package tool;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class MoveButton extends JButton {
	public MoveButton(String name, Point location) {
		super(name);
		
		setSize(365, 50);
		setLocation(location);
		setFont(new Font("Pretendard Variable Regular", Font.BOLD, 20));
		setHorizontalAlignment(SwingConstants.LEFT);
		setBackground(new Color(255, 255, 255));
		
	}
}
