package com.example.bookstore;

public class Book {
    public String courseName;
    public String courseNumber;
    public String bookTitle;
    public String contactNumber;
    public String price;
    public String condition;

    public Book(String name, String number, String title, String contact, String cost, String state) {
        courseName = name;
        courseNumber = number;
        bookTitle = title;
        contactNumber = contact;
        price = cost;
        condition = state;
    }


}
