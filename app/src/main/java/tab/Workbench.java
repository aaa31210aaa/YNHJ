package tab;


import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ui.workbench.AqjcwtList;
import ui.workbench.BmHyjg;
import ui.workbench.GzdbdjList;
import ui.workbench.Hyjg;
import ui.workbench.LsfcList;
import ui.workbench.LswtdjList;
import ui.workbench.SgdjList;
import ui.workbench.SwwtList;
import ui.workbench.YhdjList;
import ui.workbench.YhfcList;
import utils.BaseFragment;
import utils.BuilderManager;


/**
 * 工作台
 */
public class Workbench extends BaseFragment {
    private View view;
    @BindView(R.id.title_name)
    TextView title_name;
    private int str[] = {R.string.yhdj, R.string.yhzg, R.string.yhfc};
    private int sub_str[] = {R.string.yhdj_sub, R.string.yhzg_sub, R.string.yhfc_sub};
    private int getImage[] = {R.drawable.dj_img, R.drawable.dj_img, R.drawable.yhfc_img, R.drawable.dj_img};

    private int str_6s[] = {R.string.lsdj, R.string.lscl, R.string.lsfc};
    private int sub_str_6s[] = {R.string.lsdj_sub, R.string.lscl_sub, R.string.lsfc_sub};
    private int getImage_6s[] = {R.drawable.dj_img, R.drawable.dj_img, R.drawable.dj_img};

    private int str_hy[] = {R.string.brhysq, R.string.bmhysq};
    private int sub_str_hy[] = {R.string.brhysq_sub, R.string.bmhysq_sub};
    private int getImage_hy[] = {R.drawable.dj_img, R.drawable.dj_img};


    @BindView(R.id.yhgl_menu)
    BoomMenuButton yhgl_menu;
    @BindView(R.id.hyjg_menu)
    BoomMenuButton hyjg_menu;
    @BindView(R.id.lswt_menu)
    BoomMenuButton lswt_menu;


    public static String yhTag = "yhTag";
    public static String lsTag = "lsTag";

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
        init6Swt();
        initYhglMenu();
        initHyjgMenu();
    }

    /**
     * 设置6S问题管理菜单
     */
    private void init6Swt() {
        lswt_menu.setButtonEnum(ButtonEnum.Ham);
        for (int i = 0; i < lswt_menu.getPiecePlaceEnum().pieceNumber(); i++)
            lswt_menu.addBuilder(BuilderManager.getPieceCornerRadiusHamButtonBuilder(getImage_6s[i], str_6s[i], sub_str_6s[i])
                    .pieceColor(Color.WHITE)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index) {
                                case 0:
                                    //6s登记
                                    Intent intent = new Intent(getActivity(), LswtdjList.class);
                                    intent.putExtra(lsTag, "6sdj");
                                    startActivity(intent);
                                    break;
                                case 1:
                                    //6s处理
                                    intent = new Intent(getActivity(), LswtdjList.class);
                                    intent.putExtra(lsTag, "6scl");
                                    startActivity(intent);
                                    break;
                                case 2:
                                    //6s复查
                                    intent = new Intent(getActivity(),LsfcList.class);
                                    startActivity(intent);
                                    break;
                            }
                        }
                    }));
    }

    /**
     * 设置隐患管理菜单
     */
    private void initYhglMenu() {
        yhgl_menu.setButtonEnum(ButtonEnum.Ham);
        for (int i = 0; i < yhgl_menu.getPiecePlaceEnum().pieceNumber(); i++)
            yhgl_menu.addBuilder(BuilderManager.getPieceCornerRadiusHamButtonBuilder(getImage[i], str[i], sub_str[i])
                    .pieceColor(Color.WHITE)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index) {
                                case 0:
                                    //隐患登记
                                    Intent intent = new Intent(getActivity(), YhdjList.class);
                                    intent.putExtra(yhTag, "yhdj");
                                    startActivity(intent);
                                    break;
                                case 1:
                                    //隐患整改
                                    intent = new Intent(getActivity(), YhdjList.class);
                                    intent.putExtra(yhTag, "yhzg");
                                    startActivity(intent);
                                    break;
                                case 2:
                                    //隐患复查
                                    startActivity(new Intent(getActivity(), YhfcList.class));
                                    break;
                            }
                        }
                    }));
    }

    /**
     * 设置化验菜单
     */
    private void initHyjgMenu() {
        hyjg_menu.setButtonEnum(ButtonEnum.Ham);
        for (int i = 0; i < hyjg_menu.getPiecePlaceEnum().pieceNumber(); i++) {
            hyjg_menu.addBuilder(BuilderManager.getPieceCornerRadiusHamButtonBuilder(getImage_hy[i], str_hy[i], sub_str_hy[i])
                    .pieceColor(Color.WHITE)
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index) {
                                case 0:
                                    //本人化验查询
                                    Intent intent = new Intent(getActivity(), Hyjg.class);
                                    startActivity(intent);
                                    break;
                                case 1:
                                    //部门化验查询
                                    intent = new Intent(getActivity(), BmHyjg.class);
                                    startActivity(intent);
                                    break;
                            }
                        }
                    }));
        }
    }


//    /**
//     * 待办任务
//     */
//    @OnClick(R.id.workbench_dbrw)
//    void Dbrw() {
//        if (PortIpAddress.getUserType(getActivity()).equals(CKRY_CODE) || PortIpAddress.getUserType(getActivity()).equals(BZZ)) {
//            startActivity(new Intent(getActivity(), Dbrw.class));
//        } else if (PortIpAddress.getUserType(getActivity()).equals(KDDDY)) {
//            startActivity(new Intent(getActivity(), ChooseDestination.class));
//        } else if (PortIpAddress.getUserType(getActivity()).equals(KCSJ_CODE)) {
//            startActivity(new Intent(getActivity(), KcsjTodo.class));
//        } else if (PortIpAddress.getUserType(getActivity()).equals(XKDRY_CODE)) {
//            startActivity(new Intent(getActivity(), XkdryTodo.class));
//        }
//    }

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
     * 事故登记
     */
    @OnClick(R.id.workbench_sgdj)
    void Sgdj() {
        startActivity(new Intent(getActivity(), SgdjList.class));
    }


//    /**
//     * 6S问题登记
//     */
//    @OnClick(R.id.workbench_lswtdj)
//    void Lswtdj() {
//        startActivity(new Intent(getActivity(), LswtdjList.class));
//    }


}
