package com.example.choonhee.hack2hired_21.activity.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.choonhee.hack2hired_21.R;
import com.example.choonhee.hack2hired_21.activity.BookStorePrice;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style._AppThemeActionBar);
        setContentView(R.layout.activity_main);


        Button searchButton = (Button) findViewById(R.id.search_button);

        final EditText editText = (EditText) findViewById(R.id.search_title_edit_text);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText.getText().equals("")) {
                    Intent intent = new Intent(getBaseContext(), BookSearchActivity.class);
                    intent.putExtra("bookTitle", editText.getText().toString());
                    startActivity(intent);
                }
            }
        });

    }
}
