package com.example.myapp_ui.picture;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp_ui.R;
import com.example.myapp_ui.network.network_test;
import com.example.myapp_ui.util.MyNetworkUtil;
import com.hjq.toast.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class picturedownload_test extends AppCompatActivity {
    MyNetworkUtil net;
    String upLoadFilePath;
    String upLoadFileName;
    int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picturedownload_test);
        net = new MyNetworkUtil();
        net.init(this,this);


        Button showpic = findViewById(R.id.btn_show_testPicture);
        showpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dirFile = new File(Environment.getExternalStorageDirectory().getPath()+ "/ALove/");
                if (!dirFile.exists()) {
                    if (dirFile.mkdir()) {
                        Log.e("success", "建立文件成功");
                    }
                }
            }
        });
//        test_open_file_from_manager();
    }

    public void test_open_file_from_manager(){
        Button showpic = findViewById(R.id.btn_show_testPicture);
        showpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent,IMPORT_REQUEST_CODE);
            }
        });

    }
    public void upload_test(String path){
        ToastUtils.show("hello");

        String neturl="http://121.5.27.3:7778/uploadAudio";

        network_test network_test = new network_test();

        network_test.fileupload(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(picturedownload_test.this, "上传失败！", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(picturedownload_test.this, "上传成功！", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },path,neturl);
    }





    public void test_picture_download_from_net(){
        //=========================================================动态申请权限===========================================================================================


//        net.GetPhoto_save("http://www.mn94tv.cn/resources/images/xiaoxin.jpg","hello.jpg");
        Button showPicture = findViewById(R.id.btn_show_testPicture);
        ImageView image = findViewById(R.id.iv_test_input);
        Stack<Bitmap> bitmaps = buildPictureStack();

        showPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!bitmaps.empty()){
                    ToastUtils.show("现在的图片是第："+i+"个");
                    i++;
                    image.setImageBitmap(bitmaps.pop());
                }
                else {
                    ToastUtils.show("stack 里面没有数据了");
                }


            }
        });
    }
    public Stack<Bitmap> buildPictureStack(){
        downloadPicture();
        Stack<Bitmap> bitmaps = new Stack<Bitmap>();
        for (int i = 1; i < 5; i++) {
            Bitmap localImage_small_show = net.getLocalImage("ni"+".jpg");
            bitmaps.add(localImage_small_show);
        }
        return bitmaps;
    }
    public void downloadPicture(){

        try {
            CountDownLatch countDownLatch = new CountDownLatch(9);
            net.GetPhoto_save("https://hbimg.huabanimg.com/0f4ce7d922ec394cea266c510c803645b130f1607bbd0-OPqHHI_fw236/format/webp","ni.jpg",countDownLatch);
            net.GetPhoto_save("https://hbimg.huabanimg.com/82f031d7a6c16365c33e16c555452156ba6545eb93b0b-CaTkLT_fw658/format/webp",countDownLatch);
            net.GetPhoto_save("https://hbimg.huabanimg.com/373362be06a09df94f751212c13e1733f4209c5738585-EegDrH_fw658/format/webp",countDownLatch);
            net.GetPhoto_save("https://hbimg.huabanimg.com/be4ce4432652392b74c1d672f4d206a07089b829cc3d-wfY6Ag_fw658/format/webp",countDownLatch);
            net.GetPhoto_save("https://hbimg.huabanimg.com/cf772448ac2b11a2adc3b83dc0fe4ae1cf061ead21ead-quEdld_fw658/format/webp",countDownLatch);
            net.GetPhoto_save("https://hbimg.huabanimg.com/037d1f9a5210f19da5413a25c3250307e60facd616f5ba-PeEIIT_fw658/format/webp",countDownLatch);
            net.GetPhoto_save("https://hbimg.huabanimg.com/7f4fc05ee3e91660be9d99a6fd24998eb8e04e0634428-d7yVZe_fw658/format/webp",countDownLatch);
            net.GetPhoto_save("https://hbimg.huabanimg.com/f64621de9d5b4aa405c91a122c32f30d5679d37dcd15-Ee25Kt_fw658/format/webp",countDownLatch);
            net.GetPhoto_save("https://hbimg.huabanimg.com/623e279cbff8c48981732e63c4dcfc6e3174af5317a4e-APzC2v_fw658/format/webp",countDownLatch);
            try {
                //调用await方法阻塞当前线程，等待子线程完成后在继续执行
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }catch(Exception e){
            Log.e("nihao", "something wrong");
            e.printStackTrace();
        }
        Log.d("nihao", "所有东西都加载完成   所有东西都加载完成    所有东西都加载完成    所有东西都加载完成");
    }


    //定义 请求返回码
    public static final  int IMPORT_REQUEST_CODE=10005;

    public void openFileManager(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");//设置类型，我这里是任意类型，可以过滤文件类型
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,IMPORT_REQUEST_CODE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMPORT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();
                if (uri != null) {
                    String path = getPath(this, uri);
                    if (path != null) {
                        File file = new File(path);
                        if (file.exists()) {
                             upLoadFilePath = file.toString();
                             upLoadFileName = file.getName();
                             upload_test(upLoadFilePath);
                        }
                    }
                }
            }
            Log.e("导入失败","");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public void delete_localFile(){}


}