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
public class DayStatistical extends BaseFragment {
    private View view;

    public DayStatistical() {
        // Required empty public constructor
    }

    @Override
    public View makeView() {
        view = View.inflate(getActivity(), R.layout.fragment_day_statistical, null);
        //绑定fragment
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {

    }

    /**
     * 日剥离报表
     */
    @OnClick(R.id.rblbb)
    void Rblbb() {

    }

    /**
     * 日采矿报表
     */
    @OnClick(R.id.rckbb)
    void Rckbb() {

    }

    /**
     * 日供矿报表
     */
    @OnClick(R.id.rgkbb)
    void Rgkbb() {

    }

    /**
     * 日采场供矿报表
     */
    @OnClick(R.id.rccgkbb)
    void Rccgkbb() {

    }

    /**
     * 日储备堆报表
     */
    @OnClick(R.id.rcbdbb)
    void Rcbdbb() {

    }


    /**
     * 日计划报表
     */
    @OnClick(R.id.rjhbb)
    void Rjhbb() {

    }

    /**
     * 日安全统计
     */
    @OnClick(R.id.raqtj)
    void Raqtj() {

    }


}
