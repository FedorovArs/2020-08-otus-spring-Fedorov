package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PresentationServiceImpl implements PresentationService {

    @Override
    public String convertBooksForShellPresentation(List<Book> books) {
        return books.stream()
                .map(this::convertToShellString)
                .collect(Collectors.joining(",\n"));
    }

    private String convertToShellString(Book book) {
        return '(' +
                "id=" + book.getId() +
                ", название='" + book.getName() +
                ", автор=" + book.getAuthor().getName() +
                ", жанр=" + book.getGenre().getName() +
                ", комментарии=" + (book.getComments() == null || book.getComments().isEmpty()
                ? "Комментарии отсутствуют"
                : book.getComments().stream().map(Comment::getText).collect(Collectors.joining(", "))) +
                ')';
    }
}
