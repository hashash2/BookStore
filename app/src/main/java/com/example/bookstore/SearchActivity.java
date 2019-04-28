package com.example.bookstore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseUserSearch();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Book");

        options = new FirebaseRecyclerOptions.Builder<Book>().setQuery(databaseReference, Book.class).build();

        adapter = new FirebaseRecyclerAdapter<Book, BookViewHolder>() {
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

        RecyclerView.LayoutManager layoutManager = new RecyclerView.LayoutManager() {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return null;
            }
        };
        results.setLayoutManager(layoutManager);
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

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {
        public Context c;
        public ArrayList<Book>  arrayList;

        public MyAdapter(Context pC, ArrayList<Book> pArrayList) {
            this.c = pC;
            this.arrayList = pArrayList
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_layout, viewGroup,false);

            return new MyAdapterViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapterViewHolder myAdapterViewHolder, int i) {
            Book book = arrayList.get(i);

            myAdapterViewHolder.bookName.setText(book.bookTitle);
            myAdapterViewHolder.bookCondition.setText(book.condition);
            myAdapterViewHolder.bookPrice.setText(book.price);
        }

        public class MyAdapterViewHolder extends RecyclerView.ViewHolder {
            TextView bookName;
            TextView bookCondition;
            TextView bookPrice;

            public MyAdapterViewHolder(View itemView) {
                super(itemView);
                bookName = (TextView) itemView.findViewById(R.id.tvlsBookName);
                bookCondition = (TextView) itemView.findViewById(R.id.tvlsBookCondition);
                bookPrice = (TextView) itemView.findViewById(R.id.tvlsBookPrice);
            }
        }

    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        TextView bookCondition;
        TextView bookPrice;
        public BookViewHolder(View itemView) {
           super(itemView);

           bookName = (TextView) itemView.findViewById(R.id.tvlsBookName);
           bookCondition = (TextView) itemView.findViewById(R.id.tvlsBookCondition);
           bookPrice = (TextView) itemView.findViewById(R.id.tvlsBookPrice);
       }
    }
}
