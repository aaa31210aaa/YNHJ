package ui;

import android.widget.TextView;

import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.OnClick;
import utils.AppUtils;
import utils.BaseActivity;

public class About extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.version_name)
    TextView version_name;

    @Override
    protected void initView() {

    }

    @OnClick(R.id.back)
    void Back(){
        finish();
    }

    @Override
    protected void initData() {
        title_name.setText(R.string.gywm);
        version_name.setText("Version" + AppUtils.getVersionName(this));
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_about;
    }
}
