package com.r_tech.ecommerce.Filters;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingFilter extends OncePerRequestFilter {

	 @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException {

	        // Logging HTTP method and URL
	        System.out.println("HTTP Method: " + request.getMethod());
	        System.out.println("Request URL: " + request.getRequestURL());

	        // Logging  headers
	        Enumeration<String> headerNames = request.getHeaderNames();
	        while (headerNames.hasMoreElements()) {
	            String headerName = headerNames.nextElement();
	            String headerValue = Collections.list(request.getHeaders(headerName)).stream()
	                    .collect(Collectors.joining(","));
	            System.out.println("Header: " + headerName + " = " + headerValue);
	        }

	        // Logging request body
	        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
	        System.out.println("Request Body: " + body);

	        // Proceed with the next filter in the chain
	        filterChain.doFilter(request, response);
	    }
}
