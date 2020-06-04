package org.comstudy21_model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.comstudy21_Util.JdbcUtil;

//인터페이스 생성.
interface DaoInterface {

	// 4
	String INSERT = "INSERT INTO MEMBER VALUES(?,?,?,?,?)";
	String SELECTALL = "SELECT * FROM MEMBER ORDER BY CODE DESC";
	String SELECT = "SELECT * FROM MEMBER WHERE NAME LIKE '%'||?||'%'";
	String SELECT_ONE = "SELECT * FROM MEMBER WHERE CODE = ? ;";
	String UPDATE = "UPDATE MEMBER SET NAME=?, ID=?, PWD=?, AGE=? WHERE CODE=?";
	String DELETE = "DELETE FROM MEMBER WHERE CODE = ? ";
	String FINDCODEBYNAME = "SELECT CODE FROM MEMBER WHERE NAME = ?";
	// 식별을 위해 프라이머리키가 필요 그리고 날짜가 들어가야 한다 프라이머리 키 그리고 날짜로 정렬 가능 .
	// '%'||?||'%' 부분검색 (인터넷 검색 가능)

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
	private PreparedStatement pstmt = null; // 미리 컴파일해서 실행속도를 단축시켜주는 장점이 있다.
	private Statement stmt = null;
	private ResultSet rs = null;

	// 생성자 만들기2
	public Dao() {
		conn = JdbcUtil.getConnection();
	}

	public Dao(Connection conn) {
		this.conn = conn;
	}

	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	// 입력, 전체검색, 부분검색, 수정, 삭제
	// 2 넣어주는 부분.
	public void insert(Dto dto) {
		// close를 매번할 경우Optimizer
//		conn = JdbcUtil.getConnection();
		try {
			// 미리 컴파일을 해준다.
			pstmt = conn.prepareStatement(INSERT); // "INSERT INTO MEMBER VALUES(?,?,?,?,?)" preparedstatement는
													// connection 객체의 preparedstatement메소드를 사용해서 객체를 생성. sql문장이 미리 컴파일되게
													// 만들어 실행시간 동안 인수 값을 위한 공간을 확보할 수 있다는 점. 인수 값??
			pstmt.setString(1, dto.getCode());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getId());
			pstmt.setString(4, dto.getPwd());
			pstmt.setInt(5, dto.getAge());
			// 가져오는 것은 executequary 이지만 나머지는 executeupdates
			// cnt는 처리한 결과 개수
			int cnt = pstmt.executeUpdate(); //
			// 유효성 체크
			System.out.println("cnt => " + cnt);
			if (cnt > 0) {
				System.out.println("INFO: 입력 성공!");
			} else {
				System.out.println("INFO: 입력 실패!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// conn은 전용 멤버 필드이므로 close()하면 메소드 사용 불가
			JdbcUtil.close(rs, stmt, null);
		}

	}

	@Override
	// 전체다 선택하는 부분.
	public ArrayList<Dto> selectAll() { // 어레이리스트 타입의 메서드.
		ArrayList<Dto> list = new ArrayList<Dto>(); // dto 어레이 리스트를 만들고
		try {
			stmt = conn.createStatement(); // SQL>> (이상태를 만드는 것)
			rs = stmt.executeQuery(SELECTALL); // 질문실행(selectall 질문 : "SELECT * FROM MEMBER ORDER BY CODE DESC" 실행.)
			while (rs.next()) {
				String code = rs.getString("code");
				String name = rs.getString("name");
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				int age = rs.getInt("age");
				list.add(new Dto(code, name, id, pwd, age)); // 어레이 리스트에 추가 하는 것.

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, stmt, null); // 커넥션을 제외하고 나머지는 close한다.
		}
		return list;// 어레이 리스트에 추가한 결과값을 받는 것.
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

	public ArrayList<Dto> selectOne(Dto dto) { // 코드를 받아서 내용 출력
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
		return list; // 코드 하나만...
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
			int cnt = pstmt.executeUpdate(); // 업데이트 삭제한다.
			if (cnt > 0) {
				System.out.println("INFO: 수정 완료");
			} else {
				System.out.println("INFO: 수정 실패");
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
				System.out.println("Info: 삭제 처리 완료");
			} else {
				System.out.println("Info: 삭제 실패");
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JdbcUtil.close(rs, pstmt, null);
		}

	}
}
