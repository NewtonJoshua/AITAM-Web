package com.AITAM.demo.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.AITAM.demo.bean.EmpBean;

public class Register {
	public void register(EmpBean emp) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException{
		Connection conn=Connect.connect();
		PreparedStatement pst =null;
		PreparedStatement pst1=null;
		String pw=emp.getKey();
		String ques=emp.getSecQues();
		String ans=emp.getSecAns();
		Encrypt enc= new Encrypt();
		pw=enc.encrypt(pw);
		ans=enc.encrypt(ans);
		try{
			pst= conn.prepareStatement("Insert into AITAM_CRED (ID,PASSWORD,QUES,ANSWER) values(?,?,?,?)");
			pst.setInt(1, emp.getID());
			pst.setString(2, pw);
			pst.setString(3, ques);
			pst.setString(4, ans);
			pst.execute();
			pst1= conn.prepareStatement("Insert into AITAM_EMPLOYEE (ID,NAME,MAIL,PHONE,SUPERVISOR) values(?,?,?,?,?)");
			pst1.setInt(1, emp.getID());
			pst1.setString(2, emp.getName());
			pst1.setString(3, emp.getMail());
			pst1.setLong(4, emp.getPhone());
			pst1.setInt(5, emp.getSupervisor());
			pst1.execute();
		}
		finally{
			if(pst!=null){
				pst.close();
			}
			if(pst1!=null){
				pst1.close();
			}
			conn.close();
		}
	}
}
