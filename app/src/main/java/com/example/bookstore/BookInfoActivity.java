package com.example.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookstore.Model.Book;

import org.w3c.dom.Text;

public class BookInfoActivity extends AppCompatActivity {

    private TextView title, condition, price, courseName, courseNumber, contactNumber;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        title = (TextView) findViewById(R.id.tvBookInfoTitle);
        condition = (TextView) findViewById(R.id.tvBookInfoCondition);
        price = (TextView) findViewById(R.id.tvBookInfoPrice);
        courseName = (TextView) findViewById(R.id.tvBookInfoCourseName);
        courseNumber = (TextView) findViewById(R.id.tvBookInfoCourseNumber);
        contactNumber = (TextView) findViewById(R.id.tvBookInfoRealContactNumber);
        back = (Button) findViewById(R.id.btnBookInfoBack);

        Intent intent = getIntent();
        Book book = (Book)intent.getParcelableExtra("book");

        title.setText(book.bookTitle);
        condition.setText(book.condition);
        price.setText(book.price);
        courseName.setText(book.courseName);
        courseNumber.setText(book.courseNumber);
        contactNumber.setText(book.contactNumber);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookInfoActivity.this, SearchActivity.class));
            }
        });
    }
}
