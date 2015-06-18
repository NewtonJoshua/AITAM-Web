package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteMember {
	public void deleteMember(int id) throws ClassNotFoundException, SQLException{
		Connection conn= Connect.connect();
		Statement st1=null;
		Statement st2=null;
		try{
			st1= conn.createStatement();
			st2= conn.createStatement();
			st2.executeUpdate("DELETE from AITAM_EMPLOYEE where ID=" + id);
			st1.executeUpdate("DELETE from AITAM_CRED where ID=" + id);
			
		}
		finally{
			if(st1!=null){
				st1.close();
			}
			if(st2!=null){
				st2.close();
			}
			conn.close();
		}
	}
}
