package com.AITAM.demo.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class ReCaptcha {
	public static String verify(String res) throws ServletException, IOException, JSONException {
		URL url = new URL(Messages.getString("ReCaptcha.0") //$NON-NLS-1$
				+ res);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestMethod(Messages.getString("ReCaptcha.1")); //$NON-NLS-1$

		String line, outputString = Messages.getString("ReCaptcha.2"); //$NON-NLS-1$
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		while ((line = reader.readLine()) != null) {
			outputString += line;
		}
		JSONObject obj = new JSONObject(outputString);
		Boolean res1 = (Boolean) obj.get(Messages.getString("ReCaptcha.3")); //$NON-NLS-1$
		String res2 = String.valueOf(res1);

		return res2;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
