package screen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import jframe.JFrames;
import tool.AddTable;
import tool.BackButton;
import tool.BlueLongButton;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.IkeaTextField;
import tool.SmallCheckButton;

public class PRODUCT_B1 extends JFrame {
    private static final long serialVersionUID = 1L;

    String[] columnNames = {"No.", "재고명", "수량", "원가", "판매가", "무게", "납품업체"};
    
    private static JTextField inputName;
    private AddTable.TableComponents tableComp;
    private static DefaultTableModel model;
    
    HomeButton home = new HomeButton();
    BackButton back = new BackButton();
    BlueLongButton searchBtn = new BlueLongButton("검색", 14, 159);
    SmallCheckButton editBtn = new SmallCheckButton("수정", 305, 206);
	JLabel greyLabel1 = DefaultFrameUtils.makeGrayLabel("SEARCH CONDITIONS", 20, 59);
	JLabel greyLabel2 = DefaultFrameUtils.makeGrayLabel("SEARCH DATA", 6, 296);
    static boolean isLoad = false;
    
    public PRODUCT_B1() {
        DefaultFrameUtils.setDefaultSize(this);
        DefaultFrameUtils.makeLogo(this);
        
        home.addActionListener(e -> {
        	JFrames.getJFrame("PRODUCT_A1").setVisible(true);
        	this.setVisible(false);
        });
        back.addActionListener(e -> {
        	JFrames.getJFrame("PRODUCT_A1").setVisible(true);
        	this.setVisible(false);
        });
        DefaultFrameUtils.makeTopLabel(this, "상품목록조회");
        add(greyLabel1);
        add(home);
        add(back);
        
        DefaultFrameUtils.makeTopPanel(this);
        inputName = IkeaTextField.textField(12, 81, "상품명");
        add(inputName);
        
        add(searchBtn);
        add(greyLabel2);
        add(editBtn);
        
        tableComp = AddTable.getTable(columnNames);
        add(tableComp.scrollPane);
        model = (DefaultTableModel) tableComp.table.getModel();

        tableComp.table.setDefaultEditor(Object.class, null); // 수정모드 전 테이블 편집불가
        editBtn.setEnabled(false);  // 초기에 수정 버튼 비활성화
        
        inputName.addActionListener(e -> {
        	getProductName();
        	editBtn.setEnabled(true);
        });
        
        searchBtn.addActionListener(e -> {
            getProductName();
            editBtn.setEnabled(true);  // 검색 후 수정 버튼 활성화
        });
        
        editBtn.addActionListener(e -> toggleEditMode());

        this.setVisible(false);
    }
    
    private void toggleEditMode() {
        if (editBtn.getText().equals("수정")) {
            tableComp.table.setDefaultEditor(Object.class, new DefaultCellEditor(new JTextField()));
            tableComp.table.setEnabled(true);
            editBtn.setText("완료");
        } else {
            tableComp.table.setDefaultEditor(Object.class, null);
            tableComp.table.setEnabled(false);
            editBtn.setText("수정");
            updateAllRows();
        }
    }

    private void updateAllRows() {
        String updateSql = "UPDATE product SET product_name=?, product_qty=?, product_cost=?, " +
                           "product_price=?, product_weight=?, client_id=? WHERE product_seq=?";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            
            conn.setAutoCommit(false);
            
            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 1; j <= 6; j++) {
                    pstmt.setString(j, (String) model.getValueAt(i, j));
                }
                pstmt.setString(7, (String) model.getValueAt(i, 0));
                pstmt.addBatch();
            }
            
            pstmt.executeBatch();
            conn.commit();
            JOptionPane.showMessageDialog(this, "변경사항이 저장되었습니다.");
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터 저장 중 오류가 발생했습니다: " + ex.getMessage());
        }
    }
    
    public static void getProductName() {
        String name = inputName.getText();
        
        String sql = "SELECT product_seq, product_name, product_qty, product_cost, " +
                     "product_price, product_weight, client_id FROM product" +
                     (name.isBlank() ? "" : " WHERE product_name LIKE ?");

        model.setRowCount(0);

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (!name.isEmpty()) {
                pstmt.setString(1, "%" + name + "%");
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                int columnCount = rs.getMetaData().getColumnCount();
                while(rs.next()) {
                    String[] row = new String[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getString(i);
                    }
                    model.addRow(row);
                }
                isLoad = true;
                inputName.setText("");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            isLoad = false;
        }
        
    }
}