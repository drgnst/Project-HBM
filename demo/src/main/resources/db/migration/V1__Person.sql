CREATE TABLE Person(
    id UUID not null primary key,
    username varchar(30) not null,
    email varchar(30) not null,
    password varchar(30) not null,
    role varchar(30) not null
)