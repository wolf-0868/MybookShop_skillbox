package com.example.bookshop.security.jwt;

import com.example.bookshop.security.BookshopUserDetails;
import com.example.bookshop.security.BookshopUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final BookshopUserDetailsService bookshopUserDetailsService;

    public JWTRequestFilter(JWTUtil aJwtUtil, BookshopUserDetailsService aBookshopUserDetailsService) {
        jwtUtil = aJwtUtil;
        bookshopUserDetailsService = aBookshopUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest aRequest, HttpServletResponse aResponse, FilterChain aFilterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        Cookie[] cookies = aRequest.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    if (JWTBlacklist.getInstance().checkContainsToken(token)) {
                        break;
                    }
                    username = jwtUtil.extractUsername(token);
                }

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    BookshopUserDetails userDetails = (BookshopUserDetails) bookshopUserDetailsService.loadUserByUsername(username);

                    if (jwtUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails, null, userDetails.getAuthorities());

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(aRequest));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        }
        aFilterChain.doFilter(aRequest, aResponse);
    }

}
