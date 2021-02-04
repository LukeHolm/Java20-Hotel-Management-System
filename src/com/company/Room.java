package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    int roomNumber;
    String typeOfRoom;
    String roomDescription;
    int price;
    boolean wifi;
    boolean tv;
    boolean aircondition;
    boolean smoking;

    public Room(String typeOfRoom, String roomDescription, int price, boolean wifi, boolean tv, boolean aircondition, boolean smoking) {
        this.typeOfRoom = typeOfRoom;
        this.roomDescription = roomDescription;
        this.price = price;
        this.wifi = wifi;
        this.tv = tv;
        this.aircondition = aircondition;
        this.smoking = smoking;
    }

    public List<Room> getRooms() {
        Room standardSingle = new Room("Standard Single",
                "This is standard single bedroom with a single bed, " +
                        "filled with all of your necessities and your own private bathroom",
                450, false, true,false, false);

        Room standardDouble = new Room("Standard Single",
                "This is standard double bedroom with a twin bed, " +
                        "filled with all of your necessities and your own private bathroom",
                450, false, true,false, false);

        Room deluxeSingle = new Room("Standard Single",
                "This is deluxe double bedroom with a king sized bed and flat screen tv. It is filled with all of your necessities, " +
                        "a wellstocked minifridge and your own private bathroom with a jaccuzzi that fits up to 4 people.",
                450, false, true,false, false);

        Room deluxeDouble = new Room("Standard Single",
                "This is deluxe double bedroom with a king sized bed and flat screen tv. It is filled with all of your necessities, " +
                        "a wellstocked minifridge and your own private bathroom with a jaccuzzi that fits up to 4 people.",
                450, false, true,false, false);

        List<Room> rooms = new ArrayList<>();
        rooms.add(standardSingle);
        rooms.add(standardDouble);
        rooms.add(deluxeSingle);
        rooms.add(deluxeDouble);

        return rooms;
    }


}
