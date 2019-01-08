package ui.workbench;

import android.widget.TextView;

import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.OnClick;
import utils.BaseActivity;

public class Aqjcwtdj extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_aqjcwtdj;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        title_name.setText(R.string.aqjcwt_title);
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }
}
