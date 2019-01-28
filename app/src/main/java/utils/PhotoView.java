package utils;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.TextView;

import com.project.dimine.ynhj.R;

import adapter.PhotoViewPagerAdapter;
import butterknife.BindView;
import butterknife.OnClick;

public class PhotoView extends BaseActivity {
    @BindView(R.id.photo_viewPager)
    PhotoViewPager photo_viewPager;
    //点的是哪一张图片
    private int currentPosition;
    @BindView(R.id.photo_num)
    //数量显示
            TextView photo_num;
    private PhotoViewPagerAdapter adapter;
    //图片集合
    private String[] images;


    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_photo_view;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("position", 0);
        images = intent.getStringArrayExtra("urls");
        Log.e(TAG, images + "");
        adapter = new PhotoViewPagerAdapter(this, images);
        photo_viewPager.setAdapter(adapter);
        photo_viewPager.setCurrentItem(currentPosition);
        photo_num.setText(currentPosition + 1 + "/" + images.length);
        photo_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                photo_num.setText(currentPosition + 1 + "/" + images.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick(R.id.photo_view_back)
    void Back() {
        finish();
    }
}
