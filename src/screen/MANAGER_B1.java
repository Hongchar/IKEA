package screen;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import jframe.JFrames;
import tool.BackButton;
import tool.BlueLongButton;
import tool.BottomImage;
import tool.CreateTextField;
import tool.DBConnector2;
import tool.DefaultFrameUtils;
import tool.HomeButton;

public class MANAGER_B1 extends JFrame {

	// 버튼, 패널, 아이콘 등 생성
	JButton homeBtn = new HomeButton();
	JButton backBtn = new BackButton();
	JLabel grayText = DefaultFrameUtils.makeGrayLabel("CREATE ACCOUNT", 20, 60);
	JTextField idField = new CreateTextField().textField(new Point(12, 83), "ID");
	JTextField pwField = new CreateTextField().textField(new Point(12, 146), "PW");
	JButton saveBtn = new BlueLongButton("저장", 304, 205);
	JLabel bottomImage = new BottomImage();

	public MANAGER_B1() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "계정 생성");

		this.setVisible(true);

		// 홈 버튼 기능
		homeBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("MAIN_A2").setVisible(true);
				JFrames.getJFrame("MANAGER_B1").setVisible(false);
			}
		});

		// 뒤로가기 버튼 기능
		backBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("MANAGER_A1").setVisible(true);
				JFrames.getJFrame("MANAGER_B1").setVisible(false);
			}
		});

		// 저장 버튼 (계정 생성)
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAccount();
			}
		});

		// 버튼, 패널, 아이콘 등 추가
		this.add(homeBtn);
		this.add(backBtn);
		this.add(grayText);
		this.add(idField);
		this.add(pwField);
		this.add(saveBtn);
		this.add(bottomImage);

		DefaultFrameUtils.makeTopPanel(this);
	}

	// 계정 생성 (저장 버튼)을 위한 메서드들
	// ID 중복 확인 메서드
	private boolean isIdDuplicated(String id) {
		DBConnector2 connector = new DBConnector2("HR", "1234");

		String sql = "SELECT * FROM test_table WHERE table_id = ?";
								
		try (
				Connection conn = connector.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)
		) {

			pstmt.setString(1, id);

			try (
					ResultSet rs = pstmt.executeQuery()
			) {
				if (rs.next() && rs.getInt(1) > 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 계정 저장 메서드
	private void saveAccount() {
		DBConnector2 connector = new DBConnector2("HR", "1234");

		// 필드에 입력된 값 가져오기
		String id = idField.getText().trim(); // 텍스트 양쪽의 공백값 제거
		String pw = pwField.getText().trim();

		// 공백 문자와 한글을 허용하지 않는 정규 표현식
		Pattern pattern = Pattern.compile("[\\s가-힣ㄱ-ㅎ]");

		 if (pattern.matcher(id).find() || pattern.matcher(pw).find() ) {
	            JOptionPane.showMessageDialog(
	            		this, "ID와 PW를 올바르게 입력해주세요. "
	            				+ "공백이나 한글은 사용할 수 없습니다.",
	            				"입력 오류", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
		 
		// 중복 확인
		if (isIdDuplicated(id)) {

			JOptionPane.showMessageDialog(
					this, "중복된 ID입니다. 다른 ID를 입력해주세요.",
					"중복 오류", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String sql = "INSERT INTO test_table (" + " table_seq, table_id, table_pw) "
				+ " VALUES (table_seq.nextval, ?, ?)";

		try (
				Connection conn = connector.getConnection(); 
				PreparedStatement pstmt = conn.prepareStatement(sql)
		) {

			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.executeUpdate();

			JOptionPane.showMessageDialog(
					this, "계정이 성공적으로 생성되었습니다.", "성공",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(
					this, "계정 생성 중 오류가 발생했습니다.", "DB 오류",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}