package com.example.myapp_ui.movie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapp_ui.R;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.WhiteToastStyle;

public class movie_search extends AppCompatActivity {
    private EditText movie_name;
    private Button search_button;
    private String name_getted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_search);

        search_button = findViewById(R.id.btn_movie_search);

        movie_name = findViewById(R.id.et_search_input);
//        监听键盘的回车键实现回车搜索功能
        movie_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    name_getted = movie_name.getText().toString();
                    Intent intent = new Intent(movie_search.this, movie_info.class);
                    intent.putExtra("movie_name", name_getted);
                    startActivity(intent);
                }
                return false;
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_getted = movie_name.getText().toString();
                Intent intent = new Intent(movie_search.this, movie_info.class);
                intent.putExtra("movie_name", name_getted);
                startActivity(intent);
            }
        });
    }

    public void showinfo(String text) {
        ToastUtils.setStyle(new WhiteToastStyle());//白色
        ToastUtils.setGravity(Gravity.BOTTOM);//底部
        ToastUtils.show(text);
    }

}