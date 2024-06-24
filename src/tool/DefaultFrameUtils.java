package tool;

import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

// 기본 프레임 도구 
public class DefaultFrameUtils {
	static String url = "res/ikea.png";
	private static JLabel icon = new JLabel();
	
	/** 기본 사이즈로 변환 (setVisible = false)**/
	public static void setDefaultSize(JFrame f) {
		f.setSize(410, 850);
		f.setLayout(null);
		f.setVisible(false);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/** 알림창 생성 (String 메세지) 입력 **/
	public static void makeNotice(String str) {
		JOptionPane.showMessageDialog(null, str, "알림창",
				JOptionPane.OK_CANCEL_OPTION
		);
		
	}
	
	/** 상단 패널 생성 **/
	public static void makeTopPanel(JFrame f) {
		f.add(new TopPanel());
	}
	
	/** IKEA 로고 생성 **/
	public static void makeLogo(JFrame f) {
		icon.setIcon(new ImageIcon(url));
		icon.setBounds(0, 6, 100, 38);
		f.add(icon);
	}
	/** 상단 라벨 생성 (String 메세지) 입력 **/
	public static void makeTopLabel(JFrame f, String str) {
		f.add(new TopLabel(str));
	}
	/** 디지털 시계 생성 **/
	public static void makeTimer(JFrame f) {
		new DigitalWatch(f);
	}
	
//	/** 홈버튼 생성 **/
//	public static void makeHomeButton(JFrame f) {
//		f.add(new HomeButton());
//	}
//	/** 뒤로가기 버튼 생성 **/
//	public static void makeBackButton(JFrame f) {
//		f.add(new BackButton());
//	}
	public static JLabel makeGrayLabel(String str, int x, int y) {
		
		JLabel gray = new JLabel(str);
		
		gray.setFont(new Font("넥슨Lv1고딕", Font.PLAIN, 14));
		gray.setForeground(new Color(144, 144, 144));
		gray.setBounds(x, y, 359, 19);
		
		return gray;
	}
	public static void main(String[] args) {
		JFrame f = new JFrame();
		DefaultFrameUtils.setDefaultSize(f);
		DefaultFrameUtils.makeLogo(f);
		DefaultFrameUtils.makeTopPanel(f);
		DefaultFrameUtils.makeTimer(f);
		DefaultFrameUtils.makeNotice(url);
		f.setVisible(true);
		
	}
}

