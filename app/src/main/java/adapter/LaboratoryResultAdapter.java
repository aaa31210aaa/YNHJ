package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.LaboratoryResultBean;

public class LaboratoryResultAdapter extends BaseQuickAdapter<LaboratoryResultBean, BaseViewHolder> {
    public LaboratoryResultAdapter(int layoutResId, @Nullable List<LaboratoryResultBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LaboratoryResultBean item) {
        helper.setText(R.id.jcbh_tv, "检测编号:" + item.getJcbh());
        helper.setText(R.id.yybh_tv, "原样编号:" + item.getYybh());
        helper.setText(R.id.auys_tv, "Au(10*-6):" + item.getAuys());
    }
}
