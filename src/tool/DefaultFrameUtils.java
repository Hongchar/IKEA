package tool;

import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.MediaTracker;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

// 기본 프레임 도구 
public class DefaultFrameUtils {
	static String url = "./res/ikea.png";
	
	/** 기본 사이즈로 변환 (setVisible = false)**/
	public static void setDefaultSize(JFrame f) {
		f.setSize(410, 850);
		f.setLayout(null);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.getContentPane().setBackground(new Color(255, 255, 255));
		f.setVisible(false);
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
	    ImageIcon logoIcon = new ImageIcon(url);
	    if (logoIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
	        JLabel icon = new JLabel(logoIcon);
	        icon.setBounds(0, 6, 100, 38);
	        f.getContentPane().add(icon);
	        f.revalidate();
	        f.repaint();
	    } else {
	        System.err.println("로고 이미지를 불러올 수 없습니다: " + url);
	    }
	}
	/** 상단 라벨 생성 (String 메세지) 입력 **/
	public static void makeTopLabel(JFrame f, String str) {
		f.add(new TopLabel(str));
	}
	/** 디지털 시계 생성 **/
	public static void makeTimer(JFrame f) {
		new DigitalWatch(f);
	}
	
	/** 회색 문구 라벨 생성 **/
	public static JLabel makeGrayLabel(String str, int x, int y) {
		
		JLabel gray = new JLabel(str);
		
		gray.setFont(new Font("넥슨Lv1고딕 굵게", Font.BOLD, 14));
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

