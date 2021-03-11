package ru.otus.securewebbooklibrary.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String ADMIN = "ROLE_ADMIN";
    private final UserServiceImpl userService;

    public SecurityConfiguration(UserServiceImpl userService) {
        this.userService = userService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authorizeRequests().antMatchers("/authors/add", "/authors/edit/**").hasAuthority(ADMIN)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/authors/*").hasAuthority(ADMIN)
                .and()
                .authorizeRequests().antMatchers("/books/add", "/books/edit/**").hasAuthority(ADMIN)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/books/*").hasAuthority(ADMIN)
                .and()
                .authorizeRequests().antMatchers("/comments/add", "/comments/edit/**").hasAuthority(ADMIN)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/comments/*").hasAuthority(ADMIN)
                .and()
                .authorizeRequests().antMatchers("/genres/add", "/genres/edit/**").hasAuthority(ADMIN)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/genres/*").hasAuthority(ADMIN)
                .and()
                .authorizeRequests().antMatchers("/authors/**").authenticated()
                .and()
                .authorizeRequests().antMatchers("/books/**").authenticated()
                .and()
                .authorizeRequests().antMatchers("/comments/**").authenticated()
                .and()
                .authorizeRequests().antMatchers("/genres/**").authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .and()
                .rememberMe().tokenValiditySeconds(86400)
                .and()
                .logout().logoutUrl("/logout")
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }
}
