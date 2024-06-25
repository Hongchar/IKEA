import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import tool.AddTable;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.InfoLabel;

public class Product_B1 extends JFrame {
	
	CreateTextField text = new CreateTextField();
	String[] columnNames = {"ID", "재고명", "수량", "원가", "판매가", "무게", "납품업체"};
	
	JScrollPane sp;
	DefaultTableModel model;
	JTable table;
	
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
		
		Object[] tableComponents = 
				AddTable.getTableComponents(12, 278, 370, 540, columnNames, "accessRecord");
		sp = (JScrollPane) tableComponents[0];
		model = (DefaultTableModel) tableComponents[1];
		table = (JTable) tableComponents[2];
		this.add(sp);
		
		
		this.setVisible(true);
		
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
