package com.example.myapp_ui.network;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class network_test {

    public static void fileupload(Callback callback,String filePath,String netUrl) {
      final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpeg");//设置文件类型
        // 设置文件在手机里面的路径
        String path = filePath;
        File file = new File(path);//创建文件对象
        OkHttpClient client ;//定义请求器

        //=============================================
//      把 日志拦截器 放入请求器
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLogger());
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .addNetworkInterceptor(logInterceptor)
                .build();
        //=============================================

// 上传文件使用MultipartBody.Builder
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("upload", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file)) // 提交图片，第一个参数是键（name="第一个参数"），第二个参数是文件名，第三个是一个RequestBody
                .build();
        // POST请求
        Request request = new Request.Builder()
                .url(netUrl)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
//==============================打印log===================================
    public static class HttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Log.d("HttpLogInfo", message);
        }
    }




}

