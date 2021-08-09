package com.example.myapp_ui.picture;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        String path = null;
        String name = null;

        try {
            JSONObject jsonObject = allfiles.getJSONObject(position);
            path = jsonObject.getString("path");
            name = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.imageView.setImageBitmap(MyNetworkUtil.getLocalImage(name + ".jpg"));

        String finalName = name;
        holder.itemView.setOnClickListener(new View.OnClickListener() {//点击事件
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCOntext, picture_show_bigPicture.class);
                intent.putExtra("bitmap", finalName + ".jpg");
                mCOntext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        allfiles = traverse_anroid_file();//获取全部文件信息
        return allfiles.length();//返回文件个数
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_title_grid);
            imageView = itemView.findViewById(R.id.iv_grid);
        }
    }


}