package tool;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class CreateTextField {
    public static JTextField textField(int x, int y, String label) {
    	
        JTextField txf = new CustomTextField(x, y, label);
        txf.setBorder(roundBorder());
        
    	return txf;
    }
    
    public static JTextField halfTextField(int x, int y, String label) {
    	
    	JTextField txf = new HalfTextField(x, y, label);
        txf.setBorder(roundBorder());
        
    	return txf;
    }
   
    public static JTextField iconTextField(int x, int y, String label) {
    	JTextField txf = new IconTextField(x, y, label, new ImageIcon("C:\\aiautomation_kdm\\repositories\\ikea\\res\\calendar.png"));
    	txf.setBorder(roundBorder());
    	
    	return txf;
    }
    
    public static JTextField iconHalfTextField(int x, int y, String label) {
    	JTextField txf = new IconHalfTextField(x, y, label, new ImageIcon("C:\\aiautomation_kdm\\repositories\\ikea\\res\\calendar.png"));
    	txf.setBorder(roundBorder());
    	
    	return txf;
    	
    }
    
    private static Border roundBorder() {
    	return new Border() {
			private int radius = 10;
			
			@Override
			public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
				g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
			}
			
			@Override
			public Insets getBorderInsets(Component c) {
				return new Insets(radius + 1, radius + 1, radius + 2, radius);
			}
			
			@Override
			public boolean isBorderOpaque() {
				return true;
			}
		};
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
        setFont(new Font("넥슨", Font.PLAIN, 14));
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

class IconTextField extends CustomTextField {
	private Icon icon;
	private boolean isShowingIcon;
	
	public IconTextField(int x, int y, String label, Icon icon) {
		super(x, y, label);
		this.icon = icon;
		this.isShowingIcon = true;
		
		addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				isShowingIcon = false;
				repaint();
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				isShowingIcon = true;
				repaint();
			}
			
		});

	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isShowingIcon && icon != null) {
			int iconWidth = icon.getIconWidth();
			int iconHeight = icon.getIconHeight();
			int x = getWidth() - iconWidth - 10;
			int y = (getHeight() - iconHeight) / 2;
			icon.paintIcon(this,  g, x, y);
		}
	}
	
	public void setIcon(Icon icon) {
		this.icon = icon;
		repaint();
	}
	
	public Icon getIcon() {
		return this.icon;
	}
	
}

class IconHalfTextField extends HalfTextField {
	private Icon icon;
	private boolean isShowingIcon;
	
	public IconHalfTextField(int x, int y, String label, Icon icon) {
		super(x, y, label);
		this.icon = icon;
		this.isShowingIcon = true;
		
		addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				isShowingIcon = false;
				repaint();
				
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				isShowingIcon = true;
				repaint();
				
			}
		});	
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (isShowingIcon && icon != null) {
			int iconWidth = icon.getIconWidth();
			int iconHeight = icon.getIconHeight();
			int x = getWidth() - iconWidth - 10;
			int y = (getHeight() - iconHeight) / 2;
			icon.paintIcon(this, g, x, y);
		}
		
	}
	
	public void setIcon(Icon icon) {
		this.icon = icon;
		repaint();
	}
	
	public Icon getIcon() {
		return this.icon;
	}
}

