package com.example.helloworld.resources;

import java.io.Serializable;

public class FortuneContainer implements Serializable {
    private int data;

    public FortuneContainer(int data) {
        this.data = data;
    }

    public void addOne() {
        this.data++;
    }

    @Override
    public String toString() {
        return "Data is " + this.data;
    }

}
