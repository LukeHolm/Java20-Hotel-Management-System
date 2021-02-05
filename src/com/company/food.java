package com.company;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class food implements Serializable {

    int id;
    String meal;
    int price;

    public food(int id, String meal, int price) {
        this.id = id;
        this.meal = meal;
        this.price = price;
    }

    public static List<food> listOfFood() {
        food avocadoToast = new food(1, "Avocado & spiced hummus sandwich", 60);
        food breakfastBox = new food(2, "Deluxe breakfast box", 110);
        food soup = new food(3, "The yummiest soup", 110);
        food falafel = new food(4, "Falafel roll with pickled vegetables", 125);

        List<food> listOfFood = new ArrayList<>();
        listOfFood.add(avocadoToast);
        listOfFood.add(breakfastBox);
        listOfFood.add(soup);
        listOfFood.add(falafel);
        return listOfFood;
    }

    @Override
    public String toString() {
        return id + ". " + meal + " - " + price + "KR";
    }
}