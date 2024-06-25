import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import jframe.JFrames;
import screen.MAIN_A1;
import screen.MAIN_A2;
import screen.PRODUCT_B2;
import screen.PRODUCT_B3;

public class Main {
	
	public static void main(String[] args) {
		// 각 프레임 인스턴스 생성
		JFrame product_b2 = new PRODUCT_B2();
		JFrame product_b3 = new PRODUCT_B3();
		JFrame main_a1 = new MAIN_A1();
		JFrame main_a2 = new MAIN_A2();
		// 모든 프레임들 맵에 추가
		JFrames.setJFrame("MAIN_A1", main_a1);
		JFrames.setJFrame("MAIN_A2", main_a2);
		JFrames.setJFrame("PRODUCT_B2", product_b2);
		JFrames.setJFrame("PRODUCT_B3", product_b3);
		
		// 화면에 메인 프레임 그려서 시작하기
		SwingUtilities.invokeLater(() -> {
			JFrames.getJFrame("MAIN_A1");
		});
	}
	
}
