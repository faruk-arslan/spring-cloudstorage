package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private AuthenticationService authenticationService;

    public SecurityConfig(AuthenticationService authenticationService){
        this.authenticationService=authenticationService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this.authenticationService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // To enable POST requests
        http.cors().and().csrf().disable();
        // To enable H2 console
        http.headers().frameOptions().sameOrigin();
        // Other
        http.authorizeRequests()
                .antMatchers("/signup","/login","/css/**","/js/**").permitAll()
                .anyRequest().authenticated();
        http.formLogin().loginPage("/login").permitAll();
        http.formLogin().defaultSuccessUrl("/home",true);
        // Logout handler
        http.logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout");
    }
}
