package tool;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class MenuBar extends JButton {

	/**
	 	1. 버튼명
	 	2. x = x값
	 	3. y = y값
	 	4. gap 글자, 아이콘 간격 알아서...
	 	버튼 내 아이콘은 필요한 이미지로 수정해야 합니다.
	*/
	public MenuBar(String title, String url, int x, int y, int gap) {

		ImageIcon manager_icon = new ImageIcon(url);


		// 위치값은 변수로 설정했습니다
		this.setBounds(x, y, 375, 50);
		this.setBackground(new Color(255, 255, 255));

		this.setFont(new Font("넥슨Lv1고딕 굵게", Font.BOLD, 24));
		LineBorder border = new LineBorder(new Color(203, 203, 203), 1, true);


		this.setIcon(manager_icon);
		this.setText(title);
		
		// 텍스트, 아이콘 간격 설정
		this.setIconTextGap(gap);
		// 텍스트&아이콘 가운데 정렬
		this.setHorizontalAlignment(SwingConstants.CENTER);
		// 텍스트를 왼쪽으로 정렬
		this.setHorizontalTextPosition(SwingConstants.LEFT);

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
