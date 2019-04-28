package com.example.bookstore.Model;

public class Book {

    public String courseName;
    public String courseNumber;
    public String bookTitle;
    public String contactNumber;
    public String price;
    public String condition;
    public String userid;

    public Book(String name, String number, String title, String contact, String cost, String state, String id) {
        courseName = name;
        courseNumber = number;
        bookTitle = title;
        contactNumber = contact;
        price = cost;
        condition = state;
        userid = id;
    }

    public Book() {

    }
}
