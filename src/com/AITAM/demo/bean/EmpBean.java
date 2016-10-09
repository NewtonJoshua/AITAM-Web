package com.AITAM.demo.bean;

public class EmpBean implements java.io.Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8771549689992099607L;
	int ID;
	String Name;
	String mail;
	long phone;
	int Supervisor;
	String Sup_Name;
	String key;
	int Manager;
	int Admin;
	String SecQues;
	String SecAns;

	public int getAdmin() {
		return Admin;
	}

	public int getID() {
		return ID;
	}

	public String getKey() {
		return key;
	}

	public String getMail() {
		return mail;
	}

	public int getManager() {
		return Manager;
	}

	public String getName() {
		return Name;
	}

	public long getPhone() {
		return phone;
	}

	public String getSecAns() {
		return SecAns;
	}

	public String getSecQues() {
		return SecQues;
	}

	public String getSup_Name() {
		return Sup_Name;
	}

	public int getSupervisor() {
		return Supervisor;
	}

	public void setAdmin(int admin) {
		Admin = admin;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setManager(int manager) {
		Manager = manager;
	}

	public void setName(String name) {
		Name = name;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public void setSecAns(String secAns) {
		SecAns = secAns;
	}

	public void setSecQues(String secQues) {
		SecQues = secQues;
	}

	public void setSup_Name(String pw) {
		this.Sup_Name = pw;
	}

	public void setSupervisor(int supervisor) {
		Supervisor = supervisor;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
