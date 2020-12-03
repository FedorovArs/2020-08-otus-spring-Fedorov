package ru.otus.spring.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.entity.AuthUser;
import ru.otus.spring.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<AuthUser> userOptional = userRepository.findByLogin(userName);

        AuthUser authUser = userOptional.orElseThrow(() -> new UsernameNotFoundException("Unknown user: " + userName));

        return User.builder()
                .username(authUser.getLogin())
                .password(authUser.getPassword())
                .roles(authUser.getAuthority())
                .build();
    }
}
