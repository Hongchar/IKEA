import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import jframe.JFrames;
import tool.AddTable;
import tool.AddTable.TableComponents;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DBConnector;
import tool.DBConnector2;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.InfoLabel;

public class Manager_B3 extends JFrame {
	CreateTextField text = new CreateTextField();
	String[] columnNames = {"No.", "아이디", "접속일"};
	
	static boolean isAccessLog = false;
	static boolean isResult = false;
	static boolean isVaildDate = false;
	
	private JTextField startDateInput, endDateInput, accountInput;
	
	private AddTable.TableComponents tableComp;
	private static DefaultTableModel model;
	
	public Manager_B3() {
		DefaultFrameUtils.setDefaultSize(this);
		JButton home = (JButton) this.add(new BackButton());
		JButton back = (JButton) this.add(new HomeButton());
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "접근기록조회");
		DefaultFrameUtils.makeTopPanel(this);
		this.add(new InfoLabel("SEARCH CONDITIONS", 20, 58));
		startDateInput = CreateTextField.iconHalfTextField(12, 90, "날짜");		
		endDateInput = CreateTextField.iconHalfTextField(207, 90, "날짜");		
		accountInput = CreateTextField.textField(12, 153, "계정ID");
		
		JButton searchBtn = (JButton) this.add(new BlueLongButton("검색", 12, 220));
		this.add(new InfoLabel("SEARCH DATA", 6, 296));
		
		add(startDateInput);
		add(endDateInput);
		add(accountInput);
		
		tableComp = AddTable.getTable(columnNames, "WM_ACCOUNT_ACCESS");
		add(tableComp.scrollPane);
		model = (DefaultTableModel) tableComp.table.getModel();
		
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
		
		searchBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String startDate = startDateInput.getText();
				String endDate = endDateInput.getText();
				String account = accountInput.getText();
				
				inputDate(startDate, "", "");
				inputDate(startDate, "", account);
				inputDate(startDate, endDate, "");
				inputDate("", "", account);
				
			}
		});
		
		
		startDateInput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String startDate = startDateInput.getText();
				
				inputDate(startDate, "", "");
				
			    if (startDate.isEmpty()) {
			    	DefaultFrameUtils.makeNotice("조회할 날짜를 입력하세요");
			    }
			    
			    if (!isVaildDate(startDate)) {
			    	DefaultFrameUtils.makeNotice("시작날짜형식이 올바르지 않습니다 (\"yyyy-mm-dd\")");
			    	return;
			    }
			}
		});
		
		endDateInput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String startDate = startDateInput.getText();
				String endDate = endDateInput.getText();
				
				inputDate(startDate, endDate, "");
				
				
			    if (startDate.isEmpty()) {
			    	DefaultFrameUtils.makeNotice("조회할 날짜를 입력하세요");
			    }
			    
			    if (!isVaildDate(startDate)) {
			    	DefaultFrameUtils.makeNotice("시작날짜형식이 올바르지 않습니다 (\"yyyy-mm-dd\")");
			    	return;
			    }
				
				if (!isVaildDate(endDate)) {
			    	DefaultFrameUtils.makeNotice("종료날짜형식이 올바르지 않습니다 (\"yyyy-mm-dd\")");
			    	return;
			    }
				
			}
		});
		
		accountInput.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String startDate = startDateInput.getText();
				String endDate = endDateInput.getText();
				String account = accountInput.getText();
				
				System.out.println("Action performed. startDate: " + startDate + ", endDate: " + endDate + ", account: " + account);
				
				inputDate(startDate, null, account);
			}
		});
		
		this.setVisible(true);
	}
	
	private static void inputDate(String startDate, String endDate, String account) {
		DBConnector connector = new DBConnector();
		
		System.out.println("쿼리불러오기");
	    String sql;
	    
        SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
	    
	    boolean hasEndDate = endDate != null && !endDate.trim().isEmpty();
	    boolean hasAccount = account != null && !account.trim().isEmpty();

	    if (hasEndDate && hasAccount) {
	        sql = "SELECT * FROM WM_ACCOUNT_ACCESS "
	            + "WHERE access_date >= ? "
	            + "AND access_date <= ? "
	            + "AND lower(account_name) LIKE ? ORDER BY access_date";
	    } else if (!hasEndDate && hasAccount) {
	        sql = "SELECT * FROM WM_ACCOUNT_ACCESS "
	            + "WHERE access_date >= ? "
	            + "AND lower(account_name) LIKE ? ORDER BY access_date";
	    } else if (hasEndDate && !hasAccount) {
	        sql = "SELECT * FROM WM_ACCOUNT_ACCESS "
		            + "WHERE access_date >= ? "
		            + "AND access_date <= ? "
		            + "ORDER BY access_date";
	    } else if (hasAccount) {
	        sql = "SELECT * FROM WM_ACCOUNT_ACCESS "
	        		+ "WHERE lower(account_name) LIKE ? ORDER BY access_date";
	    } else {
	        sql = "SELECT * FROM WM_ACCOUNT_ACCESS "
	            + "WHERE access_date >= ? ORDER BY access_date";
	    }
	    
	    
	    model.setRowCount(0);
	    
	    System.out.println("DB불러오는 중");
	    try (
	        Connection conn = DBConnector.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	    ) {
	    	System.out.println("DB불러오기성공");
	        int parameterIndex = 1;
	        pstmt.setString(parameterIndex++, startDate);
	        
	        if (hasEndDate) {
	            pstmt.setString(parameterIndex++, endDate);
	        }
	        
	        if (hasAccount) {
	            pstmt.setString(parameterIndex, "%" + account + "%");
	        }

	        System.out.println("쿼리 가져와라~~" + pstmt.toString());
	        try (ResultSet rs = pstmt.executeQuery()) {
	        	System.out.println("쿼리 가져왔딴");
	            int columnCount = rs.getMetaData().getColumnCount();
	            int rowNum = 1;

	            while (rs.next()) {
	                String[] row = new String[columnCount + 1];
	                row[0] = String.valueOf(rowNum++); // 행번호 추가
	                
	                for (int i = 1; i <= columnCount; i++) {
	                    if (rs.getMetaData().getColumnName(i).equalsIgnoreCase("access_date")) {
	                        java.sql.Date accDate = rs.getDate("access_date");
	                        if (accDate != null) {
	                            row[i] = dateForm.format(accDate);
	                        } else {
	                            row[i] = "";
	                        }
	                    } else {
	                        row[i] = rs.getString(i);
	                    }
	                }
	                model.addRow(row);
	            }
	            isAccessLog = true;
	        }
	    } catch (SQLException e) {
	    	System.out.println("DB불러오기 실패" + e.getMessage());
	        e.printStackTrace();
	        isAccessLog = false;
	    }
	}
	
	private static boolean isVaildDate(String date) {
		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			dateForm.parse(date);
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
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
