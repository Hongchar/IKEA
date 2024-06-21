package tool;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BottomImage extends JLabel {
	
	public BottomImage() {
		ImageIcon img = new ImageIcon("ikea_bg.png");
		this.setIcon(img);
		this.setBounds(0, 561, 400, 250);
	}
}
