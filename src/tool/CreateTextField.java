package tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CreateTextField {
    public static JTextField textField(int x, int y, String label) {
        return new CustomTextField(x, y, label);
    }
    
    public static JTextField halfTextField(int x, int y, String label) {
    	return new HalfTextField(x, y, label);
    }
    
}

class CustomTextField extends JTextField {
    private String label;
    private boolean isShowingLabel;

    public CustomTextField(int x, int y, String label) {
        this.label = label;
        this.isShowingLabel = true;
        setSize(375, 50);
        setFont(new Font("나눔글꼴", Font.PLAIN, 14));
        setForeground(Color.GRAY);
        setLocation(x, y);
        setText(label);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isShowingLabel) {
                    setText("");
                    setForeground(Color.BLACK);
                    isShowingLabel = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.GRAY);
                    setText(label);
                    isShowingLabel = true;
                }
            }
        });
    }

    @Override
    public String getText() {
        return isShowingLabel ? "" : super.getText();
    }
}

class HalfTextField extends JTextField {
    private String label;
    private boolean isShowingLabel;

    public HalfTextField(int x, int y, String label) {
        this.label = label;
        this.isShowingLabel = true;
        setSize(180, 50);
        setFont(new Font("나눔글꼴", Font.PLAIN, 14));
        setForeground(Color.GRAY);
        setLocation(x, y);
        setText(label);

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isShowingLabel) {
                    setText("");
                    setForeground(Color.BLACK);
                    isShowingLabel = false;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.GRAY);
                    setText(label);
                    isShowingLabel = true;
                }
            }
        });
    }

    @Override
    public String getText() {
        return isShowingLabel ? "" : super.getText();
    }
}
