package screen;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import tool.MenuBar;

public class MANAGER_A1 extends JFrame {

	// 버튼, 패널, 아이콘 등 생성
	JButton home = new HomeButton();
	JLabel bottomImage = new BottomImage();
	JButton btn1 = new MenuBar("계정 생성", "move.png", 10, 200);
	JButton btn2 = new MenuBar("계정 관리", "move.png", 10, 300);
	JButton btn3 = new MenuBar("접속 로그 조회", "move.png", 10, 400);

	public MANAGER_A1() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "관리자 전용 메뉴");

		this.setVisible(true);

		// 홈 버튼 기능
		home.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("MAIN_A2").setVisible(true);
				JFrames.getJFrame("MANAGER_B1").setVisible(false);
			}
		});

		// 계정 생성으로 이동
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("MANAGER_B1").setVisible(true);
				JFrames.getJFrame("MANAGER_A1").setVisible(false);
			}
		});
		
		// 계정 관리로 이동
		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("MANAGER_B2").setVisible(true);
				JFrames.getJFrame("MANAGER_A1").setVisible(false);
			}
		});

		// 접속 로그 조회로 이동
		btn3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("MANAGER_B3").setVisible(true);
				JFrames.getJFrame("MANAGER_A1").setVisible(false);
			}
		});

		// 버튼, 패널, 아이콘 등 추가
		this.add(home);
		this.add(bottomImage);
		this.add(btn1);
		this.add(btn2);
		this.add(btn3);

		DefaultFrameUtils.makeTopPanel(this);
	}
}