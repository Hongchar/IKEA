package tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
// DB 연결기
public class DBConnector {
	// 주소 미리 지정
	private static String driverPath = "oracle.jdbc.driver.OracleDriver";

	private static String url = "jdbc:oracle:thin:@54.180.241.45:1521:XE";
	private static String user = "ikea1";
	private static String password = "1234";
	
	static {
		try {
			Class.forName(driverPath);
			System.out.println("드라이버 로드 성공");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패...");
		}
	}
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, user, password);
	}
}
