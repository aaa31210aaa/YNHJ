package tab;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.dimine.ynhj.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import adapter.CklAdapter;
import bean.CklBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.badgeview.BGABadgeRelativeLayout;
import ui.home.Notify;
import ui.home.Yj;
import ui.workbench.ChooseDestination;
import ui.workbench.Dbrw;
import ui.workbench.KcsjTodo;
import ui.workbench.XkdryTodo;
import utils.BaseFragment;
import utils.DialogUtil;
import utils.PortIpAddress;
import utils.ShowToast;

import static utils.PortIpAddress.BZZ;
import static utils.PortIpAddress.CKRY_CODE;
import static utils.PortIpAddress.KCSJ_CODE;
import static utils.PortIpAddress.KDDDY;
import static utils.PortIpAddress.XKDRY_CODE;


/**
 * 首页
 */
public class Home extends BaseFragment {
    private View view;
    @BindView(R.id.title_name)
    TextView title_name;
    //    @BindView(R.id.homeBanner)
//    ConvenientBanner homeBanner;
//    //banner加载的图片集
//    private ArrayList<Integer> localImages;
//    //翻页效果集
//    private ArrayList<String> transformerList;
//    private ABaseTransformer transforemer;
//    private String transforemerName;
//    private Class cls;
    @BindView(R.id.notify_badge)
    BGABadgeRelativeLayout notify_badge;
    @BindView(R.id.dbrw_badge)
    BGABadgeRelativeLayout dbrw_badge;

    private boolean isAnimFinished = true;
//    private TimePickerView homeCustomTime;

//    @BindView(R.id.ckl_ring)
//    RingView ckl_ring;
//    @BindView(R.id.bll_ring)
//    RingView bll_ring;
//    List<Integer> valueList = new ArrayList<>();// 仪表盘刻度
//    @BindView(R.id.choose_day_month)
//    Spinner choose_day_month;
//    private List<String> dayMonth;
    //定义一个ArrayAdapter适配器作为spinner的数据适配器
//    private ArrayAdapter<String> spinnerAdapter;

//    @BindView(R.id.home_barchart)
//    BarChart home_barchart;
//    private String xValues[] = {"2100T选场", "3000T选场", "4000T选场", "硫化矿堆2#高", "硫化矿堆2#低", "硫化矿堆3#"};
//    private float total[];

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<CklBean> mDatas;
    private List<CklBean> searchDatas;
    private CklAdapter adapter;
    @BindView(R.id.home_todo_lin)
    LinearLayout home_todo_lin;
    @BindView(R.id.home_yj_lin)
    LinearLayout home_yj_lin;
//    @BindView(R.id.home_aqtj_lin)
//    LinearLayout home_aqtj_lin;



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
//        title_name_right.setText(DateUtils.getStringDateShort());
        mDatas = new ArrayList<>();
        initRv();
        initRefresh();
        mConnect();
//        initSpinner();
//        setHomeBanner();
        getNotifyBadge();
        getDbrwBadge();
//        initRingView();
//        initBarChart();
//        HomeInitCustomYearMonthDay(getActivity(), title_name_right,
//                (int) (Math.random() * 100), (int) (Math.random() * 100), ckl_ring, bll_ring
//                , home_barchart, xValues, total);
    }

//    private void initSpinner() {
//        dayMonth = new ArrayList<>();
//        dayMonth.add("当天");
//        dayMonth.add("当月");
//
//        spinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, dayMonth);
//
//        //为适配器设置下拉列表下拉时的菜单样式。
//        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        //为spinner绑定我们定义好的数据适配器
//        choose_day_month.setAdapter(spinnerAdapter);
//
//        //为spinner绑定监听器，这里我们使用匿名内部类的方式实现监听器
//        choose_day_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                ShowToast.showShort(getActivity(), dayMonth.get(position));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

