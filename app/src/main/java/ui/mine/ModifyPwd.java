package ui.mine;

import android.widget.TextView;

import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.OnClick;
import utils.BaseActivity;

public class ModifyPwd extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        title_name.setText(R.string.modify_title);
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_modify_pwd;
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }
}
