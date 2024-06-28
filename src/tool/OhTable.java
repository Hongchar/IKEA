package tool;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class OhTable extends JPanel {
	static final int WIDTH = 377;
	static final int HEIGHT = 600;
	static final String[] COLUMNS = { "거래처ID", "납품업체명", "담당자명", "전화번호", "등록일시" };
	static DefaultTableModel model;
	static JTable jt;
	private MouseAdapter modifyMouseListener;

	public OhTable() {
		super();
		// 표 크기 설정
		setBounds(6, 201, WIDTH, HEIGHT);

		// 셀 수정 비활성화
		model = new DefaultTableModel(COLUMNS, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		jt = new JTable(model);

		// 테이블 외형 설정
		setTableAppearance();

		// 스크롤 바 생성 및 설정
		JScrollPane scp = new JScrollPane(jt);
		scp.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // JScrollPane의 크기 설정
		scp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS); // 가로 스크롤바 항상 표시
		// JPanel에 JScrollPane 추가
		setLayout(new BorderLayout());
		add(scp, BorderLayout.CENTER);
	}

	public void modifyMod(JButton modify, JFrame f) {
		// 이미 리스너가 있을 시 지우기
		removeModifyListener();
		// 수정 클릭시 마우스 손바닥 모양 변경
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		modifyMouseListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int row = jt.rowAtPoint(evt.getPoint());
				int col = jt.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					String columnName = jt.getColumnName(col);
					if (columnName.equals("거래처ID")) {
						DefaultFrameUtils.makeNotice("거래처ID는 수정불가합니다");
						return;
					}

					Object cellValue = jt.getValueAt(row, col);
					String cellName = String.valueOf(cellValue);

					ModifyDialog dialog = new ModifyDialog(f, columnName, cellName);
				}

				// 수정 완료 후 초기화
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

	// 테이블 외형 설정
	private void setTableAppearance() {
		// 컬럼 행 배경색, 글씨색 설정
		jt.getTableHeader().setBackground(Color.decode("#106EBE"));
		jt.getTableHeader().setForeground(Color.decode("#FFFFFF"));

		// 컬럼 행 높이 설정
		int headerHeight = 30;
		jt.getTableHeader().setPreferredSize(new Dimension(jt.getTableHeader().getPreferredSize().width, headerHeight));

		// 셀 높이 설정
		int defaultHeight = 40;
		jt.setRowHeight(defaultHeight);

		// 셀 너비 설정
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

	public void insertColumns() {
		for (String column : COLUMNS) {
			model.addColumn(column);
		}
	}

	public void loadTableData(String where, String input) {
		model.setRowCount(0);

		String sql = "SELECT * FROM CLIENTS" + where;
		try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			// 인젝션 공격 방지용 조건문
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

		setTableAppearance();
	}

	static class ModifyDialog extends JDialog {
		public ModifyDialog(Frame parent, String colunmName, String sellName) {
			super(parent, "수정 & 삭제");

			String[] eng_col = { "CLIENT_NAME", "MANAGER_NAME", "MANAGER_PHONE", "CLIENT_DATE" };
			String[] kor_col = { "납품업체명", "담당자명", "전화번호", "등록일시" };

			setLayout(new BorderLayout());
			setSize(300, 150);

			// 메세지 라벨
			String massage = "[" + colunmName + "]에 [" + sellName + "] 수정";

			JLabel messageLabel = new JLabel(massage, JLabel.CENTER);
			add(messageLabel, BorderLayout.CENTER);

			for (int i = 0; i < kor_col.length; i++) {
				if (colunmName.equals(kor_col[i])) {
					colunmName = eng_col[i];
					break;
				}
			}

			String col = colunmName;

			// 버튼 패널
			JPanel buttonPanel = new JPanel();
			JButton modifyButton = new JButton("수정");
			modifyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});

			JButton deleteButton = new JButton("행 삭제");
			deleteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int option = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", // message: 표시할 메시지
							"삭제 확인", // title: 다이얼로그 제목
							JOptionPane.YES_NO_OPTION, // optionType: 버튼 종류 (YES_NO_OPTION은 OK 및 No 버튼)
							JOptionPane.QUESTION_MESSAGE // messageType: 메시지 유형 (질문 메시지 아이콘)
					);

					// 사용자의 선택에 따른 동작 처리
					if (option == JOptionPane.YES_OPTION) {
						Update.delete(col, sellName);
						System.out.println(col);
						System.out.println(sellName);
						DefaultFrameUtils.makeNotice("삭제 완료");
					} else {
						DefaultFrameUtils.makeNotice("취소");
					}
					dispose();
				}
			});

			buttonPanel.add(modifyButton);
			buttonPanel.add(deleteButton);
			add(buttonPanel, BorderLayout.SOUTH);

			setLocationRelativeTo(parent);

			setVisible(true);
		}

	}

	static class Update {

		static void modify() {
			try (Connection conn = DBConnector.getConnection();) {

				String sql2 = "UPDATE fruits SET qty = qty + 1 WHERE fname = '사과'";

				try (PreparedStatement pstmt = conn.prepareStatement(sql2);) {
					int row = pstmt.executeUpdate();
					System.out.printf("%d행이 수정되었습니다.\n", row);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		static void delete(String colunmName, String sellName) {
		    try (Connection conn = DBConnector.getConnection();) {
		        String sql = "DELETE FROM CLIENTS WHERE " + colunmName + " = ?";
		        try (PreparedStatement pstmt = conn.prepareStatement(sql);) {
		            pstmt.setString(1, sellName);
		            int rowsAffected = pstmt.executeUpdate();
		            if (rowsAffected > 0) {
		                DefaultFrameUtils.makeNotice("삭제 완료");
		            } else {
		                DefaultFrameUtils.makeNotice("해당하는 데이터가 없습니다.");
		            }
		        } catch (SQLException e) {
		            if (e.getSQLState().equals("23000")) {
		                DefaultFrameUtils.makeNotice("납품하고 있는 물건이 있어 삭제 불가능합니다");
		            } else {
		                e.printStackTrace(); // 다른 SQLException 처리
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace(); // Connection 가져오는 과정에서 예외 발생 시 콘솔에 출력
		    }
		}
	}
}