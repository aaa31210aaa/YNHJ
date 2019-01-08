package tab;


import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ABaseTransformer;
import com.ToxicBakery.viewpager.transforms.AccordionTransformer;
import com.ToxicBakery.viewpager.transforms.BackgroundToForegroundTransformer;
import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.CubeOutTransformer;
import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.ToxicBakery.viewpager.transforms.FlipHorizontalTransformer;
import com.ToxicBakery.viewpager.transforms.FlipVerticalTransformer;
import com.ToxicBakery.viewpager.transforms.ForegroundToBackgroundTransformer;
import com.ToxicBakery.viewpager.transforms.RotateDownTransformer;
import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.ToxicBakery.viewpager.transforms.StackTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.ToxicBakery.viewpager.transforms.ZoomOutTranformer;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.project.dimine.ynhj.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.badgeview.BGABadgeRelativeLayout;
import ui.Notify;
import utils.BarChartManager;
import utils.BaseFragment;
import utils.DateUtils;
import utils.LocalImageHolderView;
import utils.RingView;
import utils.ValueFormatter;

import static utils.CustomDatePicker.END_DAY;
import static utils.CustomDatePicker.END_MONTH;
import static utils.CustomDatePicker.END_YEAR;
import static utils.CustomDatePicker.START_DAY;
import static utils.CustomDatePicker.START_MONTH;
import static utils.CustomDatePicker.START_YEAR;
import static utils.CustomDatePicker.getTimeYearMonthDay;


/**
 * 首页
 */
public class Home extends BaseFragment {
    private View view;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    TextView title_name_right;
    @BindView(R.id.homeBanner)
    ConvenientBanner homeBanner;
    //banner加载的图片集
    private ArrayList<Integer> localImages;
    //翻页效果集
    private ArrayList<String> transformerList;
    private ABaseTransformer transforemer;
    private String transforemerName;
    private Class cls;
    @BindView(R.id.notify_badge)
    BGABadgeRelativeLayout notify_badge;
    private boolean isAnimFinished = true;
    private TimePickerView homeCustomTime;

    @BindView(R.id.ckl_ring)
    RingView ckl_ring;
    @BindView(R.id.bll_ring)
    RingView bll_ring;
    List<Integer> valueList = new ArrayList<>();// 仪表盘刻度
    @BindView(R.id.choose_day_month)
    Spinner choose_day_month;
    private List<String> dayMonth;
    //定义一个ArrayAdapter适配器作为spinner的数据适配器
    private ArrayAdapter<String> spinnerAdapter;

