import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import jframe.JFrames;
import tool.DefaultFrameUtils;

public class Main {
	
	public static void main(String[] args) {
		// 각 프레임 인스턴스 생성
		JFrame manager_b3 = new Manager_B3();
		JFrame product_b1 = new Product_B1();
		
		// 모든 프레임들 맵에 추가
		JFrames.setJFrame("MANAGER_B3", manager_b3);
		JFrames.setJFrame("PRODUCT_B1", product_b1);
		
		// 화면에 메인 프레임 그려서 시작하기
		SwingUtilities.invokeLater(() -> {
			
		});
	}
	
}
