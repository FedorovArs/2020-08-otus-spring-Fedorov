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
import ru.otus.spring.service.UserService;
import ru.otus.spring.entity.User;
import ru.otus.spring.service.UserServiceImpl;

import java.util.List;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userServiceImpl;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/public").anonymous()
                .and()
                .authorizeRequests().antMatchers("/authenticated", "/success", "/book", "/book/*").authenticated()
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
        List<User> users = userServiceImpl.findAll();

        for (User user : users) {
            auth.inMemoryAuthentication().withUser(user.getLogin()).password(user.getPassword()).roles(user.getRole());
        }
    }
}
