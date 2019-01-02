package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.NotifyBean;
import bean.ProductionPanelBean;

public class ProductionPanelAdapter extends BaseQuickAdapter<ProductionPanelBean, BaseViewHolder> {
    public ProductionPanelAdapter(int layoutResId, @Nullable List<ProductionPanelBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductionPanelBean item) {
        helper.setText(R.id.item1, "日期:" + item.getDate());
        helper.setText(R.id.item2, "天气:" + item.getWeather());
        helper.setText(R.id.item3, "月计划总矿量(t):" + item.getKl());
        helper.setText(R.id.item4, "品位(g/t):" + item.getPw());

    }
}
