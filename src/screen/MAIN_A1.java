package screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.LoginTool;

public class MAIN_A1 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField idField = CreateTextField.textField(new Point(12, 282), "ID");
	private JPasswordField pwField = CreateTextField.PasswordTextField(new Point(12, 347));
	private static JButton login = new BlueLongButton("로그인", 12, 412);
	
	private static String id;
	private static String pw;
	
	private JLabel idLabel = new JLabel();
	private JLabel pwLabel = new JLabel();
	
	public MAIN_A1() {
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.setDefaultSize(this);
		
		idField.setBounds(87, 283, 228, 50);
		idField.setText(null);
		idField.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				idField.setForeground(Color.BLACK);
				idField.setText("");
			}

			@Override
			public void focusLost(FocusEvent e) {
				idField.setForeground(Color.BLACK);
			}
		});
		pwField.setBounds(87, 348, 228, 50);
		pwField.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				pwField.setForeground(Color.BLACK);
			}
			@Override
			public void focusLost(FocusEvent e) {
				pwField.setForeground(Color.BLACK);
			}
		});
		idLabel.setText("ID");
		pwLabel.setText("PW");
		
		idLabel.setFont(new Font("넥슨Lv1고딕", Font.BOLD, 22));
		pwLabel.setFont(new Font("넥슨Lv1고딕", Font.BOLD, 20));
		
		idLabel.setBounds(28, 296, 52, 24);
		pwLabel.setBounds(28, 361, 52, 24);
		
		ImageIcon img = new ImageIcon("res/ikeaLogo.png");
		JLabel jpg = new JLabel(img);
		jpg.setBounds(121, 202, 171, 69);
		
		ImageIcon img2 = new ImageIcon("res/login_bg.png");
		JLabel bg = new JLabel(img2);
		bg.setBounds(0, 522, 400, 328);
		
		login.setBounds(87, 425, 228, 50);
		login.setForeground(Color.WHITE);

		login.addActionListener(e -> {
			id = idField.getText();
			pw = new String(pwField.getPassword());
			
			String sql = "SELECT * FROM wm_account_info WHERE account_name = ? AND account_password = ?";

			String sql2 = "INSERT INTO wm_account_access VALUES (?, sysdate)";
			try (
				Connection conn = DBConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			){
				pstmt.setString(1, id);
				pstmt.setString(2, pw);
				
				try (
					ResultSet rs = pstmt.executeQuery();
					PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				){
					while(rs.next()) {
						pstmt2.setString(1, rs.getString("account_name"));
						pstmt2.executeUpdate();
						
						if (rs.getString("master_yn").equals("Y")) {
							System.out.println("관리자 계정입니다");
							LoginTool.setMaster('Y');
							LoginTool.setEnter('Y');
							this.dispose();

						} else {
							System.out.println("일반 계정입니다");
							LoginTool.setEnter('Y');
							this.dispose();
						}
						
						
					}
				}
				
			} catch (SQLException e1) {
				DefaultFrameUtils.makeNotice("ID 또는 PW가 틀렸습니다.");
			}
		});
		this.add(idLabel);
		this.add(pwLabel);
		this.add(jpg);
		this.add(bg);
		this.add(login);
		this.add(idField);
		this.add(pwField);
		DefaultFrameUtils.makeTimer(this);
		DefaultFrameUtils.makeTopPanel(this);
		this.setVisible(true);
	}
}
