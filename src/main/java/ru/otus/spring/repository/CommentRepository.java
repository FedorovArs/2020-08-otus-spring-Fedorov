package ru.otus.spring.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring.entity.Comment;

import java.util.List;
import java.util.Set;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
    List<Comment> findByBookIdIn(Set<String> bookIds);

    void removeByBookId(String id);

    List<Comment> findAllByBookId(String id);
}
