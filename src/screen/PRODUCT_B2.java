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
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

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
	
	int idText, sectorCodeText, qtyText, moveText;
	
	public PRODUCT_B2() {
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.setDefaultSize(this);
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
		
		  // 재고ID 텍스트 필드 커스텀 Document 설정
        productId.setDocument(new ProductIdDocument());
        
		
		move.addActionListener(e -> {
			try {
				String idGetText = productId.getText().split(" - ")[0]; // ID만 추출
				String secGetText = sectorCode.getText();
				String qtyGetText = productQty.getText();
				String moveGetText = moveSector.getText();
				
               			idText = Integer.parseInt(idGetText);
				sectorCodeText = Integer.parseInt(secGetText);
				qtyText = Integer.parseInt(qtyGetText);
				moveText = Integer.parseInt(moveGetText);
				
			} catch (NumberFormatException exception) {
				DefaultFrameUtils.makeNotice("입력한 값을 확인해주세요.");
			}
			String checkCapacitySql = "SELECT sector_seq, max_capacity FROM sector WHERE sector_seq = ?";
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
		            checkStmt.setInt(1, sectorCodeText);
		            try (ResultSet rs = checkStmt.executeQuery()) {
		                if (rs.next()) {
		                    int availableCapacity = rs.getInt("max_capacity");
		                    if (qtyText > availableCapacity) {
		        		        DefaultFrameUtils.makeNotice(String.format("[구역 %d]의 최대 적재량 %d을 초과합니다.", moveText, availableCapacity));
		        		        return;
		                    }
		                } 
		            } catch (SQLException ex) {
		            	 DefaultFrameUtils.makeNotice(String.format("[구역 %d]을 찾을 수 없습니다.", moveText));
		    		     return;
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
						DefaultFrameUtils.makeNotice("상품 업데이트에 실패했습니다. 해당하는 상품을 찾을 수 없습니다.");
						return;
					} else {
						try (
							PreparedStatement updateOldSectorStmt = conn.prepareStatement(updateSectorSql);
							PreparedStatement updateNewSectorStmt = conn.prepareStatement(updateSectorSql);
						) {
							// 이전 구역의 max_capacity 증가
							updateOldSectorStmt.setInt(1, -qtyText); // 증가시키므로 음수 사용
							updateOldSectorStmt.setInt(2, sectorCodeText);
							updateOldSectorStmt.executeUpdate();
							// 새 구역의 max_capacity 감소
							updateNewSectorStmt.setInt(1, qtyText);
							updateNewSectorStmt.setInt(2, moveText);
							updateNewSectorStmt.executeUpdate();
						} catch (SQLException ex) {
							conn.rollback();
							DefaultFrameUtils.makeNotice("상품 이동중 오류가 발생했습니다.");
							return;
						}
					}
				} catch (SQLException ex) {
					DefaultFrameUtils.makeNotice("상품 이동중 오류2: " + ex.getMessage());
				}
				conn.commit(); // 트랜잭션 커밋
				DefaultFrameUtils.makeNotice(
						String.format("%s (%d개)가 [%d 구역]으로 이동하였습니다", ProductIdDocument.getName(), qtyText, moveText));
			} catch (SQLException ex) {
				DefaultFrameUtils.makeNotice("데이터베이스 오류: " + ex.getMessage());
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
	// 커스텀 Document 클래스
    private class ProductIdDocument extends PlainDocument {
		private static final long serialVersionUID = 1520123699877680670L;
		private static String name;
		
		
		public static String getName() {
			return name;
		}

		@Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str == null) return;
            
            String currentText = getText(0, getLength());
            String[] parts = currentText.split(" - ");
            String idPart = parts[0];
            
            if (offs <= idPart.length()) {
                super.insertString(offs, str, a);
                updateProductName();
            }
        }
        
        @Override
        public void remove(int offs, int len) throws BadLocationException {
            super.remove(offs, len);
            updateProductName();
        }
        
        private void updateProductName() {
            SwingUtilities.invokeLater(() -> {
                try {
                    String fullText = getText(0, getLength());
                    String[] parts = fullText.split(" - ");
                    String idPart = parts[0].trim();
                    
                    if (!idPart.isEmpty()) {
                        int productId = Integer.parseInt(idPart);
                        name = getProductNameById(productId);
                        String newText = idPart + (name != null ? " - " + name : " - 해당 ID의 재고 없음");
                        
                        if (!fullText.equals(newText)) {
                            replace(0, getLength(), newText, null);
                        }
                    }
                } catch (NumberFormatException | BadLocationException e) {
                    // 숫자가 아닌 경우 또는 Document 조작 실패 시 무시
                }
            });
        }
    }
    
    private String getProductNameById(int productId) {
        String sql = "SELECT product_name FROM product WHERE product_seq = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("product_name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            DefaultFrameUtils.makeNotice("데이터베이스 오류: " + e.getMessage());
        }
        return null;
    }
}
