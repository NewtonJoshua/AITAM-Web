package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.AITAM.demo.bean.EmpBean;

public class GetMembers {
	public List<EmpBean> getAllMembers() throws ClassNotFoundException, SQLException {
		List<EmpBean> l = new ArrayList<EmpBean>();
		Connection conn = Connect.connect();
		ResultSet rs1 = null;
		Statement st1 = null;

		try {
			st1 = conn.createStatement();
			rs1 = st1.executeQuery(Messages.getString("GetMembers.0")); //$NON-NLS-1$

			while (rs1.next()) {
				EmpBean e = new EmpBean();

				e.setID(rs1.getInt(1));
				e.setName(rs1.getString(2));
				e.setSupervisor(rs1.getInt(3));
				System.out.println(e.getSupervisor());
				l.add(e);
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

		return l;
	}

	public List<EmpBean> getImmMembers(EmpBean emp) throws ClassNotFoundException, SQLException {
		List<EmpBean> l = new ArrayList<EmpBean>();
		Connection conn = Connect.connect();
		ResultSet rs1 = null;
		Statement st1 = null;

		try {
			st1 = conn.createStatement();
			rs1 = st1.executeQuery(Messages.getString("GetMembers.1") + emp.getID()); //$NON-NLS-1$

			while ((rs1 != null) && rs1.next()) {
				EmpBean e = new EmpBean();

				e.setID(rs1.getInt(1));
				e.setName(rs1.getString(2));
				l.add(e);
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

		return l;
	}

	public List<EmpBean> getMembers(EmpBean emp) throws ClassNotFoundException, SQLException {
		List<EmpBean> l = new ArrayList<EmpBean>();
		Connection conn = Connect.connect();
		ResultSet rs1 = null;
		Statement st1 = null;
		Statement st = null;
		ResultSet rs = null;
		Statement st3 = null;
		ResultSet rs3 = null;

		try {
			st1 = conn.createStatement();
			rs1 = st1.executeQuery(Messages.getString("GetMembers.2") + emp.getID()); //$NON-NLS-1$

			while ((rs1 != null) && rs1.next()) {
				EmpBean e = new EmpBean();

				e.setID(rs1.getInt(1));
				e.setName(rs1.getString(2));
				e.setManager(emp.getID());
				e.setSup_Name(emp.getName());
				e.setPhone((int) rs1.getLong(3));
				l.add(e);
				st = conn.createStatement();
				rs = st.executeQuery(Messages.getString("GetMembers.3") + rs1.getInt(1)); //$NON-NLS-1$

				while ((rs != null) && rs.next()) {
					EmpBean e1 = new EmpBean();

					e1.setID(rs.getInt(1));
					e1.setName(rs.getString(2));
					e1.setManager(rs1.getInt(1));
					e1.setSup_Name(rs1.getString(2));
					e1.setPhone((int) rs.getLong(3));
					l.add(e1);
					st3 = conn.createStatement();
					rs3 = st3.executeQuery(Messages.getString("GetMembers.4") + rs.getInt(1)); //$NON-NLS-1$

					while ((rs3 != null) && rs3.next()) {
						EmpBean e3 = new EmpBean();

						e3.setID(rs3.getInt(1));
						e3.setName(rs3.getString(2));
						e3.setManager(rs.getInt(1));
						e3.setSup_Name(rs.getString(2));
						e3.setPhone((int) rs3.getLong(3));
						l.add(e3);
					}
				}
			}
		} finally {
			if (rs1 != null) {
				rs1.close();
			}

			if (st1 != null) {
				st1.close();
			}

			if (st != null) {
				st.close();
			}

			if (rs != null) {
				rs.close();
			}

			if (st3 != null) {
				st3.close();
			}

			if (rs3 != null) {
				rs3.close();
			}

			conn.close();
		}

		return l;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
