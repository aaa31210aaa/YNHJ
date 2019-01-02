package tab;


import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ui.ChooseDestination;
import ui.workbench.AqjcwtList;
import ui.workbench.Aqyhgl;
import ui.workbench.Dbrw;
import ui.workbench.GzdbdjList;
import ui.workbench.Hyjg;
import ui.workbench.KcsjTodo;
import ui.workbench.LswtdjList;
import ui.workbench.SwwtList;
import ui.workbench.XkdryTodo;
import utils.BaseFragment;
import utils.PortIpAddress;

import static utils.PortIpAddress.BZZ;
import static utils.PortIpAddress.CKRY_CODE;
import static utils.PortIpAddress.KCSJ_CODE;
import static utils.PortIpAddress.KDDDY;
import static utils.PortIpAddress.XKDRY_CODE;


/**
 * 工作台
 */
public class Workbench extends BaseFragment {
    private View view;
    @BindView(R.id.title_name)
    TextView title_name;

    public Workbench() {
        // Required empty public constructor
    }

    @Override
    public View makeView() {
        view = View.inflate(getActivity(), R.layout.fragment_workbench, null);
        //绑定fragment
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {
        title_name.setText(R.string.workbench_title);
    }

    /**
     * 待办任务
     */
    @OnClick(R.id.workbench_dbrw)
    void Dbrw() {
        if (PortIpAddress.getUserType(getActivity()).equals(CKRY_CODE) || PortIpAddress.getUserType(getActivity()).equals(BZZ)) {
            startActivity(new Intent(getActivity(), Dbrw.class));
        }else if (PortIpAddress.getUserType(getActivity()).equals(KDDDY)){
            startActivity(new Intent(getActivity(), ChooseDestination.class));
        }else if (PortIpAddress.getUserType(getActivity()).equals(KCSJ_CODE)){
            startActivity(new Intent(getActivity(),KcsjTodo.class));
        }else if (PortIpAddress.getUserType(getActivity()).equals(XKDRY_CODE)){
            startActivity(new Intent(getActivity(),XkdryTodo.class));
        }
    }

    /**
     * 化验结果
     */
    @OnClick(R.id.workbench_hyjg)
    void Hyjg() {
        startActivity(new Intent(getActivity(), Hyjg.class));
    }

    /**
     * 工作督办登记
     */
    @OnClick(R.id.workbench_gzdbdj)
    void Gzdbdj() {
        startActivity(new Intent(getActivity(), GzdbdjList.class));
    }

    /**
     * 三违问题登记
     */
    @OnClick(R.id.workbench_swwtdj)
    void Swwtdj() {
        startActivity(new Intent(getActivity(), SwwtList.class));
    }

    /**
     * 安全检查问题登记
     */
    @OnClick(R.id.workbench_aqjcwtdj)
    void Aqjcwtdj() {
        startActivity(new Intent(getActivity(), AqjcwtList.class));
    }

    /**
     * 6S问题登记
     */
    @OnClick(R.id.workbench_lswtdj)
    void Lswtdj() {
        startActivity(new Intent(getActivity(), LswtdjList.class));
    }

    /**
     * 安全隐患管理
     */
    @OnClick(R.id.workbench_aqyhgl)
    void Aqyhgl() {
        startActivity(new Intent(getActivity(), Aqyhgl.class));
    }


}
