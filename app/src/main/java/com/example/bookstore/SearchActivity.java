package com.example.bookstore;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class SearchActivity extends AppCompatActivity {

    private EditText keyword;
    private RecyclerView results;
    private Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        keyword = (EditText) findViewById(R.id.etSearchTerms);
        results = (RecyclerView) findViewById((R.id.rvResults));
        search = (Button) findViewById(R.id.btnQuery);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUserSearch();
            }
        });


    }

    private void firebaseUserSearch() {
//        FirebaseRecyclerAdapter<Book, BookViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>() {
//            @Override
//            protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull Book model) {
//
//
//            }
//
//            @NonNull
//            @Override
//            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                return null;
//            }
//        }
    }

    //might have imported wrong view
    public class BookViewHolder extends RecyclerView.ViewHolder {
        View mView;
        public BookViewHolder(View itemView) {
           super(itemView);

           mView = itemView;
       }
    }
}
