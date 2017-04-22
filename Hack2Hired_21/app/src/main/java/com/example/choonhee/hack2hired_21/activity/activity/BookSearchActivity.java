package com.example.choonhee.hack2hired_21.activity.activity;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.choonhee.hack2hired_21.R;
import com.example.choonhee.hack2hired_21.activity.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        bookSearchList = (ListView) findViewById(R.id.book_search_list);

        getBook();
    }

    private void getBook() {
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url = "http://172.16.0.91:8080/api/book/getBooks?bookTitle=java";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("resp", response);
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

                        bookSearchAdapter = new ArrayAdapter<Book>(getBaseContext(), R.layout.book_store_item, books);
                        bookSearchList.setAdapter(bookSearchAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
            }
        });

        requestQueue.add(stringRequest);

        requestQueue.start();
    }


    private class BookSearchAdapter extends ArrayAdapter {

        public BookSearchAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView;

            if (view != null) {
                Book book = (Book) getItem(position);


            }

            return super.getView(position, convertView, parent);
        }
    }
}
