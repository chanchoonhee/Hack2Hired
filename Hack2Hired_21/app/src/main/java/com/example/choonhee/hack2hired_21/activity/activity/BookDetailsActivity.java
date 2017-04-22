package com.example.choonhee.hack2hired_21.activity.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.choonhee.hack2hired_21.R;
import com.example.choonhee.hack2hired_21.activity.Book;
import com.example.choonhee.hack2hired_21.activity.BookStorePrice;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {

    private BookStoreAdapter bookStoreAdapter;

    private ListView bookStoreList;

    private List<BookStorePrice> bookStorePrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style._AppThemeActionBar);
        setContentView(R.layout.activity_book_details);

        Book book = (Book) getIntent().getSerializableExtra("bookDetails");

        TextView title = (TextView) findViewById(R.id.title);
        TextView author = (TextView) findViewById(R.id.author);
        TextView isbn = (TextView) findViewById(R.id.isbn);
        TextView desc = (TextView) findViewById(R.id.description);

        title.setText(book.getName());
        author.setText("Author: " + book.getAuthor());
        isbn.setText("ISBN: " + book.getISBN());
        desc.setText(book.getDesc());

        ImageView bookImage = (ImageView) findViewById(R.id.book_image);

        Picasso.with(getBaseContext()).load(book.getImageUrl()).into(bookImage);

        bookStoreList = (ListView) findViewById(R.id.book_store_list);

        bookStoreAdapter = new BookStoreAdapter(getBaseContext(), R.layout.book_store_item, bookStorePrices);

        bookStoreList.setAdapter(bookStoreAdapter);
    }

    private void getBookStorePrice(String isbn) {
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url = "http://localhost:8080/api/book/getDeal?isbn=" + isbn;

        Log.d("Get Book Prices", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("resp", response);
                bookStorePrices.clear();

                try {
                    JSONObject allBookStorePrices = new JSONObject(response);
                    JSONObject bookurve = allBookStorePrices.get("bookurve");
                    JSONObject mph = allBookStorePrices.get("MPH");
                    JSONObject bookxcessonline = allBookStorePrices.get("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                builder.setMessage("Sorry, error occured. Please try again.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                builder.create().show();
                //Log.d("Error", error.getMessage());
            }
        });

        requestQueue.add(stringRequest);

        requestQueue.start();
    }

    private class BookStoreAdapter extends ArrayAdapter<BookStorePrice> {

        private final LayoutInflater mInflater;

        public BookStoreAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<BookStorePrice> objects) {
            super(context, resource, objects);
            mInflater = getLayoutInflater().from(context);
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
