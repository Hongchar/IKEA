package tool;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;


public class BlueLongButton extends JButton {

	// 파란색 긴 버튼에 노란색 라벨
	public BlueLongButton(String label, int x, int y) {
		super(label, null);
		
		setFont(new Font("넥슨Lv1고딕", Font.PLAIN, 14));
		setForeground(new Color(255, 219, 0));
		
		setBackground(new Color(16, 118, 200));
		setBounds(x, y, 370, 40);
		
	}
}
