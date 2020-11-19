package ru.otus.spring.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.spring.entity.Book;
import ru.otus.spring.repository.BookRepository;

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
                new Book("Book_1", "Грокаем алгоритмы", "Адитья Бхаргава", "Программирование"),
                new Book("Book_2", "Как пасти котов", "Дж. Ханк Рейнвотер", "ИТ"),
                new Book("Book_3", "Программист-фанатик", "Фаулер Чад", "ИТ"),
                new Book("Book_4", "Краткие ответы на большие вопросы", "Хокинг Стивен", "Научно-популярная"));
        repository.saveAll(books).subscribe();
    }
}
