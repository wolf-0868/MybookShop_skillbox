package com.example.bookshop.config;

import com.example.bookshop.security.BookshopUserDetailsService;
import com.example.bookshop.security.CustomLogoutSuccessHandler;
import com.example.bookshop.security.jwt.JWTRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BookshopUserDetailsService bookshopUserDetailsService;
    private final JWTRequestFilter filter;

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder aAuth) throws Exception {
        aAuth.userDetailsService(bookshopUserDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity aHttp) throws Exception {
        aHttp
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/my", "/profile", "/cart", "/postponed")
                .authenticated()
//                .hasRole("USER")
                .antMatchers("/**")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/signin")
                .failureUrl("/signin")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/signin")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .deleteCookies("token")
                .and()
                .oauth2Login()
                .and()
                .oauth2Client();

//        aHttp.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        aHttp.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

}
