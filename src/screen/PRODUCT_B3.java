package screen;

import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import jframe.JFrames;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.HomeButton;

public class PRODUCT_B3 extends JFrame {

	private static final long serialVersionUID = 1L;

	JButton home = new HomeButton();
	JButton back = new BackButton();

	JTextField productName = CreateTextField.textField(new Point(12, 81), "상품명");
	JTextField cost = CreateTextField.halfTextField(new Point(12, 140), "원가");
	JTextField price = CreateTextField.halfTextField(new Point(207, 140), "판매가");
	JTextField orderQty = CreateTextField.halfTextField(new Point(12, 199), "발주수량");
	JTextField clientCord = CreateTextField.halfTextField(new Point(207, 199), "업체코드");
	JTextField sectorCord = CreateTextField.halfTextField(new Point(12, 258), "구역코드");
	JTextField productQty = CreateTextField.halfTextField(new Point(207, 258), "입고수량");
	
	JLabel grayText = DefaultFrameUtils.makeGrayLabel("PROGRESS INFORMATION", 20, 59);
	
	JButton save = new BlueLongButton("저장", 12, 317);

	String nameText; // 상품명 값
	int costText; // 원가 값
	int priceText; // 판매가 값
	int orderText; // 발주수량 값
	int clientText; // 업체코드 값
	int sectorText; // 구역코드 값
	int qtyText; // 입고수량 값

	public PRODUCT_B3() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "검수/입고");

		home.addActionListener(e -> {
			JFrames.getJFrame("MAIN_A2").setVisible(true);
			JFrames.getJFrame("PRODUCT_B3").setVisible(false);
		});

		back.addActionListener(e -> {
			JFrames.getJFrame("PRODUCT_B2").setVisible(true);
			JFrames.getJFrame("PRODUCT_B3").setVisible(false);
		});
		
		save.addActionListener(e -> {
			nameText = productName.getText();
			costText = Integer.parseInt(cost.getText());
			priceText = Integer.parseInt(price.getText());
			orderText = Integer.parseInt(orderQty.getText());
			clientText = Integer.parseInt(clientCord.getText());
			sectorText = Integer.parseInt(sectorCord.getText());
			qtyText = Integer.parseInt(productQty.getText());
			String sql1 = "SELECT * FROM orders WHERE order_name = ? AND order_qty = ?";
			try (
				Connection conn = DBConnector.getConnection();
				PreparedStatement pstmt1 = conn.prepareStatement(sql1);
			){
				pstmt1.setString(1, nameText);
				pstmt1.setInt(2, orderText);
				try (
					ResultSet rs = pstmt1.executeQuery();
				){
					if(rs.next()) {
						String sql2 = "INSERT INTO product(product_seq, product_name, "
								+ "product_qty, product_cost, product_price, client_id, sector_seq) "
								+ "VALUES (product_seq.nextval, ?, ?, ?, ?, ?, ?)";
						try (
							PreparedStatement pstmt2 = conn.prepareStatement(sql2);
						){
							pstmt2.setString(1, nameText);
							pstmt2.setInt(2, qtyText);
							pstmt2.setInt(3, costText);
							pstmt2.setInt(4, priceText);
							pstmt2.setInt(5, clientText);
							pstmt2.setInt(6, sectorText);
							
							int row = pstmt2.executeUpdate();
							DefaultFrameUtils.makeNotice(String.format("%s %d 입고완료.[%d]", nameText, qtyText, row));
							String sql3 = "UPDATE orders SET wh_yn = 'Y' WHERE order_name = ? AND order_qty = ?";
							try (
								PreparedStatement pstmt3 = conn.prepareStatement(sql3);
							){
								pstmt3.setString(1, nameText);
								pstmt3.setInt(2, orderText);
								pstmt3.executeUpdate();
							}
						} catch (SQLException e1) {
							DefaultFrameUtils.makeNotice("값을 모두 입력해주세요.");
						}
					} else {
						DefaultFrameUtils.makeNotice("발주내역이 존재하지 않습니다.");
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		this.add(home);
		this.add(back);
		this.add(productName);
		this.add(cost);
		this.add(price);
		this.add(orderQty);
		this.add(clientCord);
		this.add(sectorCord);
		this.add(productQty);
		this.add(save);
		this.dispose();
		
		DefaultFrameUtils.makeTopPanel(this);
	}
}
