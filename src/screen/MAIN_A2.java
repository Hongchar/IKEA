package screen;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import jframe.JFrames;
import tool.BottomImage;
import tool.DefaultFrameUtils;
import tool.LoginTool;
import tool.MenuBar;

public class MAIN_A2 extends JFrame {
	private static final long serialVersionUID = 1L;
	private JButton mgButton = new MenuBar("관리자 전용", "res/manager_icon.png", 11, 103, 222);
	private JButton pdButton = new MenuBar("상품 관리", "res/icon4.png", 11, 206, 225);
	private JButton odButton = new MenuBar("발주 관리", "res/icon3.png", 11, 309, 225);
	private JButton clButton = new MenuBar("납품업체 관리", "res/icon2.png", 11, 400, 185);
	private JButton slButton = new MenuBar("판매실적 조회", "res/icon1.png", 11, 503, 185);
	private JLabel grayText = DefaultFrameUtils.makeGrayLabel("Administrator only", 11, 86);
	
	public MAIN_A2() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTimer(this);
		
		mgButton.addActionListener(e -> {
			JFrames.getJFrame("MANAGER_A1").setVisible(true);
			this.setVisible(false);
		});
		
		pdButton.addActionListener(e -> {
			JFrames.getJFrame("PRODUCT_A1").setVisible(true);
			this.setVisible(false);
		});
		
		odButton.addActionListener(e -> {
			JFrames.getJFrame("ORDER_A1").setVisible(true);
			this.setVisible(false);
		});
		
		clButton.addActionListener(e -> {
			JFrames.getJFrame("CLIENT_A1").setVisible(true);
			this.setVisible(false);
		});
		
		slButton.addActionListener(e -> {
			JFrames.getJFrame("SALESINFO_A1").setVisible(true);
			this.setVisible(false);
		});
		
		if (LoginTool.getMaster() == 'Y') {
			this.add(grayText);
			this.add(mgButton);
			this.add(pdButton);
			this.add(odButton);
			this.add(clButton);
			this.add(slButton);
		} else {
			pdButton.setLocation(11, 103);
			odButton.setLocation(11, 220);
			clButton.setLocation(11, 337);
			slButton.setLocation(11, 456);
			this.add(pdButton);
			this.add(odButton);
			this.add(clButton);
			this.add(slButton);
		}
		
		this.add(new BottomImage());
		DefaultFrameUtils.makeTopPanel(this);
		this.setVisible(true);
	}
}
