package tool;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class CreateTextField {
	
	public static JTextField textField(Point location, String label) {
		JTextField tf = new JTextField(label);
		tf.setSize(375, 50);
		tf.setFont(new Font("나눔글꼴", Font.PLAIN, 14));
		tf.setForeground(Color.GRAY);
		tf.setLocation(location);
		tf.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tf.getText().equals(label)) {
					tf.setText("");
					tf.setForeground(Color.BLACK);
				}
				
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if (tf.getText().isEmpty()) {
					tf.setForeground(Color.GRAY);
					tf.setText(label);
				}
				
			}
		});
		
		return tf;
	}

	public static JTextField halfTextField(Point location, String label) {
		JTextField h_tf = new JTextField();
		h_tf.setSize(180, 50);
		h_tf.setFont(new Font("나눔글꼴", Font.PLAIN, 14));
		h_tf.setForeground(Color.GRAY);
		h_tf.setLocation(location);
		h_tf.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {
				if (h_tf.getText().equals(label)) {
					h_tf.setText("");
					h_tf.setForeground(Color.BLACK);
				}
			}
			@Override
			public void focusLost(FocusEvent e) {
				if (h_tf.getText().isEmpty()) {
					h_tf.setForeground(Color.GRAY);
					h_tf.setText(label);
				}
			}
		});
		
		return h_tf;
	}
	
	public static void main(String[] args) {
		
		JFrame f = new DefaultFrame().getDefaultFrame();
		CreateTextField c = new CreateTextField();
		
		f.add(c.textField(new Point(10, 150), "상품명"));
		
	}
}