CREATE TABLE users (
   id serial PRIMARY KEY,
   name varchar(55) UNIQUE,
   email varchar(55),
   password varchar(55)
);
