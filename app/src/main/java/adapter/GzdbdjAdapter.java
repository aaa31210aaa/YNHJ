package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.GzdbBean;

public class GzdbdjAdapter extends BaseQuickAdapter<GzdbBean, BaseViewHolder> {

    public GzdbdjAdapter(int layoutResId, @Nullable List<GzdbBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GzdbBean item) {
        helper.setText(R.id.item1, "督办部门：" + item.getDbbm());
        helper.setText(R.id.item2, "督办人：" + item.getDbr());
        helper.setText(R.id.item3, "督办类型：" + item.getDblx());
    }
}
