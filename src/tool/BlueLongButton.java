package tool;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;


public class BlueLongButton extends JButton {

	// 파란색 긴 버튼에 노란색 라벨
	public BlueLongButton(String label, int x, int y) {
		super(label, null);
		
		setFont(new Font("나눔글꼴", Font.PLAIN, 14));
		setForeground(Color.decode("#FFDB00"));
		
		setBackground(Color.decode("#1076C8"));
		setBounds(x, y, 370, 40);
		
	}
}
