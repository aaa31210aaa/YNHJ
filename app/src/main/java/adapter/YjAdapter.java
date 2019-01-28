package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.YjBean;

public class YjAdapter extends BaseQuickAdapter<YjBean, BaseViewHolder> {
    public YjAdapter(int layoutResId, @Nullable List<YjBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, YjBean item) {
        helper.setText(R.id.item1, "预警名称：" + item.getYjName());
        helper.setText(R.id.item2, "预警等级：" + item.getYjLevel());
        helper.setText(R.id.item3, "发布时间：" + item.getFbTime());
    }
}
