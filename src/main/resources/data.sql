insert into AUTHORS (id, name) values (1, 'Адитья Бхаргава');
insert into AUTHORS (id, name) values (2, 'Дж. Ханк Рейнвотер');
insert into AUTHORS (id, name) values (3, 'Фаулер Чад');
insert into AUTHORS (id, name) values (4, 'Хокинг Стивен');

insert into BOOKS (id, name) values (1, 'Грокаем алгоритмы');
insert into BOOKS (id, name) values (2, 'Как пасти котов');
insert into BOOKS (id, name) values (3, 'Программист-фанатик');
insert into BOOKS (id, name) values (4, 'Краткие ответы на большие вопросы');

insert into GENRES (id, name) values (1, 'Программирование');
insert into GENRES (id, name) values (2, 'ИТ');
insert into GENRES (id, name) values (3, 'Научно-популярная');

insert into BOOKS_FULL (author_name_id, book_name_id, genre_name_id) values (1, 1, 1);
insert into BOOKS_FULL (author_name_id, book_name_id, genre_name_id) values (2, 2, 2);
insert into BOOKS_FULL (author_name_id, book_name_id, genre_name_id) values (3, 3, 2);
insert into BOOKS_FULL (author_name_id, book_name_id, genre_name_id) values (4, 4, 3);



