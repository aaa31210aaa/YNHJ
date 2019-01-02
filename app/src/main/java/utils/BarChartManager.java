package utils;

import android.graphics.Matrix;

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

import java.util.ArrayList;


public class BarChartManager {

    public static void initBarChart(BarChart home_barchart, String[] xValues, float[] total) {
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


        for (int i = 0; i < xValues.length; i++) {
            total[i] = (float) (Math.random() * 100);
        }
        setData(home_barchart, xValues, total);
    }


    public static void setData(BarChart home_barchart, String[] name, float total[]) {
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
    }

    static class LabelFormatter implements IAxisValueFormatter {
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
}
