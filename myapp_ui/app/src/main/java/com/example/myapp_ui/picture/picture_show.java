package com.example.myapp_ui.picture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Rect;
import android.os.Bundle;

import com.example.myapp_ui.R;

import android.view.View;


public class picture_show extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_main);
        RecyclerView gridCy = findViewById(R.id.rv_grid);

        //================加大缓存技巧
        gridCy.setItemViewCacheSize(20);
        gridCy.setDrawingCacheEnabled(true);
        gridCy.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        //================
        gridCy.setLayoutManager(new GridLayoutManager(picture_show.this, 3){//预留空间技巧
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        });
        gridCy.addItemDecoration(new MyDecoration(3,20,20));
        gridCy.setAdapter(new picture_show_adapter(picture_show.this));

    }

    //自定义修饰器 分割线
    class MyDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull  RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(50,10,20,getResources().getDimensionPixelOffset(R.dimen.dividerHeight));
        }
//    }

        private final String TAG = "GridSpaceItemDecoration";
        private int mSpanCount;//横条目数量
        private int mRowSpacing;//行间距
        private int mColumnSpacing;// 列间距

        /**
         * @param spanCount     列数3
         * @param rowSpacing    行间距2
         * @param columnSpacing 列间距
         */
        public  MyDecoration(int spanCount, int rowSpacing, int columnSpacing) {
            this.mSpanCount = spanCount;
            this.mRowSpacing = rowSpacing;
            this.mColumnSpacing = columnSpacing;
        }



    }
}