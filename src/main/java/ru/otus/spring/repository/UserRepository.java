package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
