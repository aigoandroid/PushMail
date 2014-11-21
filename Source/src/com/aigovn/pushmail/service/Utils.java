package com.aigovn.pushmail.service;

import android.annotation.SuppressLint;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Utils {
	public static int getRandom(int excludeMax){
		Random rand = new Random();
		return rand.nextInt(excludeMax);
	}
	
	@SuppressLint("TrulyRandom")
	public static byte[] encryptData(String encryptionKey, byte[] data){
		try {
			Cipher cipherAES = Cipher.getInstance("AES/ECB/NoPadding");
			SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
			cipherAES.init(Cipher.ENCRYPT_MODE, secretKey);
			return cipherAES.doFinal(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] decryptData(String encryptionKey, byte[] data){
		try {
			Cipher cipherAES = Cipher.getInstance("AES/ECB/NoPadding");
			SecretKeySpec secretKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
			cipherAES.init(Cipher.DECRYPT_MODE, secretKey);
			return cipherAES.doFinal(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
