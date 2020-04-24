package com.duckyshop.dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/*
 * References: 
 * 	- https://dev.to/awwsmm/how-to-encrypt-a-password-in-java-42dh
 * 	- http://www.appsdeveloperblog.com/encrypt-user-password-example-java/
 *  - https://stackoverflow.com/questions/18142745/how-do-i-generate-a-salt-in-java-for-salted-hash
 */
public class PasswordUtil {
	private static final int ITERATIONS = 2000;
	private static final int SALT_LENGTH = 64;
	private static final int KEY_LENGTH = 256;
	private static final String ALGORITHM = "PBKDF2WithHmacSHA512";
	private static final SecureRandom RANDOM = new SecureRandom();
		
	public static String generateSalt() {
		byte[] salt = new byte[SALT_LENGTH];
		RANDOM.nextBytes(salt);
		
		return Base64.getEncoder().encodeToString(salt);
	}
	
	public static String generateHashPassword(String password, String salt) {
		return generateHashPassword(password.toCharArray(),
							salt.getBytes());
	}
	
	public static String generateHashPassword(char[] password, byte[] salt) {
		PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
		Arrays.fill(password, Character.MIN_VALUE);
		
		try {
			SecretKeyFactory sk = SecretKeyFactory.getInstance(ALGORITHM);
			byte[] hashPassword = sk.generateSecret(spec).getEncoded();
			return Base64.getEncoder().encodeToString(hashPassword);
			
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
			return "";
		} finally {
			spec.clearPassword();
		}  
	}
	
	public static boolean verifyPassword(String providedPassword, String hashedPass, String salt) {
		boolean ret = false;
		String newHashPass = generateHashPassword(providedPassword, salt);
		ret = hashedPass.equals(newHashPass);
		return ret;
	}
	
}
