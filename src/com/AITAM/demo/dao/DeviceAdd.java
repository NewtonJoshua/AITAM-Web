package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.AITAM.demo.bean.DeviceBean;

public class DeviceAdd {
	public void deviceAdd(DeviceBean device) throws ClassNotFoundException, SQLException {
		Connection conn = Connect.connect();
		PreparedStatement pst = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(
					Messages.getString("DeviceAdd.0") + device.getDeviceID() + Messages.getString("DeviceAdd.1")); //$NON-NLS-1$ //$NON-NLS-2$

			if (rs.next()) {
				pst = conn.prepareStatement(Messages.getString("DeviceAdd.2")); //$NON-NLS-1$
				pst.setString(3, device.getDeviceID());
				pst.setString(2, device.getPlatform());
				pst.setInt(1, device.getEmpID());
				pst.execute();
			} else {
				pst = conn.prepareStatement(Messages.getString("DeviceAdd.3") + Messages.getString("DeviceAdd.4")); //$NON-NLS-1$ //$NON-NLS-2$
				pst.setString(1, device.getDeviceID());
				pst.setInt(2, device.getEmpID());
				pst.setString(3, device.getPlatform());
				pst.execute();
			}
		} finally {
			if (pst != null) {
				pst.close();
			}

			conn.close();
		}
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
