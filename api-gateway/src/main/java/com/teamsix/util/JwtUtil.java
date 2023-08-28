package com.teamsix.util;

import java.security.Key;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {
	
	 public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


	    public void validateToken(final String token) {
	        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
	    }


	    private Key getSignKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }

}
