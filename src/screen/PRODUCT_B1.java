package screen;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import tool.AddTable;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.InfoLabel;
import jframe.JFrames;
public class PRODUCT_B1 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String[] columnNames = {"ID", "재고명", "수량", "원가", "판매가", "무게", "납품업체"};
	
	private JTextField inputName;
	private AddTable.TableComponents tableComp;
	private static DefaultTableModel model;
	
	HomeButton home = new HomeButton();
	BackButton back = new BackButton();
	InfoLabel greyLabel1 = new InfoLabel("SEARCH CONDINTS", 20, 59);
	InfoLabel greyLabel2 = new InfoLabel("SEARCH DATE", 6, 296);
	BlueLongButton searchBtn = new BlueLongButton("검색", 14, 159);
	
	static boolean isLoad = false;
	
	
	public PRODUCT_B1() {
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.setDefaultSize(this);
		add(home);
		add(back);
		DefaultFrameUtils.makeTopLabel(this, "상품목록조회");
		DefaultFrameUtils.makeTopPanel(this);
		add(greyLabel1);
		inputName = CreateTextField.textField(new Point(12, 81), "상품명");
		add(inputName);
		
		add(searchBtn);
		add(greyLabel2);
		
		tableComp = AddTable.getTable(columnNames);
		add(tableComp.scrollPane);
		model = (DefaultTableModel) tableComp.table.getModel();
		
		// 홈 버튼 클릭 이벤트
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("MAIN_A2").setVisible(true);
				JFrames.getJFrame("PRODUCT_B1").setVisible(false);
			}
		});
		
		// 뒤로가기 버튼 클릭 이벤트
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("PRODUCT_A1").setVisible(true);
				JFrames.getJFrame("PRODUCT_B1").setVisible(false);
			}
		});
		
		inputName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = inputName.getText();
				
				getProductName(name);
				
			}
		});
		
		// 검색 버튼 클릭시
		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = inputName.getText();
				
				getProductName(name);
				
				if (name.isBlank()) {
					getProductName("");					
				}				
			}
		});
		
	}
	
	
	public static void getProductName(String name) {
		String sql = "SELECT product_seq, "
				+ "product_name, "
				+ "product_qty, "
				+ "product_cost, "	
				+ "product_price, "
				+ "product_weight, "
				+ "client_id "
				+ "FROM product"
				+ (name.isBlank() ? "" : " WHERE product_name LIKE ?");
		
		model.setRowCount(0);
			
		System.out.println(sql);
		try (
			Connection conn = DBConnector.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		) {
			if (!name.isEmpty()) {
				pstmt.setString(1, "%" + name + "%");				
			}
			
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
					isLoad = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isLoad = false;
		}
	}

}
