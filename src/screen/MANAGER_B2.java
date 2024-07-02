
package screen;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import jframe.JFrames;
import tool.AccountTablePanel;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.IkeaTextField;
import tool.SmallCheckButton;

public class MANAGER_B2 extends JFrame {

	private static final long serialVersionUID = 1L;

	// 버튼, 필드, 라벨 등 생성
	JButton home = new HomeButton();
	JButton back = new BackButton();
	JTextField searchField = IkeaTextField.textField(12, 80, "계정ID");
	JLabel grayText1 = DefaultFrameUtils.makeGrayLabel("PROGRESS INFORMATION", 20, 60);
	JLabel grayText2 = DefaultFrameUtils.makeGrayLabel("SEARCH DATA", 20, 250);
	JButton searchBtn = new SmallCheckButton("조회", 305, 140);
	JButton deleteBtn = new SmallCheckButton("삭제", 305, 200);

	// 테이블이 있는 패널 생성
	String[] columnNames = { "No.", "아이디", "비밀번호" };
	int[] columnWidths = {85, 145, 145};
	String tableName = "wm_account_info";
	String columnIdName = "account_name";
	int LocationX = 10; int LocationY = 40;
	int width = 375;    int height = 540;

	AccountTablePanel accountTablePanel = new AccountTablePanel(
			columnNames, columnWidths, tableName, columnIdName, 
			LocationX, LocationY, width, height);

	
	public MANAGER_B2() {
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeTopLabel(this, "계정 관리");

		// 홈 버튼 기능구현
		home.addActionListener(e -> {
			JFrames.getJFrame("MAIN_A2").setVisible(true);
			this.setVisible(false);
		});

		// 뒤로가기 버튼 기능구현
		back.addActionListener(e -> {
			JFrames.getJFrame("MANAGER_A1").setVisible(true);
			this.setVisible(false);
		});

		// 패널 배경색 설정
		accountTablePanel.setBackground(Color.WHITE);
		accountTablePanel.setBounds(0, 230, 400, 600);
		
		JTable table = accountTablePanel.getTable();
        table.setEnabled(false);

		// 조회 버튼 클릭 이벤트
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String accountId = searchField.getText().trim();

				// DB에서 데이터 조회 후 테이블에 추가
				((AccountTablePanel) accountTablePanel).searchAccount(accountId);
			}
		});

		/// 삭제 버튼 클릭 이벤트
        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String accountId = searchField.getText().trim();

                // 계정 삭제 기능 호출
                ((AccountTablePanel) accountTablePanel).deleteAccount(accountId);
            }
        });

        // 추가
		this.add(home);
		this.add(back);
		this.add(searchField);
		this.add(grayText1);
		this.add(searchBtn);
		this.add(deleteBtn);
		this.add(grayText2);
		this.add(accountTablePanel);

		DefaultFrameUtils.makeTopPanel(this);
		this.setVisible(false);
	}
}