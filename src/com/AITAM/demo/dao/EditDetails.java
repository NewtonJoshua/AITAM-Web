package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.AITAM.demo.bean.EmpBean;

public class EditDetails {
	public void editMail(EmpBean emp) throws ClassNotFoundException, SQLException{
		Connection conn= Connect.connect();
		Statement st1=null;
		ResultSet rs1= null;
		try{
			st1= conn.createStatement();
			System.out.println("update AITAM_EMPLOYEE set mail='"+emp.getMail()+"' where ID="+emp.getID());
			st1.executeUpdate("update AITAM_EMPLOYEE set mail='"+emp.getMail()+"' where ID="+emp.getID());	
		}
		finally{
			if(st1!=null){
				st1.close();
			}
			if(rs1!=null){
				rs1.close();
			}
			conn.close();
		}
	}
	
	public void editPhone(EmpBean emp) throws ClassNotFoundException, SQLException{
		Connection conn= Connect.connect();
		Statement st1=null;
		ResultSet rs1= null;
		try{
			st1= conn.createStatement();
			System.out.println("update AITAM_EMPLOYEE set mail="+emp.getPhone()+" where ID="+emp.getID());
			st1.executeUpdate("update AITAM_EMPLOYEE set phone="+emp.getPhone()+" where ID="+emp.getID());	
		}
		finally{
			if(st1!=null){
				st1.close();
			}
			if(rs1!=null){
				rs1.close();
			}
			conn.close();
		}
	}
}
