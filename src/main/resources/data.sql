insert into rating(rating_name)
values ('G');

insert into rating(rating_name)
values ('PG');

insert into rating(rating_name)
values ('PG-13');

insert into rating(rating_name)
values ('R');

insert into rating(rating_name)
values ('NC-17');

insert into genre(genre_name)
values ('Комедия');

insert into genre(genre_name)
values ('Драма');

insert into genre(genre_name)
values ('Мультфильм');

insert into genre(genre_name)
values ('Триллер');

insert into genre(genre_name)
values ('Документальный');

insert into genre(genre_name)
values ('Боевик');

insert into user(login, name, email, birthday)
values ('dolore', 'Nick Name', 'Nick Name', '1946-08-20');

insert into user(login, name, email, birthday)
values ('friend', 'friend adipisicing', 'friend@mail.ru', '1976-08-20');

insert into film(name, description, release_date, duration, mpa)
values ('nisi eiusmod', 'adipisicing', '1967-03-25', 100, 1);

insert into film(name, description, release_date, duration, rate, mpa)
values ('New film', 'New film about friends', '1999-04-30', 120, 4, 3);

insert into filmByGenre(filmid, genreid)
values (2, 1);
