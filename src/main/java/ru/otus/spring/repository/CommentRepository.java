package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Comment;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
}
