package screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import jframe.JFrames;
import tool.AddTable;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.IkeaTextField;

public class MANAGER_B3 extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}(\\d{2}\\d{2}|-\\d{2}-\\d{2})$");
	
	CreateTextField text = new CreateTextField();
	String[] columnNames = {"No.", "아이디", "접속일"};
	
	static boolean isAccessLog = false;
	static boolean isResult = false;
	static boolean isVaildDate = false;
	
	HomeButton home = new HomeButton();
	BackButton back = new BackButton();
	BlueLongButton searchBtn = new BlueLongButton("검색", 12, 220);
	JLabel greyLabel1 = DefaultFrameUtils.makeGrayLabel("SEARCH CONDITIONS", 17, 69);
	JLabel greyLabel2 = DefaultFrameUtils.makeGrayLabel("SEARCH DATA", 6, 296);

	private JTextField startDateInput, endDateInput, accountInput;
	
	private AddTable.TableComponents tableComp;
	private static DefaultTableModel model;
	
	public MANAGER_B3() {
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeTopLabel(this, "접근기록조회");
		startDateInput = IkeaTextField.iconHalfTextField(12, 90, "시작날짜");				
		endDateInput = IkeaTextField.iconHalfTextField(207, 90, "종료날짜");
		accountInput = IkeaTextField.textField(12, 153, "계정ID");
		
		ImageIcon img = new ImageIcon("res/calendar_icon.png");
		JLabel jpg = new JLabel(img);
		jpg.setBounds(155, 98, 26, 26);
		
		ImageIcon img2 = new ImageIcon("res/calendar_icon.png");
		JLabel bg = new JLabel(img2);
		bg.setBounds(345, 98, 26, 26);
				
		tableComp = AddTable.getTable(columnNames);
		model = (DefaultTableModel) tableComp.table.getModel();
		
		tableComp.table.getColumn("No.").setPreferredWidth(50);
		tableComp.table.getColumn("접속일").setPreferredWidth(200);
		
		// 테이블 수정 방지
		tableComp.table.setEnabled(false);
		
		// 홈 버튼 클릭 이벤트
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("MAIN_A2").setVisible(true);
				JFrames.getJFrame("MANAGER_B3").setVisible(false);				
			}
		});

		// 뒤로가기 버튼 클릭 이벤트
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("MANAGER_A1").setVisible(true);
				JFrames.getJFrame("MANAGER_B3").setVisible(false);				

			}
		});
		

	    startDateInput.addActionListener(e -> inputDate());
	    endDateInput.addActionListener(e -> inputDate());
	    accountInput.addActionListener(e -> inputDate());
	    searchBtn.addActionListener(e -> inputDate());
        
	    add(jpg);
	    add(bg);
	    
	    add(home);
	    add(back);
	    add(greyLabel1);
	    add(searchBtn);
	    add(greyLabel2);
	    
	    add(startDateInput);
	    add(endDateInput);
	    add(accountInput);
	    add(tableComp.scrollPane);
		
	    DefaultFrameUtils.makeTopPanel(this);
		this.setVisible(false);
	}

	
	private void inputDate() {
//		접속일 시간 없애는 날짜포맷
//		SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd");
		
		String startDate = startDateInput.getText().trim();
		String endDate = endDateInput.getText().trim();
		String account = accountInput.getText().trim();
		
//        if (!isValidDateFormat(startDate) && !isValidDateFormat(endDate)) {
//            JOptionPane.showMessageDialog(this, "날짜는 yyyymmdd 또는 yyyy-mm-dd 형식으로 입력해주세요.", "입력 오류", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
		
		model.setRowCount(0);

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM WM_ACCOUNT_ACCESS");

		List<String> conditions = new ArrayList<>();
		List<Object> params = new ArrayList<>();

        if (!startDate.isEmpty()) {
            conditions.add("access_date >= TO_DATE(?, 'YYYY-MM-DD')");
            params.add(startDate);
        }
        if (!endDate.isEmpty()) {
            conditions.add("access_date <= (TO_DATE(?, 'YYYY-MM-DD') + 1)");
            params.add(endDate);
        }
	    if (isValidInput(account, "계정ID")) {
	        conditions.add("lower(account_name) LIKE ?");
	        params.add("%" + account.toLowerCase().trim() + "%");
	    }
	    
	    if (!conditions.isEmpty()) {
	        sb.append(" WHERE ").append(String.join(" AND ", conditions));
	    }
		
		sb.append(" ORDER BY access_date");
		String sql = sb.toString();

	    System.out.println("Executing SQL: " + sql);
	    System.out.println("Parameters: " + params);
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
	                	row[i] = rs.getString(i);
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
	
//    private boolean isValidDateFormat(String date) {
//        return DATE_PATTERN.matcher(date).matches();
//    }
//	
//    private String formatDate(String date) {
//        if (date.length() == 8) {
//            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
//        }
//        return date.replaceAll("-", "");
//    }
	
	private boolean isValidInput(String input, String placeholder) {
	    return input != null && !input.trim().isEmpty() && !input.trim().equals(placeholder);
	}
}
