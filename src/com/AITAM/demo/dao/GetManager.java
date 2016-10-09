package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetManager {
	public String getManager(int emp) throws ClassNotFoundException, SQLException {
		Connection conn = Connect.connect();
		ResultSet rs1 = null;
		Statement st1 = null;
		ResultSet rs = null;
		Statement st = null;
		String str = null;

		try {
			st1 = conn.createStatement();
			rs1 = st1.executeQuery(Messages.getString("GetManager.0") + emp); //$NON-NLS-1$

			while ((rs1 != null) && rs1.next()) {
				st = conn.createStatement();
				rs = st.executeQuery(Messages.getString("GetManager.1") + rs1.getInt(1)); //$NON-NLS-1$

				while (rs.next()) {
					str = rs.getString(1) + Messages.getString("GetManager.2") + rs1.getInt(1) //$NON-NLS-1$
							+ Messages.getString("GetManager.3"); //$NON-NLS-1$
				}
			}
		} finally {
			if (rs1 != null) {
				rs1.close();
			}

			if (st1 != null) {
				st1.close();
			}

			if (rs != null) {
				rs.close();
			}

			if (st != null) {
				st.close();
			}

			conn.close();
		}

		return str;
	}

	public int getManagerID(int emp) throws ClassNotFoundException, SQLException {
		Connection conn = Connect.connect();
		ResultSet rs1 = null;
		Statement st1 = null;
		ResultSet rs = null;
		Statement st = null;
		int id = 0;

		try {
			st1 = conn.createStatement();
			rs1 = st1.executeQuery(Messages.getString("GetManager.4") + emp); //$NON-NLS-1$

			while ((rs1 != null) && rs1.next()) {
				id = rs1.getInt(1);
			}
		} finally {
			if (rs1 != null) {
				rs1.close();
			}

			if (st1 != null) {
				st1.close();
			}

			if (rs != null) {
				rs.close();
			}

			if (st != null) {
				st.close();
			}

			conn.close();
		}

		return id;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
