package com.example.bookstore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookstore.Model.Book;
import com.example.bookstore.ViewHolder.BookViewHolder;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    private Button addBook;
    private Button searchButton;
    private TextView username;
    private TextView email;
    private RecyclerView bookList;

    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Book> options;
    FirebaseRecyclerAdapter<Book, BookViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        firebaseAuth = FirebaseAuth.getInstance();
        logout = (Button)findViewById(R.id.btnLogout);
        addBook = (Button)findViewById(R.id.btnAddBook);
        searchButton = (Button)findViewById(R.id.btnSearch);
        username = (TextView)findViewById(R.id.tvProfileUsername);
        email = (TextView)findViewById(R.id.tvProfileEmail);

        bookList = (RecyclerView) findViewById(R.id.rvProfileBooks);
        bookList.setHasFixedSize(true);

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

        //Searching for users own books:
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Books").child("Book");

        options = new FirebaseRecyclerOptions.Builder<Book>().setQuery(databaseReference, Book.class).build();

        adapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BookViewHolder holder, final int position, @NonNull final Book model) {
                if (model.userid.toString().equals(firebaseAuth.getCurrentUser().getUid().toString())) {
                    holder.itemView.setVisibility(View.VISIBLE);
                    holder.itemView.setLayoutParams(
                            new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    holder.bookName.setText(model.bookTitle);
                    holder.bookCondition.setText(model.condition);
                    holder.bookPrice.setText(model.price);


                    //Change view info to be an option to edit current listing:
                    holder.viewInfo.setText("Edit");
                    holder.viewInfo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SecondActivity.this, EditBookActivity.class);
                            intent.putExtra("bookRef", adapter.getRef(position).toString());
                        }
                    });
                } else {
                    holder.itemView.setVisibility(View.GONE);
                    holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
                }
            }

            @NonNull
            @Override
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout, viewGroup, false);
                return new BookViewHolder(view);
            }
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        bookList.setLayoutManager(linearLayoutManager);
        adapter.startListening();
        bookList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        if (adapter != null) {
            adapter.stopListening();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.startListening();
        }
    }


    private void updateProfileDetails() {
        try {
            username.setText(firebaseAuth.getCurrentUser().getDisplayName());
        } catch (Error NullPointerException) {
            username.setText("No username found.");
        }
        email.setText(firebaseAuth.getCurrentUser().getEmail());
    }
}
