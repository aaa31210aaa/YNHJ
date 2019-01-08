package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.SgdjBean;

public class SgdjAdapter extends BaseQuickAdapter<SgdjBean, BaseViewHolder> {
    public SgdjAdapter(int layoutResId, @Nullable List<SgdjBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SgdjBean item) {
        helper.setText(R.id.item1, "事故标题:" + item.getSgbt());
        helper.setText(R.id.item2, "事故分类:" + item.getSgfl());
        helper.setText(R.id.item3, "事故等级:" + item.getSgdj());
        helper.setText(R.id.item4, "事故日期:" + item.getSgrq());
    }
}
