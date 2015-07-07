package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.AITAM.demo.bean.EmpBean;
import com.AITAM.demo.bean.TaskBean;

public class ViewMyNewTasks {
	private final Logger LOGGER = Logger.getLogger("ViewMyNewTasks");
	public List<TaskBean> view(EmpBean emp) throws ClassNotFoundException, SQLException { 
		LOGGER.log(Level.SEVERE,"Insdie ViewMyNewTasks");
		List<TaskBean> l= new ArrayList<TaskBean>();
		Connection conn= Connect.connect();
		ResultSet rs1= null;
		Statement st1=null;
		Statement st=null;
		ResultSet rs=null;
		Statement st5=null;
		ResultSet rs5=null;
		Statement st6=null;
		ResultSet rs6=null;
		try{
			st1= conn.createStatement();
			rs1= st1.executeQuery("select distinct TASK_ID from AITAM_HIST where ASSIGNED=" + emp.getID() + 
			" and (CUR_STATUS='New') order by TASK_ID DESC");
			while (rs1!=null && rs1.next()){
				int taskId=rs1.getInt(1);
				st= conn.createStatement();
				rs= st.executeQuery("select * from AITAM_TASK where TASK_ID=" + taskId);
				while (rs.next()){
					TaskBean task = new TaskBean();
					task.setTaskId(rs.getInt(1));
					task.setTitle(rs.getString(2));
					Date date = rs.getDate(3);
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					String dateN = df.format(date);
					task.setDueDate(dateN);
					task.setPriority(rs.getString(4));
					task.setReviewer(rs.getInt(5));
					task.setStatus(rs.getString(6));
					task.setDesc(rs.getString(7));
					task.setCommnets(rs.getString(8));
					task.setCreator(rs.getInt(9));
					Date date1 = rs.getDate(10);
					DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
					String dateN1 = df1.format(date1);
					task.setCreatedDate(dateN1);
					int Creator=(rs.getInt(9));
					if (rs.getInt(5)==0){
						task.setReviewerName("Self Reviewed");
						task.setAssigneeName("Self Assigned");
					}
					else{
						st5= conn.createStatement();
						rs5= st5.executeQuery("select NAME from AITAM_EMPLOYEE where ID=" + 
						(rs.getInt(5)));
						while (rs5.next()){
							task.setReviewerName(rs5.getString(1));
						}
						st6= conn.createStatement();
						rs6= st6.executeQuery("select NAME from AITAM_EMPLOYEE where ID=" + Creator);
						while (rs6.next()){
							task.setAssigneeName(rs6.getString(1));
						}
					}
					WorkingDays wd= new WorkingDays();
					int days=wd.days(rs.getDate(10), rs.getDate(3));
					task.setDays(days);
					l.add(task);
					
				}
			}
		}
		finally{
			if(rs1!=null){
				rs1.close();
			}
			if(st!=null){
				st.close();
			}
			if(st1!=null){
				st1.close();
			}
			if(rs!=null){
				rs.close();
			}
			if(st5!=null){
				st5.close();
			}
			if(rs5!=null){
				rs5.close();
			}
			if(st6!=null){
				st6.close();
			}
			if(rs6!=null){
				rs6.close();
			}
			conn.close();
		}
		return l;
		
	}

}
