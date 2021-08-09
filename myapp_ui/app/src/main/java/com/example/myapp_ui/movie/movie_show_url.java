package com.example.myapp_ui.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myapp_ui.R;

public class movie_show_url extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_show_url);
        Bundle extras = getIntent().getExtras();
        String url = (String) extras.get("url");
        TextView page_info_url = findViewById(R.id.tv_movie_info_url);
        page_info_url.setText(url);

    }
}