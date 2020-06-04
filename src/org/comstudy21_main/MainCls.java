package org.comstudy21_main;

import java.util.ArrayList;

import org.comstudy21_model.Dao;
import org.comstudy21_model.Dto;

public class MainCls {
	//다른곳에서도 쓸 수 있게
	public static Dao dao = new Dao();
	
	public static void main(String[] args) {
		update(null);
	}
	
	
	public static void test6(String[] args) {
		Dto dto = new Dto(null,null, null,null, 0);
		ArrayList<Dto> list = dao.selectOne(dto);
		for( Dto data : list) {
			System.out.println(data);
		}
	}
	
	public static void test5(String[] args) { //search
		Dto dto = new Dto(null,"김백", null, null, 0);
		ArrayList<Dto> list = dao.select(dto);
		for(Dto data : list) {
			System.out.println(data);
		}
	}
	
	public static void update(String[] args) { //update
		//수정할값
		Dto dto = new Dto("1003", "이지상","earth","1234", 31);
		dao.update(dto);
	}
	
	public static void delete(String[] args) { //delete
		Dto dto = new Dto("1003", null, null, null, 0);
		dao.delete(dto);
	}
	
	public static void selectAll(String[] args) {// output
		ArrayList<Dto> list = dao.selectAll();
		for(Dto dto : list) { // dto 안에 있는 리스트를 출력해줘.
			System.out.println(dto);
		}
	}
	
	public static void insert(String[] args) {//input
		//1 dao 호출
		Dto dto = new Dto("1003", "홍길동", "test", "4444", 45);
		dao.insert(dto);
	}
}
