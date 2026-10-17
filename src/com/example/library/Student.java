package com.example.library;

public class Student extends Reader {
    public Student(String name, int id) {
        super(name, id, MemberType.STUDENT);
    }

    @Override
    public String whoYouAre() {
        return "I am a Student";
    }
}
