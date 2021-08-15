package com.zs.itking.viewpagedialog;


import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;


public class ItHeiMaDialog extends DialogFragment {
    public int[] mImages;
    private ArrayList<View> pageViews;
    private int mPosition;
    private boolean isDragging;
    private ViewPager.PageTransformer mPageTransformer;
    public static ItHeiMaDialog instance = null;
    private boolean mIsCancel;
    private boolean mIsTransparent;

    public ItHeiMaDialog() {}
    public static ItHeiMaDialog getInstance() {
            if (instance == null) {
                instance = new ItHeiMaDialog();
            }
            return instance;
    }

    /**
     * 设置图片
     * @param images
     * @return
     */
    public ItHeiMaDialog setImages(int[] images) {
        mImages = images;
        return this;
    }

    /**
     * 设置ViewPager切换动画方式
     * @param pageTransformer
     * @return
     */
    public ItHeiMaDialog setPageTransformer(ViewPager.PageTransformer pageTransformer) {
        mPageTransformer = pageTransformer;
        return this;
    }



    public ItHeiMaDialog show(android.app.FragmentManager fragmentManager) {
        if (instance != null) {
            instance.show(fragmentManager, "ZqgDialogFragment");
        }
        return this;
    }

    /**
     * 点击四周是否取消dialog,默认取消
     * @param isCancel
     * @return
     */
    public ItHeiMaDialog setCanceledOnTouchOutside(boolean isCancel) {
        mIsCancel = isCancel;
        return this;
    }
    /**
     * 设置背景四周是否透明,调用时需要放到show方法后面
     * @param isTransparent
     * @return
     */
    public ItHeiMaDialog setOutsideIsTransparent(boolean isTransparent) {
        mIsTransparent = isTransparent;
        return this;
    }

    public ItHeiMaDialog dissmiss() {
        getDialog().dismiss();
        return this;
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Fresco.initialize(getActivity());
        pageViews = new ArrayList<>();
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        if (mIsCancel) {
            getDialog().setCanceledOnTouchOutside(mIsCancel);
        }
        View view = inflater.inflate(R.layout.fragment_dialog, container);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        for (int image : mImages) {
            View inflate = inflater.inflate(R.layout.guide_pager_four, null);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.iv_item_guide_img);
            Uri uri = new Uri.Builder().scheme("res").path(String.valueOf(image)).build();
            imageView.setImageURI(uri);
            pageViews.add(imageView);
        }
        viewPager.setPageTransformer(true, mPageTransformer);
        viewPager.setAdapter(new ZqgPagerAdapter());
        indicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (state) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        isDragging = true;
                        break;
                    case ViewPager.SCROLL_STATE_SETTLING:
                        isDragging = false;
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        if (mPosition == pageViews.size() - 1 && isDragging) {
                            getDialog().dismiss();
                        }
                        break;
                }

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIsTransparent) {
            Window window = getDialog().getWindow();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.0f;
            window.setAttributes(windowParams);
        }
    }

    class ZqgPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageViews.get(position));
            return pageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pageViews.get(position));
        }
    }
}
