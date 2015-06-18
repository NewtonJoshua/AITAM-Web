package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Manager {
	public void assignManager(int mem, int man) throws ClassNotFoundException, SQLException{
		Connection conn= Connect.connect();
		ResultSet rs1= null;
		Statement st1=null;
		try{
			st1= conn.createStatement();
			rs1= st1.executeQuery("update AITAM_EMPLOYEE set Supervisor="+man+" where ID="+mem);
		}
		finally{
			if(rs1!=null){
				rs1.close();
			}
			if(st1!=null){
				st1.close();
			}
			conn.close();
		}
	}
public void ReassignManager(String mem, String man){
		
	}
}
