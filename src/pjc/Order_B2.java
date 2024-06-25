package pjc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

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


public class Order_B2 extends JFrame {
	String[] tableLabel = {"idx", "상품명", "날짜", "수량", "입고여부", "납품업체"};
	private AddTable.TableComponents tableComponents;
	private JTextField tf1, tf2, tf3, tf4;
	
	public Order_B2() {
		
		
		add(new BackButton());
		add(new HomeButton());
		
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "발주 현황 조회");
		DefaultFrameUtils.makeTopPanel(this);
		
		tf1 = CreateTextField.halfTextField(10, 80, "발주일자");
		tf2 = CreateTextField.halfTextField(205, 80, "");
		tf3 = CreateTextField.textField(10, 140, "상품ID");
		tf4 = CreateTextField.textField(10, 200, "거래처");
		
		tableComponents = AddTable.getTable(10,  320,  370,  480, tableLabel);
		add(tableComponents.scrollPane);
		
		BlueLongButton b = new BlueLongButton("검색", 10, 260);
		add(b);
		
		b.addActionListener(e -> loadData());
		
		add(tf1);
		add(tf2);
		add(tf3);
		add(tf4);
		
	}
	
	private void loadData() {
		String dateFrom = tf1.getText().trim();
		String dateTo = tf2.getText().trim();
		String name = tf3.getText().trim();
		String clientId = tf4.getText().trim();
		StringBuilder query = new StringBuilder("SELECT * FROM orders");
		List<Object> params = new ArrayList<>();
		boolean condition = false;
		
		if (!dateFrom.isEmpty() || !dateTo.isEmpty()) {
			query.append(condition ? " AND" : " WHERE");
			
			if (!dateFrom.isEmpty() && !dateTo.isEmpty()) {
				query.append(" order_date BETWEEN TO_DATE(?, 'YYYYMMDD') AND TO_DATE(?, 'YYYYMMDD')");
				params.add(dateFrom);
				params.add(dateTo);
			} else if (!dateFrom.isEmpty()) {
				query.append(" order_date >= TO_DATE(?, 'YYYYMMDD')");
				params.add(dateFrom);
			} else if (!dateTo.isEmpty()) {
				query.append(" order_date <= TO_DATE(?, 'YYYYMMDD')");
				params.add(dateTo);
			}
			condition = true;
		}
		
		if (!name.isEmpty()) {
			query.append(condition ? " AND" : " WHERE").append(" order_name LIKE ?");
			params.add("%" + name + "%");
			condition = true;
		}
		
		if (!clientId.isEmpty()) {
			query.append(condition ? " AND" : " WHERE").append(" client_id = ?");
			params.add(clientId);
			condition = true;
		}
		
		String sql = query.toString();
		System.out.println(sql);
		System.out.println(params);
		
		try (
				Connection conn = DBConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)
				) {
			for (int i = 0; i < params.size(); ++i) {
				System.out.println("Setting parameter " + (i + 1) + ": " + params.get(i));
				pstmt.setObject(i + 1, params.get(i));
			}
			
			try (
					ResultSet rs = pstmt.executeQuery();
					) {
				
				DefaultTableModel model = (DefaultTableModel) tableComponents.table.getModel();
				model.setRowCount(0);
				
				while (rs.next()) {
					Vector<Object> row = new Vector<>();
					for (int i = 1; i <= model.getColumnCount(); ++i) {
						row.add(rs.getObject(i));
					}
					model.addRow(row);
				}
			}			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Order_B2());
	}
	
}
