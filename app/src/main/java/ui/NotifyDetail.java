package ui;

import android.content.Intent;
import android.widget.TextView;

import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.OnClick;
import utils.BaseActivity;

public class NotifyDetail extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.notify_detail_title)
    TextView notify_detail_title;
    @BindView(R.id.notify_detail_date)
    TextView notify_detail_date;
    @BindView(R.id.notify_detail_content)
    TextView notify_detail_content;
    private String id = "";
    private String title = "";
    private String date = "";
    private String content = "";

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        title_name.setText(R.string.notify_title);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        content = intent.getStringExtra("content");
        notify_detail_title.setText(title);
        notify_detail_date.setText(date);
        notify_detail_content.setText(content);

    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_notify_detail;
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

}
