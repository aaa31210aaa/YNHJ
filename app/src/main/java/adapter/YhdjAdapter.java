package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.YhBean;

public class YhdjAdapter extends BaseQuickAdapter<YhBean, BaseViewHolder> {
    public YhdjAdapter(int layoutResId, @Nullable List<YhBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, YhBean item) {
        helper.setText(R.id.item1, "隐患名称:" + item.getYhmc());
        helper.setText(R.id.item2, "隐患类型:" + item.getYhlx());
        helper.setText(R.id.item3, "隐患地点:" + item.getYhdd());
        helper.setText(R.id.item4, "录入时间:" + item.getLrsj());
    }
}
