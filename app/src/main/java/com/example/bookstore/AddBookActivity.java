package com.example.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bookstore.Model.Book;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class AddBookActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText courseName, courseNumber, bookTitle, contactNumber, price;
    private RadioGroup conditionButtons;
    private Button submit, cancel;

    private Firebase nRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        firebaseAuth = FirebaseAuth.getInstance();
        courseName = (EditText)findViewById(R.id.etAddCourseName);
        courseNumber = (EditText)findViewById(R.id.etAddCourseNumber);
        bookTitle = (EditText)findViewById(R.id.etAddBookTitle);
        contactNumber = (EditText)findViewById(R.id.etPhoneNumber);
        price = (EditText)findViewById(R.id.etAddPrice);
        conditionButtons = (RadioGroup)findViewById(R.id.rgAddConditionButtons);
        submit = (Button)findViewById(R.id.btnAddBookSubmit);
        cancel = (Button)findViewById(R.id.btnAddBookCancel);

        nRootRef = new Firebase("https://login-70f0b.firebaseio.com/Books");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AddBookActivity.this, SecondActivity.class));
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sCourseName = courseName.getText().toString();
                String sCourseNumber = courseNumber.getText().toString();
                String sBookTitle = bookTitle.getText().toString();
                String sContactNumber = contactNumber.getText().toString();
                String sPrice = price.getText().toString();
                String sCondition = "";
                //the ID number for the correct condition button, if -1 is empty.
                int conditionID = conditionButtons.getCheckedRadioButtonId();
                if (sCourseName.isEmpty()
                        || sCourseNumber.isEmpty()
                        || sBookTitle.isEmpty()
                        || sContactNumber.isEmpty()
                        || sPrice.isEmpty()
                        || conditionID == -1) {
                    Toast.makeText(AddBookActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    Firebase childRef = nRootRef.child("Book");

                    if (conditionID == R.id.rbConditionNew) {
                        sCondition = "Like New";
                    } else if (conditionID == R.id.rbConditionVeryGood) {
                        sCondition = "Very Good";
                    } else if (conditionID == R.id.rbConditionSomeWear) {
                        sCondition = "Good";
                    } else if (conditionID == R.id.rbConditionReadable) {
                        sCondition = "Readable";
                    }
                    try {
                        childRef.push().setValue(new Book(sCourseName, sCourseNumber, sBookTitle, sContactNumber, sPrice, sCondition, firebaseAuth.getCurrentUser().getUid()));
                    } catch (NullPointerException e) {
                        Toast.makeText(AddBookActivity.this, "An error occurred grabbing userid", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(AddBookActivity.this, SecondActivity.class));
                }
            }
        });
    }
}
