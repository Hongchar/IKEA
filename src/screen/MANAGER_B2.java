package screen;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import jframe.JFrames;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DBConnector2;
import tool.DefaultFrameUtils;
import tool.HomeButton;

public class MANAGER_B2 extends JFrame {

	// 버튼, 패널, 아이콘 등 생성
	JButton home = new HomeButton();
	JButton back = new BackButton();
	JLabel grayText1 = DefaultFrameUtils.makeGrayLabel("PROGRESS INFORMATION", 20, 60);
	JLabel grayText2 = DefaultFrameUtils.makeGrayLabel("SEARCH DATA", 20, 260);
	JTextField idField = new CreateTextField().textField(new Point(12, 83), "계정ID");
	JButton inquiry = new BlueLongButton("조회", 307, 143);
	JButton delete = new BlueLongButton("삭제", 307, 229);

	// 테이블 추가
	String[] columnNames = { "계정ID", "아이디", "접속날짜" };
	DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
	JTable table = new JTable(tableModel);
	JScrollPane scp = new JScrollPane(table);
	
	public MANAGER_B2() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "계정 관리");

		this.setVisible(true);

		// 조회 버튼 클릭 클릭 이벤트
		inquiry.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String accountId = idField.getText().trim();
				if (accountId.isEmpty()) {
					JOptionPane.showMessageDialog(MANAGER_B2.this,
							"계정ID를 입력하세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// DB에서 데이터 조회 후 테이블에 추가
				searchAccount(accountId);
			}
		});

		// 삭제 버튼 클릭 이벤트
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(MANAGER_B2.this,
							"삭제할 행을 선택하세요.", "선택 오류",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// 선택된 행의 계정ID를 가져옴
				String accountId = table.getValueAt(selectedRow, 0).toString();

				// 테이블에서 해당 행 삭제
				tableModel.removeRow(selectedRow);

				// DB에서 데이터 삭제
				deleteAccount(accountId);
			}
		});

		// 홈 버튼 클릭 이벤트
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("MAIN_A2").setVisible(true);
				JFrames.getJFrame("MANAGER_B2").setVisible(false);
				
			}
		});

		// 뒤로가기 버튼 클릭 이벤트
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("MANAGER_A1").setVisible(true);
				JFrames.getJFrame("MANAGER_B2").setVisible(false);
			}
		});

		// 테이블 설정
		scp.setBounds(20, 280, 365, 530);
		

		// 버튼, 패널, 아이콘 등 추가
		this.add(home);
		this.add(back);
		this.add(grayText1);
		this.add(grayText2);
		this.add(idField);
		this.add(inquiry);
		this.add(delete);
		this.add(scp);
		
		DefaultFrameUtils.makeTopPanel(this);
	}

	// 계정 조회 기능
	private void searchAccount(String accountId) {
		DBConnector2 connector = new DBConnector2("HR", "1234");

		String sql = "SELECT * FROM test_table WHERE table_id = ?";
								  // 테이블 이름         // 컬럼명
		try (
				Connection conn = connector.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)
		) {
			pstmt.setString(1, accountId);

			try (
					ResultSet rs = pstmt.executeQuery()
			) {
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
	private void deleteAccount(String accountId) {
		DBConnector2 connector = new DBConnector2("HR", "1234");

		String sql = "DELETE FROM test_table WHERE table_seq = ?";
							
		try (
				Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)
		) {
			pstmt.setString(1, accountId);
			int affectedRows = pstmt.executeUpdate();

			if (affectedRows > 0) {
				JOptionPane.showMessageDialog(MANAGER_B2.this, 
						"계정이 삭제되었습니다.", "삭제 완료", JOptionPane.INFORMATION_MESSAGE);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}