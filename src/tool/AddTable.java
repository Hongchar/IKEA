package tool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class AddTable {
	
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
		
		int rowCount = 12;
		for (int i = 0; i < rowCount; ++i) {
			model.addRow(new Object[columnNames.length]);
		}
		
		JScrollPane scp = new JScrollPane(table);
		scp.setBounds(6, 315, 377, 500);
		table.setFont(new Font("넥슨Lv1고딕", Font.PLAIN, 14));
		
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		
		// 테이블 헤더 글꼴, 색상 설정
		table.getTableHeader().setFont(new Font("넥슨Lv1고딕", Font.PLAIN, 16));
		table.getTableHeader().setBackground(Color.decode("#106EBE"));
		table.getTableHeader().setForeground(Color.decode("#FFFFFF"));
		
        // 컬럼 행 높이 설정
        int headerHeight = 30;
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width, headerHeight));
		
        // 셀 내용 가운데 정렬
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
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