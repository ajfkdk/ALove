package com.example.myapp_ui.picture;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp_ui.R;
import com.example.myapp_ui.util.MyNetworkUtil;
import com.example.myapp_ui.util.file_traverse_util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class picture_show_adapter extends RecyclerView.Adapter<picture_show_adapter.myViewHolder> {
    PopupWindow mPopWindow;
    private Context mCOntext;
    private JSONArray allfiles;

    public picture_show_adapter(Context context) {
        mCOntext = context;
    }

    //  遍历安卓手机里面的文件夹文件
    public JSONArray traverse_anroid_file() {
        String file_storage_address = "/ALove/";
        String dir = mCOntext.getFilesDir() + file_storage_address;
        return file_traverse_util.getAllFiles(dir, ".jpg");
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        myViewHolder myViewHolder = new myViewHolder(LayoutInflater.from(mCOntext).inflate(R.layout.activity_picture_main_item, null));//使用该构造方法就不会出现下拉元布局边长的问题
        LayoutInflater inflater = LayoutInflater.from(mCOntext);

        return myViewHolder;
    }

    /*

    执行渲染页面的操作

    */
    String path = null;
    String name = null;
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {


        try {
            JSONObject jsonObject = allfiles.getJSONObject(position);
            path = jsonObject.getString("path");
            name = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
//点击时间
        holder.imageView.setImageBitmap(MyNetworkUtil.getLocalImage(name + ".jpg"));

        String finalName = name;//解决

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCOntext, picture_show_bigPicture.class);
                intent.putExtra("bitmap", finalName + ".jpg");
                mCOntext.startActivity(intent);
            }
        });
//长按事件
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                View mPopView = LayoutInflater.from(mCOntext).inflate(R.layout.popwindow, null);

                TextView viewById = mPopView.findViewById(R.id.tv_deleteImg);
                viewById.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPopWindow.dismiss();
                        String deletePath = mCOntext.getFilesDir() + "/ALove/"+finalName+".jpg";
                        file_traverse_util.deleteSingleFile(deletePath);
                        //向服务器发送请求"http://121.5.27.3:7778/queryImageInfo"
                        String urlString = "http://121.5.27.3:7778/aaa?image_name="+finalName+".jpg";
                        try {
                            MyNetworkUtil.deleteUrl(urlString);
                        } catch (Exception e) {
                            Log.e("nihao","net delete wrong!");
                            e.printStackTrace();
                        }
//                        重新刷新界面
                        Intent intent = new Intent(mCOntext, picture_show.class);
                        mCOntext.startActivity(intent);
                    }
                });
                mPopWindow= new PopupWindow(mPopView, holder.imageView.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
                mPopWindow.setOutsideTouchable(true);
                mPopWindow.setFocusable(true);
                mPopWindow.showAsDropDown(holder.imageView);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        allfiles = traverse_anroid_file();//获取全部文件信息
        return allfiles.length();//返回文件个数
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.iv_grid);
        }
    }



}