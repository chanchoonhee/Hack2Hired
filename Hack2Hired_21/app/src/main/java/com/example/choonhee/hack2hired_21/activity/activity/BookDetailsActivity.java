package com.example.choonhee.hack2hired_21.activity.activity;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.choonhee.hack2hired_21.R;
import com.example.choonhee.hack2hired_21.activity.BookStorePrice;

import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style._AppThemeActionBar);
        setContentView(R.layout.activity_book_details);
    }

    private class bookStoreAdapter extends ArrayAdapter<BookStorePrice> {

        public bookStoreAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<BookStorePrice> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if (view != null) {
            }
            return super.getView(position, convertView, parent);
        }
    }
}
