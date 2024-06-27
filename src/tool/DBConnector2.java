package tool;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector2 {
	
	private static String driverPath = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@127.0.0.1:1521:XE";
	private String user;
	private String password;
	
	static {
		try {
			Class.forName(driverPath);
			System.out.println("드라이버 로드 완료");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버를 찾을 수 없다");
		}
	}

	public DBConnector2(String user, String password) {
		this.user = user;
		this.password = password;
	}
	
	
	// 예외 처리를 throws로 던진다
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}
	
}
