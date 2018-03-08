package auth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import auth.model.User;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
	
	@Value("${jwt.token.key}")
	private String JWT_KEY;
	
	@Value("${jwt.token.issuer}")
	private String JWT_ISSUER;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {

		JwtAuthentication jwtAuthentication = (JwtAuthentication) auth;

		try {

			Jws<Claims> claims = Jwts.parser()
					.setSigningKey(JWT_KEY)
					.requireIssuer(JWT_ISSUER)
					.parseClaimsJws(jwtAuthentication.getRawToken());

			String subjectString = claims.getBody().getSubject();

			User user = new ObjectMapper().readValue(subjectString, User.class);

			jwtAuthentication.setUser(user);
			jwtAuthentication.setAuthenticated(true);

		} catch (UnsupportedJwtException e) {

			throw new BadCredentialsException("InvalidJwt");

		} catch (SignatureException e) {

			throw new BadCredentialsException("InvalidJwtSignature");

		} catch (MissingClaimException e) {

			throw new BadCredentialsException("MissingJwtClaims");

		} catch (IncorrectClaimException e) {

			throw new BadCredentialsException("IncorrectJwtClaims");

		} catch (JsonParseException e) {

			throw new BadCredentialsException("InvalidJwt");

		} catch (JsonMappingException e) {

			throw new BadCredentialsException("InvalidJwt");

		} catch (IOException e) {

			throw new BadCredentialsException("InvalidJwt");
		}

		return jwtAuthentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {

		return (JwtAuthentication.class.isAssignableFrom(authentication));
	}
}