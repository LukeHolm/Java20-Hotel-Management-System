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
typeOfRoom VARCHAR(50) NOT NULL,
price INT NOT NULL, 
wifi BIT DEFAULT 1,
tv VARCHAR(50) NOT NULL,
aircondition BIT NOT NULL
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

CREATE TABLE food (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
meal VARCHAR(50) NOT NULL,
price INT NOT NULL
-- kr VARCHAR(2) DEFAULT 'kr'			-- om tid, lägga till så kr syns
);

CREATE TABLE foodOrder (
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
food_id INT NOT NULL,
customer_id INT,
FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE CASCADE,
FOREIGN KEY (food_id) REFERENCES food(id)
);

CREATE VIEW customerFoodOrder AS
SELECT customer.id, meal, price FROM customer
JOIN foodorder ON customer.id = customer_id
JOIN food ON foodorder.food_id = food.id;

CREATE VIEW availableRooms AS
SELECT DISTINCT room.roomNumber, typeOfRoom FROM room
JOIN roomBooking ON roomBooking.roomNumber = room.roomNumber
WHERE roomAvailable = 1;

CREATE VIEW bookedroom AS
SELECT DISTINCT customer_id, typeOfroom, price FROM room 
JOIN roombooking ON room.roomnumber = roombooking.roomnumber WHERE customer_id = 2;

SELECT * FROM customerFoodOrder where id = 1;

SELECT * FROM customer;
