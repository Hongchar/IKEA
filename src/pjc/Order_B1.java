package pjc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import tool.BackButton;
import tool.BlueLongButton;
import tool.BottomImage;
import tool.CreateTextField;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.HomeButton;

public class Order_B1 extends JFrame {
	private JTextField tf1, tf2, tf3, tf4, tf5;
	
	
	public Order_B1() {
		
		add(new BackButton());
		add(new HomeButton());
		
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "발주신청");
		DefaultFrameUtils.makeTopPanel(this);
		
		tf1 = CreateTextField.textField(10, 80, "신청일자");
		tf2 = CreateTextField.textField(10, 140, "상품ID");
		tf3 = CreateTextField.textField(10, 200, "상품명");
		tf4 = CreateTextField.halfTextField(10, 260, "발주수량");
		tf5 = CreateTextField.halfTextField(200, 260, "업체ID");
		BlueLongButton b1 = new BlueLongButton("신청", 10, 320);
		
		add(new BottomImage());
		
		add(tf1);
		add(tf2);
		add(tf3);
		add(tf4);
		add(tf5);
		add(b1);
		
		b1.addActionListener(e -> insertData());
		
	}
	
	private void insertData() {
		String date = tf1.getText().trim();
		String pId = tf2.getText().trim();
		String pName = tf3.getText().trim();
		String qty = tf4.getText().trim();
		String cId = tf5.getText().trim();
		
		String sql = "INSERT INTO orders VALUES (?, ?, ?, ?, ?)";
		
		
		if (date.isEmpty() || pId.isEmpty() ||
				pName.isEmpty() || qty.isEmpty() || 
				cId.isEmpty()) {
			
			JOptionPane.showMessageDialog(this,
					"모든 필드를 입력해주세요.",
					"입력 오류",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try (
			Connection conn = DBConnector.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
				) {
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Order_B1());
	
	}
}
