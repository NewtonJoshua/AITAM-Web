package com.AITAM.demo.dao;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.AITAM.demo.bean.DeviceBean;

public class DeviceLogin {
	@SuppressWarnings("resource")
	public String deviceLogin(DeviceBean device) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException {
		String name = Messages.getString("DeviceLogin.0"); //$NON-NLS-1$
		Connection conn = Connect.connect();
		Statement st = null;
		Statement st1 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String isMan = Messages.getString("DeviceLogin.1"); //$NON-NLS-1$
		int id = 0;

		try {
			st = conn.createStatement();
			st1 = conn.createStatement();
			rs = st.executeQuery(
					Messages.getString("DeviceLogin.2") + device.getDeviceID() + Messages.getString("DeviceLogin.3")); //$NON-NLS-1$ //$NON-NLS-2$

			while (rs.next()) {
				id = rs.getInt(1);

				// if(rs.getInt(1)== device.getEmpID()){
				rs1 = st1.executeQuery(Messages.getString("DeviceLogin.4") + rs.getInt(1)); //$NON-NLS-1$

				while (rs1.next()) {
					name = rs1.getString(1);
				}

				rs1 = st1.executeQuery(Messages.getString("DeviceLogin.5") + device.getEmpID()); //$NON-NLS-1$

				while (rs1.next()) {
					isMan = Messages.getString("DeviceLogin.6"); //$NON-NLS-1$
				}

				// }
			}
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (rs1 != null) {
				rs1.close();
			}

			if (st != null) {
				st.close();
			}

			if (st1 != null) {
				st1.close();
			}

			conn.close();
		}

		return name + Messages.getString("DeviceLogin.7") + isMan + Messages.getString("DeviceLogin.8") + id; //$NON-NLS-1$ //$NON-NLS-2$
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
