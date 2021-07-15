drop table persona IF EXISTS;

create TABLE persona(
id BIGINT IDENTITY NOT NULL PRIMARY KEY,
nombre varchar(20),
apellido varchar(20),
anio varchar(10)
);