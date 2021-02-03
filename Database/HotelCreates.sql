CREATE DATABASE hotel_booking_system;

USE hotel_booking_system;

CREATE TABLE customer (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
customer_name VARCHAR(50) NOT NULL,
contact_number VARCHAR(50)
);

CREATE TABLE ROOM (
roomNumber INT NOT NULL PRIMARY KEY,
available BIT DEFAULT 1
);