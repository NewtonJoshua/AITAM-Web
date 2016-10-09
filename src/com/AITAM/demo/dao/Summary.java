package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.AITAM.demo.bean.EmpBean;

public class Summary {
	public String count(EmpBean emp) throws ClassNotFoundException, SQLException {
		Connection conn = Connect.connect();
		Statement st = null;
		ResultSet rs = null;
		String str = null;
		int newTask = 0;
		int Accepted = 0;
		int Appeal = 0;
		int Progress = 0;
		int Review = 0;
		int Approve = 0;
		int ApproveAcp = 0;
		int ApproveDec = 0;
		int AppealAcp = 0;
		int AppealDec = 0;
		int Rework = 0;
		int Completed = 0;
		int open = 0;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(Messages.getString("Summary.0") + emp.getID()); //$NON-NLS-1$

			while (rs.next()) {
				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.1"))) { //$NON-NLS-1$
					newTask++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.2"))) { //$NON-NLS-1$
					Accepted++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.3"))) { //$NON-NLS-1$
					Appeal++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.4"))) { //$NON-NLS-1$
					Progress++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.5"))) { //$NON-NLS-1$
					Review++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.6"))) { //$NON-NLS-1$
					Approve++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.7"))) { //$NON-NLS-1$
					ApproveAcp++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.8"))) { //$NON-NLS-1$
					ApproveDec++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.9"))) { //$NON-NLS-1$
					AppealAcp++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.10"))) { //$NON-NLS-1$
					AppealDec++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.11"))) { //$NON-NLS-1$
					Rework++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.12"))) { //$NON-NLS-1$
					Completed++;
				}
			}

			open = Accepted + Appeal + Progress + Review + Approve + ApproveAcp + ApproveDec + AppealAcp + AppealDec
					+ Rework;
			str = newTask + Messages.getString("Summary.13") + Accepted + Messages.getString("Summary.14") + Progress //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Summary.15") + Review + Messages.getString("Summary.16") + Appeal //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Summary.17") + AppealAcp + Messages.getString("Summary.18") //$NON-NLS-1$ //$NON-NLS-2$
					+ AppealDec + Messages.getString("Summary.19") + Approve + Messages.getString("Summary.20") //$NON-NLS-1$ //$NON-NLS-2$
					+ ApproveAcp + Messages.getString("Summary.21") + ApproveDec + Messages.getString("Summary.22") //$NON-NLS-1$ //$NON-NLS-2$
					+ Rework + Messages.getString("Summary.23") + open + Messages.getString("Summary.24") //$NON-NLS-1$ //$NON-NLS-2$
					+ Completed;
		} finally {
			if (st != null) {
				st.close();
			}

			if (rs != null) {
				rs.close();
			}

			conn.close();
		}

		return str;
	}

	public String ourCount(EmpBean emp) throws ClassNotFoundException, SQLException {
		Connection conn = Connect.connect();
		Statement st = null;
		ResultSet rs = null;
		String str = null;
		int newTask = 0;
		int Accepted = 0;
		int Appeal = 0;
		int Progress = 0;
		int Review = 0;
		int Approve = 0;
		int ApproveAcp = 0;
		int ApproveDec = 0;
		int AppealAcp = 0;
		int AppealDec = 0;
		int Rework = 0;
		int Completed = 0;
		int open = 0;
		int totReview = 0;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(Messages.getString("Summary.25") + emp.getID()); //$NON-NLS-1$

			while (rs.next()) {
				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.26"))) { //$NON-NLS-1$
					newTask++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.27"))) { //$NON-NLS-1$
					Accepted++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.28"))) { //$NON-NLS-1$
					Appeal++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.29"))) { //$NON-NLS-1$
					Progress++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.30"))) { //$NON-NLS-1$
					Review++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.31"))) { //$NON-NLS-1$
					Approve++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.32"))) { //$NON-NLS-1$
					ApproveAcp++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.33"))) { //$NON-NLS-1$
					ApproveDec++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.34"))) { //$NON-NLS-1$
					AppealAcp++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.35"))) { //$NON-NLS-1$
					AppealDec++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.36"))) { //$NON-NLS-1$
					Rework++;
				}

				if (rs.getString(1).equalsIgnoreCase(Messages.getString("Summary.37"))) { //$NON-NLS-1$
					Completed++;
				}
			}

			totReview = Review + Approve + Appeal;
			open = Accepted + Progress + ApproveAcp + ApproveDec + AppealAcp + AppealDec + Rework + newTask;
			str = newTask + Messages.getString("Summary.38") + Accepted + Messages.getString("Summary.39") + Progress //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Summary.40") + Review + Messages.getString("Summary.41") + Appeal //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Summary.42") + AppealAcp + Messages.getString("Summary.43") //$NON-NLS-1$ //$NON-NLS-2$
					+ AppealDec + Messages.getString("Summary.44") + Approve + Messages.getString("Summary.45") //$NON-NLS-1$ //$NON-NLS-2$
					+ ApproveAcp + Messages.getString("Summary.46") + ApproveDec + Messages.getString("Summary.47") //$NON-NLS-1$ //$NON-NLS-2$
					+ Rework + Messages.getString("Summary.48") + open + Messages.getString("Summary.49") //$NON-NLS-1$ //$NON-NLS-2$
					+ Completed + Messages.getString("Summary.50") + totReview; //$NON-NLS-1$
		} finally {
			if (st != null) {
				st.close();
			}

			if (rs != null) {
				rs.close();
			}

			conn.close();
		}

		return str;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
