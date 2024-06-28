package screen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import jframe.JFrames;
import tool.BackButton;
import tool.BlueLongButton;
import tool.BottomImage;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.IkeaTextField;

public class CLIENT_B1 extends JFrame {
	private static final long serialVersionUID = 1L;

	JTextField Client_name = IkeaTextField.textField(7, 140, "납품업체명");
	JTextField Manager_name = IkeaTextField.textField(7, 200, "담당자명");
	JTextField Manager_phone = IkeaTextField.textField(7, 260, "전화번호");
	JTextField date_field = IkeaTextField.iconTextField(7, 81, "신청일자: " + LocalDate.now());

	JButton save = new BlueLongButton("저장", 10, 321);
	JButton home = new HomeButton();
	JButton back = new BackButton();

	public CLIENT_B1() {
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeTopLabel(this, "  정보등록");

		// 홈 버튼
		add(home);
		home.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("MAIN_A2").setVisible(true);
				JFrames.getJFrame("CLIENT_B1").setVisible(false);
			}
		});

		// 뒤로가기 버튼
		add(back);
		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("CLIENT_A1").setVisible(true);
				JFrames.getJFrame("CLIENT_B1").setVisible(false);
			}
		});

		add(DefaultFrameUtils.makeGrayLabel("CREATE CLIENT INFORMATION", 7, 59));
		add(new BottomImage());

		add(date_field);
		add(Client_name);
		add(Manager_name);
		add(Manager_phone);

		this.add(new BottomImage());
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopPanel(this);

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		add(save);
	}

	public void save() {
		String c_name = Client_name.getText();
		String m_name = Manager_name.getText();
		String m_phone = Manager_phone.getText();
		String date = date_field.getText();

		boolean date_check = false;

		if (c_name.isEmpty()) {
			DefaultFrameUtils.makeNotice("[납품업체명] 공백입니다");
			return;
		} else if (m_name.isEmpty()) {
			DefaultFrameUtils.makeNotice("[담당자명] 공백입니다");
			return;
		} else if (m_phone.isEmpty()) {
			DefaultFrameUtils.makeNotice("[전화번호] 공백입니다");
			return;
		} else if (m_phone.length() != 11 || !m_phone.matches("0[0-9]{10}")) {
			DefaultFrameUtils.makeNotice("[전화번호] - 를 제외한 0으로 시작하는 11자리 숫자");
			return;
		}

		if (date.matches("\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])")) {
			date_check = true;
		} else {
			if (date.isEmpty()) {
				DefaultFrameUtils.makeNotice("날짜입력칸이 공백이어서 금일로 등록됩니다");
			} else {
				DefaultFrameUtils.makeNotice("[yyyy-MM-dd] 형식이 잘못되어 금일로 등록됩니다");
			}
		}

		DBConnector connector = new DBConnector();

		try (Connection conn = connector.getConnection()) {
			// 날짜에 입력받은값이 있을때
			String sql = date_check
					? "INSERT INTO clients(client_id, client_name, manager_name, manager_phone, client_date)"
							+ " VALUES (clients_seq.NEXTVAL, ?, ?, ?, ?)"
					: "INSERT INTO clients(client_id, client_name, manager_name, manager_phone)"
							+ " VALUES (clients_seq.NEXTVAL, ?, ?, ?)";
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, c_name);
				pstmt.setString(2, m_name);
				pstmt.setString(3, m_phone);
				if (date_check) {
					pstmt.setString(4, date);
				}
				pstmt.executeUpdate();

				DefaultFrameUtils.makeNotice("[등록성공] 등록이 완료되었습니다");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}