package screen;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import tool.AddTable;
import tool.BackButton;
import tool.BlueLongButton;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.IkeaTextField;
import tool.InfoLabel;
import tool.SmallCheckButton;

public class PRODUCT_B1_1 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String[] columnNames = {"재고ID", "재고명", "수량", "원가", "판매가", "무게", "가로", "세로", "높이", "구역번호", "납품업체"};
	
	private static JTextField inputName;
	private static AddTable.TableComponents jtable;
	private static DefaultTableModel model;
	private static JTable jt;
	private static JScrollPane sp;
	
	private MouseAdapter modifyMouseListener;
	
	HomeButton home = new HomeButton();
	BackButton back = new BackButton();
	InfoLabel greyLabel1 = new InfoLabel("SEARCH CONDINTS", 20, 59);
	InfoLabel greyLabel2 = new InfoLabel("SEARCH DATE", 6, 296);
	BlueLongButton searchBtn = new BlueLongButton("검색", 14, 159);
	SmallCheckButton editBtn = new SmallCheckButton("수정", 210, 206);
	SmallCheckButton saveBtn = new SmallCheckButton("저장", 305, 206);
	
	static boolean isLoad = false;
	
	
	public PRODUCT_B1_1() {
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.setDefaultSize(this);
		add(home);
		add(back);
		DefaultFrameUtils.makeTopLabel(this, "상품목록조회");
		DefaultFrameUtils.makeTopPanel(this);
		add(greyLabel1);
		inputName = IkeaTextField.textField(12, 81, "상품명");
		add(inputName);
		
		add(searchBtn);
		add(greyLabel2);
		add(editBtn);
		add(saveBtn);
		
		jtable = AddTable.getTable(columnNames);
		jt = jtable.table;
		model = jtable.model;
		sp = jtable.scrollPane;

		add(sp);
		
		
//		// 홈 버튼 클릭 이벤트
//		home.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFrames.getJFrame("MAIN_A2").setVisible(true);
//				JFrames.getJFrame("PRODUCT_B1").setVisible(false);
//			}
//		});
//		
//		// 뒤로가기 버튼 클릭 이벤트
//		back.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFrames.getJFrame("PRODUCT_A1").setVisible(true);
//				JFrames.getJFrame("PRODUCT_B1").setVisible(false);
//			}
//		});
		
		// 검색어 엔터키
		inputName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getProductName();
				
				if (!isLoad) {
					DefaultFrameUtils.makeNotice("조회할 데이터가 없습니다");
				}
				
			}
		});
		
		// 검색 버튼 클릭시
		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				getProductName();
				
				if (!isLoad) {
					DefaultFrameUtils.makeNotice("조회할 데이터가 없습니다");
				}
			}
		});
		
		
		// 수정버튼
		editBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
                editBtn.setText("수정중");
                editBtn.setFont(new Font("넥슨고딕Lv1", Font.BOLD, 15));
				modifyMod(editBtn);
			}
		});
		
		
		
		this.setVisible(true);
	}
	
	public void modifyMod(JButton modify) {
        // 이전에 추가된 마우스 리스너가 있다면 제거
        removeModifyListener();
        // 커서 손모양으로 변경
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // 새로운 마우스 클릭 이벤트 리스너 추가
        modifyMouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = jt.rowAtPoint(evt.getPoint());
                int col = jt.columnAtPoint(evt.getPoint());
                
                if (row >= 0 && col >= 0) {
                    String columnName = jt.getColumnName(col);
                    if (columnName.equals("재고ID")) {
                        JOptionPane.showMessageDialog(jt, "재고ID는 수정할 수 없습니다.", "알림",
                                JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }

                    
                    String value = String.valueOf(jt.getValueAt(row, col));
                    String newValue = JOptionPane.showInputDialog(jt,
                            "[" + columnName + "] 값 변경 (" + value + " -> ):");
                    if (newValue != null && !newValue.isEmpty()) {
                        jt.setValueAt(newValue, row, col);
                        JOptionPane.showMessageDialog(jt, "[" + columnName + "] 값이 변경되었습니다.", "알림",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }

                
                
                // 수정이 완료되면 다시 리스너 제거 및 커서, 버튼 원상태
                removeModifyListener();
                setCursor(Cursor.getDefaultCursor());
                editBtn.setText("수정");
                editBtn.setFont(new Font("넥슨고딕Lv1", Font.BOLD, 20));
            }
        };

        jt.addMouseListener(modifyMouseListener);
    }

	public void removeModifyListener() {
        if (modifyMouseListener != null) {
            jt.removeMouseListener(modifyMouseListener);
            modifyMouseListener = null;
        }
    }
	
    static void modifyCell(String input, String colunmName, String sellName, int clientId) {
        try (Connection conn = DBConnector.getConnection();) {
            String sql = "UPDATE product SET " + colunmName + " = ? WHERE CLIENT_ID = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
                pstmt.setString(1, input);
                pstmt.setInt(2, clientId);
                pstmt.executeUpdate();
                DefaultFrameUtils.makeNotice("수정 완료");
            }
        } catch (SQLException e) {
        	DefaultFrameUtils.makeNotice("잘못된 입력값입니다");
        }
    }
	
	public static void getProductName() {
		String name = inputName.getText();
		
	    String sql = "SELECT product_seq, "
	            + "product_name, "
	            + "product_qty, "
	            + "product_cost, "
	            + "product_price, "
	            + "product_weight, "
	            + "product_x, "
	            + "product_y, "
	            + "product_height, "
	            + "sector_seq, "
	            + "client_id "
	            + "FROM product"
	            + (name.isBlank() ? "" : " WHERE product_name LIKE ?");

	    model.setRowCount(0);

	    System.out.println(sql);
	    
	    if (name.isEmpty()) {
	    	System.out.println("검색어: " + "검색어 없음");
	    } else {
	    	System.out.println("검색어: " + name);
	    }
	    
	    try (
	        Connection conn = DBConnector.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	    ) {
	        if (!name.isEmpty()) {
	            pstmt.setString(1, "%" + name + "%");
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            int columnCount = rs.getMetaData().getColumnCount();
	            while(rs.next()) {
	                String[] row = new String[columnCount];
	                for (int i = 1; i <= columnCount; i++) {  // <= 로 수정
	                    row[i - 1] = rs.getString(i);
	                }
	                model.addRow(row);
	                
	            }
	            isLoad = true;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        isLoad = false;
	    }
	    inputName.setText("");
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        		new PRODUCT_B1_1();
            }
        });
	}

}
