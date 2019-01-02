package ui.workbench;

import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.OnClick;
import utils.BaseActivity;
import utils.CustomDatePicker;
import utils.DateUtils;
import utils.TTSUtils;

public class KcsjTodo extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.tickets_date)
    TextView tickets_date;
    @BindView(R.id.tickets_ch)
    TextView tickets_ch;
    @BindView(R.id.tickets_xkd)
    TextView tickets_xkd;
    @BindView(R.id.tickets_weizhi)
    TextView tickets_weizhi;
    @BindView(R.id.shimmerContent)
    ShimmerFrameLayout shimmerContent;

    private String isSubmit = "";
    private TTSUtils ttsUtils;
    private String str;
    private String xc[] = {"供两千一百吨选厂", "供三千吨选厂", "供四千吨选厂"};
    public static String str_qz = "您当前的卸矿点";

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        title_name.setText(R.string.kspsj_title);
        tickets_date.setText(DateUtils.getStringDateShort());
        CustomDatePicker.initCustomYearMonthDay(this, tickets_date);

        ttsUtils = new TTSUtils();
        if (tickets_xkd.getText().toString().contains("2100")) {
            str = xc[0];
        } else if (tickets_xkd.getText().toString().contains("3000")) {
            str = xc[1];
        } else if (tickets_xkd.getText().toString().contains("4000")) {
            str = xc[2];
        } else {
            str = tickets_xkd.getText().toString();
        }
        ttsUtils.startSpeaking(str_qz + str);
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_kcsj_todo;
    }

    @OnClick(R.id.tickets_xkd)
    void SpeekAgain() {
        ttsUtils.startSpeaking(str_qz + str);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsUtils.release();
    }

    @Override
    public void onPause() {
        super.onPause();
        ttsUtils.pause();
    }
}
