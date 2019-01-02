package tab;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.dimine.ynhj.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import adapter.TicketsAdapter;
import bean.TodoBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ui.XkdryFillInTickets;
import utils.BaseFragment;
import utils.DividerItemDecoration;
import utils.ShowToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class XkdryFragment extends BaseFragment {
    private View view;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title_name)
    TextView title_name;
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
    private TicketsAdapter adapter;
    private String ch_arr[];
    private String xkd_arr[];
    private String tj_arr[];
    private String submit_time[];


    public XkdryFragment() {
        // Required empty public constructor
    }


    @Override
    public View makeView() {
        view = View.inflate(getActivity(), R.layout.fragment_xkdry, null);
        //绑定fragment
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {
        mDatas = new ArrayList<>();
        initRv();
        initRefresh();
        MonitorEditext();
        back.setVisibility(View.GONE);
        title_name.setText(R.string.ksplist_title);
        ch_arr = getResources().getStringArray(R.array.ch_data);
        xkd_arr = getResources().getStringArray(R.array.xkd_data);
        tj_arr = getResources().getStringArray(R.array.tj_data);
        submit_time = getResources().getStringArray(R.array.submit_time_data);
        mConnect();
    }

    private void initRv() {
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
        if (adapter == null) {
            adapter = new TicketsAdapter(R.layout.ticketslist_item, mDatas);
            adapter.bindToRecyclerView(recyclerView);
            recyclerView.setAdapter(adapter);
        }
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
            searchDatas = new ArrayList<TodoBean>();
            for (TodoBean entity : mDatas) {
                try {
                    if (entity.getCh().contains(str) || entity.getXkd().contains(str)
                            || entity.getTj().contains(str) || entity.getIsSubmit().contains(str)) {
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
            TodoBean bean = new TodoBean();
            bean.setCh(ch_arr[i]);
            bean.setXkd(xkd_arr[i]);
            bean.setTj(tj_arr[i]);
            if (i < 4) {
                bean.setIsSubmit("已确认");
                bean.setSubmit_date(submit_time[i]);
            } else {
                bean.setIsSubmit("未确认");
            }
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
                TodoBean bean = (TodoBean) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), XkdryFillInTickets.class);
                intent.putExtra("isSubmit", bean.getIsSubmit());
                startActivity(intent);
            }
        });
    }


}
