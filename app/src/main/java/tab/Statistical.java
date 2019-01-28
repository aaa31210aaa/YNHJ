package tab;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.project.dimine.ynhj.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.DayStatistical;
import fragment.MonthStatistical;
import fragment.YearStatistical;
import utils.BaseFragment;


/**
 * 统计分析
 */
public class Statistical extends BaseFragment {
    private View view;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.statistical_tablayout)
    TabLayout tablayout;
    @BindView(R.id.statistical_viewpager)
    ViewPager statistical_viewpager;


    public Statistical() {
        // Required empty public constructor
    }

    @Override
    public View makeView() {
        view = View.inflate(getActivity(), R.layout.fragment_statistical, null);
        //绑定fragment
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {
        title_name.setText(R.string.tjfx);
        initTab();
    }

    private void initTab(){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DayStatistical());
        fragments.add(new MonthStatistical());
        fragments.add(new YearStatistical());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager(), fragments, new String[]{"日报表","月报表","年报表"});
        statistical_viewpager.setOffscreenPageLimit(2);
        statistical_viewpager.setAdapter(adapter);

        //关联图文
        tablayout.setupWithViewPager(statistical_viewpager);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                statistical_viewpager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
