package tool;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;


public class ButtonFrame extends JButton {

	public ButtonFrame(String label) {
		super(label, null);
		
		setFont(new Font("나눔글꼴", Font.PLAIN, 14));
		setForeground(Color.decode("#FFDB00"));
		
		setBackground(Color.decode("#1076C8"));
		setSize(370, 40);
		setBounds(6, 190, 370, 40);
		
	}
	
	public static void main(String[] args) {
		
		JFrame j = new DefaultFrame().getDefaultFrame();
		
		j.add(new ButtonFrame("검색"));
		j.setVisible(true);
		
	}
}
