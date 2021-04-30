CREATE TABLE candidates (
   id SERIAL PRIMARY KEY,
   name TEXT,
   city_id int8 REFERENCES cities(id)
);
