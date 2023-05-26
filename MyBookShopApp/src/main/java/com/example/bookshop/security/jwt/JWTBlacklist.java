package com.example.bookshop.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class JWTBlacklist {

    private static JWTBlacklist INSTANCE;
    private final Set<String> blacklist = new HashSet<>();

    @Autowired
    private JWTUtil jwtUtil;

    private JWTBlacklist() {}

    public static JWTBlacklist getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JWTBlacklist();
        }
        return INSTANCE;
    }

    public void addToken(String aToken) {
        blacklist.add(aToken);
    }

    public boolean checkContainsToken(String aToken) {
        return blacklist.contains(aToken);
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 1)
    public void checkingValidityTokensInBlacklist() {
        blacklist.removeIf(jwtUtil::isTokenExpired);
    }

}
