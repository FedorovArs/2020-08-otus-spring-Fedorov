package ru.otus.spring.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.service.CustomUserDetailsService;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/public").anonymous()
                .and()
                .authorizeRequests().antMatchers("/authenticated", "/success", "/book", "/book/**").authenticated()
                .and()
                .authorizeRequests().antMatchers("/auth_manager", "/auth_admin", "/auth_user", "/auth_admin_or_manager").authenticated()
                .and()
                .authorizeRequests().antMatchers("/pre_auth_user", "/pre_auth_manager", "/pre_auth_admin", "/pre_auth_manager_or_admin").authenticated()
                .and()
                .authorizeRequests().antMatchers("/auth_manager").hasRole("MANAGER")
                .and()
                .authorizeRequests().antMatchers("/auth_admin").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/auth_user").hasRole("USER")
                .and()
                .authorizeRequests().antMatchers("/auth_admin_or_manager").hasAnyRole("ADMIN", "MANAGER")
                .and()
                .formLogin()
                .loginProcessingUrl("/custom_spring_security_check")
                .usernameParameter("custom_username")
                .passwordParameter("custom_password")
                .loginPage("/login");
    }

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
