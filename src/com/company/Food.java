package com.company;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Food implements Serializable{
    int id;
    String meal;
    int price;

    public Food(int id, String meal, int price) {
        this.id = id;
        this.meal = meal;
        this.price = price;
    }

    public static List<Food> listOfFood() {
        Food avocadoToast = new Food(1, "Avocado & spiced hummus sandwich", 60);
        Food breakfastBox = new Food(2, "Deluxe breakfast box", 110);
        Food soup = new Food(3, "The yummiest soup", 110);
        Food falafel = new Food(4, "Falafel roll with pickled vegetables", 125);

        List<Food> listOfFood = new ArrayList<>();
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