package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteMember {
	public void deleteMember(int id) throws ClassNotFoundException, SQLException {
		Connection conn = Connect.connect();
		Statement st1 = null;
		Statement st2 = null;

		try {
			st1 = conn.createStatement();
			st2 = conn.createStatement();
			st2.executeUpdate(Messages.getString("DeleteMember.0") + id); //$NON-NLS-1$
			st1.executeUpdate(Messages.getString("DeleteMember.1") + id); //$NON-NLS-1$
		} finally {
			if (st1 != null) {
				st1.close();
			}

			if (st2 != null) {
				st2.close();
			}

			conn.close();
		}
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
