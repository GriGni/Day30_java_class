package org.comstudy21_model;

public class Dto {
	private String code;
	private String name;
	private String id;
	private String pwd;
	private int age;
	
	public Dto(){}

	public Dto(String code, String name, String id, String pwd, int age) {
		super();
		this.code = code;
		this.name = name;
		this.id = id;
		this.pwd = pwd;
		this.age = age;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Dto [code=" + code + ", name=" + name + ", id=" + id + ", pwd=" + pwd + ", age=" + age + "]";
	}
	
	
	
}
