package adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.dimine.ynhj.R;

import java.util.List;

import bean.YhBean;

public class YhfcZgqAdapter extends BaseQuickAdapter<YhBean, BaseViewHolder> {
    private Context context;

    public YhfcZgqAdapter(Context context, int layoutResId, @Nullable List<YhBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, YhBean item) {
        ImageView imageView = helper.getView(R.id.yhfc_zgq_img);
        Glide.with(context)
                .load(item.getZgqImage())
                .centerCrop()
                .error(R.drawable.default_error)
                .crossFade()
                .into(imageView);
    }
}
