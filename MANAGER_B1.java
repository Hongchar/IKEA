package screen;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import jframe.JFrames;
import tool.BackButton;
import tool.BlueLongButton;
import tool.BottomImage;
import tool.CreateTextField;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.HomeButton;

public class MANAGER_B1 extends JFrame {

	private static final long serialVersionUID = 1L;

	// 버튼, 필드, 라벨 등 생성
	JButton home = new HomeButton();
	JButton back = new BackButton();
	JTextField idField = CreateTextField.textField(12, 80, "ID");
	JTextField pwField = CreateTextField.textField(12, 140, "PW");
	JLabel grayText = DefaultFrameUtils.makeGrayLabel("CREATE ACCOUNT", 20, 60);
	JButton saveBtn = new BlueLongButton("저장", 300, 200);
	JLabel bottomImage = new BottomImage();

	public MANAGER_B1() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeTopLabel(this, "계정 생성");

		// 홈 버튼 기능구현
		home.addActionListener(e -> {
			JFrames.getJFrame("MAIN_A2").setVisible(true);
			this.dispose();
		});

		// 뒤로가기 버튼 기능구현
		back.addActionListener(e -> {
			JFrames.getJFrame("MANAGER_A1").setVisible(true);
			this.dispose();
		});

		// 저장 버튼 기능구현
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 필드에 입력된 값 가져오기
				String id = idField.getText();
				String pw = pwField.getText();

				// 공백과 한글을 거르기 위해 만든 정규표현식
				Pattern pattern = Pattern.compile("[\\s가-힣ㄱ-ㅎ]");

				if (pattern.matcher(id).find() || pattern.matcher(pw).find()) {
					DefaultFrameUtils.makeNotice("ID와 PW를 올바르게 입력해주세요. " + "공백이나 한글은 사용할 수 없습니다.");

					return;
				}
				// DB에 계정정보 저장
				String sql = "INSERT INTO wm_account_info " 
				+ "(account_name, account_password) " + "VALUES (?, ?)";
				try (Connection conn = DBConnector.getConnection();
						PreparedStatement pstmt = conn.prepareStatement(sql);) {

					pstmt.setString(1, id);
					pstmt.setString(2, pw);
					pstmt.executeUpdate();
					DefaultFrameUtils.makeNotice("ID가 정상적으로 생성되었습니다.");

				} catch (SQLException ex) {
					DefaultFrameUtils.makeNotice("이미 등록된 ID입니다.");
				}
			}
		});

		// 추가
		this.add(home);
		this.add(back);
		this.add(idField);
		this.add(pwField);
		this.add(saveBtn);
		this.add(grayText);
		this.add(bottomImage);

		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopPanel(this);
		this.dispose();
	}
}