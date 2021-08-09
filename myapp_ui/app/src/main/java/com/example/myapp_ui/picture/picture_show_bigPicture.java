package com.example.myapp_ui.picture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapp_ui.R;
import com.example.myapp_ui.util.MyNetworkUtil;
import com.hjq.toast.ToastUtils;

public class picture_show_bigPicture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_main_show);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        MyNetworkUtil net = new MyNetworkUtil();
        net.init(this,this);

        String bitmap_name = (String) extras.get("bitmap");

        Bitmap localImage_small_show = net.getLocalImage(bitmap_name);

        ImageView imageView = findViewById(R.id.picture_main_showPic);
        imageView.setImageBitmap(localImage_small_show);

        ToastUtils.show("成功跳转");
    }
}