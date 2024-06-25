package screen;

import java.awt.Point;
import java.io.Reader;
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

import jframe.JFrames;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DBConnector;
import tool.DefaultFrameUtils;

public class MAIN_A1 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextField idField = CreateTextField.textField(new Point(12, 282), "ID");
	private JPasswordField pwField = CreateTextField.PasswordTextField(new Point(12, 347));
	
	private JButton login = new BlueLongButton("로그인", 12, 412);
	
	private String id;
	private String pw;
	
	private static char master = 'N';
	
	public static char getMaster() {
		return master;
	}

	private static void setMaster(char ch) {
		master = ch;
	}

	public MAIN_A1() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		
		ImageIcon img = new ImageIcon("res/ikeaLogo.png");
		JLabel jpg = new JLabel(img);
		jpg.setBounds(121, 202, 153, 55);
		
		ImageIcon img2 = new ImageIcon("res/login_bg.png");
		JLabel bg = new JLabel(img2);
		bg.setBounds(0, 522, 400, 328);
		
		login.addActionListener(e -> {
			id = idField.getText();
			pw = new String(pwField.getPassword());
			
			String sql = "SELECT * FROM wm_account_info WHERE account_name = ? AND account_password = ?";
			try (
				Connection conn = DBConnector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
			){
				pstmt.setString(1, id);
				pstmt.setString(2, pw);
				
				try (
					ResultSet rs = pstmt.executeQuery();
				){
					if (rs.next() == true) {
						if (rs.getCharacterStream("master_yn") != null) {
							System.out.println("관리자 계정입니다");
							setMaster('Y');
						}
						
						JFrames.getJFrame("MAIN_A2").setVisible(true);
						JFrames.getJFrame("MAIN_A1").setVisible(false);
					} else {
						DefaultFrameUtils.makeNotice("ID 또는 PW가 틀렸습니다.");
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		
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
