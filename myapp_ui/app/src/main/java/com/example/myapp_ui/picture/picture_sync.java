package com.example.myapp_ui.picture;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp_ui.R;
import com.hjq.toast.ToastUtils;

public class picture_sync extends AppCompatActivity {
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_sync);
    }

    @Override
    protected void onResume() {



//                progressDialog.setCancelable(false);    加载完成消失


        super.onResume();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub

        ToastUtils.show("同步完成");
        super.onWindowFocusChanged(hasFocus);

    }

}