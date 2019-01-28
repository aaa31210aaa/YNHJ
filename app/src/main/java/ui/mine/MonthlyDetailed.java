package ui.mine;


import android.content.Intent;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.dimine.ynhj.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import adapter.MonthlyDetailAdapter;
import bean.ProductionPanelBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ui.workbench.OneDayDetail;
import utils.BaseFragment;
import utils.DividerItemDecoration;
import utils.ShowToast;


/**
 * 月数据详情
 */
public class MonthlyDetailed extends BaseFragment {
    private View view;
    @BindView(R.id.search_edittext)
    EditText search_edittext;
    @BindView(R.id.search_clear)
    ImageView search_clear;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<ProductionPanelBean> mDatas;
    private List<ProductionPanelBean> searchDatas;
    private MonthlyDetailAdapter adapter;
    private String date[];
    private String weather[];
    private String ckkl[];
    private String ckpw[];
    private String ljckkl[];
    private String ljckpw[];

    public MonthlyDetailed() {
        // Required empty public constructor
    }

    @Override
    public View makeView() {
        view = View.inflate(getActivity(), R.layout.fragment_monthly_detailed, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {
        date = getResources().getStringArray(R.array.date_data);
        weather = getResources().getStringArray(R.array.weather);
        ckkl = getResources().getStringArray(R.array.kl_data);
        ckpw = getResources().getStringArray(R.array.pw_data);
        ljckkl = getResources().getStringArray(R.array.kl_data);
        ljckpw = getResources().getStringArray(R.array.pw_data);
        mDatas = new ArrayList<>();
        initRv();
        initRefresh();
        MonitorEditext();
        mConnect();
    }

    private void initRv() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        if (adapter == null) {
            adapter = new MonthlyDetailAdapter(R.layout.monthly_detail_item, mDatas);
            adapter.bindToRecyclerView(recyclerView);
            recyclerView.setAdapter(adapter);
        }
    }

    private void mConnect() {
        if (mDatas.size() > 0)
            mDatas.clear();
        for (int i = 0; i < 31; i++) {
            ProductionPanelBean bean = new ProductionPanelBean();
            bean.setId(i + 1 + "");
            bean.setDate(date[i]);
            bean.setWeather(weather[i]);
            bean.setCk_kl(ckkl[i]);
            bean.setCk_pw(ckpw[i]);
            bean.setLjck_kl(ljckkl[i]);
            bean.setLjck_pw(ljckpw[i]);
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

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ProductionPanelBean bean = (ProductionPanelBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), OneDayDetail.class);
                intent.putExtra("date", bean.getDate());
                intent.putExtra("weather", bean.getWeather());
                startActivity(intent);
            }
        });
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
            searchDatas = new ArrayList<ProductionPanelBean>();
            for (ProductionPanelBean entity : mDatas) {
                try {
                    if (entity.getDate().contains(str) || entity.getWeather().contains(str)
                            || entity.getCk_kl().contains(str) || entity.getCk_pw().contains(str)
                            || entity.getLjck_kl().contains(str) || entity.getLjck_pw().contains(str)) {
                        searchDatas.add(entity);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter.setNewData(searchDatas);
            }
        }
    }
}
