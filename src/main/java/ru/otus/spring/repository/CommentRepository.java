package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
