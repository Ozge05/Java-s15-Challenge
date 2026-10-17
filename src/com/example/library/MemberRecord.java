package com.example.library;

import java.time.LocalDate;

public class MemberRecord {
    private final int memberId;
    private final MemberType type;
    private final LocalDate dateOfMembership;
    private final int maxBookLimit;
    private String name;
    private String address = "";
    private String phoneNumber = "";
    private int booksIssued;

    public MemberRecord(int memberId, MemberType type, String name, int maxBookLimit) {
        if (memberId <= 0 || maxBookLimit <= 0) {
            throw new IllegalArgumentException("Member id and book limit must be positive.");
        }
        this.memberId = memberId;
        this.type = type;
        this.dateOfMembership = LocalDate.now();
        this.maxBookLimit = maxBookLimit;
        setName(name);
    }

    public int getMemberId() {
        return memberId;
    }

    public MemberType getType() {
        return type;
    }

    public LocalDate getDateOfMembership() {
        return dateOfMembership;
    }

    public int getMaxBookLimit() {
        return maxBookLimit;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getBooksIssued() {
        return booksIssued;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Member name cannot be empty.");
        }
        this.name = name.trim();
    }

    public void setContactDetails(String address, String phoneNumber) {
        this.address = address == null ? "" : address.trim();
        this.phoneNumber = phoneNumber == null ? "" : phoneNumber.trim();
    }

    void incrementBooksIssued() {
        if (booksIssued >= maxBookLimit) {
            throw new IllegalStateException("The member has reached the borrowing limit.");
        }
        booksIssued++;
    }

    void decrementBooksIssued() {
        if (booksIssued > 0) {
            booksIssued--;
        }
    }

    @Override
    public String toString() {
        return "MemberRecord{id=" + memberId + ", name='" + name + "', type=" + type +
                ", memberSince=" + dateOfMembership + ", booksIssued=" + booksIssued +
                "/" + maxBookLimit + "}";
    }
}
