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
import tool.BottomImage;
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
		
		// 홈 버튼 기능구현
		home.addActionListener(e -> {
			JFrames.getJFrame("MAIN_A2").setVisible(true);
			this.setVisible(false);
		});
		
		// 뒤로가기 버튼 기능구현
		back.addActionListener(e -> {
			JFrames.getJFrame("PRODUCT_A1").setVisible(true);
			this.setVisible(false);
		});
		
		// 재고ID 텍스트 필드 기능구현
		productId.addActionListener(e -> {
			try {
				String text = productId.getText();
				idText = Integer.parseInt(text);
			} catch (NumberFormatException e1) {
				DefaultFrameUtils.makeNotice("재고ID를 입력하세요.");

			}
		});
		
		// 구역코드 텍스트 필드 기능구현
		sectorCode.addActionListener(e -> {
			try {
				String text = sectorCode.getText();
				sectorCodeText = Integer.parseInt(text);
			} catch (NumberFormatException exception) {
				DefaultFrameUtils.makeNotice("구역코드를 입력하세요.");
			}
		});
		
		// 수량 텍스트 필드 기능구현
		productQty.addActionListener(e -> {
			try {
				String text = productQty.getText();
				qtyText = Integer.parseInt(text);
			} catch (NumberFormatException exception) {
				DefaultFrameUtils.makeNotice("수량을 입력하세요.");

			}
		});
		// 이동할 구역코드 텍스트 필드 기능구현
		moveSector.addActionListener(e -> {
			try {
				String text = moveSector.getText();
				moveText = Integer.parseInt(text);
			} catch (NumberFormatException exception) {
				DefaultFrameUtils.makeNotice("이동할 구역코드를 입력하세요.");

			}
		});
		
		move.addActionListener(e -> {
		    String checkCapacitySql = "SELECT max_capacity FROM sector WHERE sector_seq = ?";
		    String updateProductSql = "UPDATE product SET sector_seq = ? WHERE sector_seq = ? AND product_seq = ? AND product_qty = ?";
		    String updateSectorSql = "UPDATE sector SET max_capacity = max_capacity - ? WHERE sector_seq = ?";

		    try (
		    	Connection conn = DBConnector.getConnection()
		    ) {
		        conn.setAutoCommit(false);  // 트랜잭션 시작

		        // 이동할 구역의 여유 공간 확인
		        try (
		        	PreparedStatement checkStmt = conn.prepareStatement(checkCapacitySql)
		        ) {
		            checkStmt.setInt(1, moveText);
		            try (ResultSet rs = checkStmt.executeQuery()) {
		                if (rs.next()) {
		                    int availableCapacity = rs.getInt("max_capacity");
		                    if (qtyText > availableCapacity) {
		                        DefaultFrameUtils.makeNotice(String.format("구역 %d의 최대 적재량 %d을(를) 초과합니다.", moveText, availableCapacity));
		                        return;
		                    }
		                } else {
		                    DefaultFrameUtils.makeNotice(String.format("구역 %d을(를) 찾을 수 없습니다.", moveText));
		                    return;
		                }
		            }
		        }

		        // 상품 이동
		        try (
		        	PreparedStatement updateProductStmt = conn.prepareStatement(updateProductSql)
		        ) {
		            updateProductStmt.setInt(1, moveText);
		            updateProductStmt.setInt(2, sectorCodeText);
		            updateProductStmt.setInt(3, idText);
		            updateProductStmt.setInt(4, qtyText);
		            
		            int affectedRows = updateProductStmt.executeUpdate();
		            
		            if (affectedRows == 0) {
		                conn.rollback();
		                DefaultFrameUtils.makeNotice("해당하는 상품을 찾을 수 없습니다.");
		                return;
		            }
		        }

		        // 이전 구역의 max_capacity 증가
		        try (
		        	PreparedStatement updateOldSectorStmt = conn.prepareStatement(updateSectorSql)
		        ) {
		            updateOldSectorStmt.setInt(1, -qtyText);  // 증가시키므로 음수 사용
		            updateOldSectorStmt.setInt(2, sectorCodeText);
		            updateOldSectorStmt.executeUpdate();
		        }

		        // 새 구역의 max_capacity 감소
		        try (
		        	PreparedStatement updateNewSectorStmt = conn.prepareStatement(updateSectorSql)
		        ) {
		            updateNewSectorStmt.setInt(1, qtyText);
		            updateNewSectorStmt.setInt(2, moveText);
		            updateNewSectorStmt.executeUpdate();
		        }

		        conn.commit();  // 트랜잭션 커밋
		        DefaultFrameUtils.makeNotice(String.format("재고ID %d[%d개]가 %d 구역으로 이동하였습니다", idText, qtyText, moveText));

		    } catch (SQLException ex) {
		        DefaultFrameUtils.makeNotice("데이터베이스 오류: " + ex.getMessage());
		    } catch (NumberFormatException ex) {
		        DefaultFrameUtils.makeNotice("올바른 숫자 형식을 입력해주세요.");
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
		this.add(new BottomImage());
		DefaultFrameUtils.makeTopPanel(this);
		this.setVisible(false);
	}
}
