package com.r_tech.ecommerce.configuration;
import java.util.List;
import javax.crypto.SecretKey;
import java.io.IOException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

         //getting jwt header
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		
		if(jwt!=null) {
			//extracting Bearere from header
			jwt = jwt.substring(7);
			
			try {
				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRECT_KEY.getBytes());
				//extracting claims given while configuring jwt in frontEnd
				Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(jwt).getBody();
				
				String email= String.valueOf(claims.get("email"));
				//authorities in the form of String
				String authorities = String.valueOf(claims.get("authorities"));
				// authorities in the form of list 
				List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);
				//setting authentication in securityContextHolder
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			catch(Exception e) {
				throw new BadCredentialsException("invalid token.... from jwt validator");
				
			}
			
		}
		
		filterChain.doFilter(request, response);
	}
	
	

	
}
