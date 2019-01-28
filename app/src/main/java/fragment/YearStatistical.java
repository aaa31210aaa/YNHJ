package fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import com.project.dimine.ynhj.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ui.planreport.PlanReport;
import utils.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class YearStatistical extends BaseFragment {
    private View view;

    public YearStatistical() {
        // Required empty public constructor
    }

    @Override
    public View makeView() {
        view = View.inflate(getActivity(), R.layout.fragment_year_statistical, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {

    }


    /**
     * 年采矿报表
     */
    @OnClick(R.id.nckbb)
    void Nckbb() {

    }

    /**
     * 年供矿报表
     */
    @OnClick(R.id.ngkbb)
    void Ngkbb() {

    }

    /**
     * 年采场供矿报表
     */
    @OnClick(R.id.nccgkbb)
    void Nccgkbb() {

    }

    /**
     * 年储备堆报表
     */
    @OnClick(R.id.ncbdbb)
    void Ycbdbb() {

    }


    /**
     * 年计划报表
     */
    @OnClick(R.id.njhbb)
    void Njhbb() {
        startActivity(new Intent(getActivity(),PlanReport.class));
    }

    /**
     * 年安全统计
     */
    @OnClick(R.id.naqtj)
    void Naqtj() {

    }

}
