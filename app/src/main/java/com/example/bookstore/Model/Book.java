package com.example.bookstore.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

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

    public Book(Parcel in) {
        readFromParcel(in);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Book createFromParcel(Parcel in ) {
            return new Book( in );
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(courseName);
        dest.writeString(courseNumber);
        dest.writeString(bookTitle);
        dest.writeString(contactNumber);
        dest.writeString(price);
        dest.writeString(condition);
        dest.writeString(userid);
    }

    private void readFromParcel(Parcel in) {
        courseName = in.readString();
        courseNumber = in.readString();
        bookTitle = in.readString();
        contactNumber = in.readString();
        price = in.readString();
        condition = in.readString();
        userid = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
