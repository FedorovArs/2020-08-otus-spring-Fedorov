package ru.otus.spring.dao;

import ru.otus.spring.entity.Comment;

import java.util.List;
import java.util.Optional;

// Методов объявлено больше чем используется, что бы попрактиковаться и набить руку.
public interface CommentRepository {

    long count();

    Comment save(Comment comment);

    Optional<Comment> findById(long id);

    Optional<Comment> getByTextIgnoreCase(Comment comment);

    List<Comment> getAll();

    void deleteById(long id);

    void updateById(Comment comment);

    Comment getByTextOrCreate(Comment comment);
}
