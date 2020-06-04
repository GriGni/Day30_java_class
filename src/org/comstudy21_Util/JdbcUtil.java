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
			System.out.println("����̺� ��ġ ����");
			conn = DriverManager.getConnection(url, user, password); // ����̹� �޴��� :
			System.out.println("conn :" + conn);
			// 1
		} catch (ClassNotFoundException e) {
			System.err.println("err: ����̺� ����");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("err: ���� ����");
			e.printStackTrace();
		}
		// connection return

		return conn;

	} // end of main
	//�����ε�.
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
	
	//close ��ü ȣ���ϰ� ���� �ٸ� �Ű������� ���ε��� �θ� �� �ְ� �������~
	
	public static void close(ResultSet rs, Statement stmt,Connection conn ) {
		close(rs);
		close(stmt);
		close(conn);
	}
	
}
