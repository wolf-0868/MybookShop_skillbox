package com.example.bookshop.security;

import com.example.bookshop.security.jwt.JWTBlacklist;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest aRequest, HttpServletResponse aResponse, Authentication aAuthentication)
            throws IOException, ServletException {
        Cookie[] cookies = aRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    JWTBlacklist.getInstance().addToken(cookie.getValue());
                }
            }
        }
        HttpSession session = aRequest.getSession();
        SecurityContextHolder.clearContext();
        if (session != null) {
            session.invalidate();
        }

        super.onLogoutSuccess(aRequest, aResponse, aAuthentication);
    }

}
