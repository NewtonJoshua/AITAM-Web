package com.AITAM.demo.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.AITAM.demo.bean.EmpBean;

public class Perfometer {
	public String myChart(String from,String to, EmpBean emp) throws ClassNotFoundException, ParseException, SQLException{
		Connection conn = null;
		String str=null;
		try {
			
			conn = Connect.connect();
			str=myDayChart(from,to,emp,conn);
		} 
		finally{
			conn.close();
		}
		return str;
	}
	
	
	public String myDayChart(String from,String to, EmpBean emp, Connection conn) throws ParseException,
	ClassNotFoundException, SQLException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed1 = format.parse(from);
        java.sql.Date FromDate = new java.sql.Date(parsed1.getTime());
        Date parsed2 = format.parse(to);
        java.sql.Date ToDate = new java.sql.Date(parsed2.getTime());
        
        java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
        cal.setTime(FromDate);
        java.util.GregorianCalendar calTo = new java.util.GregorianCalendar();
        calTo.setTime(ToDate);
        String str="Date TaskSore Compliance Review Contribution Perfometer";
        calTo.add(Calendar.DATE, 1);
        int noOfDays=0;
        while(!cal.equals(calTo)){
        	if((cal.get(java.util.Calendar.DAY_OF_WEEK) != 1) && (cal.get(java.util.Calendar.DAY_OF_WEEK) != 7)){
        		
        		java.sql.Date sqlDate =new java.sql.Date(cal.getTimeInMillis());
        		String s=genMyDayChart(sqlDate, emp,conn);
        		str= str+","+s;
        		noOfDays=noOfDays+1;
        	
        	}
        	cal.add(Calendar.DATE, 1);
        }
        
        
		return str;
		
	}
	
	public String genMyDayChart(java.sql.Date sqlDate, EmpBean emp, Connection conn) throws ClassNotFoundException, 
	SQLException{
		Statement st=null;
		ResultSet rs=null;
		Statement st1=null;
		ResultSet rs1=null;
		Statement st2=null;
		ResultSet rs2=null;
		String str=null;
		try{
			st= conn.createStatement();
			rs= st.executeQuery("select SUB_ID, TIME_COMP, REVIEW, RATING from AITAM_OURTASK where CREATED<='"+
			sqlDate +"'  AND COMPLETED>='"+sqlDate +"' AND ASSIGNED="+emp.getID());
			st1= conn.createStatement();
			rs1= st1.executeQuery("select ID from AITAM_EMPLOYEE where SUPERVISOR=(select SUPERVISOR from 
			AITAM_EMPLOYEE where ID=" + emp.getID() + ")");
			
			int timeComp=0;
			int review=0;
			int rating=0;
			int contri=0;
			int count=0;
			while (rs.next()){
				timeComp=timeComp+rs.getInt(2);
				review=review+rs.getInt(3);
				rating=rating+rs.getInt(4);
				count= count+1;
			}
			if(count != 0){
				timeComp=timeComp/count;
				review=review/count;
				rating=rating/count;
			}
			
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String text = df.format(sqlDate);
			// Contribution
			
			int max=0;
			while (rs1.next()){
				int id=rs1.getInt(1);
				st2= conn.createStatement();
				rs2= st2.executeQuery("select count(SUB_ID) from AITAM_OURTASK where CREATED<='"+sqlDate +
				"'  AND COMPLETED>='"+sqlDate +"' AND ASSIGNED="+ id);
				
				while(rs2.next()){
					int c=rs2.getInt(1);
					if (c>max){
						max=c;
					}
				}
				
			}
			
			if (max !=0){
			contri=(count/max)*5;
			}
			
			float consol=(float)(timeComp+review+contri+(2*rating))/5;
			str=(text + " " + rating+ " " + timeComp + " " + review + " " + contri + " " + consol );
		}
		finally{
			if(st!=null){
				st.close();
			}
			if(rs!=null){
				rs.close();
			}
			if(st1!=null){
				st1.close();
			}
			if(rs1!=null){
				rs1.close();
			}
			if(st2!=null){
				st2.close();
			}
			if(rs2!=null){
				rs2.close();
			}
		}
		return str;
		
	}
	
	public String teamPerfometer(String from,String to, EmpBean emp) throws ClassNotFoundException, SQLException, 
	ParseException, IOException{
		Connection conn=Connect.connect();
		String str=null;
		try{
		GetMembers get= new GetMembers();
		List<EmpBean> l= get.getMembers(emp);
		int len=l.size()*2;
		System.out.println(len);
		String EmpRat="Employee TaskSore Compliance Review Contribution Perfometer";
		for (EmpBean e:l){
			EmpRat=EmpRat+ "," + e.getName()+":"+getRating(from,to,e,conn);
		}
		
		str = EmpRat;
		for (EmpBean e:l){
			str=str+";"+e.getID()+":"+e.getName()+":"+myDayChart(from,to,e,conn);
		}
		}
		finally{
			conn.close();
		}
		return str;
		
	}
	
	
	// Tool Tip Chart. Not in use.
	
	/** public String ourPerfometer(String from,String to, EmpBean emp) throws ClassNotFoundException, SQLException, 
	 * ParseException{
		String str=null;
		
		//Prim Chart
		String prim="Prim,Employee Perfometer";
		String tool="tool,Date";
		//Each Emp
		GetMembers get= new GetMembers();
		List<EmpBean> l= get.getMembers(emp);
		for (EmpBean e:l){
			tool=tool+" "+e.getName().replaceAll("\\s+","");
			//Each date
			List<java.sql.Date> d=getDateBetween(from,to);
			float rating=0;
			for (java.sql.Date sd:d){
				rating=rating+getRating(e,sd);
			}
			rating=rating/d.size();
			prim=prim+","+e.getName().replaceAll("\\s+","")+" "+rating;
		}
		
		//ToolTip chart
		//Each date
		List<java.sql.Date> d=getDateBetween(from,to);
		for (java.sql.Date sd:d){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			String text = df.format(sd);
			tool=tool+","+text;
			System.out.println(text);
			//Each Emp
			for (EmpBean e:l){
				float rating=getRating(e,sd);
				tool=tool+" "+rating;
			}
		}
		str=prim+";"+tool;
		return str;
	}    
	 * @throws ParseException **/
	
	// Get consolidate rating between two dates
	private String getRating(String from,String to,EmpBean emp,Connection conn) throws ClassNotFoundException, 
	SQLException, ParseException {
		List<java.sql.Date> d=getDateBetween(from,to);
		int timeCompAvg=0;
		int reviewAvg=0;
		int ratingAvg=0;
		int contriAvg=0;
		float consolAvg = 0;
		int totalCount=0;
		for(java.sql.Date sqlDate:d){
			Statement st=null;
			ResultSet rs=null;
			Statement st1=null;
			ResultSet rs1=null;
			try{
				st= conn.createStatement();
				rs= st.executeQuery("select SUB_ID, TIME_COMP, REVIEW, RATING from AITAM_OURTASK 
				where CREATED<='"+sqlDate +"'  AND COMPLETED>='"+sqlDate +"' AND ASSIGNED=" +emp.getID() );
				st1= conn.createStatement();
				rs1= st1.executeQuery("select ID from AITAM_EMPLOYEE where SUPERVISOR=(select SUPERVISOR 
				from AITAM_EMPLOYEE where ID=" + emp.getID() + ")");
				
				int timeComp=0;
				int review=0;
				int rating=0;
				int contri=0;
				int count=0;
				while (rs.next()){
					timeComp=timeComp+rs.getInt(2);
					review=review+rs.getInt(3);
					rating=rating+rs.getInt(4);
					count= count+1;
				}
				if(count != 0){
					timeComp=timeComp/count;
					review=review/count;
					rating=rating/count;
				}
				
				// Contribution
				
				int max=0;
				while (rs1.next()){
					int id=rs1.getInt(1);
					Statement st2=null;
					ResultSet rs2=null;
					try{
						st2= conn.createStatement();
						rs2= st2.executeQuery("select count(SUB_ID) from AITAM_OURTASK where 
						CREATED<='"+sqlDate +"'  AND COMPLETED>='"+sqlDate +"' AND ASSIGNED="+ id);
					
					while(rs2.next()){
						int c=rs2.getInt(1);
						if (c>max){
							max=c;
						}
					}
					}
					finally{
						if(st2!=null){
							st2.close();
						}
						if(rs2!=null){
							rs2.close();
						}
					}
					
				}
				
				if (max !=0){
				contri=(count/max)*5;
				}
				
				float consol=(float)(timeComp+review+contri+(2*rating))/5;
				timeCompAvg=timeCompAvg+timeComp;
				reviewAvg=reviewAvg+review;
				ratingAvg=ratingAvg+rating;
				contriAvg=contriAvg+contri;
				consolAvg=consolAvg+consol;
				totalCount=totalCount+1;
			}
			finally{
				if(st!=null){
					st.close();
				}
				if(rs!=null){
					rs.close();
				}
				if(st1!=null){
					st1.close();
				}
				if(rs1!=null){
					rs1.close();
				}
			}	
		}
		timeCompAvg=timeCompAvg/totalCount;
		reviewAvg=reviewAvg/totalCount;
		ratingAvg=ratingAvg/totalCount;
		contriAvg=contriAvg/totalCount;
		consolAvg=consolAvg/totalCount;
		String str=(ratingAvg+ " " + timeCompAvg + " " + reviewAvg + " " + contriAvg + " " + consolAvg );
		
		return str;
	}
	
	//Get Date between two dates

	private List<java.sql.Date> getDateBetween(String from, String to) throws ParseException {
		List<java.sql.Date> d= new ArrayList<java.sql.Date>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed1 = format.parse(from);
        java.sql.Date FromDate = new java.sql.Date(parsed1.getTime());
        Date parsed2 = format.parse(to);
        java.sql.Date ToDate = new java.sql.Date(parsed2.getTime());
        
        java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
        cal.setTime(FromDate);
        java.util.GregorianCalendar calTo = new java.util.GregorianCalendar();
        calTo.setTime(ToDate);
        calTo.add(Calendar.DATE, 1);
        while(!cal.equals(calTo)){
        	if((cal.get(java.util.Calendar.DAY_OF_WEEK) != 1) && (cal.get(java.util.Calendar.DAY_OF_WEEK) != 7)){
        		java.sql.Date sqlDate =new java.sql.Date(cal.getTimeInMillis());
        		d.add(sqlDate);
        	}
        	cal.add(Calendar.DATE, 1);
        }
		return d;
	}
	
	
	
	
	
	
	
	

}
