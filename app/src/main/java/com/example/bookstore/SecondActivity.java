package com.example.bookstore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private Button addBook;
    private Button searchButton;
    private EditText searchThis;
    private ImageView avatar;
    private TextView username;
    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firebaseAuth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(R.id.btnLogout);
        addBook = (Button)findViewById(R.id.btnAddBook);
        searchButton = (Button)findViewById(R.id.btnSearch);
        avatar = (ImageView)findViewById(R.id.ivProfileAvatar);
        username = (TextView)findViewById(R.id.tvProfileUsername);
        email = (TextView)findViewById(R.id.tvProfileEmail);

        updateProfileDetails();

        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SecondActivity.this, AddBookActivity.class));
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SecondActivity.this, SearchActivity.class));
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(SecondActivity.this, MainActivity.class));
            }
        });
    }

    //atm doesn't display username, issue with firebase? unsure.
    private void updateProfileDetails() {
        try {
            username.setText(firebaseAuth.getCurrentUser().getDisplayName());
        } catch (Error NullPointerException) {
            username.setText("No username found.");
        }
        email.setText(firebaseAuth.getCurrentUser().getEmail());
    }
}
