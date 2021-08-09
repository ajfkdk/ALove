package com.example.myapp_ui.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.hjq.toast.ToastUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * @author ThirdGoddess
 * @email ofmyhub@gmail.com
 * @Github https://github.com/ThirdGoddess
 * @date :2020-03-14 10:11
 */
public class SaveNetPhotoUtils {

    private static final String file_storage_address="/ALove/";

    private static Context mContext;

    private static Bitmap bitmap;
    private static String mSaveMessage = "failed";



    /**
     * 保存图片，无须自定义名字
     *
     * @param context
     * @param photoUrl
     */
    public static void savePhoto(Context context, String photoUrl,CountDownLatch countDownLatch) {
        mContext = context;
//        photoUrls = photoUrl;
        Thread myThread = new saveFileRunnable_withoutname(photoUrl,countDownLatch);
        myThread.start();
//        new Thread(saveFileRunnable3(photoUrl)).start();
    }

    /**
     * 定义图片名字保存到相册
     *
     * @param context
     * @param photoUrl
     * @param photoName 图片名字，定义格式 name.jpg/name.png/...
     */
    public static void savePhoto(Context context, String photoUrl, String photoName, CountDownLatch countDownLatch) {
        mContext = context;

        Thread saveFileRunnable2 = new saveFileRunnable_withName(photoUrl,photoName,countDownLatch);
        saveFileRunnable2.start();
    }

//    public static class saveFileRunnable extends Thread{
//        @Override
//        public void run() {
//            try {
//                if (!TextUtils.isEmpty(photoUrls)) {
//                    URL url = new URL(photoUrls);
//                    InputStream inputStream = url.openStream();
//                    bitmap = BitmapFactory.decodeStream(inputStream);
//
//                }
//                saveFile(bitmap);
//                mSaveMessage = "success！";
//            } catch (IOException e) {
//                mSaveMessage = "failed";
//                e.printStackTrace();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            messageHandler.sendMessage(messageHandler.obtainMessage());
//        }
//    };

/*
* 自定义的saveFile线程
*
* */
    public static class saveFileRunnable_withoutname extends Thread{
    String pUrl = null;
    CountDownLatch countDownLatch;
    //    线程的构造方法
    public saveFileRunnable_withoutname(String photoUrl,CountDownLatch countDownLatch){
        this.countDownLatch=countDownLatch;
        pUrl = photoUrl;
    }
    @Override
    public void run() {
        try {
            if (!TextUtils.isEmpty(pUrl)) {//判断url是否为空
                URL url = new URL(pUrl);//使用url资源定位
                InputStream inputStream = url.openStream();//打开资源的数据流
                bitmap = BitmapFactory.decodeStream(inputStream);//解码数据流
//                    inputStream.close();//关闭输入流
            }
            saveFile(bitmap);
            mSaveMessage = "success！";
        } catch (IOException e) {
            mSaveMessage = "failed";
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("nihao", "thread is runingover");
        countDownLatch.countDown();
        messageHandler.sendMessage(messageHandler.obtainMessage());
    }
}


    public static class saveFileRunnable_withName extends Thread{
        String purl =null;
        CountDownLatch countDownLatch;
        String name;
        public saveFileRunnable_withName(String url,String name, CountDownLatch countDownLatch) {
            purl =url;
            this.countDownLatch=countDownLatch;
            this.name=name;
        }

        @Override
        public void run() {
            try {
                if (!TextUtils.isEmpty(purl)) {//判断url是否为空
                    URL url = new URL(purl);//使用url资源定位
                    InputStream inputStream = url.openStream();//打开资源的数据流
                    bitmap = BitmapFactory.decodeStream(inputStream);//解码数据流
//                    inputStream.close();//关闭输入流
                }
                saveFile(bitmap, name);
                mSaveMessage = "success！";
            } catch (IOException e) {
                mSaveMessage = "failed";
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            messageHandler.sendMessage(messageHandler.obtainMessage());
        }
    };

    /**
     * 保存成功和失败通知
     */
    @SuppressLint("HandlerLeak")
    private static Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            Toast.makeText(contexts, mSaveMessage, Toast.LENGTH_SHORT).show(); 普适性的语句
            ToastUtils.show(mSaveMessage);
        }
    };

    /**
     * 保存图片
     *
     * @param bm
     * @throws IOException
     */
    public static synchronized  void saveFile(Bitmap bm) throws IOException {
        String pathName = mContext.getFilesDir() + file_storage_address;
        File dirFile = new File(pathName);
        if (!dirFile.exists()) {
            if (dirFile.mkdir()) {
                Log.e("success", "建立文件成功");
            }
        }

        //图片命名
        String fileName = UUID.randomUUID().toString() + ".jpg";

        File myCaptureFile = new File(pathName+ fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();

        //广播通知相册有图片更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
    }

    /**
     * 保存图片
     *
     * @param bm
     * @param photoName 图片命名
     * @throws IOException
     */
    public static synchronized void saveFile(Bitmap bm, String photoName) throws IOException {
        String pathName = mContext.getFilesDir() + file_storage_address;
        File dirFile = new File(pathName);

        if (!dirFile.exists()) {
            if (dirFile.mkdir()) {
                Log.e("success", "建立文件成功");
            }
        }

        //图片命名后保存到相册
        File myCaptureFile = new File(pathName + photoName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
//        bos.close();

        //广播通知相册有图片更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
    }

}


