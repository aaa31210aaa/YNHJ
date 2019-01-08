package ui.workbench;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class Swwtdj extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.swwt_swlx)
    EditText swwt_swlx;
    @BindView(R.id.swwt_swbm)
    EditText swwt_swbm;
    @BindView(R.id.swwt_swnr)
    Spinner swwt_swnr;
    @BindView(R.id.swwt_swrq)
    TextView swwt_swrq;
    @BindView(R.id.swwt_cfje)
    EditText swwt_cfje;
    @BindView(R.id.swwt_jcsj)
    TextView swwt_jcsj;
    @BindView(R.id.swwt_bmkf)
    EditText swwt_bmkf;
    @BindView(R.id.swwt_jcry)
    EditText swwt_jcry;
    @BindView(R.id.swwt_cjr)
    EditText swwt_cjr;
    @BindView(R.id.swwt_cjsj)
    TextView swwt_cjsj;
    @BindView(R.id.swwt_xgr)
    EditText swwt_xgr;
    @BindView(R.id.swwt_xgsj)
    TextView swwt_xgsj;
    @BindView(R.id.swwt_swnrqt_lin)
    LinearLayout swwt_swnrqt_lin;
    @BindView(R.id.swwt_swnrqt)
    EditText swwt_swnrqt;
    private String tag = "";


    private TimePickerView swrqTime;
    private TimePickerView jcsjTime;
    private TimePickerView cjsjTime;
    private TimePickerView xgsjTime;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_swwtdj;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        tag = getIntent().getStringExtra("tag");
        title_name.setText(R.string.swwt_title);
        swwt_swrq.setText(DateUtils.getStringDateShort());
        swwt_jcsj.setText(DateUtils.getStringDateShort());
        swwt_cjsj.setText(DateUtils.getStringDateShort());
        swwt_xgsj.setText(DateUtils.getStringDateShort());
        initSwrqPicker(swwt_swrq);
        initJcsjPicker(swwt_jcsj);
        initCjsjPicker(swwt_cjsj);
        initXgsjPicker(swwt_xgsj);
        initSpinner();
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    /**
     * 三违日期
     */
    @OnClick(R.id.swwt_swrq)
    void Swrq() {
        if (swrqTime != null)
            swrqTime.show();
    }

    /**
     * 检查时间
     */
    @OnClick(R.id.swwt_jcsj)
    void Jcsj() {
        if (jcsjTime != null)
            jcsjTime.show();
    }

    /**
     * 创建时间
     */
    @OnClick(R.id.swwt_cjsj)
    void Cjsj() {
        if (cjsjTime != null)
            cjsjTime.show();
    }


    /**
     * 要求完成时间
     */
    @OnClick(R.id.swwt_xgsj)
    void Xgsj() {
        if (xgsjTime != null)
            xgsjTime.show();
    }


    private void initSwrqPicker(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        swrqTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                swrqTime.returnData();
                                swrqTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                swrqTime.dismiss();
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

    private void initJcsjPicker(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        jcsjTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                jcsjTime.returnData();
                                jcsjTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                jcsjTime.dismiss();
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

    private void initCjsjPicker(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        cjsjTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cjsjTime.returnData();
                                cjsjTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cjsjTime.dismiss();
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

    private void initXgsjPicker(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        xgsjTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                xgsjTime.returnData();
                                xgsjTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                xgsjTime.dismiss();
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

    private void initSpinner() {
        // 数据源手动添加
        ArrayAdapter<String> spinnerAadapter = new ArrayAdapter<String>(this,
                R.layout.custom_spiner_text_item, getResources().getStringArray(R.array.swnr_data));
        spinnerAadapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        swwt_swnr.setAdapter(spinnerAadapter);

        swwt_swnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (swwt_swnr.getItemAtPosition(i).toString().equals("三违内容(其他)")) {
                    swwt_swnrqt_lin.setVisibility(View.VISIBLE);
                }else{
                    swwt_swnrqt_lin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
