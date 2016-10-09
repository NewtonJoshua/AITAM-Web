package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Manager {
	public void assignManager(int mem, int man) throws ClassNotFoundException, SQLException {
		Connection conn = Connect.connect();
		ResultSet rs1 = null;
		Statement st1 = null;

		try {
			st1 = conn.createStatement();
			rs1 = st1.executeQuery(Messages.getString("Manager.0") + man + Messages.getString("Manager.1") + mem); //$NON-NLS-1$ //$NON-NLS-2$
		} finally {
			if (rs1 != null) {
				rs1.close();
			}

			if (st1 != null) {
				st1.close();
			}

			conn.close();
		}
	}

	public void ReassignManager(String mem, String man) {
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
