
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import jframe.JFrames;
import screen.ORDER_B2;
import screen.ORDER_B1;
import screen.CLIENT_A1;
import screen.CLIENT_B1;
import screen.CLIENT_B2;
import screen.MAIN_A1;
import screen.MAIN_A2;
import screen.MANAGER_A1;
import screen.MANAGER_B1;
import screen.MANAGER_B2;
import screen.MANAGER_B3;
import screen.ORDER_A1;
import screen.PRODUCT_A1;
import screen.PRODUCT_B1;
import screen.PRODUCT_B2;
import screen.PRODUCT_B3;
import screen.SALESINFO_A1;
import screen.SALESINFO_B1;
import screen.SALESINFO_B2;
import tool.LoginTool;

public class Main {

	public static void main(String[] args) {


		// 로그인 인스턴스 생성
		JFrame main_a1 = new MAIN_A1();

		// 모든 프레임들 맵에 추가
		JFrames.setJFrame("MAIN_A1", main_a1);

		
		JFrames.getJFrame("MAIN_A1").addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				if (LoginTool.confirmExit()) {
					System.exit(0);
				}  else {
					LoginTool.setConfirm('N');
				}
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				if (LoginTool.getEnter() == 'Y') {
					// 각 프레임 인스턴스 생성
					JFrame main_a2 = new MAIN_A2();
					
					JFrame client_a1 = new CLIENT_A1();
					JFrame client_b1 = new CLIENT_B1();
					JFrame client_b2 = new CLIENT_B2();
					
					JFrame product_a1 = new PRODUCT_A1();
					JFrame product_b1 = new PRODUCT_B1();
					JFrame product_b2 = new PRODUCT_B2();
					JFrame product_b3 = new PRODUCT_B3();
					
					JFrame salesInfo_a1 = new SALESINFO_A1();
					JFrame salesInfo_b1 = new SALESINFO_B1();
					JFrame salesInfo_b2 = new SALESINFO_B2();
					
					JFrame order_a1 = new ORDER_A1();
					JFrame order_b1 = new ORDER_B1();
					JFrame order_b2 = new ORDER_B2();
					
					JFrame manager_a1 = new MANAGER_A1();
					JFrame manager_b1 = new MANAGER_B1();
					JFrame manager_b2 = new MANAGER_B2();
					JFrame manager_b3 = new MANAGER_B3();
					
					
					JFrames.setJFrame("MAIN_A2", main_a2);
					
					JFrames.setJFrame("CLIENT_A1", client_a1);
					JFrames.setJFrame("CLIENT_B1", client_b1);
					JFrames.setJFrame("CLIENT_B2", client_b2);

					JFrames.setJFrame("SALESINFO_A1", salesInfo_a1);
					JFrames.setJFrame("SALESINFO_B1", salesInfo_b1);
					JFrames.setJFrame("SALESINFO_B2", salesInfo_b2);
					
					JFrames.setJFrame("ORDER_A1", order_a1);
					JFrames.setJFrame("ORDER_B1", order_b1);
					JFrames.setJFrame("ORDER_B2", order_b2);
					
					JFrames.setJFrame("PRODUCT_A1", product_a1);
					JFrames.setJFrame("PRODUCT_B1", product_b1);
					JFrames.setJFrame("PRODUCT_B2", product_b2);
					JFrames.setJFrame("PRODUCT_B3", product_b3);
					
					JFrames.setJFrame("MANAGER_A1", manager_a1);
					JFrames.setJFrame("MANAGER_B1", manager_b1);
					JFrames.setJFrame("MANAGER_B2", manager_b2);
					JFrames.setJFrame("MANAGER_B3", manager_b3);
					
				} else if (LoginTool.getConfirm() != 'Y') {
					JFrames.getJFrame("MAIN_A1").setVisible(true);
				}
			}

		});

		// 화면에 메인 프레임 그려서 시작하기
		SwingUtilities.invokeLater(() -> {
			JFrames.getJFrame("MAIN_A2");
		});
	}

}
