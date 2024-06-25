package pjc;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import tool.BackButton;
import tool.BlueLongButton;
import tool.BottomImage;
import tool.CreateTextField;
import tool.DefaultFrameUtils;
import tool.HomeButton;

public class Order_B1 extends JFrame {
	
	public Order_B1() {
		
		add(new BackButton());
		add(new HomeButton());
		
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "발주신청");
		DefaultFrameUtils.makeTopPanel(this);
		
		JTextField tf1 = CreateTextField.textField(10, 80, "신청일자");
		JTextField tf2 = CreateTextField.textField(10, 140, "상품ID");
		JTextField tf3 = CreateTextField.textField(10, 200, "상품명");
		JTextField tf4 = CreateTextField.halfTextField(10, 260, "발주수량");
		JTextField tf5 = CreateTextField.halfTextField(200, 260, "업체ID");
		BlueLongButton b1 = new BlueLongButton("신청", 10, 320);
		
		add(new BottomImage());
		add(new BackButton());
		
		add(tf1);
		add(tf2);
		add(tf3);
		add(tf4);
		add(tf5);
		add(b1);
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Order_B1());
	}
	
}
