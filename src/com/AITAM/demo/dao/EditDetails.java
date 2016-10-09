package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.AITAM.demo.bean.EmpBean;

public class EditDetails {
	public void editMail(EmpBean emp) throws ClassNotFoundException, SQLException {
		Connection conn = Connect.connect();
		Statement st1 = null;
		ResultSet rs1 = null;

		try {
			st1 = conn.createStatement();
			System.out.println(Messages.getString("EditDetails.0") + emp.getMail() + Messages.getString("EditDetails.1") //$NON-NLS-1$ //$NON-NLS-2$
					+ emp.getID());
			st1.executeUpdate(Messages.getString("EditDetails.2") + emp.getMail() + Messages.getString("EditDetails.3") //$NON-NLS-1$ //$NON-NLS-2$
					+ emp.getID());
		} finally {
			if (st1 != null) {
				st1.close();
			}

			if (rs1 != null) {
				rs1.close();
			}

			conn.close();
		}
	}

	public void editPhone(EmpBean emp) throws ClassNotFoundException, SQLException {
		Connection conn = Connect.connect();
		Statement st1 = null;
		ResultSet rs1 = null;

		try {
			st1 = conn.createStatement();
			System.out.println(Messages.getString("EditDetails.4") + emp.getPhone() //$NON-NLS-1$
					+ Messages.getString("EditDetails.5") + emp.getID()); //$NON-NLS-1$
			st1.executeUpdate(Messages.getString("EditDetails.6") + emp.getPhone() + Messages.getString("EditDetails.7") //$NON-NLS-1$ //$NON-NLS-2$
					+ emp.getID());
		} finally {
			if (st1 != null) {
				st1.close();
			}

			if (rs1 != null) {
				rs1.close();
			}

			conn.close();
		}
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
