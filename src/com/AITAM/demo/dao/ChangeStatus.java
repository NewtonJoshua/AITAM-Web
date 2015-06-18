package com.AITAM.demo.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONException;

import com.AITAM.demo.bean.TaskBean;

public class ChangeStatus {
	public void changeStatus( TaskBean task) throws ClassNotFoundException, SQLException, ParseException, JSONException, IOException{
		Connection conn= Connect.connect();
		PreparedStatement pst2=null;
		Statement st1=null;
		ResultSet rs1=null;
		PreparedStatement pst=null;
		PreparedStatement pst1=null;
		try{
			pst2 = conn.prepareStatement("update AITAM_TASK set status=? where task_id=?");
			pst2.setString(1, task.getStatus());
			pst2.setInt(2, task.getTaskId());
			pst2.execute();

			st1= conn.createStatement();
			rs1= st1.executeQuery("select MAX(SL) from AITAM_HIST");
			int sl=0;
			while(rs1.next()){
				sl=rs1.getInt(1);
			}
			pst = conn.prepareStatement("insert into AITAM_HIST (SL, TASK_ID, ASSIGNED,MODIFIED,STATUS,CUR_STATUS) "
					+ "values(?,?,?,?,?,?)");
			pst.setInt(1, sl+1);
			pst.setInt(2, task.getTaskId());
			pst.setInt(3,task.getAssigned());
			
			Calendar currenttime = Calendar.getInstance();
		    Date sqldate = new Date((currenttime.getTime()).getTime());
		    java.sql.Date sqlDate = new java.sql.Date( sqldate.getTime() );
			pst.setDate(4, sqlDate);
			System.out.println(sqlDate);
			pst.setString(5, task.getStatus());
			pst.setString(6, task.getStatus());
			pst.execute();
			
			pst1 = conn.prepareStatement("update AITAM_HIST set cur_status=? , appeal_date=? where task_id=?");
			pst1.setString(1, task.getStatus());
			
			java.sql.Date date =null;
			if(task.getAppealedDate() != null){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		        Date parsed = format.parse(task.getAppealedDate());
		        date = new java.sql.Date(parsed.getTime());
			}
			
	        
			pst1.setDate(2, date);
			pst1.setInt(3, task.getTaskId());
			pst1.execute();
		}
		finally{
			if (pst2!=null){
				pst2.close();
			}
			if(st1!=null){
				st1.close();
			}
			if(rs1!=null){
				rs1.close();
			}
			if(pst!=null){
				pst.close();
			}
			if(pst1!=null){
				pst1.close();
			}
		}
		conn.close();
		
	}
	
	public void changeReviewer( TaskBean task) throws ClassNotFoundException, SQLException, ParseException{
		Connection conn= Connect.connect();
		PreparedStatement pst2=null ;
		try{
		pst2= conn.prepareStatement("update AITAM_TASK set REVIEWER=? where task_id=?");
		pst2.setInt(1, task.getReviewer());
		pst2.setInt(2, task.getTaskId());
		pst2.execute();
		}
		finally{
		if(pst2!=null){
			pst2.close();
		}
			conn.close();
		}
	}

}
