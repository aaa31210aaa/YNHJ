package tab;


import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dimine.ynhj.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import adapter.LedgerAdapter;
import bean.LedgerBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.BaseFragment;
import utils.CustomDatePicker;
import utils.DateUtils;
import utils.DividerItemDecoration;
import utils.ShowToast;

import static utils.CustomDatePicker.pvCustomOptions;
import static utils.CustomDatePicker.pvCustomTime;

/**
 * 台帐
 */
public class Ledger extends BaseFragment {
    private View view;
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right1)
    TextView title_name_right1;
    @BindView(R.id.title_name_right2)
    TextView title_name_right2;
    @BindView(R.id.title_name_right3)
    TextView title_name_right3;
    private String tj[];
    private String kkgzm[];
    private String kslx[];
    private String pw[];
    private String jsl[];
    private String ksl[];
    private List<LedgerBean> tj_datas;

    @BindView(R.id.search_edittext)
    EditText search_edittext;
    @BindView(R.id.search_clear)
    ImageView search_clear;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<LedgerBean> mDatas;
    private List<LedgerBean> searchDatas;
    private LedgerAdapter adapter;


    public Ledger() {
        // Required empty public constructor
    }

    @Override
    public View makeView() {
        view = View.inflate(getActivity(), R.layout.fragment_ledger, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {
        mDatas = new ArrayList<>();
        title_name.setText(R.string.ledger_title);
        title_name_right3.setText("台阶");
        initDate();
        initTjData();
        initRv();
        initRefresh();
        MonitorEditext();
        mConnect();
    }

    private void initDate() {
        title_name_right1.setText(DateUtils.getStringDateShort());
        CustomDatePicker.initCustomYearMonthDay(getActivity(), title_name_right1);
    }


    private void initTjData() {
        tj_datas = new ArrayList<>();
        tj = getResources().getStringArray(R.array.tj_data);
        kkgzm = getResources().getStringArray(R.array.kkgzm_data);
        kslx = getResources().getStringArray(R.array.kslx_data);
        pw = getResources().getStringArray(R.array.ledger_pw_data);
        jsl = getResources().getStringArray(R.array.jsl_data);
        ksl = getResources().getStringArray(R.array.ksl_data);

        for (int i = 0; i < tj.length; i++) {
            LedgerBean bean = new LedgerBean();
            bean.setTj_content(tj[i]);
            tj_datas.add(bean);
        }

        title_name_right2.setText(tj_datas.get(0).getTj_content());
        CustomDatePicker.initCustomOptionPicker(getActivity(), tj_datas, title_name_right2);
    }

    private void initRv() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        if (adapter == null) {
            adapter = new LedgerAdapter(R.layout.rv_item5, mDatas);
            adapter.bindToRecyclerView(recyclerView);
            recyclerView.setAdapter(adapter);
        }
//        dialog = DialogUtil.createLoadingDialog(getActivity(), R.string.loading);
    }

    private void initRefresh() {
        //刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                handler.sendEmptyMessageDelayed(1, ShowToast.refreshTime);
            }
        });

        //加载
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                handler.sendEmptyMessageDelayed(0, ShowToast.refreshTime);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    refreshLayout.finishLoadmore();
                    break;
                case 1:
                    mConnect();
                    break;
                default:
                    break;
            }
        }
    };


    /**
     * 监听搜索框
     */
    private void MonitorEditext() {
        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, count + "----");
                if (mDatas != null) {
                    if (search_edittext.length() > 0) {
                        refreshLayout.setEnableRefresh(false);
                        search_clear.setVisibility(View.VISIBLE);
                        search(search_edittext.getText().toString().trim());
                    } else {
                        refreshLayout.setEnableRefresh(true);
                        search_clear.setVisibility(View.GONE);
                        if (adapter != null) {
                            adapter.setNewData(mDatas);
                        }
                    }
                } else {
                    if (search_edittext.length() > 0) {
                        search_clear.setVisibility(View.VISIBLE);
                    } else {
                        search_clear.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 清除搜索框内容
     */
    @OnClick(R.id.search_clear)
    public void ClearSearch() {
        search_edittext.setText("");
        search_clear.setVisibility(View.GONE);
    }

    //搜索框
    private void search(String str) {
        if (mDatas != null) {
            searchDatas = new ArrayList<LedgerBean>();
            for (LedgerBean entity : mDatas) {
                try {
                    if (entity.getKkgzm().contains(str) || entity.getKslx().contains(str)
                            || entity.getPw().contains(str) || entity.getJsl().contains(str)
                            || entity.getKsl().contains(str)) {
                        searchDatas.add(entity);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter.setNewData(searchDatas);
            }
        }
    }


    private void mConnect() {
        if (mDatas.size() > 0)
            mDatas.clear();
        for (int i = 0; i < 7; i++) {
            LedgerBean bean = new LedgerBean();
            bean.setKkgzm(kkgzm[i]);
            bean.setKslx(kslx[i]);
            bean.setPw(pw[i]);
            bean.setJsl(jsl[i]);
            bean.setKsl(ksl[i]);
            mDatas.add(bean);
        }

        //如果无数据设置空布局
        if (mDatas.size() == 0) {
            adapter.setEmptyView(R.layout.nodata_layout, (ViewGroup) recyclerView.getParent());
        } else {
            adapter.setNewData(mDatas);
        }

        if (refreshLayout.isRefreshing())
            refreshLayout.finishRefresh();
    }

    @OnClick(R.id.title_name_right1)
    void Date() {
        if (pvCustomTime != null)
            pvCustomTime.show();
    }


    @OnClick(R.id.title_name_right2)
    void TaiJie() {
        if (pvCustomOptions != null)
            pvCustomOptions.show();
    }
}
