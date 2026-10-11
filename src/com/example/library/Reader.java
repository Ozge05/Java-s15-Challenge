package com.example.library;

import java.util.ArrayList;
import java.util.List;

public class Reader extends Person {
    private List<Book> borrowedBooks;

    public Reader(String name, int id) {
        super(id,name);
        this.borrowedBooks = new ArrayList<>();
    }


    @Override
    public String whoYouAre() {
        return "I am a Reader";
    }
}
