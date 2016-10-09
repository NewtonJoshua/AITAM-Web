package com.AITAM.demo.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {
	public String encrypt(String password) throws NoSuchAlgorithmException {
		String salt = Messages.getString("Encrypt.0"); //$NON-NLS-1$
		MessageDigest md = MessageDigest.getInstance(Messages.getString("Encrypt.1")); //$NON-NLS-1$

		md.update((password + salt).getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		// convert the byte to hex format method 2
		StringBuffer hexString = new StringBuffer();

		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);

			if (hex.length() == 1) {
				hexString.append('0');
			}

			hexString.append(hex);
		}

		return hexString.toString();
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
