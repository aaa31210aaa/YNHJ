package ui.workbench;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.project.dimine.ynhj.R;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import utils.BaseActivity;
import utils.DateUtils;

import static utils.CustomDatePicker.END_DAY;
import static utils.CustomDatePicker.END_MONTH;
import static utils.CustomDatePicker.END_YEAR;
import static utils.CustomDatePicker.START_DAY;
import static utils.CustomDatePicker.START_MONTH;
import static utils.CustomDatePicker.START_YEAR;
import static utils.CustomDatePicker.getTimeYearMonthDay;

public class Sgdj extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    TextView title_name_right;
    @BindView(R.id.sgdj_sgbt)
    EditText sgdj_sgbt;
    @BindView(R.id.sgdj_sgfl)
    TextView sgdj_sgfl;
    @BindView(R.id.sgdj_sglevel)
    TextView sgdj_sglevel;
    @BindView(R.id.sgdj_sgrq)
    TextView sgdj_sgrq;

    private TimePickerView sgrqTime;


    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_sgdj;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        title_name.setText(R.string.sgdj);
        title_name_right.setText(R.string.submit);

        sgdj_sgrq.setText(DateUtils.getStringDateShort());
        initSgrq(sgdj_sgrq);
    }


    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    /**
     * 事故日期
     */
    @OnClick(R.id.sgdj_sgrq)
    void Pcrq() {
        if (sgrqTime != null)
            sgrqTime.show();
    }

    private void initSgrq(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        sgrqTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(getTimeYearMonthDay(date));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sgrqTime.returnData();
                                sgrqTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sgrqTime.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }
}
