insert into AUTHORS (id, name)
values (1, 'Адитья Бхаргава');
insert into AUTHORS (id, name)
values (2, 'Дж. Ханк Рейнвотер');
insert into AUTHORS (id, name)
values (3, 'Фаулер Чад');
insert into AUTHORS (id, name)
values (4, 'Хокинг Стивен');

insert into GENRES (id, name)
values (1, 'Программирование');
insert into GENRES (id, name)
values (2, 'ИТ');
insert into GENRES (id, name)
values (3, 'Научно-популярная');

insert into BOOKS (id, name, author_id, genre_id)
values (1, 'Грокаем алгоритмы', 1, 1);
insert into BOOKS (id, name, author_id, genre_id)
values (2, 'Как пасти котов', 2, 2);
insert into BOOKS (id, name, author_id, genre_id)
values (3, 'Программист-фанатик', 3, 2);
insert into BOOKS (id, name, author_id, genre_id)
values (4, 'Краткие ответы на большие вопросы', 4, 3);

insert into COMMENTS (id, book_id, text)
values (1, 1, 'Хорошая книга');
insert into COMMENTS (id, book_id, text)
values (2, 1, 'Классная книга');
insert into COMMENTS (id, book_id, text)
values (3, 2, 'Рекомендую');

insert into USERS(login, position, password, authority)
values ('user', '1', 'password1', 'USER');
insert into USERS(login, position, password, authority)
values ('manager', '2', 'password2', 'MANAGER');
insert into USERS(login, position, password, authority)
values ('admin', '3', 'password3', 'ADMIN');
insert into USERS(login, position, password, authority)
values ('guest', '4', 'password4', 'GUEST');
