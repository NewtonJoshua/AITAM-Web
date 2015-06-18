package com.AITAM.demo.dao;

import java.sql.SQLException;
import java.util.List;

import com.AITAM.demo.bean.EmpBean;

public class TeamTree {
	
	public String buildTree(EmpBean n) throws ClassNotFoundException, SQLException{
		EmpBean emp=Login.getDetails(n);
		String Sup=null;
		Sup=emp.getSup_Name();
		if(Sup==null){
			Sup="";
		}
		String str=emp.getID() + ","+emp.getName()+","+emp.getPhone()+","+Sup;
		GetMembers get= new GetMembers();
		List<EmpBean> l=get.getMembers(emp);
		for(EmpBean e:l){
			str=str+";"+e.getID()+","+e.getName()+","+e.getPhone()+"," +e.getManager();
		}
		System.out.println(str);
		return str;
	}

}