    @BindView(R.id.home_barchart)
    BarChart home_barchart;
    private String xValues[] = {"2100T选场", "3000T选场", "4000T选场", "硫化矿堆2#高", "硫化矿堆2#低", "硫化矿堆3#"};
    private float total[];

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View makeView() {
        view = View.inflate(getActivity(), R.layout.fragment_home, null);
        //绑定fragment
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {
        title_name.setText(R.string.home_title);
        title_name_right.setText(DateUtils.getStringDateShort());


        initSpinner();
        setHomeBanner();
        getNotifyBadge();
//        initDashboard();
        initRingView();
        initBarChart();
        HomeInitCustomYearMonthDay(getActivity(), title_name_right,
                (int) (Math.random() * 100), (int) (Math.random() * 100), ckl_ring, bll_ring
                , home_barchart, xValues, total);
    }

    private void initSpinner() {
        dayMonth = new ArrayList<>();
        dayMonth.add("当天");
        dayMonth.add("当月");

        spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dayMonth);

        //为适配器设置下拉列表下拉时的菜单样式。
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //为spinner绑定我们定义好的数据适配器
        choose_day_month.setAdapter(spinnerAdapter);

        //为spinner绑定监听器，这里我们使用匿名内部类的方式实现监听器
        choose_day_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                ShowToast.showShort(getActivity(), dayMonth.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * 设置轮播
     */
    private void setHomeBanner() {
        localImages = new ArrayList<Integer>();
        transformerList = new ArrayList<String>();

        for (int position = 1; position < 5; position++) {
            localImages.add(getResId("banner" + position, R.drawable.class));
        }

        //自定义Holder
        homeBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        // 设置翻页的效果，不需要翻页效果可用不设

//                .setPageTransformer(Transformer.CubeIn);
//        convenientBanner.setManualPageable(false);//设置不能手动影响


        //加载网络图片
//        networkImages= Arrays.asList(imageUrls);
//        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
//            @Override
//            public NetworkImageHolderView createHolder() {
//                return new NetworkImageHolderView();
//            }
//        },networkImages);

        //各种翻页效果
        transformerList.add(DefaultTransformer.class.getSimpleName());
        transformerList.add(AccordionTransformer.class.getSimpleName());
        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
        transformerList.add(CubeInTransformer.class.getSimpleName());
        transformerList.add(CubeOutTransformer.class.getSimpleName());
        transformerList.add(DepthPageTransformer.class.getSimpleName());
        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
        transformerList.add(RotateDownTransformer.class.getSimpleName());
        transformerList.add(RotateUpTransformer.class.getSimpleName());
        transformerList.add(StackTransformer.class.getSimpleName());
        transformerList.add(ZoomInTransformer.class.getSimpleName());
        transformerList.add(ZoomOutTranformer.class.getSimpleName());

        transforemerName = transformerList.get(13);
        try {
            cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
            transforemer = (ABaseTransformer) cls.newInstance();
            homeBanner.getViewPager().setPageTransformer(true, transforemer);

            if (transforemerName.equals("StackTransformer")) {
                homeBanner.setScrollDuration(5000);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取通知公告数量徽章显示
     */
    private void getNotifyBadge() {
        notify_badge.showTextBadge("3");
    }

//    private void initDashboard() {
//        if (isAnimFinished) {
//            @SuppressLint("ObjectAnimatorBinding")
//            ObjectAnimator animator = ObjectAnimator.ofInt(ckl_ybp, "mRealTimeValue",
//                    ckl_ybp.getVelocity(), new value().nextInt(100));
//            animator.setDuration(1000).setInterpolator(new LinearInterpolator());
//            animator.addListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationStart(Animator animation) {
//                    isAnimFinished = false;
//                }
//
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    isAnimFinished = true;
//                }
//
//                @Override
//                public void onAnimationCancel(Animator animation) {
//                    isAnimFinished = true;
//                }
//            });
//            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    int value = (int) animation.getAnimatedValue();
//                    ckl_ybp.setVelocity(value);
//                }
//            });
//            animator.start();
//        }
//    }

    private void initRingView() {
        valueList.add(0);
        valueList.add(10);
        valueList.add(20);
        valueList.add(30);
        valueList.add(40);
        valueList.add(50);
        valueList.add(60);
        valueList.add(70);
        valueList.add(80);
        valueList.add(90);
        valueList.add(100);
        ckl_ring.setValueList(valueList);
        bll_ring.setValueList(valueList);
        ckl_ring.setPointer(false);
        bll_ring.setPointer(false);
        RingView.startCkl((int) (Math.random() * 100), ckl_ring);
        RingView.startBll((int) (Math.random() * 100), bll_ring);
    }


    /**
     * 初始化BarChart图表
     */
    private void initBarChart() {
        home_barchart.getDescription().setEnabled(false); //设置描述
        home_barchart.setMaxVisibleValueCount(60); //设置最大可见绘制的 chart count 的数量
        home_barchart.setPinchZoom(false); //只能在X轴或者Y轴上缩放
        home_barchart.setDrawBarShadow(false); //绘制当前展示的内容顶部阴影
        home_barchart.setDrawGridBackground(false); //绘制中心内容区域背景色
        home_barchart.setHighlightFullBarEnabled(false);
        //设置X轴
        XAxis xAxis = home_barchart.getXAxis();
        xAxis.setTextSize(8);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //X轴坐标上的文字位置
//        xAxis.setLabelRotationAngle(-70); //偏转X轴文字角度避免重合
        xAxis.setDrawGridLines(false);
        home_barchart.getAxisLeft().setDrawGridLines(false);


        //设置Y轴
//        ValueFormatter custom = new MyValueFormatter("t");
        //Y轴左边
        YAxis leftAxis = home_barchart.getAxisLeft();

        //设置左边Y轴单位
        leftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return (int) value + "t";
            }
        });
//        leftAxis.setTypeface(tfLight);
        leftAxis.setLabelCount(8, false);
//        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        //Y轴右边
        YAxis rightAxis = home_barchart.getAxisRight();
        rightAxis.setEnabled(false);

        //设置X轴Y轴的动画效果
        home_barchart.animateXY(1500, 1500);
        home_barchart.getLegend().setEnabled(false);

        total = new float[xValues.length];
        for (int i = 0; i < xValues.length; i++) {
            total[i] = (float) (Math.random() * 100);
        }
        setData(xValues, total);
    }

    public void setData(String[] name, float[] total) {
        home_barchart.setScaleMinima(1, 1); //设置最小缩放
        home_barchart.getViewPortHandler().refresh(new Matrix(), home_barchart, true);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        //Y轴设置值
        for (int i = 0; i < total.length; i++) {
            yVals1.add(new BarEntry(i, (float) total[i]));
        }

        BarDataSet set1;

        if (home_barchart.getData() != null && home_barchart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) home_barchart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            XAxis xAxis = home_barchart.getXAxis();
            xAxis.setLabelCount(total.length, false);

            LabelFormatter labelFormatter = new LabelFormatter(name);
            xAxis.setValueFormatter(labelFormatter);
            home_barchart.getData().notifyDataChanged();
            home_barchart.notifyDataSetChanged();
            home_barchart.animateXY(1500, 1500);
        } else {
            set1 = new BarDataSet(yVals1, "");
            set1.setDrawIcons(false);
            set1.setDrawValues(true);
            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
//            set1.setColor(getResources().getColor(R.color.c1));
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
//            data.setValueTypeface(mTfLight);
            data.setBarWidth(0.9f);

            //X轴数据设置
            XAxis xAxis = home_barchart.getXAxis();
            xAxis.setLabelCount(total.length, false);

            LabelFormatter labelFormatter = new LabelFormatter(name);
            xAxis.setValueFormatter(labelFormatter);
            home_barchart.setData(data);
            home_barchart.animateXY(1500, 1500);
        }


//        //创建柱形图对象  如果多柱形图就创建多个
//        BarDataSet barSet;
//        //创建数据集合
//        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//
//        //将Y轴数据添加到集合中
//
//        if (home_barchart.getData() != null && home_barchart.getData().getDataSetCount() > 0) {
//            barSet = (BarDataSet) home_barchart.getData().getDataSetByIndex(0);
//            barSet.setValues(values);
//            home_barchart.getData().notifyDataChanged();
//            home_barchart.notifyDataSetChanged();
//        } else {
//            barSet = new BarDataSet(values, "Data Set");
//            barSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
//            barSet.setDrawValues(true);
//
//            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
//            dataSets.add(barSet);
//
//            BarData data = new BarData(dataSets);
//            data.setValueTextSize(10f); //设置顶部文字大小
//            home_barchart.setData(data);
//            home_barchart.setFitBars(true);
//        }
//        home_barchart.invalidate();
    }


    class LabelFormatter implements IAxisValueFormatter {
        private final String[] mLabels;

        public LabelFormatter(String[] labels) {
            mLabels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            try {
                return mLabels[(int) value];
            } catch (Exception e) {
                e.printStackTrace();
                return mLabels[0];
            }
        }
    }


//    /**
//     * 剥离量数值
//     *
//     * @param value
//     */
//    private void startBll(int value) {
//        float f = (value - valueList.get(0)) * 1.0f / (valueList.get(valueList.size() - 1) - valueList.get(0));
//        if (f <= 0.5f) {
//            endUseColor = (Integer) evaluator.evaluate(f, startColor, centerColor);
//
//        } else {
//            endUseColor = (Integer) evaluator.evaluate(f, centerColor, endColor);
//
//        }
//
//        bll_ring.setValue(value, new RingView.OnProgerssChange() {
//            @Override
//            public void OnProgerssChange(float interpolatedTime) {
//                int evaluate = 0;
//                if (interpolatedTime <= 0.5f) {
//                    evaluate = (Integer) evaluator.evaluate(interpolatedTime, startColor, endUseColor);
//                } else {
//                    evaluate = (Integer) evaluator.evaluate(interpolatedTime, centerColor, endUseColor);
//                }
////                ly_content.setBackgroundColor(evaluate);
//            }
//        }, (int) (f * animDuration));
//    }


    @OnClick(R.id.title_name_right)
    void Date() {
        if (homeCustomTime != null)
            homeCustomTime.show();
    }

    @OnClick(R.id.notify_badge)
    void Notify() {
        startActivity(new Intent(getActivity(), Notify.class));
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        homeBanner.startTurning(5000);
        Log.e("TAG", "onResume");
    }

    // 停止自动翻页
    @Override
    public void onStop() {
        super.onStop();
        //停止翻页
        homeBanner.stopTurning();
        Log.e("TAG", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy");
    }


    private void HomeInitCustomYearMonthDay(Context context, final TextView textView
            , final int cklValue, final int bllValue, final RingView cklView, final RingView bllView
            , final BarChart barChart, final String[] xValue, final float[] total) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        homeCustomTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(getTimeYearMonthDay(date));
                RingView.startCkl(cklValue, cklView);
                RingView.startBll(bllValue, bllView);
                BarChartManager.initBarChart(barChart, xValue, total);
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
                                homeCustomTime.returnData();
                                homeCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                homeCustomTime.dismiss();
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


    //    /**
//     * 生产看板
//     */
//    @OnClick(R.id.sckb_rl)
//    void Sckb() {
//        Intent intent = new Intent(getActivity(), ProductionPanel.class);
//        startActivity(intent);
//    }
//
//    /**
//     * 通知公告
//     */
//    @OnClick(R.id.notify_badge)
//    void Tzgg() {
//        startActivity(new Intent(getActivity(), Notify.class));
//    }
//
//    /**
//     * 待办事宜
//     */
//    @OnClick(R.id.dbsy_rl)
//    void Dbsy() {
//        if (PortIpAddress.getUserType(getActivity()).equals(CKRY_CODE)) {
//            Intent intent = new Intent(getActivity(), Todo.class);
//            intent.putExtra("usertype", PortIpAddress.getUserType(getActivity()));
//            startActivity(intent);
//        } else if (PortIpAddress.getUserType(getActivity()).equals(KCSJ_CODE)) {
//            Intent intent = new Intent(getActivity(), FillInMineralTickets.class);
//            intent.putExtra("usertype", PortIpAddress.getUserType(getActivity()));
//            startActivity(intent);
//        } else if (PortIpAddress.getUserType(getActivity()).equals(XKDRY_CODE)) {
//            Intent intent = new Intent(getActivity(), TicketsList.class);
//            intent.putExtra("usertype", PortIpAddress.getUserType(getActivity()));
//            startActivity(intent);
//        }
//    }
//
//    /**
//     * 化验结果
//     */
//    @OnClick(R.id.hyjg_rl)
//    void Hyjg() {
//        Intent intent = new Intent(getActivity(), LaboratoryResult.class);
//        startActivity(intent);
//    }

}
