package com.apps.developerblog.app.ws.shared;

import com.apps.developerblog.app.ws.security.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

@Component
public class Utils {
	
	private final Random RANDOM = new SecureRandom();
	private final String ALPHABET ="0123456789ABCDFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";



	public String generatedUserId(int length) {
		return generateRandomString(length);
	}
	
	public String generateAddresId(int length) {
		return generateRandomString(length);
	}

	private String generateRandomString(int length) {
		StringBuilder returnValue = new StringBuilder(length);
		
		for(int i = 0; i <length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return new String(returnValue);
	}

	public static boolean hasTokenExpired(String token){

		byte[] secretKeyBytes = SecurityConstants.getTokenSecret().getBytes();
		SecretKey key = Keys.hmacShaKeyFor(secretKeyBytes);

		JwtParser parser = Jwts.parser()
				.verifyWith(key)
				.build();

		Claims claims = parser.parseSignedClaims(token).getPayload();

		Date tokenExpirationDate = claims.getExpiration();
		Date todayDate = new Date();

		return tokenExpirationDate.before(todayDate);

	}

	public static String generateEmailVerificationToken(String userId){

		// Generate JWT and add it to a Response Header
		byte[] secretKeyBytes = SecurityConstants.getTokenSecret().getBytes();
		SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

		String token = Jwts.builder()
				.subject(userId)
				.expiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(secretKey)
				.compact();

		return token;
	}

	public static String generatePasswordResetToken(String userId) {
		// Generate JWT and add it to a Response Header
		byte[] secretKeyBytes = SecurityConstants.getTokenSecret().getBytes();
		SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

		String token = Jwts.builder()
				.subject(userId)
				.expiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(secretKey)
				.compact();

		return token;
	}
}
