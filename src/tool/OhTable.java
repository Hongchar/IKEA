package tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class OhTable extends JPanel {
	/*
	   표 추가 add(new Table(x 좌표값, y좌표값, 가로길이 width, 세로길이 height, 테이블명)
	   
	 */
	static final int WIDTH = 377;
	static final int HEIGHT = 600;
	static final String[] COLUMNS = { "거래처ID", "납품업체명", "담당자명", "전화번호", "등록일시" };
	static DefaultTableModel model = new DefaultTableModel();
	static JTable jt = new JTable();
	private MouseAdapter modifyMouseListener;
	 
	public OhTable() {
		super();
		// 표 크기 설정
		setBounds(6, 201, WIDTH, HEIGHT);

		insertColumns();
		jt.setModel(model);

		// 스크롤 바 생성 및 설정
		JScrollPane scp = new JScrollPane(jt);
		scp.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // JScrollPane의 크기 설정
		scp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // 가로 스크롤바 항상 표시
		// JPanel에 JScrollPane 추가
		add(scp, BorderLayout.CENTER);

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
                    if (columnName.equals("거래처ID")) {
                        JOptionPane.showMessageDialog(jt, "거래처ID는 수정할 수 없습니다.", "알림",
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
                modify.setText("수정");
                modify.setFont(new Font("넥슨고딕Lv1", Font.BOLD, 20));
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
	
	// 컬럼 채우기
	public void insertColumns() {
		for (String column : COLUMNS) {
			model.addColumn(column);
		}
		// 컬럼 행 배경색, 글씨색 설정
		jt.getTableHeader().setBackground(Color.decode("#106EBE"));
		jt.getTableHeader().setForeground(Color.decode("#FFFFFF"));
		// 컬럼 행 높이 설정
		int headerHeight = 30; // 원하는 높이 값 설정
		jt.getTableHeader().setPreferredSize(new Dimension(jt.getTableHeader().getPreferredSize().width, headerHeight));
	}
	
//	private void update
	
	// 셀 외형 세팅
	private void cellSetting() {
		// 셀 높이 설정
		int defaultHeight = 40;
		jt.setRowHeight(defaultHeight);
		// 셀 너비 설정
		// 너비 자동 크기 조정 비활성화
		jt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int bestSize = 100;
		for (int i = 0; i < jt.getColumnCount(); i++) {
			TableColumn column = jt.getColumnModel().getColumn(i);
			column.setPreferredWidth(bestSize);
		}

		// 셀 내용 가운데 정렬
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < jt.getColumnCount(); i++) {
			jt.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}
	}

	// Data 가져와서 테이블에 넣기
	public void loadTableData(String where, String input) {
		// 테이블 데이터 초기화
		model.setRowCount(0);

		// 데이터 가져오기
		String sql = "SELECT * FROM CLIENTS" + where;
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 인젝션 공격방지를 위한 조건문
			if (!where.isEmpty()) {
				pstmt.setString(1, "%" + input + "%");
			}
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Object[] row = new Object[COLUMNS.length];
				for (int i = 0; i < COLUMNS.length; i++) {
					row[i] = rs.getObject(i + 1);
				}
				model.addRow(row);
			}
			jt.setModel(model);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		cellSetting();
	}
}
