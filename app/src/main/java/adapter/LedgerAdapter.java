package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.LedgerBean;

public class LedgerAdapter extends BaseQuickAdapter<LedgerBean, BaseViewHolder> {
    public LedgerAdapter(int layoutResId, @Nullable List<LedgerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LedgerBean item) {
        helper.setText(R.id.item1,"矿块(工作面)："+item.getKkgzm());
        helper.setText(R.id.item2,"矿石类型："+item.getKslx());
        helper.setText(R.id.item3,"品位："+item.getPw());
        helper.setText(R.id.item4,"金属量："+item.getJsl());
        helper.setText(R.id.item5,"矿石量："+item.getKsl());
    }
}
