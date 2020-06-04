package org.comstudy21_model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.comstudy21_Util.JdbcUtil;

//�������̽� ����.
interface DaoInterface {

	// 4
	String INSERT = "INSERT INTO MEMBER VALUES(?,?,?,?,?)";
	String SELECTALL = "SELECT * FROM MEMBER ORDER BY CODE DESC";
	String SELECT = "SELECT * FROM MEMBER WHERE NAME LIKE '%'||?||'%'";
	String SELECT_ONE = "SELECT * FROM MEMBER WHERE CODE = ? ;";
	String UPDATE = "UPDATE MEMBER SET NAME=?, ID=?, PWD=?, AGE=? WHERE CODE=?";
	String DELETE = "DELETE FROM MEMBER WHERE CODE = ? ";
	String FINDCODEBYNAME = "SELECT CODE FROM MEMBER WHERE NAME = ?";
	// �ĺ��� ���� �����̸Ӹ�Ű�� �ʿ� �׸��� ��¥�� ���� �Ѵ� �����̸Ӹ� Ű �׸��� ��¥�� ���� ���� .
	// '%'||?||'%' �κа˻� (���ͳ� �˻� ����)

	// 3
	public void insert(Dto dto);

	public ArrayList<Dto> selectAll();

	public ArrayList<Dto> select(Dto dto);

	public ArrayList<Dto> selectOne(Dto dto);

	public void update(Dto dto);

	public void delete(Dto dto);
}

public class Dao implements DaoInterface {
	// 1
	private Connection conn = null;
	private PreparedStatement pstmt = null; // �̸� �������ؼ� ����ӵ��� ��������ִ� ������ �ִ�.
	private Statement stmt = null;
	private ResultSet rs = null;

	// ������ �����2
	public Dao() {
		conn = JdbcUtil.getConnection();
	}

	public Dao(Connection conn) {
		this.conn = conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	// �Է�, ��ü�˻�, �κа˻�, ����, ����
	// 2 �־��ִ� �κ�.
	public void insert(Dto dto) {
		// close�� �Ź��� ���Optimizer
//		conn = JdbcUtil.getConnection();
		try {
			// �̸� �������� ���ش�.
			pstmt = conn.prepareStatement(INSERT); // "INSERT INTO MEMBER VALUES(?,?,?,?,?)" preparedstatement��
													// connection ��ü�� preparedstatement�޼ҵ带 ����ؼ� ��ü�� ����. sql������ �̸� �����ϵǰ�
													// ����� ����ð� ���� �μ� ���� ���� ������ Ȯ���� �� �ִٴ� ��. �μ� ��??
			pstmt.setString(1, dto.getCode());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getPwd());
			pstmt.setInt(5, dto.getAge());
			// �������� ���� executequary ������ �������� executeupdates
			// cnt�� ó���� ��� ����
			int cnt = pstmt.executeUpdate(); //
			// ��ȿ�� üũ
			System.out.println("cnt => " + cnt);
			if (cnt > 0) {
				System.out.println("INFO: �Է� ����!");
			} else {
				System.out.println("INFO: �Է� ����!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// conn�� ���� ��� �ʵ��̹Ƿ� close()�ϸ� �޼ҵ� ��� �Ұ�
			JdbcUtil.close(rs, stmt, null);
		}

	}

	@Override
	// ��ü�� �����ϴ� �κ�.
	public ArrayList<Dto> selectAll() { // ��̸���Ʈ Ÿ���� �޼���.
		ArrayList<Dto> list = new ArrayList<Dto>(); // dto ��� ����Ʈ�� �����
		try {
			stmt = conn.createStatement(); // SQL>> (�̻��¸� ����� ��)
			rs = stmt.executeQuery(SELECTALL); // ��������(selectall ���� : "SELECT * FROM MEMBER ORDER BY CODE DESC" ����.)
			while (rs.next()) {
				String code = rs.getString("code");
				String name = rs.getString("name");
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				int age = rs.getInt("age");
				list.add(new Dto(code, name, id, pwd, age)); // ��� ����Ʈ�� �߰� �ϴ� ��.

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, stmt, null); // Ŀ�ؼ��� �����ϰ� �������� close�Ѵ�.
		}
		return list;// ��� ����Ʈ�� �߰��� ������� �޴� ��.
	}

	@Override
	public ArrayList<Dto> select(Dto dto) {
		ArrayList<Dto> list = new ArrayList<Dto>();
		try {
			pstmt = conn.prepareStatement(SELECT);
			pstmt.setString(1, dto.getName());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String code = rs.getString("code");
				String name = rs.getString("name");
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				int age = rs.getInt("age");
				list.add(new Dto(code, name, id, pwd, age));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, stmt, null);
		}
		return list;
	}

	public ArrayList<Dto> selectOne(Dto dto) { // �ڵ带 �޾Ƽ� ���� ���
		ArrayList<Dto> list = new ArrayList<Dto>();
		try { //  
			pstmt = conn.prepareStatement(SELECT_ONE);
			pstmt.setString(1, dto.getCode());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String code = rs.getString("code");
				String name = rs.getString("name");
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				int age = rs.getInt("age");
				list.add(new Dto(code, name, id, pwd, age));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, stmt, null);
		}
		return list; // �ڵ� �ϳ���...
	}
	
	public String findCodeByName(String name) {
		String code = null;
		ArrayList<Dto> list = new ArrayList<Dto>();
		
		try {
			pstmt = conn.prepareStatement(FINDCODEBYNAME);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				code = rs.getString("code");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JdbcUtil.close(rs, pstmt, null);
		}
		return code;
	}
	
	@Override
	public void update(Dto dto) {
		try {
			pstmt = conn.prepareStatement(UPDATE);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getId());
			pstmt.setString(3, dto.getPwd());
			pstmt.setInt(4, dto.getAge());
			pstmt.setString(5, dto.getCode());
			int cnt = pstmt.executeUpdate(); // ������Ʈ �����Ѵ�.
			if (cnt > 0) {
				System.out.println("INFO: ���� �Ϸ�");
			} else {
				System.out.println("INFO: ���� ����");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, null);
		}

	}

	@Override
	public void delete(Dto dto) {
//		pstmt = conn.prepareStatement();
		try {
			pstmt = conn.prepareStatement(DELETE);
			pstmt.setString(1, dto.getCode());
			int cnt = pstmt.executeUpdate();
			if (cnt > 0) {
				System.out.println("Info: ���� ó�� �Ϸ�");
			} else {
				System.out.println("Info: ���� ����");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, null);
		}

	}
}
