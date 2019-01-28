package adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.TodoBean;

public class TicketsAdapter extends BaseQuickAdapter<TodoBean, BaseViewHolder> {
    public TicketsAdapter(int layoutResId, @Nullable List<TodoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TodoBean item) {
        helper.setText(R.id.item1, "车号：" + item.getCh());
        helper.setText(R.id.item2, "卸矿点：" + item.getXkd());
        helper.setText(R.id.item3, "台阶：" + item.getTj());
//        if (item.getIsSubmit().equals("未确认")) {
        TextView textView = helper.getView(R.id.item4);
        textView.setTextColor(Color.RED);
        textView.setText(item.getIsSubmit());
//        } else {
//            helper.setText(R.id.qrrq_tv, "确认日期：" + item.getSubmit_date());
//            TextView textView = helper.getView(R.id.item4);
//            textView.setTextColor(Color.GREEN);
//        }
//        helper.setText(R.id.item4, "是否确认：" + item.getIsSubmit());


    }
}
