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
import java.util.logging.Level;
import java.util.logging.Logger;

import com.AITAM.demo.bean.EmpBean;
import com.AITAM.demo.bean.TaskBean;

public class ViewMyNewTasks {
	private final Logger LOGGER = Logger.getLogger(Messages.getString("ViewMyNewTasks.0")); //$NON-NLS-1$

	public List<TaskBean> view(EmpBean emp) throws ClassNotFoundException, SQLException {
		LOGGER.log(Level.SEVERE, Messages.getString("ViewMyNewTasks.1")); //$NON-NLS-1$

		List<TaskBean> l = new ArrayList<TaskBean>();
		Connection conn = Connect.connect();
		ResultSet rs1 = null;
		Statement st1 = null;
		Statement st = null;
		ResultSet rs = null;
		Statement st5 = null;
		ResultSet rs5 = null;
		Statement st6 = null;
		ResultSet rs6 = null;

		try {
			st1 = conn.createStatement();
			rs1 = st1.executeQuery(Messages.getString("ViewMyNewTasks.2") + emp.getID() //$NON-NLS-1$
					+ Messages.getString("ViewMyNewTasks.3")); //$NON-NLS-1$

			while ((rs1 != null) && rs1.next()) {
				int taskId = rs1.getInt(1);

				st = conn.createStatement();
				rs = st.executeQuery(Messages.getString("ViewMyNewTasks.4") + taskId); //$NON-NLS-1$

				while (rs.next()) {
					TaskBean task = new TaskBean();

					task.setTaskId(rs.getInt(1));
					task.setTitle(rs.getString(2));

					Date date = rs.getDate(3);
					DateFormat df = new SimpleDateFormat(Messages.getString("ViewMyNewTasks.5")); //$NON-NLS-1$
					String dateN = df.format(date);

					task.setDueDate(dateN);
					task.setPriority(rs.getString(4));
					task.setReviewer(rs.getInt(5));
					task.setStatus(rs.getString(6));
					task.setDesc(rs.getString(7));
					task.setCommnets(rs.getString(8));
					task.setCreator(rs.getInt(9));

					Date date1 = rs.getDate(10);
					DateFormat df1 = new SimpleDateFormat(Messages.getString("ViewMyNewTasks.6")); //$NON-NLS-1$
					String dateN1 = df1.format(date1);

					task.setCreatedDate(dateN1);

					int Creator = (rs.getInt(9));

					if (rs.getInt(5) == 0) {
						task.setReviewerName(Messages.getString("ViewMyNewTasks.7")); //$NON-NLS-1$
						task.setAssigneeName(Messages.getString("ViewMyNewTasks.8")); //$NON-NLS-1$
					} else {
						st5 = conn.createStatement();
						rs5 = st5.executeQuery(Messages.getString("ViewMyNewTasks.9") + (rs.getInt(5))); //$NON-NLS-1$

						while (rs5.next()) {
							task.setReviewerName(rs5.getString(1));
						}

						st6 = conn.createStatement();
						rs6 = st6.executeQuery(Messages.getString("ViewMyNewTasks.10") + Creator); //$NON-NLS-1$

						while (rs6.next()) {
							task.setAssigneeName(rs6.getString(1));
						}
					}

					WorkingDays wd = new WorkingDays();
					int days = wd.days(rs.getDate(10), rs.getDate(3));

					task.setDays(days);
					l.add(task);
				}
			}
		} finally {
			if (rs1 != null) {
				rs1.close();
			}

			if (st != null) {
				st.close();
			}

			if (st1 != null) {
				st1.close();
			}

			if (rs != null) {
				rs.close();
			}

			if (st5 != null) {
				st5.close();
			}

			if (rs5 != null) {
				rs5.close();
			}

			if (st6 != null) {
				st6.close();
			}

			if (rs6 != null) {
				rs6.close();
			}

			conn.close();
		}

		return l;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
