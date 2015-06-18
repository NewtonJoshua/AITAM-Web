package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.AITAM.demo.bean.DeviceBean;

public class DeviceRegister {
	public void deviceRegister(DeviceBean device) throws ClassNotFoundException, SQLException{
		Connection conn=Connect.connect();
		PreparedStatement pst =null;
		try{
		pst= conn.prepareStatement("update AITAM_DEVICES set TOKEN=?, PLATFORM=?, MODEL=? where DEVICE_ID= ?");
		pst.setString(4, device.getDeviceID());
		pst.setString(1, device.getToken());
		pst.setString(2, device.getPlatform());
		pst.setString(3, device.getModel());
		pst.execute();
		}
		finally{
			if(pst!=null){
				pst.close();
			}
			conn.close();
		}
		
	}
}
