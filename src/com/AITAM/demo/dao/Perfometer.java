package com.AITAM.demo.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.AITAM.demo.bean.EmpBean;

public class Perfometer {
	public String genMyDayChart(java.sql.Date sqlDate, EmpBean emp, Connection conn)
			throws ClassNotFoundException, SQLException {
		Statement st = null;
		ResultSet rs = null;
		Statement st1 = null;
		ResultSet rs1 = null;
		Statement st2 = null;
		ResultSet rs2 = null;
		String str = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(Messages.getString("Perfometer.0") //$NON-NLS-1$
					+ sqlDate + Messages.getString("Perfometer.1") + sqlDate + Messages.getString("Perfometer.2") //$NON-NLS-1$ //$NON-NLS-2$
					+ emp.getID());
			st1 = conn.createStatement();
			rs1 = st1.executeQuery(Messages.getString("Perfometer.3") //$NON-NLS-1$
					+ emp.getID() + Messages.getString("Perfometer.4")); //$NON-NLS-1$

			int timeComp = 0;
			int review = 0;
			int rating = 0;
			int contri = 0;
			int count = 0;

			while (rs.next()) {
				timeComp = timeComp + rs.getInt(2);
				review = review + rs.getInt(3);
				rating = rating + rs.getInt(4);
				count = count + 1;
			}

			if (count != 0) {
				timeComp = timeComp / count;
				review = review / count;
				rating = rating / count;
			}

			DateFormat df = new SimpleDateFormat(Messages.getString("Perfometer.5")); //$NON-NLS-1$
			String text = df.format(sqlDate);

			// Contribution
			int max = 0;

			while (rs1.next()) {
				int id = rs1.getInt(1);

				st2 = conn.createStatement();
				rs2 = st2.executeQuery(Messages.getString("Perfometer.6") + sqlDate //$NON-NLS-1$
						+ Messages.getString("Perfometer.7") + sqlDate + Messages.getString("Perfometer.8") + id); //$NON-NLS-1$ //$NON-NLS-2$

				while (rs2.next()) {
					int c = rs2.getInt(1);

					if (c > max) {
						max = c;
					}
				}
			}

			if (max != 0) {
				contri = (count / max) * 5;
			}

			float consol = (float) (timeComp + review + contri + (2 * rating)) / 5;

			str = (text + Messages.getString("Perfometer.9") + rating + Messages.getString("Perfometer.10") + timeComp //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Perfometer.11") + review + Messages.getString("Perfometer.12") + contri //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Perfometer.13") + consol); //$NON-NLS-1$
		} finally {
			if (st != null) {
				st.close();
			}

			if (rs != null) {
				rs.close();
			}

			if (st1 != null) {
				st1.close();
			}

			if (rs1 != null) {
				rs1.close();
			}

			if (st2 != null) {
				st2.close();
			}

			if (rs2 != null) {
				rs2.close();
			}
		}

		return str;
	}

	// Get Date between two dates
	private List<java.sql.Date> getDateBetween(String from, String to) throws ParseException {
		List<java.sql.Date> d = new ArrayList<java.sql.Date>();
		SimpleDateFormat format = new SimpleDateFormat(Messages.getString("Perfometer.14")); //$NON-NLS-1$
		Date parsed1 = format.parse(from);
		java.sql.Date FromDate = new java.sql.Date(parsed1.getTime());
		Date parsed2 = format.parse(to);
		java.sql.Date ToDate = new java.sql.Date(parsed2.getTime());
		java.util.GregorianCalendar cal = new java.util.GregorianCalendar();

		cal.setTime(FromDate);

		java.util.GregorianCalendar calTo = new java.util.GregorianCalendar();

		calTo.setTime(ToDate);
		calTo.add(Calendar.DATE, 1);

		while (!cal.equals(calTo)) {
			if ((cal.get(java.util.Calendar.DAY_OF_WEEK) != 1) && (cal.get(java.util.Calendar.DAY_OF_WEEK) != 7)) {
				java.sql.Date sqlDate = new java.sql.Date(cal.getTimeInMillis());

				d.add(sqlDate);
			}

			cal.add(Calendar.DATE, 1);
		}

		return d;
	}

