package com.AITAM.demo.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.AITAM.demo.bean.DeviceBean;

public class DeviceLogin {
	@SuppressWarnings("resource")
	public  String deviceLogin(DeviceBean device) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException{ 
		String name ="incorrect";
		Connection conn=Connect.connect();
		Statement st=null;
		Statement st1=null;
		ResultSet rs=null;
		ResultSet rs1= null;
		String isMan="No";
		int id=0;
		try{
			st= conn.createStatement();
			st1= conn.createStatement();
			rs= st.executeQuery("select EMP_ID from AITAM_DEVICES where DEVICE_ID='" + device.getDeviceID()+"'");
				while(rs.next()){
					id=rs.getInt(1);
					//if(rs.getInt(1)== device.getEmpID()){
						rs1=st1.executeQuery("select NAME from AITAM_EMPLOYEE where ID=" + rs.getInt(1));
						while(rs1.next()){
							name=rs1.getString(1);
						}
						rs1= st1.executeQuery("select ID from AITAM_EMPLOYEE where SUPERVISOR =" + device.getEmpID());
						while(rs1.next()){
							isMan= "Yes";
						}
					//}

						
				}
		}
		finally{
			if(rs!=null){
				rs.close();
			}
			if(rs1!=null){
				rs1.close();
			}
			if(st!=null){
				st.close();
			}
			if(st1!=null){
				st1.close();
			}

			conn.close();
		}
		return name+";"+isMan+";"+id;
		
		}
}
