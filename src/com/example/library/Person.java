package com.example.library;

public abstract class Person {
    private String name;
    private final int id;

    public Person(int id, String name) {
        if (id <= 0) {
            throw new IllegalArgumentException("Person id must be positive.");
        }
        setName(name);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        this.name = name.trim();
    }

    public abstract String whoYouAre();

    @Override
    public String toString() {
        return name + " (id=" + id + ")";
    }
}
