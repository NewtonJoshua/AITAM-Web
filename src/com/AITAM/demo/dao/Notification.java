package com.AITAM.demo.dao;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.AITAM.demo.bean.NotificationBean;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class Notification {
	private final static Logger LOGGER = Logger.getLogger(Messages.getString("Notification.0")); //$NON-NLS-1$

	public static void pushNotification(NotificationBean n) throws ClassNotFoundException, SQLException, IOException {
		String msg = null;
		String From = null;
		String Task = null;
		Connection conn = Connect.connect();
		ResultSet rs = null;
		Statement st = null;

		// Notification Message
		st = conn.createStatement();
		rs = st.executeQuery(Messages.getString("Notification.1") + n.getFrom()); //$NON-NLS-1$

		while (rs.next()) {
			From = rs.getString(1);
		}

		rs = st.executeQuery(Messages.getString("Notification.2") + n.getTask()); //$NON-NLS-1$

		while (rs.next()) {
			Task = rs.getString(1);
		}

		switch (n.getId()) {
		case 1:
			msg = Messages.getString("Notification.3") + From + Messages.getString("Notification.4") + Task //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Notification.5"); //$NON-NLS-1$

			break;

		case 2:
			msg = Messages.getString("Notification.6") + From + Messages.getString("Notification.7") + Task //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Notification.8"); //$NON-NLS-1$

			break;

		case 3:
			msg = Messages.getString("Notification.9") + From + Messages.getString("Notification.10") + Task //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Notification.11"); //$NON-NLS-1$

			break;

		case 4:
			msg = Messages.getString("Notification.12") + From + Messages.getString("Notification.13") + Task //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Notification.14"); //$NON-NLS-1$

			break;

		case 5:
			msg = Messages.getString("Notification.15") + From + Messages.getString("Notification.16") + Task //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Notification.17"); //$NON-NLS-1$

			break;

		case 6:
			msg = Messages.getString("Notification.18") + From + Messages.getString("Notification.19") + Task //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Notification.20"); //$NON-NLS-1$

			break;

		case 7:
			msg = Messages.getString("Notification.21") + From + Messages.getString("Notification.22") + Task //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Notification.23"); //$NON-NLS-1$

			break;

		case 8:
			msg = Messages.getString("Notification.24") + From + Messages.getString("Notification.25") + Task //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Notification.26"); //$NON-NLS-1$

			break;

		case 9:
			msg = Messages.getString("Notification.27") + From + Messages.getString("Notification.28") + Task //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Notification.29"); //$NON-NLS-1$

			break;

		case 10:
			msg = Messages.getString("Notification.30") + From + Messages.getString("Notification.31") + Task //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("Notification.32"); //$NON-NLS-1$

			break;
		}

		try {

			// get Tokens
			int count = 0;

			rs = st.executeQuery(Messages.getString("Notification.33") + n.getTo()); //$NON-NLS-1$

			while (rs.next()) {
				count = rs.getInt(1);
			}

			String[] tokens = new String[count];
			String[] tokens1 = new String[count];
			int countGCM = 0;
			int countIonic = 0;

			rs = st.executeQuery(Messages.getString("Notification.34") + n.getTo()); //$NON-NLS-1$

			while (rs.next()) {
				if (rs.getString(1) != null) {
					if (rs.getString(1).toLowerCase().contains(Messages.getString("Notification.35"))) { //$NON-NLS-1$
						tokens1[countIonic] = rs.getString(1);
						countIonic++;
					} else {
						tokens[countGCM] = rs.getString(1);
						countGCM++;
					}
				}
			}

			String[] tokensGCM = new String[countGCM];

			System.arraycopy(tokens, 0, tokensGCM, 0, countGCM);

			String[] tokensIonic = new String[countIonic];

			System.arraycopy(tokens1, 0, tokensIonic, 0, countIonic);

			// GCM
			// create JSON
			JSONObject msg1 = new JSONObject();

			msg1.put(Messages.getString("Notification.36"), msg); //$NON-NLS-1$

			JSONObject json = new JSONObject();

			json.put(Messages.getString("Notification.37"), tokensGCM); //$NON-NLS-1$
			json.put(Messages.getString("Notification.38"), msg1); //$NON-NLS-1$

			String data = json.toString();

			// Push
			URL obj = new URL(Messages.getString("Notification.39")); //$NON-NLS-1$
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setRequestMethod(Messages.getString("Notification.40")); //$NON-NLS-1$
			con.setRequestProperty(Messages.getString("Notification.41"), Messages.getString("Notification.42")); //$NON-NLS-1$ //$NON-NLS-2$
			con.setRequestProperty(Messages.getString("Notification.43"), Messages.getString("Notification.44")); //$NON-NLS-1$ //$NON-NLS-2$
			con.setDoOutput(true);

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());

			wr.writeBytes(data);
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();
			LOGGER.log(Level.SEVERE, Messages.getString("Notification.45") + response.toString() //$NON-NLS-1$
					+ Messages.getString("Notification.46") + data); //$NON-NLS-1$

			// Ionic
			// json
			JSONObject msg2 = new JSONObject();

			msg2.put(Messages.getString("Notification.47"), msg); //$NON-NLS-1$

			JSONObject json2 = new JSONObject();

			json2.put(Messages.getString("Notification.48"), tokensIonic); //$NON-NLS-1$
			json2.put(Messages.getString("Notification.49"), msg2); //$NON-NLS-1$

			String dataIonic = json2.toString();

			// Push
			URL objIonic = new URL(Messages.getString("Notification.50")); //$NON-NLS-1$
			HttpURLConnection conIonic = (HttpURLConnection) objIonic.openConnection();

			conIonic.setRequestMethod(Messages.getString("Notification.51")); //$NON-NLS-1$
			conIonic.setRequestProperty(Messages.getString("Notification.52"), Messages.getString("Notification.53")); //$NON-NLS-1$ //$NON-NLS-2$
			conIonic.setRequestProperty(Messages.getString("Notification.54"), Messages.getString("Notification.55")); //$NON-NLS-1$ //$NON-NLS-2$
			conIonic.setRequestProperty(Messages.getString("Notification.56"), //$NON-NLS-1$
					Messages.getString("Notification.57")); //$NON-NLS-1$
			conIonic.setDoOutput(true);

			DataOutputStream wrIonic = new DataOutputStream(conIonic.getOutputStream());

			wrIonic.writeBytes(dataIonic);
			wrIonic.flush();
			wrIonic.close();

			BufferedReader inIonic = new BufferedReader(new InputStreamReader(conIonic.getInputStream()));
			StringBuffer responseIonic = new StringBuffer();

			while ((inputLine = inIonic.readLine()) != null) {
				responseIonic.append(inputLine);
			}

			inIonic.close();
			LOGGER.log(Level.SEVERE, Messages.getString("Notification.58") + responseIonic.toString() //$NON-NLS-1$
					+ Messages.getString("Notification.59") + dataIonic); //$NON-NLS-1$
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, Messages.getString("Notification.60") + e.toString() //$NON-NLS-1$
					+ Messages.getString("Notification.61") + e.getStackTrace().toString()); //$NON-NLS-1$
		} finally {
			if (st != null) {
				st.close();
			}

			if (rs != null) {
				rs.close();
			}

			conn.close();
		}
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
