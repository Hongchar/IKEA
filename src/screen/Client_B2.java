package screen;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jframe.JFrames;
import tool.BackButton;
import tool.CreateTextField;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.InfoLabel;
import tool.OhTable;
import tool.SmallCheckButton;
import tool.TopLabel;
import tool.TopPanel;

public class Client_B2 extends JFrame {
	JTextField text_field = CreateTextField.textField(new Point(12, 83), "전체조회");
	JButton search = new SmallCheckButton("조회", 307, 143);
	JButton modify = new SmallCheckButton("수정", 207, 143);
	String kor_value = "";
	String eng_value = "";
	String sql = "";
	
	BackButton back = new BackButton();
	HomeButton home = new HomeButton();
	
	boolean isListenerAdded = false; // 플래그 변수 추가
	JPanel table = new OhTable();

	public Client_B2() {
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.setDefaultSize(this);
		add(back);
		add(home);
		add(new TopLabel("   정보 수정"));
		DefaultFrameUtils.makeTopPanel(this);
		add(new InfoLabel("PROGRESS INFORMATION", 20, 59));
		add(new InfoLabel("SEARCH DATA", 8, 178));
		add(text_field);
		add(comboBox());
		add(table);
		
		home.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("CLIENT_B2").setVisible(false);
				JFrames.getJFrame("MANAGER_A2").setVisible(true);				
			}
		});
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrames.getJFrame("CLIENT_B2").setVisible(false);
				JFrames.getJFrame("CLIENT_B1").setVisible(true);
			}
		});
		
		
		// 수정버튼 추가 및 기능구현
		add(modify);
        modify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modify.setText("수정중");
                modify.setFont(new Font("넥슨고딕Lv1", Font.BOLD, 15));
            	OhTable addTable = (OhTable) table;
                addTable.modifyMod(modify);
            }
        });
        
		// 조회버튼 추가 및 기능구현
		add(search);
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				search();
			}
		});
		add(new TopPanel());
	}

	// 기준으로 검색하기
	public JComboBox comboBox() {
		String criteria[] = { "전체조회", "등록일시", "납품업체명", "담당자명", "전화번호" };

		JComboBox cb = new JComboBox(criteria);
		cb.setBounds(8, 140, 100, 30);
		cb.addActionListener(e -> {
			String selectedOption = (String) cb.getSelectedItem();
			switch (selectedOption) {
			case "전체조회":
				kor_value = "전체조회";
				sql = "";
				break;
			case "등록일시":
				kor_value = "등록일시";
				break;
			case "납품업체명":
				kor_value = "납품업체명";
				break;
			case "담당자명":
				kor_value = "담당자명";
				break;
			case "전화번호":
				kor_value = "전화번호";
				break;
			default:
				break;
			}
			text_field.setText(kor_value);
		});
		return cb;
	}

	public void search() {
		String input = text_field.getText();
		switch (kor_value) {
		case "전체조회":
			sql = "";
			break;
		case "등록일시":
			sql = " WHERE TO_CHAR(client_date, 'YYYY-MM-DD') LIKE ?";
			break;
		case "납품업체명":
			kor_value = "납품업체명";
			sql = " WHERE client_name LIKE ?";
			break;
		case "담당자명":
			kor_value = "담당자명";
			sql = " WHERE manager_name LIKE ?";
			break;
		case "전화번호":
			kor_value = "전화번호";
			sql = " WHERE manager_phone LIKE ?";
			break;
		default:
			break;
		}

		OhTable addTable = (OhTable) table;
		addTable.loadTableData(sql, input);
	}

	public static void main(String[] args) {

		JFrame f = new Client_B2();

		f.setVisible(true);

	}
}
