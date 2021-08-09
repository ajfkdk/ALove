package com.example.myapp_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapp_ui.movie.movie_search;
import com.example.myapp_ui.picture.picture_main_function;
import com.example.myapp_ui.picture.picture_show;
import com.example.myapp_ui.picture.picturedownload_test;

public class MainActivity extends AppCompatActivity {
    Button btn_movie,btn_picture,btn_note_lin,btn_note_chen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//===========================================movie part=========================================================================
        btn_movie = findViewById(R.id.btn_movie);
        btn_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, movie_search.class);
                startActivity(intent);
            }
        });
//===========================================picture part=========================================================================

        btn_picture = findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, picture_main_function.class);
                startActivity(intent);
            }
        });
//===========================================note_chen part=========================================================================
        btn_note_chen = findViewById(R.id.btn_note_chen);
        btn_note_chen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, picturedownload_test.class);
                startActivity(intent);
            }
        });
    }
}