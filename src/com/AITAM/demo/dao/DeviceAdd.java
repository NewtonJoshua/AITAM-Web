package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.AITAM.demo.bean.DeviceBean;

public class DeviceAdd {
	
	public void deviceAdd(DeviceBean device) throws ClassNotFoundException, SQLException{
		Connection conn=Connect.connect();
		PreparedStatement pst =null;
		Statement st=null;
		ResultSet rs=null;
		try{
		st= conn.createStatement();
		rs= st.executeQuery("select EMP_ID from AITAM_DEVICES where DEVICE_ID='" + device.getDeviceID()+"'");
		if(rs.next()){
			pst= conn.prepareStatement("update AITAM_DEVICES set EMP_ID=?, PLATFORM=? where DEVICE_ID = ?");
			pst.setString(3, device.getDeviceID());
			pst.setString(2, device.getPlatform());
			pst.setInt(1, device.getEmpID());
			pst.execute();
		}
		else{
			pst= conn.prepareStatement("insert into AITAM_DEVICES (DEVICE_ID, EMP_ID, PLATFORM)"
					+ " values(?,?,?)");
			pst.setString(1, device.getDeviceID());
			pst.setInt(2, device.getEmpID());
			pst.setString(3, device.getPlatform());
			pst.execute();
		}

		}
		finally{
			if(pst!=null){
				pst.close();
			}
			conn.close();
		}
	}

}
