package tool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class AccountTablePanel extends JPanel {
	private DefaultTableModel tableModel;
	private JTable table;
	private JScrollPane scp;
	private String tableName;
	private String columnIdName;

	// 인트값 (행 크기 관련)

	public AccountTablePanel(String[] columnNames, String tableName, String columnIdName, int locationX, int locationY,
			int width, int height) {

		this.tableName = tableName;
		this.columnIdName = columnIdName;

		tableModel = new DefaultTableModel(columnNames, 0);
		table = new JTable(tableModel);
		scp = new JScrollPane(table);

		setLayout(null);

		// JScrollPane 설정
		scp.setBounds(locationX, locationY, width, height);
		scp.setFont(new Font("넥슨Lv1고딕", Font.PLAIN, 14));

		table.setOpaque(false); // 테이블의 행의 배경을 투명하게 설정

		// 테이블 배경색 설정
		table.setBackground(Color.WHITE);
		// JScrollPane 배경색 설정
		scp.setBackground(Color.WHITE);
		// JPanel(현재 클래스) 배경색 설정
		setBackground(Color.WHITE);

		// 테이블 헤더 스타일 설정
		table.getTableHeader().setBackground(Color.decode("#106EBE"));
		table.getTableHeader().setForeground(Color.decode("#FFFFFF"));
		table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getPreferredSize().width, 30)); // 헤더
																														// 설정
		table.getTableHeader().setFont(new Font("넥슨Lv1고딕", Font.PLAIN, 16));

		// 행 글꼴 설정
		table.setFont(new Font("넥슨Lv1고딕", Font.PLAIN, 14));

		// 셀 높이 설정
		table.setRowHeight(40);

		// 셀 너비 설정
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int columnWidth = 121;
		for (int i = 0; i < table.getColumnCount(); i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(columnWidth);
		}

		// 테이블 셀 배경색 및 폰트 설정
		DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
		cellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER); // 가운데 정렬
		cellRenderer.setBackground(Color.WHITE); // 셀 배경색 설정
		cellRenderer.setFont(new Font("넥슨Lv1고딕", Font.PLAIN, 14)); // 폰트 설정
		table.setDefaultRenderer(Object.class, cellRenderer);

		add(scp);
	}

	// 테이블 모델 반환
	public DefaultTableModel getTableModel() {
		return tableModel;
	}

	// 테이블 반환
	public JTable getTable() {
		return table;
	}

	// 계정 조회 기능
	public void searchAccount(String accountId) {
		String sql = "SELECT rownum, account_name, account_password FROM " 
				+ tableName + " WHERE " 
				+ columnIdName + " = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, accountId);

			try (ResultSet rs = pstmt.executeQuery()) {
				// 테이블 초기화
				tableModel.setRowCount(0);

				// 조회 결과 테이블에 추가
				while (rs.next()) {
					String[] rowData = { rs.getString(1), rs.getString(2), rs.getString(3) };
					tableModel.addRow(rowData);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 계정 삭제 기능
	public void deleteAccount(String accountId) {
		String sql = "DELETE FROM " + tableName + " WHERE " + columnIdName + " = ?";

		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, accountId);

			int rowsDeleted = pstmt.executeUpdate();
			if (rowsDeleted > 0) {
				JOptionPane.showMessageDialog(this, "계정이 삭제되었습니다.");
				// 삭제된 계정을 테이블에서도 제거
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					tableModel.removeRow(selectedRow);
				}
			} else {
				JOptionPane.showMessageDialog(this, "삭제할 계정을 찾을 수 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}