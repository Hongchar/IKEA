import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import jframe.JFrames;
import screen.PRODUCT_B2;

public class Main {
	
	public static void main(String[] args) {
		// 각 프레임 인스턴스 생성
		JFrame product_b2 = new PRODUCT_B2();
		
		// 모든 프레임들 맵에 추가
		JFrames.setJFrame("PRODUCT_B2", product_b2);
		
		// 화면에 메인 프레임 그려서 시작하기
		SwingUtilities.invokeLater(() -> {
			
		});
	}
	
}
