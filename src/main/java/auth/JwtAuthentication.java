package auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import auth.model.User;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthentication extends AbstractAuthenticationToken {

	private static final long serialVersionUID = -3880363878176836971L;

	private String rawToken;

	private User user;

	public JwtAuthentication() {

		super(null);
		super.setAuthenticated(false);
	}

	public JwtAuthentication(Collection<? extends GrantedAuthority> authorities) {

		super(authorities);
		super.setAuthenticated(false);
	}

	public JwtAuthentication(String rawToken) {

		super(null);
		this.rawToken = rawToken;
		super.setAuthenticated(false);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRawToken() {
		return rawToken;
	}

	public void setRawToken(String rawToken) {
		this.rawToken = rawToken;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

}
