package tool;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
// 전자 시계 추가
public class DigitalWatch implements Runnable {
	JFrame f;
	Thread t = null;
	int hours = 0, minutes = 0, seconds = 0;
	String timeString = "";
	JButton b;
	
	public DigitalWatch(JFrame jframe) {
		t = new Thread(this);
		t.start();
		
		f = jframe;
		
		b = new JButton();
		b.setBounds(247, 13, 153, 23);
		b.setBackground(new Color(0, 88, 163));
		b.setForeground(new Color(255, 219, 0));
		f.add(b);
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				Calendar cal = Calendar.getInstance();
				hours = cal.get(Calendar.HOUR_OF_DAY);
				if (hours > 12)
					hours -= 12;
				minutes = cal.get(Calendar.MINUTE);
				seconds = cal.get(Calendar.SECOND);

				SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss(E)");
				Date date = cal.getTime();
				timeString = formatter.format(date);
				printTime();

				Thread.sleep(1000); // interval given in milliseconds
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void printTime() {
		b.setText(timeString);
	}
	
}