	/**
	 * public String ourPerfometer(String from,String to, EmpBean emp) throws
	 * ClassNotFoundException, SQLException, ParseException{ String str=null;
	 *
	 * //Prim Chart String prim="Prim,Employee Perfometer"; String
	 * tool="tool,Date"; //Each Emp GetMembers get= new GetMembers();
	 * List<EmpBean> l= get.getMembers(emp); for (EmpBean e:l){ tool=tool+"
	 * "+e.getName().replaceAll("\\s+",""); //Each date List<java.sql.Date>
	 * d=getDateBetween(from,to); float rating=0; for (java.sql.Date sd:d){
	 * rating=rating+getRating(e,sd); } rating=rating/d.size();
	 * prim=prim+","+e.getName().replaceAll("\\s+","")+" "+rating; }
	 *
	 * //ToolTip chart //Each date List<java.sql.Date>
	 * d=getDateBetween(from,to); for (java.sql.Date sd:d){ DateFormat df = new
	 * SimpleDateFormat("MM/dd/yyyy"); String text = df.format(sd);
	 * tool=tool+","+text; System.out.println(text); //Each Emp for (EmpBean
	 * e:l){ float rating=getRating(e,sd); tool=tool+" "+rating; } }
	 * str=prim+";"+tool; return str; }
	 * 
	 * @throws ParseException
	 *             *
	 */
	// Get consolidate rating between two dates
	private String getRating(String from, String to, EmpBean emp, Connection conn)
			throws ClassNotFoundException, SQLException, ParseException {
		List<java.sql.Date> d = getDateBetween(from, to);
		int timeCompAvg = 0;
		int reviewAvg = 0;
		int ratingAvg = 0;
		int contriAvg = 0;
		float consolAvg = 0;
		int totalCount = 0;

		for (java.sql.Date sqlDate : d) {
			Statement st = null;
			ResultSet rs = null;
			Statement st1 = null;
			ResultSet rs1 = null;

			try {
				st = conn.createStatement();
				rs = st.executeQuery(Messages.getString("Perfometer.15") //$NON-NLS-1$
						+ sqlDate + Messages.getString("Perfometer.16") + sqlDate + Messages.getString("Perfometer.17") //$NON-NLS-1$ //$NON-NLS-2$
						+ emp.getID());
				st1 = conn.createStatement();
				rs1 = st1.executeQuery(Messages.getString("Perfometer.18") //$NON-NLS-1$
						+ emp.getID() + Messages.getString("Perfometer.19")); //$NON-NLS-1$

				int timeComp = 0;
				int review = 0;
				int rating = 0;
				int contri = 0;
				int count = 0;

				while (rs.next()) {
					timeComp = timeComp + rs.getInt(2);
					review = review + rs.getInt(3);
					rating = rating + rs.getInt(4);
					count = count + 1;
				}

				if (count != 0) {
					timeComp = timeComp / count;
					review = review / count;
					rating = rating / count;
				}

				// Contribution
				int max = 0;

				while (rs1.next()) {
					int id = rs1.getInt(1);
					Statement st2 = null;
					ResultSet rs2 = null;

					try {
						st2 = conn.createStatement();
						rs2 = st2.executeQuery(Messages.getString("Perfometer.20") + sqlDate //$NON-NLS-1$
								+ Messages.getString("Perfometer.21") + sqlDate + Messages.getString("Perfometer.22") //$NON-NLS-1$ //$NON-NLS-2$
								+ id);

						while (rs2.next()) {
							int c = rs2.getInt(1);

							if (c > max) {
								max = c;
							}
						}
					} finally {
						if (st2 != null) {
							st2.close();
						}

						if (rs2 != null) {
							rs2.close();
						}
					}
				}

				if (max != 0) {
					contri = (count / max) * 5;
				}

				float consol = (float) (timeComp + review + contri + (2 * rating)) / 5;

				timeCompAvg = timeCompAvg + timeComp;
				reviewAvg = reviewAvg + review;
				ratingAvg = ratingAvg + rating;
				contriAvg = contriAvg + contri;
				consolAvg = consolAvg + consol;
				totalCount = totalCount + 1;
			} finally {
				if (st != null) {
					st.close();
				}

				if (rs != null) {
					rs.close();
				}

				if (st1 != null) {
					st1.close();
				}

				if (rs1 != null) {
					rs1.close();
				}
			}
		}

		timeCompAvg = timeCompAvg / totalCount;
		reviewAvg = reviewAvg / totalCount;
		ratingAvg = ratingAvg / totalCount;
		contriAvg = contriAvg / totalCount;
		consolAvg = consolAvg / totalCount;

		String str = (ratingAvg + Messages.getString("Perfometer.23") + timeCompAvg //$NON-NLS-1$
				+ Messages.getString("Perfometer.24") + reviewAvg + Messages.getString("Perfometer.25") + contriAvg //$NON-NLS-1$ //$NON-NLS-2$
				+ Messages.getString("Perfometer.26") + consolAvg); //$NON-NLS-1$

		return str;
	}

