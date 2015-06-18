package com.AITAM.demo.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.AITAM.demo.bean.EmpBean;


public class Login {
	
	public static Boolean login(int id, String pw) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException{ 
	Boolean status;
	Connection conn=Connect.connect();
	Statement st=null;
	ResultSet rs=null;
	Encrypt enc= new Encrypt();
	pw=enc.encrypt(pw);
	try{
		st= conn.createStatement();
		rs= st.executeQuery("select password from AITAM_CRED where id =" + id);
		String pw_db=null;
		while (rs != null && rs.next()){
		pw_db=(String)rs.getString(1);
		}
		System.out.println(pw_db);
		if(pw.equals(pw_db)){
			status=true;
			System.out.println("pw correct");
		}
		else{
			status=false;
			System.out.println("pw incorrect");
		}
	}
	finally{
		if(st!=null){
			st.close();
		}
		if(rs!=null){
			rs.close();
		}
		conn.close();
	}
	return status;
	
	}
	
	public static EmpBean getDetails(EmpBean emp) throws ClassNotFoundException, SQLException{
		Connection conn=Connect.connect();
		Statement st=null;
		ResultSet rs=null;
		Statement st1=null;
		ResultSet rs1=null;
		Statement st2=null;
		ResultSet rs2=null;
		try{
			st= conn.createStatement();
			rs= st.executeQuery("select * from AITAM_EMPLOYEE where id =" + emp.getID());
			while (rs.next()){
				emp.setName(rs.getString(2));
				System.out.println(rs.getString(2));
				emp.setMail(rs.getString(3));
				emp.setPhone((int) rs.getLong(4));
				emp.setSupervisor(rs.getInt(5));
				st1= conn.createStatement();
				rs1= st1.executeQuery("select NAME from AITAM_EMPLOYEE where id =" + rs.getInt(5));
				while(rs1.next()){
					emp.setSup_Name(rs1.getString(1));
				}
				st2= conn.createStatement();
				rs2= st2.executeQuery("select ID from AITAM_EMPLOYEE where SUPERVISOR =" + emp.getID());
				while(rs2.next()){
					emp.setManager(1);
				}
			}
		}
		finally{
			if(st!=null){
				st.close();
			}
			if(rs!=null){
				rs.close();
			}
			if(st1!=null){
				st1.close();
			}
			if(rs1!=null){
				rs1.close();
			}
			if(st2!=null){
				st2.close();
			}
			if(rs2!=null){
				rs2.close();
			}
			conn.close();
		}
		return emp;
		
	}
	
	public static String mobLogin(int id, String pw) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException{ 
		String name = null;
		Connection conn=Connect.connect();
		Statement st=null;
		ResultSet rs=null;
		ResultSet rs1= null;
		Encrypt enc= new Encrypt();
		pw=enc.encrypt(pw);
		try{
			st= conn.createStatement();
			rs= st.executeQuery("select password from AITAM_CRED where id =" + id);
			String pw_db=null;
			while (rs != null && rs.next()){
			pw_db=(String)rs.getString(1);
			}
			System.out.println(pw_db);
			if(pw.equals(pw_db)){
				rs1= st.executeQuery("select NAME from AITAM_EMPLOYEE where ID=" + id);
				while(rs1.next()){
					name=rs1.getString(1);
				}
			}
			else{
				name="incorrect";
				System.out.println("pw incorrect");
			}
		}
		finally{
			if(st!=null){
				st.close();
			}
			if(rs!=null){
				rs.close();
			}
			conn.close();
		}
		return name;
		
		}
}
