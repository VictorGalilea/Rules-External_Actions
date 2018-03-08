package auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	@Value("${jwt.token.header}")
	private String JWT_TOKEN_HEADER_PARAM;
	
	@Value("${jwt.token.type}")
	private String JWT_TOKEN_TYPE;

	public JwtAuthenticationFilter(String defaultFilterProcessesUrl) {

		super(defaultFilterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException, IOException, ServletException {

		String rawToken = request.getHeader(JWT_TOKEN_HEADER_PARAM);

		if (rawToken != null && !rawToken.isEmpty()) {

			rawToken = rawToken.replace(JWT_TOKEN_TYPE, "").trim();

			return getAuthenticationManager().authenticate(new JwtAuthentication(rawToken));

		} else {

			throw new BadCredentialsException("JwtNotPresent");
		}
	}
	
	@Override
    protected void successfulAuthentication(
    		HttpServletRequest request, 
    		HttpServletResponse response, 
    		FilterChain chain, 
    		Authentication authResult) throws IOException, ServletException {
        
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        
        context.setAuthentication(authResult);
        
        SecurityContextHolder.setContext(context);
        
        chain.doFilter(request, response);
        
        SecurityContextHolder.clearContext();
    }

}
