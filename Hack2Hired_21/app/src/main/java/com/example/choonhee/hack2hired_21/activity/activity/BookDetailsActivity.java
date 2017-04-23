package com.example.choonhee.hack2hired_21.activity.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.choonhee.hack2hired_21.R;
import com.example.choonhee.hack2hired_21.activity.book.Book;
import com.example.choonhee.hack2hired_21.activity.book.BookStorePrice;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BookDetailsActivity extends AppCompatActivity {

    private BookStoreAdapter bookStoreAdapter;

    private ListView bookStoreList;

    private List<BookStorePrice> bookStorePrices = new ArrayList<>();

    private String recommendedBookStore = "";

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

        bookStoreList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BookStorePrice bookStorePrice = bookStorePrices.get(position);
                String url = bookStorePrice.getUrl();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        getBookStorePrice(book.getISBN());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getBookStorePrice(String isbn) {
        RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
        String url = "http://172.16.0.91:8080/api/book/getDeal?isbn=" + isbn;

        Log.d("Get Book Prices", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("resp", response);
                bookStorePrices.clear();

                JSONObject allBookStorePrices = null;
                try {
                    allBookStorePrices = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if (!allBookStorePrices.getJSONObject("Best").get("Deal").equals("")) {
                        recommendedBookStore = (String) allBookStorePrices.getJSONObject("Best").get("Deal");

                        JSONObject mph = null;
                        try {
                            mph = (JSONObject) allBookStorePrices.get("MPH");
                            if (mph != null) {
                                BookStorePrice bookStorePrice = new BookStorePrice();
                                bookStorePrice.setName("MPH");
                                bookStorePrice.setPrice((Double) mph.get("price"));
                                bookStorePrice.setUrl((String) mph.get("url"));
                                bookStorePrices.add(bookStorePrice);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JSONObject bookurve = null;
                        try {
                            bookurve = (JSONObject) allBookStorePrices.get("Bookurve");
                            if (bookurve != null) {
                                BookStorePrice bookStorePrice = new BookStorePrice();
                                bookStorePrice.setName("Bookurve");
                                bookStorePrice.setPrice((Double) bookurve.get("price"));
                                bookStorePrice.setUrl((String) bookurve.get("url"));
                                bookStorePrices.add(bookStorePrice);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        try {
                            JSONObject bookxcessonline = (JSONObject) allBookStorePrices.get("BookXcessOnline");
                            if (bookxcessonline != null) {
                                BookStorePrice bookStorePrice = new BookStorePrice();
                                bookStorePrice.setName("BookXcessOnline");
                                bookStorePrice.setPrice((Double) bookxcessonline.get("price"));
                                bookStorePrice.setUrl((String) bookxcessonline.get("url"));
                                bookStorePrices.add(bookStorePrice);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(BookDetailsActivity.this, "No results found!", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                

                bookStoreAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookDetailsActivity.this, "Please try again later", Toast.LENGTH_SHORT).show();
                /*AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                builder.setMessage("Sorry, error occured. Please try again.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        });
                builder.create().show();*/
                //Log.d("Error", error.getMessage());
            }
        });

        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

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
            BookStorePrice bookStorePrice = bookStorePrices.get(position);

            convertView = mInflater.inflate(R.layout.book_store_item, parent, false);

            TextView name = (TextView) convertView.findViewById(R.id.bookstore_name);
            TextView price = (TextView) convertView.findViewById(R.id.book_price);
            //ImageView bookImage = (ImageView) convertView.findViewById(R.id.book_img);

            name.setText(bookStorePrice.getName());
            DecimalFormat decimalFormat = new DecimalFormat("####0.00");
            price.setText("RM " + decimalFormat.format(bookStorePrice.getPrice()));
            if (bookStorePrice.getName().equals(recommendedBookStore)) {
                ImageView recommendedIcon = (ImageView) convertView.findViewById(R.id.recommended_icon);
                recommendedIcon.setVisibility(View.VISIBLE);
            }

            ImageView bookImg = (ImageView) convertView.findViewById(R.id.book_img);

            switch (bookStorePrice.getName()) {
                case "Bookurve":
                    bookImg.setImageResource(R.drawable.bookurve);
                    break;
                case "MPH":
                    bookImg.setImageResource(R.drawable.mphonline);
                    break;
                case "BookXcessOnline":
                    bookImg.setImageResource(R.drawable.bookxcess);
                    break;
            }

            //Picasso.with(getBaseContext()).load(book.getImageUrl()).into(bookImage);

            return convertView;
        }
    }
}
