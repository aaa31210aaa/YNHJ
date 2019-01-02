package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.LswtBean;

public class LswtAdapter extends BaseQuickAdapter<LswtBean, BaseViewHolder> {
    public LswtAdapter(int layoutResId, @Nullable List<LswtBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LswtBean item) {
        helper.setText(R.id.item1, "问题部门:" + item.getWtbm());
        helper.setText(R.id.item2, "发现时间:" + item.getFxsj());
        helper.setText(R.id.item3, "责任区:" + item.getZrq());
    }
}
