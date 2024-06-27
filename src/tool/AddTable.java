package tool;

import java.awt.Color;
import java.awt.Font;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class AddTable {
	
//	private static class RowCount {
//		private static final DBConnector connector = new DBConnector();
//		
//		public static int getRowCount(String tableName) {
//			String sql = "SELECT COUNT(*) FROM " + tableName;
//			int result = 0;
//			
//			try (
//				Connection conn = connector.getConnection();
//				PreparedStatement pstmt = conn.prepareStatement(sql);
//				ResultSet rs = pstmt.executeQuery()
//			) {
//				if (rs.next()) {
//					result = rs.getInt(1);
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			return result;
//		}
//	}
	
	public static class TableComponents {
		public JScrollPane scrollPane;
		public JTable table;
		public DefaultTableModel model;
		
		public TableComponents(JScrollPane scrollPane, JTable table, DefaultTableModel model) {
			this.scrollPane = scrollPane;
			this.table = table;
			this.model = model;
		}
	}
	
	public static TableComponents getTable(String[] columnNames) {
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		JTable table = new JTable(model);
		
//		int rowCount = RowCount.getRowCount(tableName);
		int rowCount = 12;
		for (int i = 0; i < rowCount; ++i) {
//			model.addRow(new Object[] { "" });
			model.addRow(new Object[columnNames.length]);
		}
		
		JScrollPane scp = new JScrollPane(table);
		scp.setBounds(6, 315, 377, 500);
		scp.setFont(new Font("넥슨Lv1고딕", Font.PLAIN, 14));
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		
		table.getTableHeader().setBackground(Color.decode("#106EBE"));
		table.getTableHeader().setForeground(Color.decode("#FFFFFF"));
		
		int defaultHeight = 40;
		table.setRowHeight(defaultHeight);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		int bestSize = 121;
		for (int i = 0; i < table.getColumnCount(); ++i) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(bestSize);
		}
		
		scp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		return new TableComponents(scp, table, model);	
	}
}