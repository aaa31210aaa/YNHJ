package ui;


import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.OnClick;
import utils.BaseActivity;
import utils.CustomDatePicker;
import utils.DateUtils;
import utils.ShowToast;

import static utils.CustomDatePicker.pvCustomTime;

public class XkdryFillInTickets extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    TextView title_name_right;
    @BindView(R.id.tickets_date)
    TextView tickets_date;
    @BindView(R.id.tickets_ch)
    EditText tickets_ch;
    @BindView(R.id.tickets_xkd)
    TextView tickets_xkd;
    @BindView(R.id.tickets_tj)
    TextView tickets_tj;
    @BindView(R.id.tickets_ktx)
    TextView tickets_ktx;
    @BindView(R.id.tickets_kt)
    TextView tickets_kt;
    @BindView(R.id.tickets_kk)
    TextView tickets_kk;
    @BindView(R.id.tickets_aupw)
    TextView tickets_aupw;
    @BindView(R.id.tickets_fepw)
    TextView tickets_fepw;
    @BindView(R.id.tickets_agpw)
    TextView tickets_agpw;
    @BindView(R.id.tickets_cupw)
    TextView tickets_cupw;
    private String isSubmit = "";


    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        isSubmit = intent.getStringExtra("isSubmit");
        title_name.setText(R.string.ksptb_title);
        title_name_right.setText(R.string.submit);
        tickets_date.setText(DateUtils.getStringDateShort());
        CustomDatePicker.initCustomYearMonthDay(this, tickets_date);
        tickets_xkd.setText("4000T");
        tickets_tj.setText("1604");
        tickets_ktx.setText("50-52");
        tickets_kt.setText("KT52-8");
        tickets_kk.setText("10-40");
        tickets_aupw.setText("2");
        tickets_fepw.setText("37");
        tickets_agpw.setText("30");
        tickets_cupw.setText("27");
        tickets_ch.setText("152");
        tickets_ch.setFocusable(false);
        tickets_ch.setFocusableInTouchMode(false);
        if (isSubmit.equals("已确认")) {
            title_name_right.setVisibility(View.GONE);
        } else {
            title_name_right.setText(R.string.submit);
        }
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_xkdry_fill_in_tickets;
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    @OnClick(R.id.title_name_right)
    void SaveAndSubmit() {
        ShowToast.showShort(this, R.string.submit_success);
        finish();
    }
}
