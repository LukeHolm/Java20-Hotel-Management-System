package com.company;

import java.io.*;
import java.util.List;

public class DataHandler {

    public static <T> void writeToFile(T object, String fileLocation) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileLocation));

        objectOutputStream.writeObject(object);

        objectOutputStream.close();
    }

    public static List readFromFile(String fileLocation) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileLocation));

        return (List) objectInputStream.readObject();
    }
}
