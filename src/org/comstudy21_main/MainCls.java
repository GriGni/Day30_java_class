package org.comstudy21_main;

import java.util.ArrayList;

import org.comstudy21_model.Dao;
import org.comstudy21_model.Dto;

public class MainCls {
	//�ٸ��������� �� �� �ְ�
	public static Dao dao = new Dao();
	
	public static void main(String[] args) {
		findCodeByName(null);
	}
	
	public static void findCodeByName(String[] args) {
		String name = "������";
		String code = dao.findCodeByName(name);
		System.out.println("i want to find the code by the name, it is " + code);
	}
	
	public static void selectOne(String[] args) {
		Dto dto = new Dto("1003",null, null,null, 0);
		ArrayList<Dto> list = dao.selectOne(dto);
		for( Dto data : list) {
			System.out.println(data);
		}
	}
	
	public static void selectName(String[] args) { //search
		Dto dto = new Dto(null,"���", null, null, 0);
		ArrayList<Dto> list = dao.select(dto);
		for(Dto data : list) {
			System.out.println(data);
		}
	}
	
	public static void update(String[] args) { //update
		//�����Ұ�
		Dto dto = new Dto("1003", "������","earth","1234", 31);
		dao.update(dto);
	}
	
	public static void delete(String[] args) { //delete
		Dto dto = new Dto("1005", null, null, null, 0);
		dao.delete(dto);
	}
	
	public static void selectAll(String[] args) {// output
		ArrayList<Dto> list = dao.selectAll();
		for(Dto dto : list) { // dto �ȿ� �ִ� ����Ʈ�� �������.
			System.out.println(dto);
		}
	}
	
	public static void insert(String[] args) {//input
		//1 dao ȣ��
		Dto dto = new Dto("1005", "ȫ�浿", "test", "4444", 45);
		dao.insert(dto);
	}
}
