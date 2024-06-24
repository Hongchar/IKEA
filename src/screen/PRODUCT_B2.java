package screen;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import jframe.JFrames;
import tool.BackButton;
import tool.BlueLongButton;
import tool.CreateTextField;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.HomeButton;

public class PRODUCT_B2 extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	JButton home = new HomeButton();
	JButton back = new BackButton();
	
	JTextField date = CreateTextField.textField(new Point(12, 82), "일자");
	JTextField sectorCode = CreateTextField.halfTextField(new Point(12, 141), "구역코드");
	JTextField productId = CreateTextField.halfTextField(new Point(207, 141), "재고ID");
	JTextField moveSector = CreateTextField.textField(new Point(12,200), "이동할 구역코드");
	
	JLabel grayText = DefaultFrameUtils.makeGrayLabel("PROGRESS INFORMATION", 20, 60);
	JButton move = new BlueLongButton("이동", 12, 260);
	
	String dateText = "";
	String sectorCodeText = "";
	String idText = "";
	String moveText = "";
	
	public PRODUCT_B2() {
		DefaultFrameUtils.setDefaultSize(this);
		DefaultFrameUtils.makeLogo(this);
		DefaultFrameUtils.makeTopLabel(this, "상품이동");
		
		this.setVisible(true);
		home.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				JFrames.getJFrame("MAIN_A2").setVisible(true);
				JFrames.getJFrame("PRODUCT_B2").setVisible(false);
			}
		});
		
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				JFrames.getJFrame("PRODUCT_B1").setVisible(true);
				JFrames.getJFrame("PRODUCT_B2").setVisible(false);
			}
		});
		
		date.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dateText += date.getText();
			}
		});
		
		sectorCode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				sectorCodeText += sectorCode.getText();
			}
		});
		
		productId.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idText += productId.getText();
			}
		});
		
		moveSector.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				moveText += productId.getText();
			}
		});
		
		String sql = "DELETE FROM product WHERE sector_seq = ? AND product_seq = ?";
		try (
			Connection conn = DBConnector.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){
			pstmt.setString(1, sectorCodeText);
			pstmt.setString(2, idText);
			
			int row = pstmt.executeUpdate();
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.add(home);
		this.add(back);
		this.add(date);
		this.add(sectorCode);
		this.add(productId);
		this.add(moveSector);
		this.add(move);
		this.add(grayText);
		
		DefaultFrameUtils.makeTopPanel(this);
		
	}
}
