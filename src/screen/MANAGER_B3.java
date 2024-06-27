package screen;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
import tool.InfoLabel;

public class MANAGER_B3 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	CreateTextField text = new CreateTextField();
	String[] columnNames = {"No.", "아이디", "접속일"};
	
	static boolean isAccessLog = false;
	static boolean isResult = false;
	static boolean isVaildDate = false;
	
	HomeButton home = new HomeButton();
	BackButton back = new BackButton();
	BlueLongButton searchBtn = new BlueLongButton("검색", 12, 220);
	InfoLabel greyLabel1 = new InfoLabel("SEARCH CONDITIONS", 20, 58);
	InfoLabel greyLabel2 = new InfoLabel("SEARCH DATA", 6, 296);
	
	private JTextField startDateInput, endDateInput, accountInput;
	
	private AddTable.TableComponents tableComp;
	private static DefaultTableModel model;
	
	public MANAGER_B3() {
		DefaultFrameUtils.setDefaultSize(this);
		add(home);
		add(back);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "접근기록조회");
		DefaultFrameUtils.makeTopPanel(this);
		add(greyLabel1);
		startDateInput = CreateTextField.halfTextField(new Point(12, 90), "시작날짜");		
		endDateInput = CreateTextField.halfTextField(new Point(207, 90), "종료날짜");		
		accountInput = CreateTextField.textField(new Point(12, 153), "계정ID");
		
		add(searchBtn);
		add(greyLabel2);
		
		add(startDateInput);
		add(endDateInput);
		add(accountInput);
				
		tableComp = AddTable.getTable(columnNames);
		add(tableComp.scrollPane);
		model = (DefaultTableModel) tableComp.table.getModel();
		
		// 테이블 수정 방지
		tableComp.table.setEnabled(false);
		
		// 홈 버튼 클릭 이벤트
//		home.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFrames.getJFrame("MAIN_A2").setVisible(true);
//				JFrames.getJFrame("MANAGER_B3").setVisible(false);				
//			}
//		});
//
//		// 뒤로가기 버튼 클릭 이벤트
//		back.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				JFrames.getJFrame("MANAGER_B2").setVisible(true);
//				JFrames.getJFrame("MANAGER_B3").setVisible(false);				
//
//			}
//		});
		
        startDateInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        
        endDateInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        
        accountInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
		
		
		this.setVisible(true);
	}
	
	private void performSearch() {
		String startDate = startDateInput.getText();
		String endDate = endDateInput.getText();
		String account = accountInput.getText();
		
		inputDate(startDate, endDate, account);
	}
	
	private void inputDate(String startDate, String endDate, String account) {
	    SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");	    
	    model.setRowCount(0);

	    StringBuilder sb = new StringBuilder();
	    sb.append("SELECT * FROM WM_ACCOUNT_ACCESS WHERE 1=1");

//	    List<String> conditions = new ArrayList<>();
	    List<Object> params = new ArrayList<>();
	    
		boolean firstCondition = true;

		if (!startDate.isEmpty()) {
			if (firstCondition) {
				sb.append(" AND access_date >= ?");
				firstCondition = false;
			} else {
				sb.append(" AND access_date >= ?");
			}
			params.add(startDate);
		}

		if (!endDate.isEmpty()) {
			if (firstCondition) {
				sb.append(" AND access_date <= ?");
				firstCondition = false;
			} else {
				sb.append(" AND access_date <= ?");
			}
			params.add(endDate);
		}

		if (!account.isEmpty()) {
			if (firstCondition) {
				sb.append(" AND lower(account_name) LIKE ?");
				firstCondition = false;
			} else {
				sb.append(" AND lower(account_name) LIKE ?");
			}
			params.add("%" + account.toLowerCase() + "%");
		}

	    sb.append(" ORDER BY access_date");

	    String sql = sb.toString();

	    System.out.println("Executing SQL: " + sql);
	    try (
	        Connection conn = DBConnector.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	    ) {
	        for (int i = 0; i < params.size(); i++) {
	            pstmt.setObject(i + 1, params.get(i));
	        }

	        try (ResultSet rs = pstmt.executeQuery()) {
	            int colCnt = rs.getMetaData().getColumnCount();
	            int rowNum = 1;

	            while(rs.next()) {
	                String[] row = new String[colCnt + 1];
	                row[0] = String.valueOf(rowNum++);

	                for (int i = 1; i <= colCnt; i++) {
	                    if (rs.getMetaData().getColumnName(i).equalsIgnoreCase("access_date")) {
	                        java.sql.Date accDate = rs.getDate("access_date");
	                        row[i] = (accDate != null) ? dateForm.format(accDate) : "";
	                    } else {
	                        row[i] = rs.getString(i);
	                    }
	                }
	                model.addRow(row);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
		
//	private void inputDate(String startDate, String endDate, String account) {
//	    SimpleDateFormat dateForm = new SimpleDateFormat("yyyyMMdd");
//
//	    model.setRowCount(0);
//
//	    StringBuilder sb = new StringBuilder();
//	    sb.append("SELECT * FROM WM_ACCOUNT_ACCESS");
//
//	    List<String> conditions = new ArrayList<>();
//	    List<String> params = new ArrayList<>();
//	    
//
//	    sb.append(" ORDER BY access_date");
//
//	    String sql = sb.toString();
//
//	    System.out.println("Executing SQL: " + sql);
//	    try (
//	        Connection conn = DBConnector.getConnection();
//	        PreparedStatement pstmt = conn.prepareStatement(sql);
//	    ) {
//	        for (int i = 0; i < params.size(); i++) {
//	            pstmt.setString(i + 1, params.get(i));
//	        }
//
//            System.out.println("결과불러오기");
//	        try (ResultSet rs = pstmt.executeQuery()) {
//	            int colCnt = rs.getMetaData().getColumnCount();
//	            int rowNum = 1;
//
//	            while(rs.next()) {
//	                String[] row = new String[colCnt + 1];
//	                row[0] = String.valueOf(rowNum++);	
//
//	                for (int i = 1; i <= colCnt; i++) {
//	                    if (rs.getMetaData().getColumnName(i).equalsIgnoreCase("access_date")) {
//	                        java.sql.Date accDate = rs.getDate("access_date");
//	                        row[i] = (accDate != null) ? dateForm.format(accDate) : "";
//	                    } else {
//	                        row[i] = rs.getString(i);
//	                    }
//	                }
//	                model.addRow(row);
//	            }
//	            isAccessLog = true;
//	            System.out.println("결과불러왔다");
//	        }
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	        isAccessLog = false;
//	    }
//	}

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        		new MANAGER_B3();
            }
        });
	}

}
