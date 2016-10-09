package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Team {
	public String getTeam() throws ClassNotFoundException, SQLException {
		String str = Messages.getString("Team.0"); //$NON-NLS-1$
		Connection conn = Connect.connect();
		ResultSet rs1 = null;
		Statement st1 = null;

		try {
			st1 = conn.createStatement();
			rs1 = st1.executeQuery(Messages.getString("Team.1")); //$NON-NLS-1$

			while (rs1.next()) {
				str = str + Messages.getString("Team.2") + rs1.getInt(1) + Messages.getString("Team.3") //$NON-NLS-1$ //$NON-NLS-2$
						+ rs1.getString(2) + Messages.getString("Team.4") + rs1.getLong(3) //$NON-NLS-1$
						+ Messages.getString("Team.5") + rs1.getInt(4); //$NON-NLS-1$
			}
		} finally {
			if (rs1 != null) {
				rs1.close();
			}

			if (st1 != null) {
				st1.close();
			}

			conn.close();
		}

		System.out.println(str);

		return str;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
