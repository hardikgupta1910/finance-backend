package Finance.Data.Processing.and.Access.Control.Config;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class jwtService {
	
	private final String SECRET_KEY="iVP9FsAgnWYK9WCZoB27Mn2w/fUvzzsCnFu/RCLQv80=";
	
	public String generateToken(Long UserId) {
		return Jwts.builder()
				.setSubject(String.valueOf(UserId))
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
				.signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
				.compact();
	}
	
	
	public Long extractUserId(String token) {
		return Long.parseLong(
				Jwts.parserBuilder()
						.setSigningKey(SECRET_KEY.getBytes())
						.build()
						.parseClaimsJws(token)
						.getBody()
						.getSubject()
		);
	}

	public boolean validateToken(String token) {
		try{
			Jwts.parserBuilder()
					.setSigningKey(SECRET_KEY.getBytes())
					.build()
					.parseClaimsJws(token);
			
			return true;
		}catch (Exception e){
			return false;
		}
	}
}
