package com.example.alihasan.synergytwo;

public class CounterSingleton {

    private static int counter;

    private static CounterSingleton instance;

    public static CounterSingleton getInstance() {

        if (instance == null)
            instance = new CounterSingleton();

        return instance;


    }

    public int addCounter() {
        return ++counter;
    }

    public int decreaseCounter() {
        return --counter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int n){
        counter = n;
    }

}