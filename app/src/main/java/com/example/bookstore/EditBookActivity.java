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
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class EditBookActivity extends AppCompatActivity {
    private EditText title, price, contactNumber, courseName, courseNumber;
    private RadioGroup editConditionButtons;
    private Button submit, remove;
    Firebase bookRef;
    Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        title = (EditText) findViewById(R.id.etEditTitle);
        price = (EditText) findViewById(R.id.etEditPrice);
        contactNumber = (EditText) findViewById(R.id.etEditContactInfo);
        courseName = (EditText) findViewById(R.id.etEditCourseName);
        courseNumber = (EditText) findViewById(R.id.etEditCourseNumber);
        editConditionButtons = (RadioGroup) findViewById(R.id.rgEditConditionButtons);
        submit = (Button) findViewById(R.id.btnEditBookSubmit);
        remove = (Button) findViewById(R.id.btnEditBookRemove);

        Intent intent = getIntent();
        String bookUrl = (String) intent.getSerializableExtra("bookRef");
        bookRef = new Firebase(bookUrl);
        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                book = dataSnapshot.getValue(Book.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(EditBookActivity.this, "Error retrieving book.", Toast.LENGTH_SHORT).show();
            }
        });

        title.setText(book.bookTitle);
        price.setText(book.price);
        contactNumber.setText(book.contactNumber);
        courseName.setText(book.courseName);
        courseNumber.setText(book.courseNumber);
        if (book.condition.equals("Like New")) {
            editConditionButtons.check(R.id.rbConditionNew);
        } else if (book.condition.equals("Very Good")) {
            editConditionButtons.check(R.id.rbConditionVeryGood);
        } else if (book.condition.equals("Good")) {
            editConditionButtons.check(R.id.rbConditionSomeWear);
        } else if (book.condition.equals("Readable")) {
            editConditionButtons.check(R.id.rbConditionReadable);
        }

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookRef.removeValue();
                Toast.makeText(EditBookActivity.this,"Successfully removed book listing.", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(EditBookActivity.this, SecondActivity.class));
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sCourseName = courseName.getText().toString();
                String sCourseNumber = courseNumber.getText().toString();
                String sBookTitle = title.getText().toString();
                String sContactNumber = contactNumber.getText().toString();
                String sPrice = price.getText().toString();
                String sCondition = "";
                //the ID number for the correct condition button, if -1 is empty.
                int conditionID = editConditionButtons.getCheckedRadioButtonId();
                if (sCourseName.isEmpty()
                        || sCourseNumber.isEmpty()
                        || sBookTitle.isEmpty()
                        || sContactNumber.isEmpty()
                        || sPrice.isEmpty()
                        || conditionID == -1) {
                    Toast.makeText(EditBookActivity.this, "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    if (conditionID == R.id.rbConditionNew) {
                        sCondition = "Like New";
                    } else if (conditionID == R.id.rbConditionVeryGood) {
                        sCondition = "Very Good";
                    } else if (conditionID == R.id.rbConditionSomeWear) {
                        sCondition = "Good";
                    } else if (conditionID == R.id.rbConditionReadable) {
                        sCondition = "Readable";
                    }

                    bookRef.setValue(new Book(sCourseName, sCourseNumber, sBookTitle, sContactNumber, sPrice, sCondition, book.userid));
                    startActivity(new Intent(EditBookActivity.this, SecondActivity.class));
                }
            }
        });
    }
}
