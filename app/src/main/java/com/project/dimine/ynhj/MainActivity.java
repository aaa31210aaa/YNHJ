package com.project.dimine.ynhj;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;

import adapter.ViewPagerAdapter;
import butterknife.BindView;
import pub.devrel.easypermissions.EasyPermissions;
import tab.Home;
import tab.Mine;
import tab.Statistical;
import tab.Workbench;
import utils.BaseActivity;
import utils.NoScrollViewPager;
import utils.PermissionUtil;
import utils.ShowToast;

import static utils.PermissionUtil.STORAGE_REQUESTCODE;

public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.main_viewpager)
    NoScrollViewPager main_viewpager;
    @BindView(R.id.main_tablayout)
    TabLayout main_tablayout;

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //设置tab
        SetTab();
        checkWritePermissions();

    }


    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_main;
    }


    /**
     * 检测读写权限
     */
//    @AfterPermissionGranted(PermissionUtil.STORAGE_REQUESTCODE)
    private void checkWritePermissions() {
        String[] perms = {PermissionUtil.WriteFilePermission};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
//            CheckVersion checkVersion = new CheckVersion();
//            checkVersion.CheckVersions(this, TAG);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_external_storage),
                    STORAGE_REQUESTCODE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 请求权限成功。
     *
     * @param requestCode
     * @param list
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // Some permissions have been granted
//        CheckVersion checkVersion = new CheckVersion();
//        checkVersion.CheckVersions(this, TAG);
        Log.e(TAG,"66");
    }

    /**
     * 请求权限失败。
     *
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            PermissionSettingPage.GoToPermissionSetting(this);
        }
    }

    private void SetTab() {
        List<Fragment> fragments = new ArrayList<>();

//        fragments.add(new Ledger());
        fragments.add(new Home());
//            fragments.add(new TodoFragment());
//            fragments.add(new LaboratoryResultFragment());
        fragments.add(new Workbench());
        fragments.add(new Statistical());
        fragments.add(new Mine());
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, new String[]{"首页", "工作台", "统计分析", "我的"});
        main_viewpager.setOffscreenPageLimit(3);
        main_viewpager.setAdapter(adapter);

        //关联图文
        main_tablayout.setupWithViewPager(main_viewpager);
        main_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                main_viewpager.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        for (int i = 0; i < main_tablayout.getTabCount(); i++) {
            TabLayout.Tab tab = main_tablayout.getTabAt(i);
            Drawable d = null;
            switch (i) {
                case 0:
                    d = ContextCompat.getDrawable(this, R.drawable.home_tab);
                    break;
                case 1:
                    d = ContextCompat.getDrawable(this, R.drawable.workbench_tab);
//                  d = ContextCompat.getDrawable(this, R.drawable.ledger_tab);
                    break;
                case 2:
                    d = ContextCompat.getDrawable(this, R.drawable.statistical_tab);
                    break;
                case 3:
                    d = ContextCompat.getDrawable(this, R.drawable.mine_tab);
                    break;
            }
            tab.setIcon(d);
        }
    }


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
            handler.sendEmptyMessageDelayed(0, send_time);
        } else {
            finish();
            System.exit(0);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    isExit = false;
                    break;
            }
        }
    };
}
