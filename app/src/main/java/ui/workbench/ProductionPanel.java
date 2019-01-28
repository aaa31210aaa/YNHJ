package ui.workbench;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.project.dimine.ynhj.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.ViewPagerAdapter;
import butterknife.BindView;
import butterknife.OnClick;
import ui.mine.MonthlyDetailed;
import utils.BaseActivity;
import utils.CustomDatePicker;
import utils.DateUtils;


import static utils.CustomDatePicker.pvCustomTime;

public class ProductionPanel extends BaseActivity {
    private static final String[] CHANNELS = new String[]{"月数据总量", "月详细数据"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    TextView title_name_right;
    @BindView(R.id.production_indicator)
    MagicIndicator production_indicator;
    @BindView(R.id.production_viewpager)
    ViewPager production_viewpager;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        title_name.setText(R.string.productionpanel_title);
        title_name_right.setText(DateUtils.getStringDate());
        CustomDatePicker.initCustomTimePicker(this, title_name_right);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TotalMonthly());
        fragments.add(new MonthlyDetailed());

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        production_viewpager.setOffscreenPageLimit(1);
        production_viewpager.setAdapter(adapter);
        initMagicIndicator();
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_production_panel;
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }


    @OnClick(R.id.title_name_right)
    void ChooseMonth() {
        if (pvCustomTime != null)
            pvCustomTime.show();
    }

    private void initMagicIndicator() {
        production_indicator.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                clipPagerTitleView.setClipColor(Color.WHITE);
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        production_viewpager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        production_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(production_indicator, production_viewpager);
    }
}
