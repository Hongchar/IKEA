package screen;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class PRODUCT_B2 extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JButton home = new HomeButton();
	JButton back = new BackButton();
	
	JTextField productId = CreateTextField.textField(new Point(12, 82), "재고ID");
	JTextField sectorCode = CreateTextField.halfTextField(new Point(12, 141), "구역코드");
	JTextField productQty = CreateTextField.halfTextField(new Point(207, 141), "수량");
	JTextField moveSector = CreateTextField.textField(new Point(12,200), "이동할 구역코드");
	
	JLabel grayText = DefaultFrameUtils.makeGrayLabel("PROGRESS INFORMATION", 20, 60);
	JButton move = new BlueLongButton("이동", 12, 260);
	
	int idText; // 재고ID 값
	int sectorCodeText; // 구역코드 값
	int qtyText; // 수량 값
	int moveText; // 이동할 구역코드 값
	
	public PRODUCT_B2() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "상품이동");
		
		this.setVisible(true);
		
		// 홈 버튼 기능구현
		home.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				JFrames.getJFrame("MAIN_A2").setVisible(true);
				JFrames.getJFrame("PRODUCT_B2").setVisible(false);
			}
		});
		
		// 뒤로가기 버튼 기능구현
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				JFrames.getJFrame("PRODUCT_B1").setVisible(true);
				JFrames.getJFrame("PRODUCT_B2").setVisible(false);
			}
		});
		
		// 재고ID 텍스트 필드 기능구현
		productId.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String text = productId.getText();
					idText = Integer.parseInt(text);
				} catch (NumberFormatException e1) {
					DefaultFrameUtils.makeNotice("재고ID를 입력하세요.");
					
				}
			}
		});
		
		// 구역코드 텍스트 필드 기능구현
		sectorCode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String text = sectorCode.getText();
					sectorCodeText = Integer.parseInt(text);
				} catch (NumberFormatException exception) {
					DefaultFrameUtils.makeNotice("구역코드를 입력하세요.");
				}
			}
		});
		
		// 수량 텍스트 필드 기능구현
		productQty.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String text = productQty.getText();
					qtyText = Integer.parseInt(text);
				} catch (NumberFormatException exception) {
					DefaultFrameUtils.makeNotice("수량을 입력하세요.");
					
				}
			}
		});
		// 이동할 구역코드 텍스트 필드 기능구현
		moveSector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String text = moveSector.getText();
					moveText = Integer.parseInt(text);
				} catch (NumberFormatException exception) {
					DefaultFrameUtils.makeNotice("이동할 구역코드를 입력하세요.");
					
				}
			}
		});
		
		move.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String sql1 = "SELECT COUNT(*) FROM product";
				String sql2 = "UPDATE product SET sector_seq = ? "
						+ "WHERE sector_seq = ? AND product_seq = ? AND product_qty = ?";
				try (
					Connection conn = DBConnector.getConnection();
					PreparedStatement pstmt1 = conn.prepareStatement(sql1);
					ResultSet rs = pstmt1.executeQuery();
				){
					if(rs.next()) {
						int count = rs.getInt(1);
						if (count > 0) {
							try (
								PreparedStatement pstmt2 = conn.prepareStatement(sql2);
							){
								pstmt2.setInt(1, moveText);
								pstmt2.setInt(2, sectorCodeText);
								pstmt2.setInt(3, idText);
								pstmt2.setInt(4, qtyText);
								
								pstmt2.executeUpdate();
								DefaultFrameUtils.makeNotice(String.format("재고ID %d[%d개]가 %d 구역으로 이동하였습니다", 
										idText, qtyText,moveText));
							} catch (SQLException e1) {
								DefaultFrameUtils.makeNotice("값을 모두 입력해주세요.");
							}
						} else {
							DefaultFrameUtils.makeNotice("해당하는 값이 없습니다");
						}
					}
				} catch (SQLException e1) {
					DefaultFrameUtils.makeNotice("조회할 수 없습니다.");
				}

			}
		});

		this.add(home);
		this.add(back);
		this.add(productId);
		this.add(sectorCode);
		this.add(productQty);
		this.add(moveSector);
		this.add(move);
		this.add(grayText);
		
		DefaultFrameUtils.makeTopPanel(this);
	}
}
