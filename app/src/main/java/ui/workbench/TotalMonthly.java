package ui.workbench;


import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import utils.BaseFragment;

/**
 * 月数据总量
 */
public class TotalMonthly extends BaseFragment {
    private View view;
    @BindView(R.id.ck_kl_tv)
    TextView ck_kl_tv;
    @BindView(R.id.ck_pw_tv)
    TextView ck_pw_tv;
    @BindView(R.id.ljck_kl_tv)
    TextView ljck_kl_tv;
    @BindView(R.id.ljck_pw_tv)
    TextView ljck_pw_tv;
    @BindView(R.id.bll_jjcs_tv)
    TextView bll_jjcs_tv;
    @BindView(R.id.bll_tj_tv)
    TextView bll_tj_tv;
    @BindView(R.id.bll_ljtj_tv)
    TextView bll_ljtj_tv;
    @BindView(R.id.xcgk2000_kl_tv)
    TextView xcgk2000_kl_tv;
    @BindView(R.id.xcgk2000_pw_tv)
    TextView xcgk2000_pw_tv;
    @BindView(R.id.ljgk2100_kl_tv)
    TextView ljgk2100_kl_tv;
    @BindView(R.id.ljgk2100_pw_tv)
    TextView ljgk2100_pw_tv;
    @BindView(R.id.xcgk4kt_kl_tv)
    TextView xcgk4kt_kl_tv;
    @BindView(R.id.xcgk4kt_pw_tv)
    TextView xcgk4kt_pw_tv;
    @BindView(R.id.ljgk4000t_kl_tv)
    TextView ljgk4000t_kl_tv;
    @BindView(R.id.ljgk4000t_pw_tv)
    TextView ljgk4000t_pw_tv;
    @BindView(R.id.xcgk3kt_kl_tv)
    TextView xcgk3kt_kl_tv;
    @BindView(R.id.xcgk3kt_pw_tv)
    TextView xcgk3kt_pw_tv;
    @BindView(R.id.ljgk3000t_kl_tv)
    TextView ljgk3000t_kl_tv;
    @BindView(R.id.ljgk3000t_pw_tv)
    TextView ljgk3000t_pw_tv;
    @BindView(R.id.ljgk9100_kl_tv)
    TextView ljgk9100_kl_tv;
    @BindView(R.id.ljgk9100_pw_tv)
    TextView ljgk9100_pw_tv;
    @BindView(R.id.ljgk9100_jsl_tv)
    TextView ljgk9100_jsl_tv;


    public TotalMonthly() {
        // Required empty public constructor
    }

    @Override
    public View makeView() {
        view = View.inflate(getActivity(), R.layout.fragment_total_monthly, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {
        TextView textView = getActivity().findViewById(R.id.title_name_right);
        Log.e(TAG, textView.getText().toString());
        ck_kl_tv.setText("15,520.88");
        ck_pw_tv.setText("1.81");
        ljck_kl_tv.setText("15,520.88");
        ljck_pw_tv.setText("1.75");
        bll_jjcs_tv.setText("134,584");
        bll_tj_tv.setText("6.40");
        bll_ljtj_tv.setText("13.53");
        xcgk2000_kl_tv.setText("1,904.07");
        xcgk2000_pw_tv.setText("1.76");
        ljgk2100_kl_tv.setText("1,733.40");
        ljgk2100_pw_tv.setText("1.76");
        xcgk4kt_kl_tv.setText("3,773.33");
        xcgk4kt_pw_tv.setText("1.85");
        ljgk4000t_kl_tv.setText("3,773.33");
        ljgk4000t_pw_tv.setText("1.85");
        xcgk3kt_kl_tv.setText("4,372.57");
        xcgk3kt_pw_tv.setText("1.89");
        ljgk3000t_kl_tv.setText("12,015.83");
        ljgk3000t_pw_tv.setText("1.90");
        ljgk9100_kl_tv.setText("21,348.79");
        ljgk9100_pw_tv.setText("1.90");
        ljgk9100_jsl_tv.setText("40,602.22");
    }

}
