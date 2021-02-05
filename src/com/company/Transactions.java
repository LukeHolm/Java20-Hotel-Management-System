package com.company;

public class Transactions {
    int customerId;
    String nameOnTransaction;
    int price;

    public Transactions(int customerId, String nameOnTransaction, int price) {
        this.customerId = customerId;
        this.nameOnTransaction = nameOnTransaction;
        this.price = price;
    }

    public static void transactionsToFile(Object object) {

    }
}
