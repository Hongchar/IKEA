package screen;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import jframe.JFrames;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.HomeButton;

import java.awt.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
public class SALESINFO_B2 extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JButton serch = new BlueLongButton("검색", 7, 137);
	private JButton home = new HomeButton();
	private JButton back = new BackButton();
	private JLabel gray1 = DefaultFrameUtils.makeGrayLabel("SEARCH CONDITIONS", 7, 54);
	private JLabel gray2 = DefaultFrameUtils.makeGrayLabel("SEARCH DATA", 7, 222);
	private JTextField start = CreateTextField.halfTextField(new Point(7, 80), "시작");
	private JTextField end = CreateTextField.halfTextField(new Point(198, 80), "종료");
	
	public SALESINFO_B2() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "반품 조회");
		ImageIcon img = new ImageIcon("res/calendar_icon.png");
		JLabel jpg = new JLabel(img);
		jpg.setBounds(155, 91, 26, 26);
		
		ImageIcon img2 = new ImageIcon("res/calendar_icon.png");
		JLabel bg = new JLabel(img2);
		bg.setBounds(343, 91, 26, 26);
		
		start.addActionListener(e -> {
			
		});
		
		end.addActionListener(e -> {
			
		});
		
		
		serch.addActionListener(e -> {
		StringBuilder unSql = new StringBuilder("SELECT sales_key, total_price, reg_date, product_name, qty, product_price "
				+ "FROM sales_record s "
				+ "INNER JOIN (SELECT * FROM sales_record_info i INNER JOIN product p USING (product_seq)) "
				+ "USING ( sales_key ) "
				+ "WHERE type_id LIKE 'R' AND reg_date ");	
		String startData = start.getText().trim();
		String endData = end.getText().trim();
		if (start.getText() == null && end.getText() != null) {
			unSql.append("<= '" + endData + "'");
		} else if (start.getText() != null && end.getText() == null){
			unSql.append(">= '" + startData + "'");
		} else if (start.getText() != null && end.getText() != null) {
			unSql.append(">= '" + startData + "' AND " + "reg_date " + "<= '" + endData + "'");
		}
		String sql2 = new String(unSql);
		
			try(
				Connection conn = DBConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql2);
				ResultSet rs = pstmt.executeQuery();
			) {
				while (rs.next()) {
					int key = rs.getInt("sales_key");
					int totalPrice = rs.getInt("total_price");
					String reg_date = rs.getString("reg_date");
					String product_name = rs.getString("product_name");
					int qty = rs.getInt("qty");
					int product_price = rs.getInt("product_price");
					// 확인용 
					//System.out.printf("key = %d\ntotalPrice = %d\nreg_date = %s\nproduct_name = %s\nqty = %d\nproduct_price = %d\n",
					//    				 key, totalPrice, reg_date, product_name, qty, product_price);
				}
			} catch (SQLException e1) {
				DefaultFrameUtils.makeNotice("날짜를 입력해주세요.");
				System.out.println("SQL문 오류");
			}
		});
		
		home.addActionListener(e -> {
			JFrames.getJFrame("MAIN_A2").setVisible(true);
			this.setVisible(false);
		});
		
		back.addActionListener(e -> {
			JFrames.getJFrame("SALESINFO_A1").setVisible(true);
			this.setVisible(false);
		});
		
		this.add(jpg);
		this.add(bg);
		this.add(start);
		this.add(end);
		this.add(serch);
		this.add(home);
		this.add(back);
		this.add(gray1);
		this.add(gray2);
		DefaultFrameUtils.makeTopPanel(this);
		this.setVisible(false);
	}
}
