package adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.NotifyBean;
import cn.bingoogolapple.badgeview.BGABadgeLinearLayout;

public class NotifyAdapter extends BaseQuickAdapter<NotifyBean, BaseViewHolder> {
    public NotifyAdapter(int layoutResId, @Nullable List<NotifyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NotifyBean item) {
        helper.setText(R.id.item1, item.getTitle());
        helper.setText(R.id.item2, item.getDate());

        BGABadgeLinearLayout notice_item_badge = helper.getView(R.id.notify_badge);
        if (item.isRead()) {
            notice_item_badge.hiddenBadge();
        } else {
            notice_item_badge.showCirclePointBadge();
        }

    }
}
