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

public class Product_B1 extends JFrame {
	
	CreateTextField text = new CreateTextField();
	
	public Product_B1() {
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.setDefaultSize(this);
		this.add(new HomeButton());
		this.add(new BackButton());
		DefaultFrameUtils.makeTopLabel(this, "상품목록조회");
		DefaultFrameUtils.makeTopPanel(this);
		this.add(new InfoLabel("SEARCH CONDINTS", 20, 59));
		this.add(text.textField(new Point(12, 81), "상품목록조회"));
		
		this.add(new BlueLongButton("검색", 14, 159));
		this.add(new InfoLabel("SEARCH DATE", 15, 249));
		
		this.add(AddTable.getTable());
		
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
        		new Product_B1();
            }
        });
	}

}