	public String myChart(String from, String to, EmpBean emp)
			throws ClassNotFoundException, ParseException, SQLException {
		Connection conn = null;
		String str = null;

		try {
			conn = Connect.connect();
			str = myDayChart(from, to, emp, conn);
		} finally {
			conn.close();
		}

		return str;
	}

	public String myDayChart(String from, String to, EmpBean emp, Connection conn)
			throws ParseException, ClassNotFoundException, SQLException {
		SimpleDateFormat format = new SimpleDateFormat(Messages.getString("Perfometer.27")); //$NON-NLS-1$
		Date parsed1 = format.parse(from);
		java.sql.Date FromDate = new java.sql.Date(parsed1.getTime());
		Date parsed2 = format.parse(to);
		java.sql.Date ToDate = new java.sql.Date(parsed2.getTime());
		java.util.GregorianCalendar cal = new java.util.GregorianCalendar();

		cal.setTime(FromDate);

		java.util.GregorianCalendar calTo = new java.util.GregorianCalendar();

		calTo.setTime(ToDate);

		String str = Messages.getString("Perfometer.28"); //$NON-NLS-1$

		calTo.add(Calendar.DATE, 1);

		int noOfDays = 0;

		while (!cal.equals(calTo)) {
			if ((cal.get(java.util.Calendar.DAY_OF_WEEK) != 1) && (cal.get(java.util.Calendar.DAY_OF_WEEK) != 7)) {
				java.sql.Date sqlDate = new java.sql.Date(cal.getTimeInMillis());
				String s = genMyDayChart(sqlDate, emp, conn);

				str = str + Messages.getString("Perfometer.29") + s; //$NON-NLS-1$
				noOfDays = noOfDays + 1;
			}

			cal.add(Calendar.DATE, 1);
		}

		return str;
	}

	// Tool Tip Chart. Not in use.

	public String teamPerfometer(String from, String to, EmpBean emp)
			throws ClassNotFoundException, SQLException, ParseException, IOException {
		Connection conn = Connect.connect();
		String str = null;

		try {
			GetMembers get = new GetMembers();
			List<EmpBean> l = get.getMembers(emp);
			int len = l.size() * 2;

			System.out.println(len);

			String EmpRat = Messages.getString("Perfometer.30"); //$NON-NLS-1$

			for (EmpBean e : l) {
				EmpRat = EmpRat + Messages.getString("Perfometer.31") + e.getName() //$NON-NLS-1$
						+ Messages.getString("Perfometer.32") + getRating(from, to, e, conn); //$NON-NLS-1$
			}

			str = EmpRat;

			for (EmpBean e : l) {
				str = str + Messages.getString("Perfometer.33") + e.getID() + Messages.getString("Perfometer.34") //$NON-NLS-1$ //$NON-NLS-2$
						+ e.getName() + Messages.getString("Perfometer.35") + myDayChart(from, to, e, conn); //$NON-NLS-1$
			}
		} finally {
			conn.close();
		}

		return str;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
