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

import com.AITAM.demo.bean.EmpBean;
import com.AITAM.demo.bean.TaskBean;

public class ViewOurTask {
	public List<TaskBean> view(EmpBean emp) throws ClassNotFoundException, SQLException{
		List<TaskBean> l= new ArrayList<TaskBean>();
		Connection conn= Connect.connect();
		ResultSet rs1= null;
		Statement st1=null;
		ResultSet rs2= null;
		Statement st2=null;
		Statement st5=null;
		ResultSet rs5=null;
		try{
			st1= conn.createStatement();
			rs1=st1.executeQuery("select * from AITAM_TASK where REVIEWER=" + emp.getID() + " and ASIGNEE<>" +  emp.getID()+ " and (STATUS='Accepted' or STATUS='Progress' or STATUS='New' or STATUS='Approve-Acp' or STATUS='Approve-Dec' or STATUS='Appeal-Acp' or STATUS='Appeal-Dec' or STATUS='ReWork') order by TASK_ID");
			while (rs1!=null && rs1.next()){
				int id=rs1.getInt(1);
				TaskBean task = new TaskBean();
				task.setTaskId(id);
				task.setTitle(rs1.getString(2));
				task.setDesc(rs1.getString(7));
				Date date = rs1.getDate(3);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				String dateN = df.format(date);
				task.setDueDate(dateN);
				task.setPriority(rs1.getString(4));
				task.setReviewer(rs1.getInt(5));
				task.setStatus(rs1.getString(6));
				Date date1 = rs1.getDate(10);
				DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
				String dateN1 = df1.format(date1);
				task.setCreatedDate(dateN1);
				task.setAssigned(rs1.getInt(11));
				st2= conn.createStatement();
				rs2=st2.executeQuery("Select * from AITAM_OURTASK where SUB_ID=" + id);
				while (rs2!=null && rs2.next()){
					task.setMainTitle(rs2.getString(2));
					task.setMainDesc(rs2.getString(4));
					Date date2 = rs2.getDate(3);
					DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
					String dateN2 = df2.format(date2);
					task.setMainDate(dateN2);
					task.setMainID(rs2.getInt(11));
				}
				st5= conn.createStatement();
				rs5= st5.executeQuery("select NAME from AITAM_EMPLOYEE where ID=" + (rs1.getInt(11)));
				while (rs5.next()){
					task.setAssigneeName(rs5.getString(1));
				}
				l.add(task);
				
			}
		}
		finally{
			if(rs1!=null){
				rs1.close();
			}
			if(st1!=null){
				st1.close();
			}
			if(rs2!=null){
				rs2.close();
			}
			if(st2!=null){
				st2.close();
			}
			if(st5!=null){
				st5.close();
			}
			if(rs5!=null){
				rs5.close();
			}
			conn.close();
		}
		
		return l;
		
	}
}
