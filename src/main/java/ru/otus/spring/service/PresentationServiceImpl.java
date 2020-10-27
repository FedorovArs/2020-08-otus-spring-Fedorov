package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Comment;
import ru.otus.spring.repository.CommentRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PresentationServiceImpl implements PresentationService {

    private final CommentRepository commentRepository;

    public PresentationServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public String convertBooksForShellPresentation(List<Book> books) {
        Set<String> bookIds = books.stream()
                .map(Book::getId)
                .collect(Collectors.toSet());

        List<Comment> comments = commentRepository.findByBookIdIn(bookIds);
        Map<String, String> commentsGroupedByBookId = comments.stream()
                .collect(Collectors.groupingBy(Comment::getBookId,
                        Collectors.mapping(Comment::getText,
                                Collectors.joining(", "))));

        return books.stream()
                .map(s -> convertToShellString(s, commentsGroupedByBookId.get(s.getId())))
                .collect(Collectors.joining(",\n"));
    }

    private String convertToShellString(Book book, String comments) {
        return '(' +
                "id=" + book.getId() +
                ", название='" + book.getName() +
                ", автор=" + book.getAuthor() +
                ", жанр=" + book.getGenre() +
                ", комментарии=" + (comments == null ? "Комментарии отсутствуют" : comments) +
                ')';
    }
}
