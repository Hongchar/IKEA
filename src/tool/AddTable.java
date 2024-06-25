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

	// DB에서 테이블의 COUNT 값을 가져오는 기능
	private static class RowCount {
		// 테스트용 DB연결기 & 계정임 
		private static final DBConnector2 connector = new DBConnector2("HR", "1234");
		
		// 실제로 사용할 DB 연결 기능 (위의 테스트용 기능 지우고 이거 사용하면 됨)
//		private static final DBConnector connector = new DBConnector();

		// 테이블의 행 수를 반환하는 메서드
		public static int getRowCount() {
			String sql = "SELECT COUNT(*) FROM test_table"; 
										  // 테스트용 이름임
			int result = 0;
			try (
					Connection conn = connector.getConnection();
					PreparedStatement pstmt = conn.prepareStatement(sql);
					ResultSet rs = pstmt.executeQuery()
			) {
				if (rs.next()) {
					result = rs.getInt(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			// 해당 테이블의 행 개수
			return result;
		}
	}

	// 테이블의 위치, 사이즈, 열 이름 (맨 윗줄 행) 값
	public static JScrollPane getTable(
			int locationX, int locationY, 
			int width, int height, String[] columnNames) {
		
		// 받아온 열 이름으로 한 행을 가진 모델을 생성 후 테이블에 적용
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		JTable table = new JTable(model);
		
		// 테이블의 행 개수만큼 자동으로 행 추가
		int rowCount = RowCount.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			model.addRow(new Object[] { "" });
		}

		// 스크롤 패널 생성
		JScrollPane scp = new JScrollPane(table);

		// 각종 설정
		scp.setBounds(locationX, locationY, width, height);
		scp.setFont(new Font("나눔글꼴", Font.PLAIN, 14));

		// columnNames 행 글씨 가운데 정렬
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);

		// columnNames 행 배경색, 글씨색 설정
		table.getTableHeader().setBackground(Color.decode("#106EBE"));
		table.getTableHeader().setForeground(Color.decode("#FFFFFF"));
		
		// 셀 높이 설정
		int defaultHeight = 40;
		table.setRowHeight(defaultHeight); 
		
		//셀 너비 설정
		// 너비 자동 크기 조정 비활성화
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int bestSize = 121; // 가장 보기 좋은 사이즈
		for (int i = 0; i < table.getColumnCount(); i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(bestSize);
		}

		// 가로 스크롤바 추가
		scp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		return scp;
	}
}