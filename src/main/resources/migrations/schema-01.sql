--liquibase formatted sql

--changeset nvoxland:1
CREATE TABLE users
(
    id SERIAL PRIMARY KEY,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(20) NOT NULL,
    reg_date VARCHAR(20) NOT NULL,
    banned BOOLEAN NOT NULL
);

--changeset nvoxland:2
CREATE TABLE roles
(
  id SERIAL PRIMARY KEY,
  name VARCHAR(20) NOT NULL
);

--changeset nvoxland:3
CREATE TABLE articles
(
    id SERIAL PRIMARY KEY,
    header VARCHAR(50) NOT NULL,
    content VARCHAR(255),
    rating INT NOT NULL,
    cre_date VARCHAR(20) NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id)

);

--changeset nvoxland:4
CREATE TABLE users_roles
(
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);
