package ui.planreport;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.project.dimine.ynhj.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdapter;
import butterknife.BindView;
import butterknife.OnClick;
import fragment.DayPlanReport;
import fragment.MonthPlanReport;
import fragment.YearPlanReport;
import utils.BaseActivity;
import utils.StatusBarUtils;

public class PlanReport extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.plan_report_radiogroup)
    RadioGroup plan_report_radiogroup;
    @BindView(R.id.plan_report_viewpager)
    ViewPager plan_report_viewpager;
    @BindView(R.id.day_report_rbt)
    RadioButton day_report_rbt;
    @BindView(R.id.month_report_rbt)
    RadioButton month_report_rbt;
    @BindView(R.id.year_report_rbt)
    RadioButton year_report_rbt;


    private List<Fragment> reportFragments;
    private ViewPagerAdapter adapter;
    private int[] rbs = {R.id.day_report_rbt, R.id.month_report_rbt, R.id.year_report_rbt};

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_year_plan_report;
    }

    @Override
    protected void initView() {
        initViewPager();
    }

    @Override
    protected void initData() {
        StatusBarUtils.initStatusBarColor(this, R.color.report_bg);
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    private void initViewPager() {
        reportFragments = new ArrayList<>();
        reportFragments.add(new DayPlanReport());
        reportFragments.add(new MonthPlanReport());
        reportFragments.add(new YearPlanReport());

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), reportFragments);
        plan_report_viewpager.setOffscreenPageLimit(2);
        plan_report_viewpager.setAdapter(adapter);
        plan_report_viewpager.setCurrentItem(0);
        //ViewPager页面切换监听
        plan_report_viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        day_report_rbt.setChecked(true);
        day_report_rbt.setTextColor(getResources().getColor(R.color.report_1));
        plan_report_radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.day_report_rbt:
                        plan_report_viewpager.setCurrentItem(0);
                        day_report_rbt.setTextColor(getResources().getColor(R.color.report_1));
                        break;
                    case R.id.month_report_rbt:
                        plan_report_viewpager.setCurrentItem(1);
                        month_report_rbt.setTextColor(getResources().getColor(R.color.report_1));
                        break;
                    case R.id.year_report_rbt:
                        plan_report_viewpager.setCurrentItem(2);
                        year_report_rbt.setTextColor(getResources().getColor(R.color.report_1));
                        break;
                }
            }
        });
    }


    /**
     * ViewPager切换Fragment,RadioGroup做相应变化
     */
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            plan_report_radiogroup.check(rbs[position]);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.day_report_rbt:
                //ViewPager显示第一个Fragment且关闭页面切换动画效果
                plan_report_viewpager.setCurrentItem(0, true);
                break;
            case R.id.month_report_rbt:
                plan_report_viewpager.setCurrentItem(1, true);
                break;
            case R.id.year_report_rbt:
                plan_report_viewpager.setCurrentItem(2, true);
                break;
        }
    }
}
