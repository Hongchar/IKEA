package screen;


import javax.swing.JFrame;

import jframe.JFrames;
import tool.BottomImage;

import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.MenuBar;

import javax.swing.JButton;
public class MANAGER_A1 extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton createButton = new MenuBar("계정 생성", "res/arrow.png", 12, 190,222); 
	private JButton mgmButton = new MenuBar("계정 관리", "res/arrow.png", 12, 293, 222);
	private JButton logButton = new MenuBar("접속 로그 조회", "res/arrow.png", 12, 396, 173);
	
	private JButton home = new HomeButton();
	
	public MANAGER_A1() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "관리자 전용");
		
		createButton.addActionListener(e -> {
			JFrames.getJFrame("MANAGER_B1").setVisible(true);
			this.dispose();
		});
		
		mgmButton.addActionListener(e -> {
			JFrames.getJFrame("MANAGER_B2").setVisible(true);
			this.dispose();
		});
		
		logButton.addActionListener(e -> {
			JFrames.getJFrame("MANAGER_B3").setVisible(true);
			this.dispose();
		});
		
		home.addActionListener(e -> {
			JFrames.getJFrame("MAIN_A2").setVisible(true);
			this.dispose();
		});
		
		this.add(createButton);
		this.add(mgmButton);
		this.add(logButton);
		this.add(home);
		this.add(new BottomImage());
		DefaultFrameUtils.makeTopPanel(this);
		this.dispose();
	}
}

