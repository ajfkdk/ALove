package com.example.myapp_ui.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyNetworkUtil {

    private static Context mContext;
    private static final String file_storage_address="/ALove/";
    //动态申请存储权限
    public void init(Context context, Activity activity){
        mContext = context;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    //获取图片并保存  有名字
    public void GetPhoto_save(String url,String photoName, CountDownLatch countDownLatch){
        SaveNetPhotoUtils.savePhoto(mContext, url, photoName,countDownLatch);
    }

    //获取图片并保存
    public void GetPhoto_save(String url, CountDownLatch countDownLatch){
        SaveNetPhotoUtils.savePhoto(mContext, url,countDownLatch);
    }

    //通过绝对路径获取本地文件图片的bitmap文件
    public static Bitmap getLocalImage(String imageName){
        String prefixname =mContext.getFilesDir() + file_storage_address;
        String imageUrl=prefixname+imageName;
        File file = new File(imageUrl);
        try{
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            in.close();
            return bitmap;
        }catch(Exception e){
            Log.e("error in get image", "文件打开失败");
        }
      return null;

    }

    public String[]  geturl(Activity activity, String url){
         String[] result = new String[1];
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        Call call = client.newCall(request);
        //同步调用,返回Response,会抛出IO异常
        try {
            Response response = call.execute();
            String res = response.body().string();
            Log.d("nihao", res);
            result[0] =res;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;//小技巧利用数组指针把值传出来
    }






























































}
