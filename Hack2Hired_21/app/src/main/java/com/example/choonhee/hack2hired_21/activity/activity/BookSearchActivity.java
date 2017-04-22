package com.example.choonhee.hack2hired_21.activity.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
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
import android.widget.AdapterView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BookSearchActivity extends AppCompatActivity {

    List<Book> books = new ArrayList<>();

    ArrayAdapter<Book> bookSearchAdapter;

    ListView bookSearchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style._AppThemeActionBar);
        setContentView(R.layout.activity_book_search);

        getSupportActionBar().setTitle("Search Results");

        String title = getIntent().getStringExtra("bookTitle");

        TextView titleTextView = (TextView) findViewById(R.id.search_book_title);
        titleTextView.setText(title);
        bookSearchList = (ListView) findViewById(R.id.book_search_list);

        bookSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book book = books.get(position);
                Intent intent = new Intent(getBaseContext(), BookDetailsActivity.class);
                intent.putExtra("bookDetails", book);
                startActivity(intent);
            }
        });

        bookSearchAdapter = new BookSearchAdapter(getBaseContext(), R.layout.book_retrieved_item, books);
        bookSearchList.setAdapter(bookSearchAdapter);

        getBook(title);
    }

    private void getBook(String title) {
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url = "http://172.16.0.91:8080/api/book/getBooks?bookTitle=" + title;

        Log.d("Get Book Request", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("resp", response);
                books.clear();
                try {
                    JSONArray allBooks = new JSONArray(response);
                    if (allBooks.length() > 0) {
                        for (int i=0;i<allBooks.length();i++) {
                            JSONObject bookObj = (JSONObject) allBooks.get(i);
                            String name = (String) bookObj.get("name");
                            Log.d("name", name);
                            String desc = "";
                            desc = (String) bookObj.get("desc");
                            String author = (String) bookObj.get("author");
                            String imageUrl = (String) bookObj.get("imageUrl");
                            String isbn = (String) bookObj.get("isbn");
                            Book book = new Book();
                            book.setAuthor(author);
                            book.setName(name);
                            book.setDesc(desc);
                            book.setImageUrl(imageUrl);
                            book.setISBN(isbn);
                            books.add(book);

                        }

                        bookSearchAdapter.notifyDataSetChanged();

                    }
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


    private class BookSearchAdapter extends ArrayAdapter<Book> {

        private final LayoutInflater mInflater;

        public BookSearchAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
            super(context, resource, objects);
            mInflater = getLayoutInflater().from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Book book = books.get(position);
            Log.d("Book", book.toString());

            convertView = mInflater.inflate(R.layout.book_retrieved_item, parent, false);

            TextView title = (TextView) convertView.findViewById(R.id.book_title);
            TextView author = (TextView) convertView.findViewById(R.id.book_author);
            ImageView bookImage = (ImageView) convertView.findViewById(R.id.book_img);

            title.setText(book.getName());
            author.setText(book.getAuthor());

            Picasso.with(getBaseContext()).load(book.getImageUrl()).into(bookImage);

            return convertView;
        }



    }
}
