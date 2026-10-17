package com.example.library;

public class Faculty extends Reader {
    public Faculty(String name, int id) {
        super(name, id, MemberType.FACULTY);
    }

    @Override
    public String whoYouAre() {
        return "I am a Faculty member";
    }
}
