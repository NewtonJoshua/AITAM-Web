package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Team {
	public String getTeam() throws ClassNotFoundException, SQLException{
		String str="Team";
		Connection conn= Connect.connect();
		ResultSet rs1= null;
		Statement st1=null;
		try{
			st1= conn.createStatement();
			rs1= st1.executeQuery("select ID,Name,Phone,Supervisor from AITAM_EMPLOYEE");
			while(rs1.next()){
				str=str+";"+rs1.getInt(1)+","+rs1.getString(2)+","+rs1.getLong(3)+"," +rs1.getInt(4);
			}
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
		System.out.println(str);
		return str;
	}
}
