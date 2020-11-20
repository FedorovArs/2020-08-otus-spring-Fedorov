package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.entity.User;

import java.util.List;

@Service
public interface UserService {

    List<User> findAll();
}
