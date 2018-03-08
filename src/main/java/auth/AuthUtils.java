package auth;

import org.springframework.security.core.context.SecurityContextHolder;

import auth.model.User;

public abstract class AuthUtils {

	public static User getUser() {
		try {
			JwtAuthentication auth = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
			
			return auth.getUser();
		}
		catch(Exception e) {
			return null;
		}
	}
	
	public static JwtAuthentication getAuth() {
		
		return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
	}
	
}
