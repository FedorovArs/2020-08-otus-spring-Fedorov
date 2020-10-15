package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.entity.Book;
import ru.otus.spring.entity.Comment;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.CommentRepository;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "Arsenii", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertBooks", author = "Arsenii", runAlways = true)
    public void insertBooks(BookRepository repository) {
        List<Book> books = List.of(
                new Book("Book #1", "Грокаем алгоритмы", "Адитья Бхаргава", "Программирование"),
                new Book("Book #2", "Как пасти котов", "Дж. Ханк Рейнвотер", "ИТ"),
                new Book("Book #3", "Программист-фанатик", "Фаулер Чад", "ИТ"),
                new Book("Book #4", "Краткие ответы на большие вопросы", "Хокинг Стивен", "Научно-популярная"));
        repository.saveAll(books);
    }

    @ChangeSet(order = "003", id = "insertComments", author = "Arsenii", runAlways = true)
    public void insertComments(CommentRepository repository) {
        List<Comment> comments = List.of(
                new Comment("Comment #1", "Book #1", "Хорошая книга"),
                new Comment("Comment #2","Book #1", "Классная книга"),
                new Comment("Comment #3","Book #2", "Рекомендую")
        );
        repository.saveAll(comments);
    }
}
