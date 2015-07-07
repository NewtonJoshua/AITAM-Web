package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.AITAM.demo.bean.EmpBean;

public class Summary {
	
	public String count(EmpBean emp) throws ClassNotFoundException, SQLException{
		Connection conn= Connect.connect();
		Statement st=null;
		ResultSet rs=null;
		String str=null;
		int newTask=0;
		int Accepted=0;
		int Appeal=0;
		int Progress=0;
		int Review=0;
		int Approve=0;
		int ApproveAcp=0;
		int ApproveDec=0;
		int AppealAcp=0;
		int AppealDec=0;
		int Rework=0;
		int Completed=0;
		int open=0;
		try{
			st= conn.createStatement();
			rs= st.executeQuery("select STATUS from  AITAM_TASK where  ASIGNEE="+ emp.getID());
			while(rs.next()){
				
				if(rs.getString(1).equalsIgnoreCase("New")){
					 newTask++;
				}
				if(rs.getString(1).equalsIgnoreCase("Accepted")){
					 Accepted++;
				}
				if(rs.getString(1).equalsIgnoreCase("Appeal")){
					 Appeal++;
				}
				if(rs.getString(1).equalsIgnoreCase("Progress")){
					Progress++;
				}
				if(rs.getString(1).equalsIgnoreCase("Review")){
					Review++;
				}
				if(rs.getString(1).equalsIgnoreCase("Approve")){
					Approve++;
				}
				if(rs.getString(1).equalsIgnoreCase("Approve-Acp")){
					ApproveAcp++;
				}
				if(rs.getString(1).equalsIgnoreCase("Approve-Dec")){
					ApproveDec++;
				}
				if(rs.getString(1).equalsIgnoreCase("Appeal-Acp")){
					AppealAcp++;
				}
				if(rs.getString(1).equalsIgnoreCase("Appeal-Dec")){
					AppealDec++;
				}
				if(rs.getString(1).equalsIgnoreCase("Rework")){
					Rework++;
				}
				if(rs.getString(1).equalsIgnoreCase("Completed")){
					Completed++;
				}
				
				
				
			}
			open=Accepted+Appeal+Progress+Review+Approve+ApproveAcp+ApproveDec+AppealAcp+AppealDec+Rework;
			str=newTask+" "+Accepted+" "+Progress+" "+Review+" "+Appeal+" "+AppealAcp+" "+AppealDec+" "+
			Approve+" "+ApproveAcp+" "+ApproveDec+" "+Rework+" "+open+" "+Completed;
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
		return str;
	}
	
	public String ourCount(EmpBean emp) throws ClassNotFoundException, SQLException{
		Connection conn= Connect.connect();
		Statement st=null;
		ResultSet rs=null;
		String str=null;
		int newTask=0;
		int Accepted=0;
		int Appeal=0;
		int Progress=0;
		int Review=0;
		int Approve=0;
		int ApproveAcp=0;
		int ApproveDec=0;
		int AppealAcp=0;
		int AppealDec=0;
		int Rework=0;
		int Completed=0;
		int open=0;
		int totReview=0;
		try{
			st= conn.createStatement();
			rs= st.executeQuery("select STATUS from  AITAM_TASK where  Reviewer="+ emp.getID());
			while(rs.next()){
				if(rs.getString(1).equalsIgnoreCase("New")){
					 newTask++;
				}
				if(rs.getString(1).equalsIgnoreCase("Accepted")){
					 Accepted++;
				}
				if(rs.getString(1).equalsIgnoreCase("Appeal")){
					 Appeal++;
				}
				if(rs.getString(1).equalsIgnoreCase("Progress")){
					Progress++;
				}
				if(rs.getString(1).equalsIgnoreCase("Review")){
					Review++;
				}
				if(rs.getString(1).equalsIgnoreCase("Approve")){
					Approve++;
				}
				if(rs.getString(1).equalsIgnoreCase("Approve-Acp")){
					ApproveAcp++;
				}
				if(rs.getString(1).equalsIgnoreCase("Approve-Dec")){
					ApproveDec++;
				}
				if(rs.getString(1).equalsIgnoreCase("Appeal-Acp")){
					AppealAcp++;
				}
				if(rs.getString(1).equalsIgnoreCase("Appeal-Dec")){
					AppealDec++;
				}
				if(rs.getString(1).equalsIgnoreCase("Rework")){
					Rework++;
				}
				if(rs.getString(1).equalsIgnoreCase("Completed")){
					Completed++;
				}
				
				
				
			}
			totReview=Review+Approve+Appeal;
			open=Accepted+Progress+ApproveAcp+ApproveDec+AppealAcp+AppealDec+Rework+newTask;
			str=newTask+" "+Accepted+" "+Progress+" "+Review+" "+Appeal+" "+AppealAcp+" "+AppealDec+" "+
			Approve+" "+ApproveAcp+" "+ApproveDec+" "+Rework+" "+open+" "+Completed+" "+totReview;
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
		return str;
	}
	


}