//    /**
//     * 设置轮播
//     */
//    private void setHomeBanner() {
//        localImages = new ArrayList<Integer>();
//        transformerList = new ArrayList<String>();
//
//        for (int position = 1; position < 5; position++) {
//            localImages.add(getResId("banner" + position, R.drawable.class));
//        }
//
//        //自定义Holder
//        homeBanner.setPages(
//                new CBViewHolderCreator<LocalImageHolderView>() {
//                    @Override
//                    public LocalImageHolderView createHolder() {
//                        return new LocalImageHolderView();
//                    }
//                }, localImages)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
//                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
//
//
//        //各种翻页效果
//        transformerList.add(DefaultTransformer.class.getSimpleName());
//        transformerList.add(AccordionTransformer.class.getSimpleName());
//        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
//        transformerList.add(CubeInTransformer.class.getSimpleName());
//        transformerList.add(CubeOutTransformer.class.getSimpleName());
//        transformerList.add(DepthPageTransformer.class.getSimpleName());
//        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
//        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
//        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
//        transformerList.add(RotateDownTransformer.class.getSimpleName());
//        transformerList.add(RotateUpTransformer.class.getSimpleName());
//        transformerList.add(StackTransformer.class.getSimpleName());
//        transformerList.add(ZoomInTransformer.class.getSimpleName());
//        transformerList.add(ZoomOutTranformer.class.getSimpleName());
//
//        transforemerName = transformerList.get(13);
//        try {
//            cls = Class.forName("com.ToxicBakery.viewpager.transforms." + transforemerName);
//            transforemer = (ABaseTransformer) cls.newInstance();
//            homeBanner.getViewPager().setPageTransformer(true, transforemer);
//
//            if (transforemerName.equals("StackTransformer")) {
//                homeBanner.setScrollDuration(5000);
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (java.lang.InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 获取通知公告数量徽章显示
     */
    private void getNotifyBadge() {
        notify_badge.showTextBadge("3");
    }

    /**
     * 获取待办任务的徽章数量
     */
    private void getDbrwBadge(){
        dbrw_badge.showTextBadge("3");
    }


//    private void initRingView() {
//        valueList.add(0);
//        valueList.add(10);
//        valueList.add(20);
//        valueList.add(30);
//        valueList.add(40);
//        valueList.add(50);
//        valueList.add(60);
//        valueList.add(70);
//        valueList.add(80);
//        valueList.add(90);
//        valueList.add(100);
//        ckl_ring.setValueList(valueList);
//        bll_ring.setValueList(valueList);
//        ckl_ring.setPointer(false);
//        bll_ring.setPointer(false);
//        RingView.startCkl((int) (Math.random() * 100), ckl_ring);
//        RingView.startBll((int) (Math.random() * 100), bll_ring);
//    }


//    /**
//     * 初始化BarChart图表
//     */
//    private void initBarChart() {
//        home_barchart.getDescription().setEnabled(false); //设置描述
//        home_barchart.setMaxVisibleValueCount(60); //设置最大可见绘制的 chart count 的数量
//        home_barchart.setPinchZoom(false); //只能在X轴或者Y轴上缩放
//        home_barchart.setDrawBarShadow(false); //绘制当前展示的内容顶部阴影
//        home_barchart.setDrawGridBackground(false); //绘制中心内容区域背景色
//        home_barchart.setHighlightFullBarEnabled(false);
//        //设置X轴
//        XAxis xAxis = home_barchart.getXAxis();
//        xAxis.setTextSize(8);
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //X轴坐标上的文字位置
////        xAxis.setLabelRotationAngle(-70); //偏转X轴文字角度避免重合
//        xAxis.setDrawGridLines(false);
//        home_barchart.getAxisLeft().setDrawGridLines(false);
//
//
//        //设置Y轴
////        ValueFormatter custom = new MyValueFormatter("t");
//        //Y轴左边
//        YAxis leftAxis = home_barchart.getAxisLeft();
//
//        //设置左边Y轴单位
//        leftAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
//                return (int) value + "t";
//            }
//        });
////        leftAxis.setTypeface(tfLight);
//        leftAxis.setLabelCount(8, false);
////        leftAxis.setValueFormatter(custom);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
//        leftAxis.setSpaceTop(15f);
//        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
//
//        //Y轴右边
//        YAxis rightAxis = home_barchart.getAxisRight();
//        rightAxis.setEnabled(false);
//
//        //设置X轴Y轴的动画效果
//        home_barchart.animateXY(1500, 1500);
//        home_barchart.getLegend().setEnabled(false);
//
//        total = new float[xValues.length];
//        for (int i = 0; i < xValues.length; i++) {
//            total[i] = (float) (Math.random() * 100);
//        }
//        setData(xValues, total);
//    }
//
//    public void setData(String[] name, float[] total) {
//        home_barchart.setScaleMinima(1, 1); //设置最小缩放
//        home_barchart.getViewPortHandler().refresh(new Matrix(), home_barchart, true);
//
//        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//        //Y轴设置值
//        for (int i = 0; i < total.length; i++) {
//            yVals1.add(new BarEntry(i, (float) total[i]));
//        }
//
//        BarDataSet set1;
//
//        if (home_barchart.getData() != null && home_barchart.getData().getDataSetCount() > 0) {
//            set1 = (BarDataSet) home_barchart.getData().getDataSetByIndex(0);
//            set1.setValues(yVals1);
//            XAxis xAxis = home_barchart.getXAxis();
//            xAxis.setLabelCount(total.length, false);
//
//            LabelFormatter labelFormatter = new LabelFormatter(name);
//            xAxis.setValueFormatter(labelFormatter);
//            home_barchart.getData().notifyDataChanged();
//            home_barchart.notifyDataSetChanged();
//            home_barchart.animateXY(1500, 1500);
//        } else {
//            set1 = new BarDataSet(yVals1, "");
//            set1.setDrawIcons(false);
//            set1.setDrawValues(true);
//            set1.setColors(ColorTemplate.VORDIPLOM_COLORS);
////            set1.setColor(getResources().getColor(R.color.c1));
//            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//            dataSets.add(set1);
//
//            BarData data = new BarData(dataSets);
//            data.setValueTextSize(10f);
////            data.setValueTypeface(mTfLight);
//            data.setBarWidth(0.9f);
//
//            //X轴数据设置
//            XAxis xAxis = home_barchart.getXAxis();
//            xAxis.setLabelCount(total.length, false);
//
//            LabelFormatter labelFormatter = new LabelFormatter(name);
//            xAxis.setValueFormatter(labelFormatter);
//            home_barchart.setData(data);
//            home_barchart.animateXY(1500, 1500);
//        }
//    }


//    class LabelFormatter implements IAxisValueFormatter {
//        private final String[] mLabels;
//
//        public LabelFormatter(String[] labels) {
//            mLabels = labels;
//        }
//
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//            try {
//                return mLabels[(int) value];
//            } catch (Exception e) {
//                e.printStackTrace();
//                return mLabels[0];
//            }
//        }
//    }

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

//    @OnClick(R.id.title_name_right)
//    void Date() {
//        if (homeCustomTime != null)
//            homeCustomTime.show();
//    }

    @OnClick(R.id.notify_badge)
    void Notify() {
        startActivity(new Intent(getActivity(), Notify.class));
    }

    /**
     * 待办
     */
    @OnClick(R.id.home_todo_lin)
    void Todo(){
        if (PortIpAddress.getUserType(getActivity()).equals(CKRY_CODE) || PortIpAddress.getUserType(getActivity()).equals(BZZ)) {
            startActivity(new Intent(getActivity(), Dbrw.class));
        } else if (PortIpAddress.getUserType(getActivity()).equals(KDDDY)) {
            startActivity(new Intent(getActivity(), ChooseDestination.class));
        } else if (PortIpAddress.getUserType(getActivity()).equals(KCSJ_CODE)) {
            startActivity(new Intent(getActivity(), KcsjTodo.class));
        } else if (PortIpAddress.getUserType(getActivity()).equals(XKDRY_CODE)) {
            startActivity(new Intent(getActivity(), XkdryTodo.class));
        }
    }

    /**
     * 预警
     */
    @OnClick(R.id.home_yj_lin)
    void Yj(){
        startActivity(new Intent(getActivity(),Yj.class));
    }

//    /**
//     * 安全统计
//     */
//    @OnClick(R.id.home_aqtj_lin)
//    void Aqtj(){
//        startActivity(new Intent(getActivity(),Aqtj.class));
//    }

    private void initRv() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        if (adapter == null) {
            adapter = new CklAdapter(R.layout.ckl_item, mDatas);
            adapter.bindToRecyclerView(recyclerView);
            recyclerView.setAdapter(adapter);
        }
    }


    private void initRefresh() {
        //刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                handler.sendEmptyMessageDelayed(1, ShowToast.refreshTime);
            }
        });

        //加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                handler.sendEmptyMessageDelayed(0, ShowToast.refreshTime);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    refreshLayout.finishLoadmore();
                    break;
                case 1:
                    mConnect();
                    break;
                default:
                    break;
            }
        }
    };

    private void mConnect() {
        dialog = DialogUtil.createLoadingDialog(getActivity(), R.string.loading);
        if (mDatas.size() > 0)
            mDatas.clear();
        for (int i = 0; i < 10; i++) {
            CklBean bean = new CklBean();
            bean.setJhckl("xxx");
            bean.setLjckl("xxx");
            bean.setByckl("xxx");
            bean.setJhbll("xxx");
            bean.setLjbll("xxx");
            bean.setBybll("xxx");
            mDatas.add(bean);
        }
        //如果无数据设置空布局
        if (mDatas.size() == 0) {
            adapter.setEmptyView(R.layout.nodata_layout, (ViewGroup) recyclerView.getParent());
        } else {
            adapter.setNewData(mDatas);
        }

        if (dialog.isShowing())
            dialog.dismiss();
        if (refreshLayout.isRefreshing())
            refreshLayout.finishRefresh();
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
//        homeBanner.startTurning(5000);
        Log.e("TAG", "onResume");
    }

    // 停止自动翻页
    @Override
    public void onStop() {
        super.onStop();
        //停止翻页
//        homeBanner.stopTurning();
        Log.e("TAG", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy");
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
