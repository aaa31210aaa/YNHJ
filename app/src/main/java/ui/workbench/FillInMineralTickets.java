package ui.workbench;

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

/**
 * 填写矿石票
 */
public class FillInMineralTickets extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
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

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        title_name.setText(R.string.ksptb_title);
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
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_fill_in_mineral_tickets;
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    @OnClick(R.id.tickets_save)
    void SaveAndSubmit() {
        if (tickets_ch.getText().toString().equals("")) {
            ShowToast.showShort(this, "请填写车号");
        } else {
            ShowToast.showShort(this, R.string.save_success);
            finish();
            ChooseDestination.instance.finish();
        }
    }


    @OnClick(R.id.tickets_date)
    void Date() {
        if (pvCustomTime != null)
            pvCustomTime.show();
    }
}
