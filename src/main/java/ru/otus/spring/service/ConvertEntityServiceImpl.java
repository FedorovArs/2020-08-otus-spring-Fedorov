package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.entity.nosql.MongoBook;
import ru.otus.spring.entity.sql.Book;

@Service
public class ConvertEntityServiceImpl implements ConvertEntityService {

    @Override
    public MongoBook convertSqlBookEntityToMongoBook(Book book) {
        return new MongoBook(book.getName(), book.getAuthor().getName(), book.getGenre().getName());
    }
}
