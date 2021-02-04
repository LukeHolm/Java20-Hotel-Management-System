package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private static final long serialVersionUID = 6584851985744000754L;
//    int roomNumber = 101;                     //TODO: NÄr vi vill ha fler än 4 rum
    int typeOfRoom;
    String roomName;
    String roomDescription;
    int price;
    boolean wifi;
    boolean tv;
    boolean aircondition;
    boolean smoking;

    public Room(int typeOfRoom, String roomName, String roomDescription, int price, boolean wifi, boolean tv, boolean aircondition, boolean smoking) {
        this.typeOfRoom = typeOfRoom;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.price = price;
        this.wifi = wifi;
        this.tv = tv;
        this.aircondition = aircondition;
        this.smoking = smoking;
//        roomNumber++;                         //TODO: NÄr vi vill ha fler än 4 rum
    }

    public static void roomsToFile() throws IOException {
        Room standardSingle = new Room(1, "Standard single",
                "This is standard single bedroom with a single bed,\n" +
                        "filled with all of your necessities and your own private bathroom",
                450, false, true,false, false);

        Room standardDouble = new Room(2, "Standard double",
                "This is standard double bedroom with a twin bed,\n" +
                        "filled with all of your necessities and your own private bathroom",
                600, false, true,false, false);

        Room deluxeSingle = new Room(3, "Deluxe single",
                "This is deluxe single bedroom with a queen sized bed\nand flat screen tv. It is filled with all of your necessities,\n"+
                        "a wellstocked minifridge and your own private bathroom with a jet stream bathtub.",
                650, true, true,true, false);

        Room deluxeDouble = new Room(4, "Deluxe Double",
                "This is deluxe double bedroom with a king sized bed\nand flat screen tv. It is filled with all of your necessities,\n" +
                        "a wellstocked minifridge and your own private bathroom with a jaccuzzi that fits up to 4 people.",
                950, true, true,true, false);

        List<Room> rooms = new ArrayList<>();
        rooms.add(standardSingle);
        rooms.add(standardDouble);
        rooms.add(deluxeSingle);
        rooms.add(deluxeDouble);

        HotelData.writeToFile(rooms, "Rooms.txt");
    }

    @Override
    public String toString() {
        return roomDescription + " - " + price + "KR";
    }
}
