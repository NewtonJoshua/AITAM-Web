package com.AITAM.demo.bean;

public class EmpBean implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8771549689992099607L;
	int ID;
	String Name;
	String mail;
	long phone;
	int  Supervisor;
	String Sup_Name;
	String key;
	int Manager;
	int Admin;
	String SecQues;
	String SecAns;
	
	public String getSecQues() {
		return SecQues;
	}
	public void setSecQues(String secQues) {
		SecQues = secQues;
	}
	public String getSecAns() {
		return SecAns;
	}
	public void setSecAns(String secAns) {
		SecAns = secAns;
	}
	public int getManager() {
		return Manager;
	}
	public void setManager(int manager) {
		Manager = manager;
	}
	public int getAdmin() {
		return Admin;
	}
	public void setAdmin(int admin) {
		Admin = admin;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
	public int getSupervisor() {
		return Supervisor;
	}
	public void setSupervisor(int supervisor) {
		Supervisor = supervisor;
	}
	public String getSup_Name() {
		return Sup_Name;
	}
	public void setSup_Name(String pw) {
		this.Sup_Name = pw;
	}
	
}
