package screen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import tool.BackButton;
import tool.BlueLongButton;
import tool.BottomImage;
import tool.DBConnector;
import tool.DataValidator;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.IkeaTextField;

public class ORDER_B1 extends JFrame {
	private JTextField tf1, tf2, tf3, tf4, tf5;
	
	public ORDER_B1() {
		
		add(new BackButton());
		add(new HomeButton());
		
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "발주신청");
		DefaultFrameUtils.makeTopPanel(this);
		
		tf1 = IkeaTextField.iconTextField(10, 80, "신청일자");
		tf2 = IkeaTextField.textField(10, 140, "상품ID");
		tf3 = IkeaTextField.textField(10, 200, "상품명");
		tf4 = IkeaTextField.halfTextField(10, 260, "발주수량");
		tf5 = IkeaTextField.halfTextField(200, 260, "업체ID");
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
		List<Object> params = new ArrayList<>();
		
		String sql = "INSERT INTO orders (order_date, order_seq, order_name, order_qty, client_id, wh_yn)"
				+ " VALUES (?, ?, ?, ?, ?,'N')";

		if (!date.isEmpty() && !DataValidator.validateDate(date)) {
			JOptionPane.showMessageDialog(null, "날짜의 형식이 올바르지 않습니다. 8자리 숫자로 입력해 주세요.");
			return ;
		}
		
//		if (!pId.isEmpty() && !DataValidator.validateId(pId)) {
//			JOptionPane.showMessageDialog(null, "상품 ID의 형식이 올바르지 않습니다. 숫자로 입력해 주세요.");
//			return ;
//		}
		
		pName = DataValidator.validateProductName(pName);
		
		if (qty.startsWith("-")) {
			JOptionPane.showMessageDialog(null, "0 이상의 숫자를 입력해 주세요.");
			return ;
		}
		
		if (!qty.isEmpty() && !DataValidator.validateQuantity(qty)) {
			JOptionPane.showMessageDialog(null, "수량은 숫자만 입력할 수 있습니다.");
			return ;
		}
		
		if (date.isEmpty() || pId.isEmpty() ||
				pName.isEmpty() || qty.isEmpty() || 
				cId.isEmpty()) {
			
			JOptionPane.showMessageDialog(this,
					"모든 필드를 입력해주세요.",
					"입력 오류",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		params.add(date);
		params.add(pId);
		params.add(pName);
		params.add(qty);
		params.add(cId);
		
		try (
			Connection conn = DBConnector.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			) {
			
			for (int i = 0; i < params.size(); ++i) {
				System.out.println("Setting parameter " + (i + 1) + ":" + params.get(i));
				pstmt.setObject(i + 1, params.get(i));
			}
			
			int row = pstmt.executeUpdate();
			System.out.println(row + "행 추가되었습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new ORDER_B1());
	
	}
}
