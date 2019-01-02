package tab;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dimine.ynhj.R;

import java.util.ArrayList;
import java.util.List;

import adapter.LaboratoryResultAdapter;
import bean.LaboratoryResultBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.BaseFragment;
import utils.DialogUtil;
import utils.DividerItemDecoration;
import utils.ShowToast;

/**
 * A simple {@link Fragment} subclass.
 */
public class LaboratoryResultFragment extends BaseFragment {
    private View view;
    @BindView(R.id.back)
    ImageView back;
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


    public LaboratoryResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View makeView() {
        view = View.inflate(getActivity(), R.layout.fragment_laboratory_result, null);
        //绑定fragment
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {
        back.setVisibility(View.GONE);
        mDatas = new ArrayList<>();
        initRv();
        title_name.setText(R.string.laboratoryresult_title);
        title_name_right.setText(R.string.query);
        jcbh = getResources().getStringArray(R.array.jcys_data);
        yybh = getResources().getStringArray(R.array.yybh_data);
        Auys = getResources().getStringArray(R.array.Auys_data);
    }

    private void initRv() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        if (adapter == null) {
            adapter = new LaboratoryResultAdapter(R.layout.laboratory_result_item, mDatas);
            adapter.bindToRecyclerView(recyclerView);
            recyclerView.setAdapter(adapter);
        }
    }

    @OnClick(R.id.title_name_right)
    void Query() {
        if (search_edittext.getText().toString().equals("")) {
            ShowToast.showShort(getActivity(), R.string.input_search_nr);
        } else {
            dialog = DialogUtil.createLoadingDialog(getActivity(), R.string.loading);
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
