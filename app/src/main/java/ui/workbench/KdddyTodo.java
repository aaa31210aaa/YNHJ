package ui.workbench;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dimine.ynhj.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import bean.TodoBean;
import butterknife.BindView;
import utils.BaseActivity;

/**
 * 矿堆调度员待办列表
 */
public class KdddyTodo extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    TextView title_name_right;
    @BindView(R.id.search_edittext)
    EditText search_edittext;
    @BindView(R.id.search_clear)
    ImageView search_clear;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<TodoBean> mDatas;
    private List<TodoBean> searchDatas;

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_kdddy_todo;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
