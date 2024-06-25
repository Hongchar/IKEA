import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import tool.AddTable;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.InfoLabel;
import tool.MenuBar;

public class Manager_B3 extends JFrame {

	CreateTextField text = new CreateTextField();
	
	public Manager_B3() {
		DefaultFrameUtils.setDefaultSize(this);
		this.add(new BackButton());
		this.add(new HomeButton());
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "접근기록조회");
		DefaultFrameUtils.makeTopPanel(this);
		this.add(new InfoLabel("SEARCH CONDITIONS", 20, 58));
		this.add(text.halfTextField(new Point(12, 90), "날짜"));
		this.add(text.halfTextField(new Point(207, 90), "날짜"));
		this.add(text.textField(new Point(12, 153), "계정ID"));
		this.add(new BlueLongButton("검색", 12, 220));
		this.add(new InfoLabel("SEARCH DATA", 12, 259));
//		this.add(AddTable.getTable());
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        		new Manager_B3();
            }
        });
	}

}
