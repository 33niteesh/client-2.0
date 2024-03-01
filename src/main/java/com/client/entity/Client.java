package com.client.entity;

import org.json.JSONObject;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Persons")
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private int age;
    public Client(int id, String fname, String lname, String email, String phone,int age) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.phone = phone;
		this.age=age;
	}
    
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public Client() {
		super();
	}
    
	public String citizen() {
		String s = "";
		s = (age>60 && age<100)?"Senior Citizen":((age>18 && age<=60)?"Major":"Minor");
		return s;
	}

	public JSONObject toJson() {
		JSONObject r=new JSONObject();
		r.put("id", id);
		r.put("fname", fname);
		r.put("lname", lname);
		r.put("phone", phone);
		r.put("age", age);
		r.put("catogery", citizen());
		return r;
	}

	
    
}

