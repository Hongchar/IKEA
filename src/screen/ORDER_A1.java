package screen;

import javax.swing.JFrame;

import jframe.JFrames;
import tool.BottomImage;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.MenuBar;

import javax.swing.JButton;
public class ORDER_A1 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton appButton = new MenuBar("발주 신청", "res/arrow.png", 7, 241, 222);
	private JButton orderButton = new MenuBar("발주 현황 조회", "res/arrow.png", 7, 344, 173);
	private JButton home = new HomeButton();
	
	public ORDER_A1() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "발주 관리 메뉴");
		
		appButton.addActionListener(e -> {
			JFrames.getJFrame("ORDER_B1").setVisible(true);
			this.setVisible(false);
		});
		
		orderButton.addActionListener(e -> {
			JFrames.getJFrame("ORDER_B2").setVisible(true);
			this.setVisible(false);
		});
		
		home.addActionListener(e -> {
			JFrames.getJFrame("MAIN_A2").setVisible(true);
			this.setVisible(false);
		});
		
		this.add(appButton);
		this.add(orderButton);
		this.add(home);
		this.add(new BottomImage());
		DefaultFrameUtils.makeTopPanel(this);
		this.setVisible(false);
	}
}
