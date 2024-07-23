package com.rdev.userregistery.config;


import com.rdev.userregistery.service.JWTUtils;
import com.rdev.userregistery.service.RegistryDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private RegistryDetailsService registryDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            final String authHeader= request.getHeader("Authorization");
            final String jwtToken;
            final String userEmail;

            if (authHeader==null || authHeader.isBlank()){
                filterChain.doFilter(request,response);
                return;

            }
            jwtToken = authHeader.substring(7);
            userEmail = jwtUtils.extractUsername(jwtToken);
        // If email  not null and the user is not already authenticated, proceed with authentication
        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = registryDetailsService.loadUserByUsername(userEmail);

            // Validate the JWT token
            if(jwtUtils.isTokenValid(jwtToken, userDetails)){

                     // Create an empty SecurityContext
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                    // Create an authentication token with the user details and authorities
                    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());

                    token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                     // Set the authentication in the SecurityContext

                    securityContext.setAuthentication(token);
                    // Set the SecurityContextHolder with the created SecurityContext

                    SecurityContextHolder.setContext(securityContext);
                }
            }
            filterChain.doFilter(request,response);



    }





}
