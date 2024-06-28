package tool;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;

public class DigitalWatch implements Runnable {
    JFrame f;
    Thread t = null;
    int hours = 0, minutes = 0, seconds = 0;
    String timeString = "";
    JButton b;
    private volatile boolean running = true;  // 스레드 실행 상태를 제어하는 변수

    public DigitalWatch(JFrame jframe) {

        f = jframe;

        b = new JButton();
        b.setBounds(247, 13, 153, 23);
        b.setBackground(new Color(0, 88, 163));
        b.setForeground(new Color(255, 219, 0));
        b.setBorder(null);
        b.setBorder(new LineBorder(new Color(0, 88, 163)));
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        f.add(b);

        t = new Thread(this);
        t.start();
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            stopClock();
        }));
    }

    @Override
    public void run() {
        try {
            while (running) {
                Calendar cal = Calendar.getInstance();
                hours = cal.get(Calendar.HOUR_OF_DAY);
                if (hours > 12)
                    hours -= 12;
                minutes = cal.get(Calendar.MINUTE);
                seconds = cal.get(Calendar.SECOND);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss (E)");
                Date date = cal.getTime();
                timeString = formatter.format(date);
                printTime();
                Thread.sleep(1000); // interval given in milliseconds
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // 인터럽트 상태 복원
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printTime() {
        b.setText(timeString);
    }

    // 스레드를 안전하게 중지하는 메서드
    public void stopClock() {
        running = false;
        if (t != null) {
            t.interrupt();  // 스레드가 sleep 상태일 때 즉시 깨우기 위해
        }
    }
}