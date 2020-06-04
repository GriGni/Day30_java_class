package org.comstudy21_Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {

	public static Connection getConnection() {
		// 2
		String url = "jdbc:h2:tcp://localhost/~/test";
		String user = "sa";
		String password = "";
		Connection conn = null;
		try {
			Class.forName("org.h2.Driver");
			System.out.println("드라이브 설치 성공");
			conn = DriverManager.getConnection(url, user, password); // 드라이버 메니져 :
			System.out.println("conn :" + conn);
			// 1
		} catch (ClassNotFoundException e) {
			System.err.println("err: 드라이브 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("err: 접속 실패");
			e.printStackTrace();
		}
		// connection return

		return conn;

	} // end of main
	//오버로딩.
	public static void close(Connection conn) {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs) {
		if(rs != null) {			
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
	}

	public static void close(Statement stmt) {
		if(stmt != null) {			
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}
	}
	
	//close 전체 호출하고 서로 다른 매개변수로 따로따로 부를 수 있게 만들었네~
	
	public static void close(ResultSet rs, Statement stmt,Connection conn ) {
		close(rs);
		close(stmt);
		close(conn);
	}
	
}
