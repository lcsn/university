package de.lsn.playground.zgame.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;

public class HashService {

	private static MessageDigest digest;
	private static byte[] SALT = { 0xC, 0xD, 0xB, 0x2, 0x0, 0x0, 0x6 };

	static {
		try {
			digest = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			System.err.println(e);
		}
	}

	public static String getDigest(String user, String clearPassword) {
		if (user != null && user.length() > 0 && clearPassword != null) {
			digest.reset();
			digest.update(SALT);
			digest.update(user.getBytes());
			digest.update(clearPassword.getBytes());
			return (Base64.encodeBase64String(digest.digest()));
		} else {
			return "password encoding error";
		}
	}

	public static String[] getRandomPasswordData(String user) {
		String[] result = new String[2];
		String clearPassword = getRandomPasswordString();
		String digestPassword = getDigest(user, clearPassword);
		result[0] = clearPassword;
		result[1] = digestPassword;
		return result;
	}

	public static String getRandomPasswordString() {

		final int SMALL_LETTERS_INDEX = 97;

		String randomString = "";
		for (int i = 0; i < 8; i++) {
			Random r = new Random();
			int num = SMALL_LETTERS_INDEX + r.nextInt(26);
			char c = (char) num;
			randomString += c;
		}
		return randomString;
	}

}
