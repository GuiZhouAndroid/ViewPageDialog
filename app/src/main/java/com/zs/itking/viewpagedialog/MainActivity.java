package com.zs.itking.viewpagedialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button button;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    private void showDialog() {
        //仿转转轮播样式
        ItHeiMaDialog.getInstance()
                .setImages(new int[]{R.drawable.new_user_guide_1, R.drawable.new_user_guide_2, R.drawable.new_user_guide_3, R.drawable.new_user_guide_4})
                .setCanceledOnTouchOutside(true)
                .setPageTransformer(new ZoomOutPageTransformer())
                .show(getFragmentManager());
        //左右平滑
//        ItHeiMaDialog.getInstance()
//                .setImages(new int[]{R.drawable.new_user_guide_1, R.drawable.new_user_guide_2, R.drawable.new_user_guide_3, R.drawable.new_user_guide_4})
//                .show(getFragmentManager());

        //浅度重叠
//        ItHeiMaDialog.getInstance()
//                .setImages(new int[]{R.drawable.new_user_guide_1,R.drawable.new_user_guide_2,R.drawable.new_user_guide_3, R.drawable.new_user_guide_4})
//                .setPageTransformer(new DepthPageTransformer())
//                .show(getFragmentManager());

    }

}
