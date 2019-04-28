package com.example.bookstore;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookstore.Model.Book;
import com.example.bookstore.ViewHolder.BookViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText keyword;
    private RecyclerView results;
    private Button search;

    ArrayList<Book> arrayList;

    DatabaseReference databaseReference;
    FirebaseRecyclerOptions<Book> options;
    FirebaseRecyclerAdapter<Book, BookViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        keyword = (EditText) findViewById(R.id.etSearchTerms);

        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    firebaseUserSearch(s.toString());
                } else {
                    firebaseUserSearch("");
                }

            }
        });

        arrayList = new ArrayList<>();
        results = (RecyclerView) findViewById((R.id.rvResults));
        results.setHasFixedSize(true);
        search = (Button) findViewById(R.id.btnQuery);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Books").child("Book");

        options = new FirebaseRecyclerOptions.Builder<Book>().setQuery(databaseReference, Book.class).build();

        adapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull Book model) {
                holder.bookName.setText(model.bookTitle);
                holder.bookCondition.setText(model.condition);
                holder.bookPrice.setText(model.price);
            }

            @NonNull
            @Override
            public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout,viewGroup,false);
                return new BookViewHolder(view);
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        results.setLayoutManager(gridLayoutManager);
        adapter.startListening();
        results.setAdapter(adapter);
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

    private void firebaseUserSearch(String input) {
        Query query = databaseReference.orderByChild("courseName").startAt(input).endAt(input + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    arrayList.clear();
                    for (DataSnapshot dss: dataSnapshot.getChildren()) {
                        final Book book = dss.getValue(Book.class);
                        arrayList.add(book);
                    }
                }

                MyAdapter myAdapter = new MyAdapter(getApplicationContext(), arrayList);
                results.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
