package com.finance.backend.Config;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class tokengenerator {
	
		public static void main(String[] args) {
			Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
			String encodedKey = Encoders.BASE64.encode(key.getEncoded());
			System.out.println(encodedKey);
		}
	
}
