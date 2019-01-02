package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.TodoBean;

public class TodoAdapter extends BaseQuickAdapter<TodoBean, BaseViewHolder> {
    public TodoAdapter(int layoutResId, @Nullable List<TodoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodoBean item) {
        helper.setText(R.id.item1,"位置："+item.getWeizhi());
        helper.setText(R.id.item2,"已发车/总车数："+item.getCs());
    }
}
