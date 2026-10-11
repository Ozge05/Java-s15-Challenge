package com.example.library;

public abstract class Person {
    private String name;
    private int id;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public abstract String whoYouAre();
}
