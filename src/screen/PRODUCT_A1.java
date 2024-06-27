package screen;

import javax.swing.JFrame;

import jframe.JFrames;
import tool.BottomImage;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.MenuBar;

import javax.swing.JButton;
public class PRODUCT_A1 extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JButton proButton = new MenuBar("목록 관리", "res/arrow.png", 12, 190, 224);
	private JButton moveButton = new MenuBar("상품 이동", "res/arrow.png", 12, 293, 224);
	private JButton inButton = new MenuBar("검수 입고", "res/arrow.png", 12, 396, 224);
	
	private JButton home = new HomeButton();
	
	public PRODUCT_A1() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "상품 관리 메뉴");
		
		proButton.addActionListener(e -> {
			JFrames.getJFrame("PRODUCT_B1").setVisible(true);
			this.setVisible(false);
		});
		
		moveButton.addActionListener(e -> {
			JFrames.getJFrame("PRODUCT_B2").setVisible(true);
			this.setVisible(false);
		});
		
		inButton.addActionListener(e -> {
			JFrames.getJFrame("PRODUCT_B3").setVisible(true);
			this.setVisible(false);
		});
		
		home.addActionListener(e -> {
			JFrames.getJFrame("MAIN_A2").setVisible(true);
			this.setVisible(false);
		});
		
		this.add(new BottomImage());
		this.add(proButton);
		this.add(moveButton);
		this.add(inButton);
		this.add(home);
		DefaultFrameUtils.makeTopPanel(this);
		this.setVisible(false);
	}
}
