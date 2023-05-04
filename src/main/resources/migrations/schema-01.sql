--liquibase formatted sql

--changeset nvoxland:1
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(20) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       reg_date VARCHAR(20) NOT NULL,
                       banned BOOLEAN NOT NULL
);
--rollback drop table users_table;

--changeset nvoxland:2
CREATE TABLE roles
(
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(20) NOT NULL
);
--rollback drop table roles;

--changeset nvoxland:3
CREATE TABLE user_roles (
                            user_id INTEGER REFERENCES users(id) ON DELETE CASCADE,
                            role_id INTEGER REFERENCES roles(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role_id)
);
--rollback drop table user_roles;

--changeset nvoxland:4
CREATE TABLE articles
(
    id SERIAL PRIMARY KEY,
    header VARCHAR(50) NOT NULL,
    content VARCHAR(255),
    rating INT NOT NULL,
    cre_date VARCHAR(20) NOT NULL,
    author_id INT NOT NULL,
    FOREIGN KEY (author_id) REFERENCES users(id)

);
--rollback drop table articles;
