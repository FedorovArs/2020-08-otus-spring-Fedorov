package ru.otus.spring.repository;

import ru.otus.spring.entity.Comment;


public interface CustomCommentRepository {
    Comment getByTextOrCreate(Comment comment);
}
