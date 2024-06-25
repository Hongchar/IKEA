import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import tool.AddTable;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DBConnector2;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.InfoLabel;

public class Manager_B3 extends JFrame {
	static DBConnector2 connector = new DBConnector2("HR", "1234");

	CreateTextField text = new CreateTextField();
	String[] columnNames = {"계정ID", "아이디", "접속일"};
	
	static boolean isAccessLog = false;
	
	static JScrollPane sp;
	static DefaultTableModel model;
	static JTable table;
	
	public Manager_B3() {
		DefaultFrameUtils.setDefaultSize(this);
		this.add(new BackButton());
		this.add(new HomeButton());
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "접근기록조회");
		DefaultFrameUtils.makeTopPanel(this);
		this.add(new InfoLabel("SEARCH CONDITIONS", 20, 58));
		JTextField startDateInput = (JTextField) add(text.halfTextField(new Point(12, 90), "날짜"));
		JTextField endDateInput = (JTextField) add(text.halfTextField(new Point(207, 90), "날짜"));
		JTextField accountInput = (JTextField) add(text.textField(new Point(12, 153), "계정ID"));
		JButton searchBtn = (JButton) this.add(new BlueLongButton("검색", 12, 220));
		this.add(new InfoLabel("SEARCH DATA", 12, 259));
		
		// 검색
		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String startDate = startDateInput.getText();
				String endDate = endDateInput.getText();
				String account = accountInput.getText();
				
				inputDate(startDate, endDate, account);
				
				if(!isAccessLog) {
					DefaultFrameUtils.makeNotice("조회할 데이터가 없습니다");
				}
				
			}
		});
		
		
		Object[] tableComponents = 
				AddTable.getTableComponents(12, 278, 370, 540, columnNames, "accessRecord");
		sp = (JScrollPane) tableComponents[0];
		model = (DefaultTableModel) tableComponents[1];
		table = (JTable) tableComponents[2];
		this.add(sp);
		
		this.setVisible(true);
	}
	
	private static void inputDate(String startDate, String endDate, String account) {
		
		String sql = "SELECT * FROM accessRecord "
				+ "WHERE acc_date >= ? AND acc_date <= ? "
				+ "AND account_id LIKE ? ORDER BY acc_date";
		
		model.setRowCount(0);
		
		try (
			Connection conn = connector.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		) {
			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);
			pstmt.setString(3, "%" + account + "%");
			
			try (
				ResultSet rs = pstmt.executeQuery();
			) {
    			int columnCount = rs.getMetaData().getColumnCount();
    			
    			while (rs.next()) {
    				String[] row = new String[columnCount];
    				for (int i = 1; i <= columnCount; i++) {
    					row[i - 1] = rs.getString(i);
    				}
    				model.addRow(row);
    				isAccessLog = true;
    			}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			isAccessLog = false;
		}
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        		new Manager_B3();
            }
        });
	}

}
