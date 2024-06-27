package tool;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BottomImage extends JLabel {
	
	// 화면 하단에 이케아 사진 배치
	public BottomImage() {
		ImageIcon img = new ImageIcon("res/ikea_bg.png");
		Image img2 = img.getImage();
		Image updateImg = img2.getScaledInstance(400, 252, Image.SCALE_SMOOTH);
		ImageIcon updateIcon = new ImageIcon(updateImg);
		
		this.setIcon(updateIcon);
		this.setBounds(0, 592, 394, 252);
	}
}
