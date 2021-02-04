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
roomDescription VARCHAR(250),
price INT NOT NULL, 
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
JOIN roombooking ON room.roomnumber = roombooking.roomnumber;

CREATE VIEW roomDescription
SELECT DISTINCT typeOfRoom, price FROM room,
SELECT DISTINCT roomDescription FROM room,
SELECT DISTINCT wifi, tv, aircondition, smoking FROM room;


SELECT * FROM customerFoodOrder where id = 1;

SELECT * FROM customer;

/*creates a standard double bedroom */
INSERT INTO room (room_description,tv, price) VALUES
(
"This is standard double bedroom with a twin bed, \nfilled with all of your necessities and your own private bathroom",
0);

/*creates a standard single bedroom */
INSERT INTO room (room_description,tv) VALUES
(
"This is standard single bedroom with a single bed, \nfilled with all of your necessities and your own private bathroom",
0);

/*creates a deluxe double bedroom */
INSERT INTO room (room_description,tv) VALUES
("This is deluxe double bedroom with a king sized bed\n and flat screen tv. It is filled with all of your necessities, \na wellstocked minifridge and your own private bathroom with a jaccuzzi that fits up to 4 people.",
1);

/*creates a deluxe single bedroom */
INSERT INTO room (room_description,tv) VALUES
("This is deluxe single bedroom with a queen sized bed\n and flat screen tv. It is filled with all of your necessities, \na wellstocked minifridge and your own private bathroom with a jet stream bathtub.",
1);