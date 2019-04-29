package com.example.bookstore;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bookstore.Model.Book;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyAdapterViewHolder> {
    public Context c;
    public ArrayList<Book> arrayList;

    public MyAdapter(Context pC, ArrayList<Book> pArrayList) {
        this.c = pC;
        this.arrayList = pArrayList;
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
        final Book book = arrayList.get(i);

        myAdapterViewHolder.bookName.setText(book.bookTitle);
        myAdapterViewHolder.bookCondition.setText(book.condition);
        myAdapterViewHolder.bookPrice.setText(book.price);
        myAdapterViewHolder.bookInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, BookInfoActivity.class);
                intent.putExtra("book", book);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c.startActivity(intent);
            }
        });
    }

    public class MyAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView bookName;
        TextView bookCondition;
        TextView bookPrice;
        Button bookInfo;

        public MyAdapterViewHolder(View itemView) {
            super(itemView);
            bookName = (TextView) itemView.findViewById(R.id.tvlsBookName);
            bookCondition = (TextView) itemView.findViewById(R.id.tvlsBookCondition);
            bookPrice = (TextView) itemView.findViewById(R.id.tvlsBookPrice);
            bookInfo = (Button) itemView.findViewById(R.id.btnlsOpenBookInfo);
        }
    }

}