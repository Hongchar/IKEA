package tool;

import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class AddTable {

	
	public static JScrollPane getTable() {

		// 열 이름 설정
		String[] columnNames = { "ID", "재고명", "수량", "원가", "판매가", "무게", "납품업체" };

		// 위에 값만큼 컬럼 네임 행 만들고 열의 개수 0부터 시작하는 모델 생성
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		// ddd
		// 테이블 생성
		JTable table = new JTable(model);

		// 열 추가
		// 테스트용 3줄
		model.addRow(new Object[] { "1", "상품1", "10", "10000", "15000", "1.2", "업체A" });
        model.addRow(new Object[] { "2", "상품2", "20", "8000", "12000", "0.8", "업체B" });
        model.addRow(new Object[] { "3", "상품3", "15", "12000", "18000", "1.5", "업체C" });
		
        // 공백 열 13줄 추가
		int rowCount = 13;
		for (int i = 0; i < rowCount; i++) {
			model.addRow(new Object[] { 
					"", "", "", "", "", "", "", "", "", "", "", "", "" });
		}

		// 스크롤 패널 생성 
		JScrollPane scp = new JScrollPane(table);
		
		// 각종 설정
		// 크기, 글꼴 설정
		scp.setBounds(10, 250, 365, 543);
		scp.setFont(new Font("나눔글꼴", Font.PLAIN, 14));
		
		// 컬럼 네임 행 글씨 가운데 정렬
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer(); 
	    dtcr.setHorizontalAlignment(SwingConstants.CENTER); 
	    
	    // 컬럼 네임 행 배경색, 글씨색 설정
	    table.getTableHeader().setBackground(Color.decode("#106EBE"));
	    table.getTableHeader().setForeground(Color.decode("#FFFFFF"));
		
		// 셀 높이, 너비 조절
        table.setRowHeight(40);
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);  // 자동 크기 조정 비활성화
        TableColumn column;
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(80);  // 각 열의 너비 설정
        }
	    
        // 가로 스크롤바 추가
        scp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
		return scp;
	}
}