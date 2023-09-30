CREATE TABLE user
(
    user_id  INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    login    VARCHAR(50) NOT NULL,
    name     VARCHAR(50) NOT NULL,
    email    VARCHAR(100),
    birthday DATE        NOT NULL

);

CREATE TABLE friend
(
    friends_id  INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id     INTEGER REFERENCES user (user_id),
    follower_id INTEGER REFERENCES user (user_id)
);

CREATE TABLE rating
(
    rating_id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    rating_name VARCHAR(50)
);

CREATE TABLE genre
(
    genre_id   INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    genre_name VARCHAR(50)
);

CREATE TABLE film
(
    film_id      INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         VARCHAR(50)   NOT NULL,
    description  VARCHAR(1000) NOT NULL,
    release_date DATE          NOT NULL,
    duration     INTEGER       NOT NULL,
    rate         INTEGER,
    mpa          INTEGER REFERENCES rating (rating_id)
);

CREATE TABLE filmByGenre
(
    filmByGenre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    film_id        INTEGER REFERENCES film (film_id),
    genre_id       INTEGER REFERENCES genre (genre_id)
);
