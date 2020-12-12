package ru.otus.spring.service;

import ru.otus.spring.entity.nosql.MongoBook;
import ru.otus.spring.entity.sql.Book;

public interface ConvertEntityService {

    MongoBook convertSqlBookEntityToMongoBook(Book book);
}
