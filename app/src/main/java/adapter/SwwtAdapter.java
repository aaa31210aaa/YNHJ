package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.SwwtBean;

public class SwwtAdapter extends BaseQuickAdapter<SwwtBean, BaseViewHolder> {
    public SwwtAdapter(int layoutResId, @Nullable List<SwwtBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SwwtBean item) {
        helper.setText(R.id.item1, "三违类型:" + item.getSwlx());
        helper.setText(R.id.item2, "三违部门:" + item.getSwbm());
        helper.setText(R.id.item3, "三违日期:" + item.getSwrq());
    }
}
