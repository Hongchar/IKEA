package screen;

import javax.swing.JFrame;

import jframe.JFrames;

import javax.swing.JButton;

import tool.BottomImage;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.MenuBar;

public class CLIENT_A1 extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JButton regButton = new MenuBar("납품업체정보 등록", "res/arrow.png", 7, 241, 120);
	private JButton modButton = new MenuBar("납품업체정보 수정", "res/arrow.png", 7, 344, 120);
	
	private JButton home = new HomeButton();
	
	public CLIENT_A1() {
		
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeTopLabel(this, "납품업체 메뉴");
		
		regButton.addActionListener(e -> {
			JFrames.getJFrame("CLIENT_B1").setVisible(true);
			this.setVisible(false);
		});
		
		modButton.addActionListener(e -> {
			JFrames.getJFrame("CLIENT_B2").setVisible(true);
			this.setVisible(false);
		});
		
		home.addActionListener(e -> {
			JFrames.getJFrame("MAIN_A2").setVisible(true);
			this.setVisible(false);
		});
		
		this.add(home);
		this.add(regButton);
		this.add(modButton);
		DefaultFrameUtils.makeTopPanel(this);
		this.add(new BottomImage());
		this.setVisible(false);
	}
}
