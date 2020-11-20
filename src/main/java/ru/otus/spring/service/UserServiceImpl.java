package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.repository.UserRepository;
import ru.otus.spring.entity.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
