package com.example.bookshop.security.jwt;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class JWTBlacklist {

    private static JWTBlacklist instance;
    private final Set<String> blacklist = new HashSet<>();

    @Setter(onMethod_ = {@Autowired})
    private JWTUtil jwtUtil;

    private JWTBlacklist() {}

    public static JWTBlacklist getInstance() {
        if (instance == null) {
            instance = new JWTBlacklist();
        }
        return instance;
    }

    public void addToken(String aToken) {
        blacklist.add(aToken);
    }

    public boolean checkContainsToken(String aToken) {
        return blacklist.contains(aToken);
    }

    @Scheduled(fixedRate = 3600000)
    public void checkingValidityTokensInBlacklist() {
        blacklist.removeIf(jwtUtil::isTokenExpired);
    }

}
