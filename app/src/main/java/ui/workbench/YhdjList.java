package ui.workbench;

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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.dimine.ynhj.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import adapter.YhdjAdapter;
import bean.YhBean;
import butterknife.BindView;
import butterknife.OnClick;
import utils.BaseActivity;
import utils.DialogUtil;
import utils.DividerItemDecoration;
import utils.ShowToast;

import static tab.Workbench.yhTag;

public class YhdjList extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    ImageView title_name_right;
    @BindView(R.id.search_edittext)
    EditText search_edittext;
    @BindView(R.id.search_clear)
    ImageView search_clear;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List<YhBean> mDatas;
    private List<YhBean> searchDatas;
    private YhdjAdapter adapter;
    private String tag = "";

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_yhdj_list;
    }

    @Override
    protected void initView() {
        mDatas = new ArrayList<>();
        initRv();
        initRefresh();
        MonitorEditext();
    }

    @Override
    protected void initData() {
        tag = getIntent().getStringExtra(yhTag);
        title_name.setText(R.string.yhlb);
        if (tag.equals("yhdj")) {
            title_name_right.setImageResource(R.drawable.add);
        }
        mConnect();
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    @OnClick(R.id.title_name_right)
    void Add() {
        Intent intent = new Intent(this, Yhdj.class);
        intent.putExtra("tag", "add");
        startActivityForResult(intent, 10);
    }


    private void initRv() {
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        if (adapter == null) {
            adapter = new YhdjAdapter(R.layout.rv_item4, mDatas);
            adapter.bindToRecyclerView(recyclerView);
            recyclerView.setAdapter(adapter);
        }
        dialog = DialogUtil.createLoadingDialog(this, R.string.loading);
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
            searchDatas = new ArrayList<YhBean>();
            for (YhBean entity : mDatas) {
                try {
                    if (entity.getYhmc().contains(str) || entity.getYhlx().contains(str) || entity.getYhdd().contains(str) || entity.getLrsj().contains(str)) {
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
        for (int i = 0; i < 10; i++) {
            YhBean bean = new YhBean();
            bean.setYhmc("名称" + i);
            bean.setYhlx("类型" + i);
            bean.setYhdd("地点" + i);
            bean.setLrsj("时间" + i);
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
        if (refreshLayout.isRefreshing())
            refreshLayout.finishRefresh();

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (tag.equals("yhzg")) {
                    YhBean bean = (YhBean) adapter.getData().get(position);
                    Intent intent = new Intent(YhdjList.this, Yhzg.class);
                    startActivity(intent);
                }
            }
        });


    }
}
