import javax.swing.SwingUtilities;

import jframe.JFrames;

public class Main {
	
	public static void main(String[] args) {
		// 각 프레임 인스턴스 생성
		
		
		// 모든 프레임들 맵에 추가
		JFrames.setJFrame(null, null);
		JFrames.setJFrame(null, null);
		JFrames.setJFrame(null, null);
		JFrames.setJFrame(null, null);
		
		// 화면에 메인 프레임 그려서 시작하기
		SwingUtilities.invokeLater(() -> {
			
		});
	}
	
}
