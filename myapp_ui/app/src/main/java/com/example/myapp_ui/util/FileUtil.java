package com.example.myapp_ui.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.myapp_ui.domain.image_info;
import com.hjq.toast.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class FileUtil {
    Context context;
    Activity activity;
    MyNetworkUtil net;
    static String  preUrl="http://121.5.27.3:7778/image/";
    public FileUtil(Context context, Activity activity) {

        net = new MyNetworkUtil();
        net.init(context,activity);
        this.context=context;
        this.activity=activity;
    }

    public FileUtil() {

    }

    public void sync(){
//demo：http://121.5.27.3:7778/image/3FB4E1AC4E9F3A1F24919B6D52E3790A.jpg
        try {
            List<image_info> picnames = getPicnames();
            Log.d("nihao", "获取名字后："+picnames.toString());
            CountDownLatch countDownLatch = new CountDownLatch(picnames.size());
            for (image_info picname : picnames) {
                String name=picname.getImage_url();
                String url=preUrl+name;
                net.GetPhoto_save(url,name,countDownLatch);
            }

                //调用await方法阻塞当前线程，等待子线程完成后在继续执行
                countDownLatch.await();


        }catch(Exception e){
            Log.e("nihao", "something wrong");
            e.printStackTrace();
        }
        Log.d("nihao", "所有东西都加载完成   所有东西都加载完成    所有东西都加载完成    所有东西都加载完成");

    } String[] res;
    public List<image_info> getPicnames(){
        List<image_info> infos = null;
            try {
                String FileName = null;

                Thread thread = new Thread() {
                    public void run() {
                        res = net.geturl("http://121.5.27.3:7778/queryImageInfo");
                    }
                };
                thread.start();
                thread.join();//线程执行完毕后才执行下列代码
                String a = res[0];
                infos= JSON.parseArray(a, image_info.class);
            } catch (Exception e) {
                ToastUtils.show("连接服务器失败");
            }
//            获取本地数据
        String file_storage_address = "/ALove/";
        String dir = context.getFilesDir() + file_storage_address;
        JSONArray allFiles = file_traverse_util.getAllFiles(dir, ".jpg");
        List<image_info> sameFile=new ArrayList<>();
        try {
            for (int i = 0; i < allFiles.length() ;i++) {
                JSONObject jsonObject = allFiles.getJSONObject(i);
                String name = (String) jsonObject.get("name");
                name += ".jpg";
                for (image_info info : infos) {
                    String image_url = info.getImage_url();
                    if (name.equals(image_url))//云端和本地进行对比找出相同的文件
                    {
                        sameFile.add(info);
                        break;
                    }
                }
            }
            for (image_info image_info : sameFile) {
                infos.remove(image_info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*
            * 对infos进行筛选
            * */

        return infos;

    }




}



