package ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.project.dimine.ynhj.MainActivity;
import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import utils.AppUtils;
import utils.ClearEditText;
import utils.DialogUtil;
import utils.SharedPrefsUtil;
import utils.ShowToast;
import utils.StatusBarUtils;

public class Login extends AppCompatActivity {
    @BindView(R.id.account_etv)
    ClearEditText account_etv;
    @BindView(R.id.pwd_etv)
    ClearEditText pwd_etv;
    @BindView(R.id.account_textinputlayout)
    TextInputLayout account_textinputlayout;
    @BindView(R.id.pwd_textinputlayout)
    TextInputLayout pwd_textinputlayout;

    @BindView(R.id.saveAccountCb)
    CheckBox saveAccountCb;
    @BindView(R.id.savePwdCb)
    CheckBox savePwdCb;
    @BindView(R.id.btn_login)
    Button btn_login;
    private Dialog dialog;
    private boolean isExit = false;
    private String usertype = "";
    private String usertypeName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        StatusBarUtils.transparencyBar(this);
        initView();
        iniData();
    }

    private void initView() {
        AppUtils.EtvNoSpace(account_etv);
        AppUtils.EtvNoSpace(pwd_etv);
        MonitorEtv();
//        account_textinputlayout.setFocusable(true);
//        account_textinputlayout.setFocusableInTouchMode(true);
    }

    private void iniData() {
        SaveAccount();
        SavePwd();
    }

    /**
     * 监听Etv
     */
    private void MonitorEtv() {
        account_etv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (account_etv.getText().length() > 0) {
                    account_textinputlayout.setErrorEnabled(false);
                } else {
                    account_textinputlayout.setError(null);
                }

                if (pwd_etv.getText().length() > 0 || account_etv.getText().length() > 0) {
                    btn_login.setEnabled(true);
                } else {
                    btn_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        pwd_etv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (pwd_etv.getText().length() > 0) {
                    pwd_textinputlayout.setErrorEnabled(false);
                } else {
                    pwd_textinputlayout.setError(null);
                }

                if (pwd_etv.getText().length() > 0 || account_etv.getText().length() > 0) {
                    btn_login.setEnabled(true);
                } else {
                    btn_login.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    /**
     * 设置记住账号
     */
    private void SaveAccount() {
        //判断记住账号选框的状态
        if (SharedPrefsUtil.getValue(this, "userInfo", "ISCHECK", false)) {
            //设置默认是记住账号状态
            saveAccountCb.setChecked(true);

            account_etv.setText(SharedPrefsUtil.getValue(this, "userInfo", "USER_NAME", ""));
//            if (account_etv.getText().toString().trim().equals("")) {
//                account_etv.requestFocus();
//            } else {
//                pwd_etv.requestFocus();
//            }
        }

        //监听保存账号的选择框
        saveAccountCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (saveAccountCb.isChecked()) {
                    SharedPrefsUtil.putValue(Login.this, "userInfo", "ISCHECK", true);
                } else {
                    SharedPrefsUtil.putValue(Login.this, "userInfo", "ISCHECK", false);
                }
            }
        });
    }

    /**
     * 设置记住密码
     */
    private void SavePwd() {
        //判断记住密码选框的状态
        if (SharedPrefsUtil.getValue(this, "userInfo", "PWDISCHECK", false)) {
            savePwdCb.setChecked(true);
            pwd_etv.setText(SharedPrefsUtil.getValue(this, "userInfo", "PWD", ""));

//            if (pwd_etv.getText().toString().trim().equals("")) {
//                pwd_etv.requestFocus();
//            } else {
//                account_etv.requestFocus();
//            }
        }

        //监听保存密码的选框
        savePwdCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (savePwdCb.isChecked()) {
                    SharedPrefsUtil.putValue(Login.this, "userInfo", "PWDISCHECK", true);
                } else {
                    SharedPrefsUtil.putValue(Login.this, "userInfo", "PWDISCHECK", false);
                }
            }
        });
    }

    @OnClick(R.id.btn_login)
    void Login() {
        dialog = DialogUtil.createLoadingDialog(Login.this, R.string.loading_write);
        if (account_etv.getText().toString().equals("")) {
//            ShowToast.showShort();
            account_textinputlayout.setError(getString(R.string.account_no_empty));
            account_etv.startShakeAnimation();
        } else if (pwd_etv.getText().toString().equals("")) {
            pwd_textinputlayout.setError(getString(R.string.pwd_no_empty));
            pwd_etv.startShakeAnimation();
        } else if (account_etv.getText().toString().equals("admin1") && pwd_etv.getText().toString().equals("123")) {
            usertype = "1";
            usertypeName = "采矿调度员";
            handler.sendEmptyMessageDelayed(1, 1500);
        } else if (account_etv.getText().toString().equals("admin2") && pwd_etv.getText().toString().equals("123")) {
            usertype = "2";
            usertypeName = "卡车司机";
            handler.sendEmptyMessageDelayed(1, 1500);
        } else if (account_etv.getText().toString().equals("admin3") && pwd_etv.getText().toString().equals("123")) {
            usertype = "3";
            usertypeName = "卸矿点操作员";
            handler.sendEmptyMessageDelayed(1, 1500);
        } else if (account_etv.getText().toString().equals("admin4") && pwd_etv.getText().toString().equals("123")) {
            usertype = "4";
            usertypeName = "采矿班组长";
            handler.sendEmptyMessageDelayed(1, 1500);
        } else if (account_etv.getText().toString().equals("admin5") && pwd_etv.getText().toString().equals("123")) {
            usertype = "5";
            usertypeName = "矿堆调度员";
            handler.sendEmptyMessageDelayed(1, 1500);
        } else {
            handler.sendEmptyMessageDelayed(2, 1500);
        }
    }


    public Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    isExit = false;
                    break;
                case 1:
                    mConnect();
                    break;
                case 2:
                    dialog.dismiss();
                    ShowToast.showShort(Login.this, R.string.account_or_pwd_err);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 延迟发送退出
     */
    private void exit() {
        if (!isExit) {
            isExit = true;
            ShowToast.showShort(this, R.string.click_agin);
            // 利用handler延迟发送更改状态信息
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }


    private void mConnect() {
        //用户角色类型  1=采矿调度员、2=卡车司机、3=卸矿点操作员  4=采矿班组长
        JPushInterface.setAlias(this, 0, usertype + "");
        SharedPrefsUtil.putValue(Login.this, "userInfo", "usertype", usertype);
        SharedPrefsUtil.putValue(Login.this, "userInfo", "usertypename", usertypeName);
        if (SharedPrefsUtil.getValue(Login.this, "userInfo", "ISCHECK", true)) {
            SharedPrefsUtil.putValue(Login.this, "userInfo", "USER_NAME", account_etv.getText().toString());
        }
        if (SharedPrefsUtil.getValue(Login.this, "userInfo", "PWDISCHECK", true)) {
            SharedPrefsUtil.putValue(Login.this, "userInfo", "PWD", pwd_etv.getText().toString());
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        dialog.dismiss();
        finish();
    }

}
