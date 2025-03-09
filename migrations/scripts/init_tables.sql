BEGIN;

CREATE TYPE user_role AS ENUM ('ROLE_ADMIN', 'ROLE_READER');

CREATE CAST ( varchar AS user_role) WITH INOUT AS IMPLICIT;

CREATE TABLE IF NOT EXISTS "users"
(
    "user_id"  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "name"     varchar,
    "surname"  varchar,
    "username" varchar,
    "email"    varchar,
    "password" varchar,
    "role"     user_role
);


CREATE TABLE IF NOT EXISTS "books"
(
    "book_id"          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    "title"            varchar,
    "author"           varchar,
    "publication_date" DATE,
    "genre"            varchar,
    "is_booked"        boolean,
    "reader_id"        BIGINT REFERENCES users (user_id),
    "booking_date"     DATE,
    "booked_before"    DATE,
    "description"      varchar

);

COMMIT;