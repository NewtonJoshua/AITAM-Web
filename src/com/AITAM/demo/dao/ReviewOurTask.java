package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.AITAM.demo.bean.EmpBean;
import com.AITAM.demo.bean.TaskBean;

public class ReviewOurTask {
	@SuppressWarnings("resource")
	public List<TaskBean> ReView(EmpBean emp) throws ClassNotFoundException, SQLException {
		List<TaskBean> l = new ArrayList<TaskBean>();
		Connection conn = Connect.connect();
		Statement st1 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Statement st2 = null;
		Statement st5 = null;
		ResultSet rs5 = null;
		Statement st3 = null;
		ResultSet rs3 = null;

		try {
			st1 = conn.createStatement();

			// Appeal + Review
			rs1 = st1.executeQuery(Messages.getString("ReviewOurTask.0") + emp.getID() //$NON-NLS-1$
					+ Messages.getString("ReviewOurTask.1") //$NON-NLS-1$
					+ emp.getID() + Messages.getString("ReviewOurTask.2") + Messages.getString("ReviewOurTask.3")); //$NON-NLS-1$ //$NON-NLS-2$

			while ((rs1 != null) && rs1.next()) {
				int id = rs1.getInt(1);
				TaskBean task = new TaskBean();

				task.setTaskId(id);
				task.setTitle(rs1.getString(2));
				task.setDesc(rs1.getString(7));

				Date date = rs1.getDate(3);
				DateFormat df = new SimpleDateFormat(Messages.getString("ReviewOurTask.4")); //$NON-NLS-1$
				String dateN = df.format(date);

				task.setDueDate(dateN);
				task.setPriority(rs1.getString(4));
				task.setReviewer(rs1.getInt(5));
				task.setStatus(rs1.getString(6));

				Date date1 = rs1.getDate(10);
				DateFormat df1 = new SimpleDateFormat(Messages.getString("ReviewOurTask.5")); //$NON-NLS-1$
				String dateN1 = df1.format(date1);

				task.setCreatedDate(dateN1);
				task.setAssigned(rs1.getInt(11));
				st2 = conn.createStatement();
				rs2 = st2.executeQuery(Messages.getString("ReviewOurTask.6") + id); //$NON-NLS-1$

				while ((rs2 != null) && rs2.next()) {
					task.setMainTitle(rs2.getString(2));
					task.setMainDesc(rs2.getString(4));

					Date date2 = rs2.getDate(3);
					DateFormat df2 = new SimpleDateFormat(Messages.getString("ReviewOurTask.7")); //$NON-NLS-1$
					String dateN2 = df2.format(date2);

					task.setMainDate(dateN2);
				}

				st5 = conn.createStatement();
				rs5 = st5.executeQuery(Messages.getString("ReviewOurTask.8") + (rs1.getInt(11))); //$NON-NLS-1$

				while (rs5.next()) {
					task.setAssigneeName(rs5.getString(1));
				}

				if (rs1.getString(6).equalsIgnoreCase(Messages.getString("ReviewOurTask.9"))) { //$NON-NLS-1$
					st3 = conn.createStatement();
					rs3 = st3.executeQuery(
							Messages.getString("ReviewOurTask.10") + id + Messages.getString("ReviewOurTask.11")); //$NON-NLS-1$ //$NON-NLS-2$

					if (rs3.next()) {
						Date date4 = rs3.getDate(1);
						DateFormat df4 = new SimpleDateFormat(Messages.getString("ReviewOurTask.12")); //$NON-NLS-1$
						String dateN4 = df4.format(date4);

						task.setAppealedDate(dateN4);
					}
				}

				if (rs1.getString(6).equalsIgnoreCase(Messages.getString("ReviewOurTask.13"))) { //$NON-NLS-1$
					st3 = conn.createStatement();
					rs3 = st3.executeQuery(
							Messages.getString("ReviewOurTask.14") + id + Messages.getString("ReviewOurTask.15")); //$NON-NLS-1$ //$NON-NLS-2$

					if (rs3.next()) {
						Date date4 = rs3.getDate(1);
						DateFormat df4 = new SimpleDateFormat(Messages.getString("ReviewOurTask.16")); //$NON-NLS-1$
						String dateN4 = df4.format(date4);

						task.setAppealedDate(dateN4);
					}
				}

				l.add(task);
			}

			// Approve
			GetMembers get = new GetMembers();
			List<EmpBean> l1 = get.getImmMembers(emp);

			for (EmpBean emp1 : l1) {
				rs1 = st1.executeQuery(
						Messages.getString("ReviewOurTask.17") + emp1.getID() + Messages.getString("ReviewOurTask.18")); //$NON-NLS-1$ //$NON-NLS-2$

				while (rs1.next()) {
					int id = rs1.getInt(1);
					TaskBean task = new TaskBean();

					task.setTaskId(id);
					task.setTitle(rs1.getString(2));
					task.setDesc(rs1.getString(7));

					Date date = rs1.getDate(3);
					DateFormat df = new SimpleDateFormat(Messages.getString("ReviewOurTask.19")); //$NON-NLS-1$
					String dateN = df.format(date);

					task.setDueDate(dateN);
					task.setPriority(rs1.getString(4));
					task.setReviewer(rs1.getInt(5));
					task.setStatus(rs1.getString(6));

					Date date1 = rs1.getDate(10);
					DateFormat df1 = new SimpleDateFormat(Messages.getString("ReviewOurTask.20")); //$NON-NLS-1$
					String dateN1 = df1.format(date1);

					task.setCreatedDate(dateN1);
					task.setAssigned(rs1.getInt(11));
					st2 = conn.createStatement();
					rs2 = null;
					rs2 = st2.executeQuery(Messages.getString("ReviewOurTask.21") + id); //$NON-NLS-1$

					while ((rs2 != null) && rs2.next()) {
						task.setMainTitle(rs2.getString(2));
						task.setMainDesc(rs2.getString(4));

						Date date2 = rs2.getDate(3);
						DateFormat df2 = new SimpleDateFormat(Messages.getString("ReviewOurTask.22")); //$NON-NLS-1$
						String dateN2 = df2.format(date2);

						task.setMainDate(dateN2);
					}

					Statement st6 = conn.createStatement();
					ResultSet rs6 = st6.executeQuery(Messages.getString("ReviewOurTask.23") + (rs1.getInt(11))); //$NON-NLS-1$

					while (rs6.next()) {
						task.setAssigneeName(rs6.getString(1));
					}

					l.add(task);
				}
			}
		} finally {
			if (st1 != null) {
				st1.close();
			}

			if (st2 != null) {
				st2.close();
			}

			if (st5 != null) {
				st5.close();
			}

			if (rs5 != null) {
				rs5.close();
			}

			if (st3 != null) {
				st3.close();
			}

			if (rs3 != null) {
				rs3.close();
			}

			if (rs1 != null) {
				rs1.close();
			}

			if (rs2 != null) {
				rs2.close();
			}

			conn.close();
		}

		return l;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
