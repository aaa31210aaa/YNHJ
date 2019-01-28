package ui.workbench;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.dimine.ynhj.R;

import java.util.ArrayList;
import java.util.List;

import adapter.LaboratoryResultAdapter;
import bean.LaboratoryResultBean;
import butterknife.BindView;
import butterknife.OnClick;
import utils.BaseActivity;
import utils.DialogUtil;
import utils.ShowToast;

public class BmHyjg extends BaseActivity {
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
    private List<LaboratoryResultBean> mDatas;
    private LaboratoryResultAdapter adapter;
    private String jcbh[];
    private String yybh[];
    private String Auys[];

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_bm_hyjg;
    }

    @Override
    protected void initView() {
        mDatas = new ArrayList<>();
        initRv();
    }

    @Override
    protected void initData() {
        title_name.setText(R.string.laboratoryresult_title);
        title_name_right.setText(R.string.query);
        jcbh = getResources().getStringArray(R.array.jcys_data);
        yybh = getResources().getStringArray(R.array.yybh_data);
        Auys = getResources().getStringArray(R.array.Auys_data);
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    private void initRv() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        if (adapter == null) {
            adapter = new LaboratoryResultAdapter(R.layout.laboratory_result_item, mDatas);
            adapter.bindToRecyclerView(recyclerView);
            adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            adapter.isFirstOnly(true);
            recyclerView.setAdapter(adapter);
        }
    }

    @OnClick(R.id.title_name_right)
    void Query() {
        if (search_edittext.getText().toString().equals("")) {
            ShowToast.showShort(this, R.string.input_search_nr);
        } else {
            dialog = DialogUtil.createLoadingDialog(this, R.string.loading);
            mConnect();
        }
    }

    private void mConnect() {
        if (mDatas.size() > 0)
            mDatas.clear();
        for (int i = 0; i < 5; i++) {
            LaboratoryResultBean bean = new LaboratoryResultBean();
            bean.setJcbh(jcbh[i]);
            bean.setYybh(yybh[i]);
            bean.setAuys(Auys[i]);
            mDatas.add(bean);
        }

        //如果无数据设置空布局
        if (mDatas.size() == 0) {
            adapter.setEmptyView(R.layout.nodata_layout, (ViewGroup) recyclerView.getParent());
        } else {
            adapter.setNewData(mDatas);
        }

        if (dialog.isShowing())
            dialog.dismiss();

    }
}
