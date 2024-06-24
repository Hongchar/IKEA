package pjc;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import tool.AddTable;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DefaultFrameUtils;
import tool.HomeButton;

public class Order_B2 extends JFrame {
	
	public Order_B2() {
		
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		add(new BackButton());
		add(new HomeButton());
		DefaultFrameUtils.makeTopLabel(this, "발주 현황 조회");
		DefaultFrameUtils.makeTopPanel(this);
		
		JTextField tf1 = CreateTextField.halfTextField(new Point(10, 80), "발주일자");
		JTextField tf2 = CreateTextField.halfTextField(new Point(205, 80), "");
		JTextField tf3 = CreateTextField.textField(new Point(10, 140), "상품ID");
		JTextField tf4 = CreateTextField.textField(new Point(10, 200), "거래처");
		
		add(new BlueLongButton("검색", 10, 260));
		
		add(tf1);
		add(tf2);
		add(tf3);
		add(tf4);
		JScrollPane table = AddTable.getTable();
		add(table);
		
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Order_B2());
	}
	
}
