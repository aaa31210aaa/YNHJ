package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.AqjcwtBean;

public class AqjcwtAdapter extends BaseQuickAdapter<AqjcwtBean, BaseViewHolder> {
    public AqjcwtAdapter(int layoutResId, @Nullable List<AqjcwtBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AqjcwtBean item) {
        helper.setText(R.id.item1, "检查类别:" + item.getJclb());
        helper.setText(R.id.item2, "被检查单位:" + item.getBjcdw());
        helper.setText(R.id.item3, "检查时间:" + item.getJcsj());
    }
}
