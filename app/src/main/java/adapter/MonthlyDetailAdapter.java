package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.NotifyBean;
import bean.ProductionPanelBean;
import ui.ProductionPanel;

public class MonthlyDetailAdapter extends BaseQuickAdapter<ProductionPanelBean, BaseViewHolder> {
    public MonthlyDetailAdapter(int layoutResId, @Nullable List<ProductionPanelBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductionPanelBean item) {
        helper.setText(R.id.monthly_detail_date, "日期：" + item.getDate());
        helper.setText(R.id.monthly_detail_weather, "天气：" + item.getWeather());
        helper.setText(R.id.ck_kl_tv, item.getCk_kl());
        helper.setText(R.id.ck_pw_tv, item.getCk_pw());
        helper.setText(R.id.ljck_kl_tv, item.getLjck_kl());
        helper.setText(R.id.ljck_pw_tv, item.getLjck_pw());

    }
}
