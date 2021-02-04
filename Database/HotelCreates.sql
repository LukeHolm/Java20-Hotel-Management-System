CREATE DATABASE hotel_booking_system;
-- DROP DATABASE hotel_booking_system;

USE hotel_booking_system;

CREATE TABLE customer (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
customer_name VARCHAR(50) NOT NULL,
contact_number VARCHAR(50)
);

CREATE TABLE room (
roomNumber INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
typeOfRoom INT NOT NULL,
room_Description VARCHAR(250),
price_per_night INT NOT NULL, 
wifi BIT DEFAULT 1,
tv VARCHAR(50) NOT NULL,
aircondition BIT NOT NULL,
smoking BIT DEFAULT 0
);

CREATE TABLE roomBooking (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
roomNumber INT NOT NULL,
customer_id INT DEFAULT NULL,
roomAvailable BIT DEFAULT 1,
checkInDate DATETIME DEFAULT NULL,
checkOutDate DATETIME DEFAULT NULL,
FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE,
FOREIGN KEY (roomnumber) REFERENCES room(roomNumber) ON DELETE CASCADE
);

CREATE TABLE foodOrder (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
food_id INT NOT NULL,
customer_id INT,
FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE
);

CREATE VIEW availableRooms AS
SELECT DISTINCT room.roomNumber, typeOfRoom FROM room
JOIN roomBooking ON roomBooking.roomNumber = room.roomNumber
WHERE roomAvailable = 1;

CREATE VIEW bookedroom AS
SELECT DISTINCT customer_id, typeOfroom, price_per_night FROM room 
JOIN roombooking ON room.roomnumber = roombooking.roomnumber;

CREATE VIEW roomDescription 
SELECT DISTINCT typeOfRoom, price FROM room,
SELECT DISTINCT roomDescription FROM room,
SELECT DISTINCT wifi, tv, aircondition, smoking FROM room;