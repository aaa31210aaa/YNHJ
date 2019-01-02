package tab;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ui.About;
import ui.Login;
import ui.ModifyPwd;
import utils.AppUtils;
import utils.BaseFragment;
import utils.PortIpAddress;


/**
 * A simple {@link Fragment} subclass.
 */
public class Mine extends BaseFragment {
    private View view;
    //    @BindView(R.id.title_name)
//    TextView title_name;
//    @BindView(R.id.back)
//    ImageView back;
    @BindView(R.id.mine_headimg)
    ImageView mine_headimg;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.usertype_tv)
    TextView usertype_tv;
    @BindView(R.id.version_check_tv)
    TextView version_check_tv;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private String versionName = "Version";

    public Mine() {
        // Required empty public constructor
    }

    @Override
    public View makeView() {
        view = View.inflate(getActivity(), R.layout.fragment_mine, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void loadData() {
//        back.setVisibility(View.GONE);
//        title_name.setText(R.string.mine_title);
        name_tv.setText(PortIpAddress.getUserName(getActivity()));
        usertype_tv.setText(PortIpAddress.getUserTypeName(getActivity()));
        version_check_tv.setText(versionName + AppUtils.getVersionName(getActivity()));
        switch (PortIpAddress.getUserType(getActivity())) {
            case "1":
                mine_headimg.setBackgroundResource(R.drawable.ckddy_head);
                break;
            case "2":
                mine_headimg.setBackgroundResource(R.drawable.kcsj_head);
                break;
            case "3":
                mine_headimg.setBackgroundResource(R.drawable.xkdczy_head);
                break;
        }
        sp = getActivity().getSharedPreferences("userInfo", Activity.MODE_PRIVATE);
        editor = sp.edit();
    }


    /**
     * 修改密码
     */
    @OnClick(R.id.modify_pwd)
    void ModifyPwd() {
        startActivity(new Intent(getActivity(), ModifyPwd.class));
    }

    /**
     * 检查版本
     */
    @OnClick(R.id.mine_version_check)
    void CheckVersion() {

    }

    /**
     * 清理缓存
     */
    @OnClick(R.id.clear_cache)
    void ClearCache() {

    }

    /**
     * 关于
     */
    @OnClick(R.id.mine_about)
    void About() {
        startActivity(new Intent(getActivity(), About.class));
    }


    /**
     * 注销
     */
    @OnClick(R.id.mine_cancellation_rl)
    void Cancel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.mine_cancellation_dialog_title);
        builder.setMessage(R.string.mine_cancellation_dialog_content);
        builder.setPositiveButton(R.string.mine_cancellation_dialog_btn2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editor.remove("ISCHECK");
                editor.remove("PWDISCHECK");
                editor.remove("USER_NAME");
                editor.remove("PWD");
                editor.commit();
                Intent logoutIntent = new Intent(getActivity(), Login.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutIntent);
            }
        });

        builder.setNegativeButton(R.string.mine_cancellation_dialog_btn1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


}
