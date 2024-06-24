package jframe;

import tool.DefaultFrame;
import javax.swing.JFrame;

public class MyJFrame extends JFrame {

	private static final long serialVersionUID = 8524004720178938725L;

	private MyJFrame() {};
	
	public static JFrame getInstance(String viewId) {
		
		JFrame tmp = Operator.getJFrameMap(viewId);
		
		if(tmp == null) {
			tmp = new DefaultFrame().getDefaultFrame();
			Operator.setJFrameMap(viewId, tmp);
		}
		
		return tmp;
	}
	
}