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
		
		setBorder(new Border() {
			private int radius = 10;

			@Override
			public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
				g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
			}

			@Override
			public Insets getBorderInsets(Component c) {
				return new Insets(radius + 1, radius + 1, radius + 2, radius);
			}

			@Override
			public boolean isBorderOpaque() {
				return true;
			}
		});
	}
}
