package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.AITAM.demo.bean.TaskBean;

public class Rating {
	public void getRating(TaskBean task) throws ClassNotFoundException, SQLException{
		int taskID=task.getTaskId();
		int Review=task.getReview();
		 int TimeComp = 0;
		 int PrioInt= task.getPriorInt();
		 Connection conn= Connect.connect();
		 Statement st1=null;
		 ResultSet rs1=null;
		 PreparedStatement pst=null;
		 try{
			 st1= conn.createStatement();
			 rs1=st1.executeQuery("Select ETA,CREATED,COMPLETED from AITAM_TASK where task_id="+taskID);
			 while(rs1.next()){
				 WorkingDays Wdays= new WorkingDays();
				 Date ETA=rs1.getTimestamp(1);
				 Date Created=rs1.getTimestamp(2);
				 Date Completed=rs1.getTimestamp(3);
				 long ActualDays=Wdays.days(Created, Completed);
				 long ExpectedDays=Wdays.days(Created, ETA);
				 long Comp=ActualDays/ExpectedDays*100;
	
				 if (Comp<75){
					 TimeComp=5;
				 }
				 else if(Comp<90){
					 TimeComp=4;
				 }
				 else if(Comp<110){
					 TimeComp=3;
				 }
				 else if(Comp<125){
					 TimeComp=2;
				 }
				 else{
					 TimeComp=1;
				 }
			 }
			 int rating=((Review*PrioInt)+TimeComp)*5/20;
			 pst= conn.prepareStatement("update AITAM_OURTASK set TIME_COMP=?, REVIEW=?, RATING=? where sub_id=?");
			 pst.setInt(1, TimeComp);
			 pst.setInt(2, Review);
			 pst.setInt(3, rating);
			 pst.setInt(4, taskID);
			 pst.execute();
		 }
		 finally{
			 if(st1!=null){
				 st1.close();
			 }
			 if(rs1!=null){
				 rs1.close();
			 }
			 if(pst!=null){
				 pst.close();
			 }
			 conn.close();
		 }
	}
}
