package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.AITAM.demo.bean.TaskBean;

public class ChangeDate {
	public void changeMain(TaskBean task) throws ClassNotFoundException, SQLException, ParseException{
		Connection conn= Connect.connect();
		Statement st1= conn.createStatement();
		ResultSet rs1= null;
		PreparedStatement pst2 = null;
		try{
			rs1=st1.executeQuery("Select MAIN_ID from AITAM_OURTASK where SUB_ID=" + task.getTaskId());
			while (rs1!=null && rs1.next()){
				task.setMainID(rs1.getInt(1));
			}
			pst2 = conn.prepareStatement("update AITAM_OURTASK set ETA=? where main_id=?");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        Date parsed = format.parse(task.getMainDate());
	        java.sql.Date date = new java.sql.Date(parsed.getTime());
	        System.out.println(date);
	        pst2.setDate(1, date);
	        pst2.setInt(2, task.getMainID());
	        pst2.execute();     
		}
		finally{
			if (rs1!=null){
				rs1.close();
			}
			if (st1!=null){
				st1.close();
			}
			if (pst2!=null){
				pst2.close();
			}
			conn.close();
		}
        
	}
	public void changeSub(TaskBean task) throws ClassNotFoundException, SQLException, ParseException{
		Connection conn= Connect.connect();
		PreparedStatement pst2 =null;
		try{
			pst2 = conn.prepareStatement("update AITAM_TASK set ETA=? where task_id=?");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        Date parsed = format.parse(task.getDueDate());
	        java.sql.Date date = new java.sql.Date(parsed.getTime());
	        System.out.println(date);
	        pst2.setDate(1, date);
	        pst2.setInt(2, task.getTaskId());
	        pst2.execute();
		}
		finally{
			if (pst2!=null){
				pst2.close();
			}
	        conn.close();
		}  
	}
	
	public void changeCompleted(TaskBean task) throws ClassNotFoundException, SQLException, ParseException{
		Connection conn= Connect.connect();
		PreparedStatement pst2=null;
		PreparedStatement pst1=null;
		Statement st1=null;
		Statement st4=null;
		Statement st3=null;
		ResultSet rs1= null;
		ResultSet rs4= null;
		ResultSet rs3= null;
		try{
			pst2 = conn.prepareStatement("update AITAM_TASK set COMPLETED=? where task_id=?");
			Calendar currenttime = Calendar.getInstance();
		    Date sqldate = new Date((currenttime.getTime()).getTime());
		    java.sql.Date sqlDate = new java.sql.Date( sqldate.getTime() );
	        pst2.setDate(1, sqlDate);
	        pst2.setInt(2, task.getTaskId());
	        pst2.execute();
	        st1= conn.createStatement();
			rs1=st1.executeQuery("Select MAIN_ID,SUB_ID from AITAM_OURTASK where SUB_ID=" + task.getTaskId());
			int isCompleted=1;
			while (rs1!=null && rs1.next()){
				task.setMainID(rs1.getInt(1));
				st4= conn.createStatement();
				
				rs4=st4.executeQuery("select sub_id from AITAM_OURTASK where MAIN_ID=" + rs1.getInt(1));
				while(rs4.next() & rs4!=null){
					int subID=rs4.getInt(1);
					st3= conn.createStatement();
					rs3=st3.executeQuery("Select STATUS from AITAM_TASK where TASK_ID=" + subID);
					while(rs3.next() && rs3!=null){
						if(!(rs3.getString(1).equalsIgnoreCase("Completed"))){
							isCompleted=0;
							System.out.println(subID +"Is Completed"+ isCompleted);
						}
					}
				}	
			}
			if (isCompleted==1){
			pst1 = conn.prepareStatement("update AITAM_OURTASK set COMPLETED=? where main_id=?");
	        pst1.setDate(1, sqlDate);
	        pst1.setInt(2, task.getMainID());
	        pst1.execute();     
			}
		}
		finally{
			if (rs1!=null){
				rs1.close();
			}
			if (rs3!=null){
				rs3.close();
			}
			if (rs4!=null){
				rs4.close();
			}
			if (st1!=null){
				st1.close();
			}
			if (st3!=null){
				st3.close();
			}
			if (st4!=null){
				st4.close();
			}
			if (pst1!=null){
				pst1.close();
			}
			if (pst2!=null){
				pst2.close();
			}
			conn.close();
		}    
	}
}
