package ui.workbench;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.dimine.ynhj.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adapter.YhfcZgqAdapter;
import bean.YhBean;
import butterknife.BindView;
import butterknife.OnClick;
import utils.BaseActivity;
import utils.DateUtils;
import utils.PhotoView;

import static utils.CustomDatePicker.END_DAY;
import static utils.CustomDatePicker.END_MONTH;
import static utils.CustomDatePicker.END_YEAR;
import static utils.CustomDatePicker.START_DAY;
import static utils.CustomDatePicker.START_MONTH;
import static utils.CustomDatePicker.START_YEAR;
import static utils.CustomDatePicker.getTimeYearMonthDay;

public class Yhfc extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.yhfc_xasj)
    TextView yhfc_xasj;
    private TimePickerView xasjTime;
    @BindView(R.id.yhfc_zgq_img)
    RecyclerView yhfc_zgq_img;
    private List<YhBean> zgqImgList;
    private YhfcZgqAdapter adapter;
    private String[] mImages;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_yhfc;
    }

    @Override
    protected void initView() {
        initZgqRv();

    }

    @Override
    protected void initData() {
        title_name.setText(R.string.yhfc);
        yhfc_xasj.setText(DateUtils.getStringDateShort());
        initXasj(yhfc_xasj);
        yhfc_zgq_img.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    private void initXasj(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        xasjTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                                xasjTime.returnData();
                                xasjTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                xasjTime.dismiss();
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

    /**
     * 销案时间
     */
    @OnClick(R.id.yhfc_xasj)
    void Sjwcrq() {
        if (xasjTime != null)
            xasjTime.show();
    }

    private void initZgqRv() {
        zgqImgList = new ArrayList<>();
        mImages = new String[5];
        for (int i = 0; i < 5; i++) {
            YhBean bean = new YhBean();
            bean.setZgqImage("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=2206825903,3455955087&fm=58");
            mImages[i] = bean.getZgqImage();
            zgqImgList.add(bean);
        }

        if (adapter == null) {
            adapter = new YhfcZgqAdapter(this, R.layout.yhfc_zgq_item, zgqImgList);
            yhfc_zgq_img.setAdapter(adapter);
        } else {
            adapter.setNewData(zgqImgList);
        }

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(Yhfc.this, PhotoView.class);
                intent.putExtra("position", position);
                intent.putExtra("urls", mImages);
                startActivity(intent);
            }
        });
    }
}
