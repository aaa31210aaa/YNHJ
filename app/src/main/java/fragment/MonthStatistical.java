package fragment;


import android.support.v4.app.Fragment;
import android.view.View;

import com.project.dimine.ynhj.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MonthStatistical extends BaseFragment {
    private View view;

    public MonthStatistical() {
        // Required empty public constructor
    }

    @Override
    public View makeView() {
        view = View.inflate(getActivity(), R.layout.fragment_month_statistical, null);
        //绑定fragment
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    /**
     * 月采矿报表
     */
    @OnClick(R.id.yckbb)
    void Yckbb() {

    }

    /**
     * 月供矿报表
     */
    @OnClick(R.id.ygkbb)
    void Ygkbb() {

    }

    /**
     * 月采场供矿报表
     */
    @OnClick(R.id.yccgkbb)
    void Yccgkbb() {

    }

    /**
     * 月储备堆报表
     */
    @OnClick(R.id.ycbdbb)
    void Ycbdbb() {

    }


    /**
     * 月计划报表
     */
    @OnClick(R.id.yjhbb)
    void Yjhbb() {

    }

    /**
     * 月安全统计
     */
    @OnClick(R.id.yaqtj)
    void Yaqtj() {

    }

}
