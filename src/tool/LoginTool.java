package tool;

import javax.swing.JOptionPane;

public class LoginTool {
	private static char master = 'N';
	private static char enter = 'N';
	private static char confirm = 'Y';
	
	public static char getMaster() {
		return master;
	}

	public static void setMaster(char ch) {
		master = ch;
	}

	public static char getEnter() {
		return enter;
	}

	public static void setEnter(char enter) {
		LoginTool.enter = enter;
	}

	public static boolean confirmExit() {
		 int result = JOptionPane.showConfirmDialog(null, "프로그램을 종료하시겠습니까?", "종료 확인", JOptionPane.YES_NO_OPTION);
	     return result == JOptionPane.YES_OPTION;
	}

	public static char getConfirm() {
		return confirm;
	}

	public static void setConfirm(char confirm) {
		LoginTool.confirm = confirm;
	}

	
	
}
