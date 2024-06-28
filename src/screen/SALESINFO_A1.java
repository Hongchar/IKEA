package screen;
import tool.BottomImage;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.MenuBar;

import javax.swing.JButton;
import javax.swing.JFrame;

import jframe.JFrames;

public class SALESINFO_A1 extends JFrame {
	private static final long serialVersionUID = 1L;

	private JButton home = new HomeButton();
	private JButton salesButton = new MenuBar("매출 조회", "res/arrow.png", 7, 241, 222);
	private JButton returnButton = new MenuBar("반품 조회", "res/arrow.png", 7, 344, 222);
	
	public SALESINFO_A1() {
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeTopLabel(this, "판매 관리 메뉴");
		
		salesButton.addActionListener(e -> {
			JFrames.getJFrame("SALESINFO_B1").setVisible(true);
			this.setVisible(false);
		});
		
		returnButton.addActionListener(e -> {
			JFrames.getJFrame("SALESINFO_B2").setVisible(true);
			this.setVisible(false);
			
		});
		
		home.addActionListener(e -> {
			JFrames.getJFrame("MAIN_A2").setVisible(true);
			this.setVisible(false);
		});

		this.add(home);
		this.add(salesButton);
		this.add(returnButton);
		
		DefaultFrameUtils.makeTopPanel(this);
		this.add(new BottomImage());
		this.setVisible(false);
	}
}
