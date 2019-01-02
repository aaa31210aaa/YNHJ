package ui.workbench;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import utils.PortIpAddress;

import static utils.CustomDatePicker.END_DAY;
import static utils.CustomDatePicker.END_MONTH;
import static utils.CustomDatePicker.END_YEAR;
import static utils.CustomDatePicker.START_DAY;
import static utils.CustomDatePicker.START_MONTH;
import static utils.CustomDatePicker.START_YEAR;
import static utils.CustomDatePicker.getTimeYearMonthDay;
import static utils.PortIpAddress.CKRY_CODE;
import static utils.PortIpAddress.KCSJ_CODE;
import static utils.PortIpAddress.KDDDY;
import static utils.PortIpAddress.XKDRY_CODE;

public class Gzdbdj extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    TextView title_name_right;
    @BindView(R.id.gzdbdj_dbbm)
    EditText gzdbdj_dbbm;
    @BindView(R.id.gzdbdj_dbr)
    EditText gzdbdj_dbr;
    @BindView(R.id.gzdbdj_zxr)
    EditText gzdbdj_zxr;
    @BindView(R.id.gzdbdj_dblx)
    EditText gzdbdj_dblx;
    @BindView(R.id.gzdbdj_dbnr)
    EditText gzdbdj_dbnr;
    @BindView(R.id.gzdbdj_xdsj)
    TextView gzdbdj_xdsj;
    @BindView(R.id.gzdbdj_yqwcsj)
    TextView gzdbdj_yqwcsj;
    @BindView(R.id.gzdbdj_cjsj)
    TextView gzdbdj_cjsj;
    @BindView(R.id.gzdbdj_xgsj)
    TextView gzdbdj_xgsj;


    @BindView(R.id.gzdbdj_wcjg_lin)
    LinearLayout gzdbdj_wcjg_lin;
    @BindView(R.id.gzdbdj_wcqk_lin)
    LinearLayout gzdbdj_wcqk_lin;
    @BindView(R.id.gzdbdj_sjwcsj_lin)
    LinearLayout gzdbdj_sjwcsj_lin;
    @BindView(R.id.gzdbdj_qrqk_lin)
    LinearLayout gzdbdj_qrqk_lin;
    @BindView(R.id.gzdbdj_qrsj_lin)
    LinearLayout gzdbdj_qrsj_lin;

    private TimePickerView XdsjCustomTime;
    private TimePickerView YqwcsjCustomTime;
    private TimePickerView CjsjCustomTime;
    private TimePickerView XgsjCustomTime;

    private String tag = "";

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        title_name.setText(R.string.gzdbdj_title);
        title_name_right.setText(R.string.submit);
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");
        if (tag.equals("modify")) {
            if (PortIpAddress.getUserType(this).equals(CKRY_CODE)
                    || PortIpAddress.getUserType(this).equals(KCSJ_CODE)
                    || PortIpAddress.getUserType(this).equals(XKDRY_CODE)) {
                gzdbdj_wcjg_lin.setVisibility(View.GONE);
                gzdbdj_sjwcsj_lin.setVisibility(View.GONE);
                gzdbdj_qrqk_lin.setVisibility(View.GONE);
                gzdbdj_qrsj_lin.setVisibility(View.GONE);
                //获取数据
            } else if (PortIpAddress.getUserType(this).equals(KDDDY)) {
                gzdbdj_qrqk_lin.setVisibility(View.GONE);
                gzdbdj_qrsj_lin.setVisibility(View.GONE);
            }
        } else {
            gzdbdj_wcjg_lin.setVisibility(View.GONE);
            gzdbdj_sjwcsj_lin.setVisibility(View.GONE);
            gzdbdj_qrqk_lin.setVisibility(View.GONE);
            gzdbdj_qrsj_lin.setVisibility(View.GONE);
        }

        gzdbdj_xdsj.setText(DateUtils.getStringDateShort());
        gzdbdj_yqwcsj.setText(DateUtils.getStringDateShort());
        gzdbdj_cjsj.setText(DateUtils.getStringDateShort());
        gzdbdj_xgsj.setText(DateUtils.getStringDateShort());
        initXdsjDatePicker(gzdbdj_xdsj);
        initcjsjDatePicker(gzdbdj_cjsj);
        initxgsjDatePicker(gzdbdj_xgsj);
        initYqwcsjDatePicker(gzdbdj_yqwcsj);
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_gzdbdj;
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    @OnClick(R.id.title_name_right)
    void Submit(){

    }

    /**
     * 下达时间
     */
    @OnClick(R.id.gzdbdj_xdsj)
    void Xdsj() {
        if (XdsjCustomTime != null)
            XdsjCustomTime.show();
    }

    /**
     * 创建时间
     */
    @OnClick(R.id.gzdbdj_cjsj)
    void Cjsj() {
        if (CjsjCustomTime != null)
            CjsjCustomTime.show();
    }

    /**
     * 修改时间
     */
    @OnClick(R.id.gzdbdj_xgsj)
    void Xgsj() {
        if (XgsjCustomTime != null)
            XgsjCustomTime.show();
    }


    /**
     * 要求完成时间
     */
    @OnClick(R.id.gzdbdj_yqwcsj)
    void Yqwcsj() {
        if (YqwcsjCustomTime != null)
            YqwcsjCustomTime.show();
    }


    private void initXdsjDatePicker(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        XdsjCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                                XdsjCustomTime.returnData();
                                XdsjCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                XdsjCustomTime.dismiss();
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

    private void initYqwcsjDatePicker(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        YqwcsjCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                                YqwcsjCustomTime.returnData();
                                YqwcsjCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                YqwcsjCustomTime.dismiss();
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

    private void initcjsjDatePicker(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        CjsjCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                                CjsjCustomTime.returnData();
                                CjsjCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                CjsjCustomTime.dismiss();
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

    private void initxgsjDatePicker(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        XgsjCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                                XgsjCustomTime.returnData();
                                XgsjCustomTime.dismiss();
                            }
                        });

                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                XgsjCustomTime.dismiss();
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
