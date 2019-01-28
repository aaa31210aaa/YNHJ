package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.CklBean;

public class CklAdapter extends BaseQuickAdapter<CklBean, BaseViewHolder> {
    public CklAdapter(int layoutResId, @Nullable List<CklBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CklBean item) {
        helper.setText(R.id.jhckl_tv, item.getJhckl());
        helper.setText(R.id.ljckl_tv, item.getLjckl());
        helper.setText(R.id.byckl_tv, item.getByckl());
        helper.setText(R.id.jhbll_tv, item.getJhbll());
        helper.setText(R.id.ljbll_tv, item.getLjbll());
        helper.setText(R.id.bybll_tv, item.getBybll());

    }
}
