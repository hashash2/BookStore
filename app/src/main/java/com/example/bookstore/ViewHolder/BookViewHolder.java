package com.example.bookstore.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bookstore.R;

public class BookViewHolder extends RecyclerView.ViewHolder {
    public TextView bookName;
    public TextView bookCondition;
    public TextView bookPrice;
    public BookViewHolder(View itemView) {
        super(itemView);

        bookName = (TextView) itemView.findViewById(R.id.tvlsBookName);
        bookCondition = (TextView) itemView.findViewById(R.id.tvlsBookCondition);
        bookPrice = (TextView) itemView.findViewById(R.id.tvlsBookPrice);
    }
}