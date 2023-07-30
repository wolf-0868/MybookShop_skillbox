package com.example.bookshop.security.jwt;

import com.example.bookshop.security.BookshopUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final BookshopUserDetailsService bookshopUserDetailsService;


    private static UsernamePasswordAuthenticationToken getAuthenticationToken(UserDetails aUserDetails, HttpServletRequest aRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = createAuthenticationToken(aUserDetails);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(aRequest));
        return authenticationToken;
    }

    private static UsernamePasswordAuthenticationToken createAuthenticationToken(UserDetails aUserDetails) {
        return new UsernamePasswordAuthenticationToken(aUserDetails, null, aUserDetails.getAuthorities());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest aRequest, HttpServletResponse aResponse, FilterChain aFilterChain) throws ServletException, IOException {
        Cookie[] cookies = aRequest.getCookies();
        if (cookies != null) {
            String username = null;
            for (Cookie cookie : cookies) {
                String token = cookie.getValue();
                if (cookie.getName().equals("token") && !JWTBlacklist.getInstance().checkContainsToken(token)) {
                    username = jwtUtil.extractUsername(token);
                }
                updateAuthentication(username, token, aRequest);
            }
        }
        aFilterChain.doFilter(aRequest, aResponse);
    }

    private void updateAuthentication(String aUsername, String aToken, HttpServletRequest aRequest) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if ((aUsername != null) && (securityContext.getAuthentication() == null)) {
            UserDetails userDetails = bookshopUserDetailsService.loadUserByUsername(aUsername);
            if (jwtUtil.validateToken(aToken, userDetails)) {
                securityContext.setAuthentication(getAuthenticationToken(userDetails, aRequest));
            }
        }
    }

}
