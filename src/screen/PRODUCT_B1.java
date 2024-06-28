package screen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import tool.AddTable;
import tool.BackButton;
import tool.BlueLongButton;
import tool.DBConnector;
import tool.DefaultFrameUtils;
import tool.HomeButton;
import tool.IkeaTextField;
import tool.InfoLabel;
import tool.SmallCheckButton;

public class PRODUCT_B1 extends JFrame {
    private static final long serialVersionUID = 1L;

    String[] columnNames = {"ID", "재고명", "수량", "원가", "판매가", "무게", "납품업체"};
    
    private static JTextField inputName;
    private AddTable.TableComponents tableComp;
    private static DefaultTableModel model;
    
    HomeButton home = new HomeButton();
    BackButton back = new BackButton();
    InfoLabel greyLabel1 = new InfoLabel("SEARCH CONDINTS", 20, 59);
    InfoLabel greyLabel2 = new InfoLabel("SEARCH DATE", 6, 296);
    BlueLongButton searchBtn = new BlueLongButton("검색", 14, 159);
    SmallCheckButton editBtn = new SmallCheckButton("수정", 305, 206);
    
    static boolean isLoad = false;
    
    public PRODUCT_B1() {
        DefaultFrameUtils.makeLogo(this);
        DefaultFrameUtils.setDefaultSize(this);
        add(home);
        add(back);
        DefaultFrameUtils.makeTopLabel(this, "상품목록조회");
        DefaultFrameUtils.makeTopPanel(this);
        add(greyLabel1);
        inputName = IkeaTextField.textField(12, 81, "상품명");
        add(inputName);
        
        add(searchBtn);
        add(greyLabel2);
        add(editBtn);
        
        tableComp = AddTable.getTable(columnNames);
        add(tableComp.scrollPane);
        model = (DefaultTableModel) tableComp.table.getModel();
        
        editBtn.setEnabled(false);  // 초기에 수정 버튼 비활성화
        
        inputName.addActionListener(e -> getProductName());
        
        searchBtn.addActionListener(e -> {
            getProductName();
            editBtn.setEnabled(true);  // 검색 후 수정 버튼 활성화
        });
        
        editBtn.addActionListener(e -> toggleEditMode());

        this.setVisible(true);
    }

    private void toggleEditMode() {
        if (editBtn.getText().equals("수정")) {
            enableTableEditing();
        } else {
            disableTableEditing();
        }
    }

    private void enableTableEditing() {
        tableComp.table.setEnabled(true);
        tableComp.table.setCellSelectionEnabled(true);
        editBtn.setText("완료");

        // 각 열에 CellEditor 추가
        for (int i = 1; i < tableComp.table.getColumnCount(); i++) {
            final int column = i;
            TableCellEditor editor = tableComp.table.getDefaultEditor(tableComp.table.getColumnClass(i));
            editor.addCellEditorListener(new CellEditorListener() {
                @Override
                public void editingStopped(ChangeEvent e) {
                    int row = tableComp.table.getSelectedRow();
                    updateDatabase(row, column);
                }

                @Override
                public void editingCanceled(ChangeEvent e) {}
            });
        }
    }

    private void disableTableEditing() {
        tableComp.table.setEnabled(false);
        tableComp.table.setCellSelectionEnabled(false);
        editBtn.setText("수정");
    }

    private void updateDatabase(int row, int column) {
        String updateSql = "UPDATE product SET " + getColumnName(column) + "=? WHERE product_seq=?";
        
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
            
            String value = (String) model.getValueAt(row, column);
            String productSeq = (String) model.getValueAt(row, 0);

            setParameterByColumnType(pstmt, 1, column, value);
            pstmt.setInt(2, Integer.parseInt(productSeq));

            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("DB updated successfully");
            } else {
                System.out.println("DB update failed");
            }
        } catch (SQLException | NumberFormatException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터 저장 중 오류가 발생했습니다: " + ex.getMessage());
        }
    }

    private String getColumnName(int column) {
        switch (column) {
            case 1: return "product_name";
            case 2: return "product_qty";
            case 3: return "product_cost";
            case 4: return "product_price";
            case 5: return "product_weight";
            case 6: return "client_id";
            default: return "";
        }
    }

    private void setParameterByColumnType(PreparedStatement pstmt, int parameterIndex, int column, String value) throws SQLException {
        switch (column) {
            case 1: // product_name
            case 6: // client_id
                pstmt.setString(parameterIndex, value);
                break;
            case 2: // product_qty
                pstmt.setInt(parameterIndex, value != null && !value.isEmpty() ? Integer.parseInt(value) : 0);
                break;
            case 3: // product_cost
            case 4: // product_price
            case 5: // product_weight
                pstmt.setDouble(parameterIndex, value != null && !value.isEmpty() ? Double.parseDouble(value) : 0.0);
                break;
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
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PRODUCT_B1());
    }
}