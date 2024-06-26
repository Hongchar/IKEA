import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import jframe.JFrames;
import tool.AddTable;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.InfoLabel;

public class Product_B1 extends JFrame {
	
	String[] columnNames = {"ID", "재고명", "수량", "원가", "판매가", "무게", "납품업체"};
	
	private JTextField inputName;
	private AddTable.TableComponents tableComp;
	private static DefaultTableModel model;
	
	public Product_B1() {
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.setDefaultSize(this);
		JButton home = (JButton) this.add(new HomeButton());
		JButton back = (JButton) this.add(new BackButton());
		DefaultFrameUtils.makeTopLabel(this, "상품목록조회");
		DefaultFrameUtils.makeTopPanel(this);
		this.add(new InfoLabel("SEARCH CONDINTS", 20, 59));
		inputName = CreateTextField.textField(12, 81, "상품명");
		add(inputName);
		
		JButton search = (JButton) this.add(new BlueLongButton("검색", 14, 159));
		this.add(new InfoLabel("SEARCH DATE", 6, 296));
		
		tableComp = AddTable.getTable(columnNames, "product");
		add(tableComp.scrollPane);
		model = (DefaultTableModel) tableComp.table.getModel();
		
//		// 홈 버튼 클릭 이벤트
//		home.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFrames.getJFrame("MAIN_A2").setVisible(true);
//				JFrames.getJFrame("PRODUCT_B1").setVisible(false);
//			}
//		});
//		
////		// 뒤로가기 버튼 클릭 이벤트
//		back.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFrames.getJFrame("PRODUCT_A1").setVisible(true);
//				JFrames.getJFrame("PRODUCT_B1").setVisible(false);
//			}
//		});
		
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = inputName.getText();
				
				getProductName(name);
				
			}
		});
		
		this.setVisible(true);
	}
	
	public static void getProductName(String name) {
		String sql = "SELECT * FROM product WHERE product_name LIKE ?";
		
		model.setRowCount(0);
		
		try (
			Connection conn = DBConnector.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		) {
			pstmt.setString(1, "%" + name + "%");
			
			try (
				ResultSet rs = pstmt.executeQuery();					
			) {
				int columnCount = rs.getMetaData().getColumnCount();
				while(rs.next()) {
					String[] row = new String[columnCount];
					for (int i = 1; i < columnCount; i++) {
						row[i - 1] = rs.getString(i);
					}
					model.addRow(row);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        		new Product_B1();
            }
        });
	}

}
