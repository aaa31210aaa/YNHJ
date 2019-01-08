package ui.workbench;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.project.dimine.ynhj.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adapter.CommonlyGridViewAdapter;
import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;
import utils.BaseActivity;
import utils.CameraUtil;
import utils.DateUtils;
import utils.ImageCompressUtil;
import utils.MyGridView;
import utils.PermissionSettingPage;
import utils.PermissionUtil;
import utils.SharedPrefsUtil;
import utils.ShowToast;
import utils.WaterImage;

import static utils.CustomDatePicker.END_DAY;
import static utils.CustomDatePicker.END_MONTH;
import static utils.CustomDatePicker.END_YEAR;
import static utils.CustomDatePicker.START_DAY;
import static utils.CustomDatePicker.START_MONTH;
import static utils.CustomDatePicker.START_YEAR;
import static utils.CustomDatePicker.getTimeYearMonthDay;
import static utils.PermissionUtil.CAMERA_REQUESTCODE;
import static utils.PermissionUtil.CameraPermission;
import static utils.PermissionUtil.LocationPermission;

public class LswtCl extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    TextView title_name_right;
    @BindView(R.id.lswt_gszrr)
    EditText lswt_gszrr;
    @BindView(R.id.lswt_gsnr)
    EditText lswt_gsnr;
    @BindView(R.id.lswt_sjwcrq)
    TextView lswt_sjwcrq;
    @BindView(R.id.lswt_jgpj)
    EditText lswt_jgpj;
    @BindView(R.id.lswt_cjr)
    EditText lswt_cjr;
    @BindView(R.id.lswt_cjsj)
    TextView lswt_cjsj;
    @BindView(R.id.lswt_xgr)
    EditText lswt_xgr;
    @BindView(R.id.lswt_xgsj)
    TextView lswt_xgsj;
    @BindView(R.id.lswt_bz)
    EditText lswt_bz;
    @BindView(R.id.lswt_gridview_zgh)
    MyGridView lswt_gridview_zgh;

    private TimePickerView sjwcrqTime;
    private TimePickerView cjsjTime;
    private TimePickerView xgsjTime;

    //当前位置的信息
    private String local;
    //默认字符
    private static final String myCode_zgh = "000000";
    //选择的图片的集合
    private ArrayList<String> imagePaths_zgh;

    //返回码
    private static final int REQUEST_CAMERA_CODE_ZGH = 15;
    private static final int REQUEST_PREVIEW_CODE_ZGH = 25;
    private CommonlyGridViewAdapter gridAdapter_zgh;


    private Uri cameraFileUriZgh;

    private String savePath_zgh;
    private File picture_zgh;

    //加水印后的图片地址
    private String waterPath_zgh;
    private File waterf_zgh;
    private ArrayList<String> list_zgh;

    //定位需要的声明
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String imgs_zgh;

    private int clickPosition_zgh;
    //版本比较：是否是4.4及以上版本
    private final boolean mIsKitKat = Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT;
    private String safeMan = "";
    private String isZg = "";


    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_lswt_cl;
    }

    @Override
    protected void initView() {
        initLocal();
        CameraUtil.init(this);
    }

    @Override
    protected void initData() {
        title_name.setText(R.string.lscl);
        title_name_right.setText(R.string.submit);
        imagePaths_zgh = new ArrayList<>();
        list_zgh = new ArrayList<>();
        lswt_gszrr.setText("责任人XXX");
        lswt_cjr.setText("创建人XXX");
        initGridZgh();
        lswt_sjwcrq.setText(DateUtils.getStringDateShort());
        initSjwcrq(lswt_sjwcrq);
        lswt_cjsj.setText(DateUtils.getStringDateShort());
        initCjsj(lswt_cjsj);
        lswt_xgsj.setText(DateUtils.getStringDateShort());
        initXgsj(lswt_xgsj);
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    /**
     * 实际完成日期
     */
    @OnClick(R.id.lswt_sjwcrq)
    void Sjwcrq() {
        if (sjwcrqTime != null)
            sjwcrqTime.show();
    }

    /**
     * 创建时间
     */
    @OnClick(R.id.lswt_cjsj)
    void Cjsj() {
        if (cjsjTime != null)
            cjsjTime.show();
    }


    /**
     * 修改时间
     */
    @OnClick(R.id.lswt_xgsj)
    void Xgsj() {
        if (xgsjTime != null)
            xgsjTime.show();
    }


    private void initSjwcrq(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        sjwcrqTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(getTimeYearMonthDay(date));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sjwcrqTime.returnData();
                                sjwcrqTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sjwcrqTime.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }

    private void initCjsj(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        cjsjTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(getTimeYearMonthDay(date));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cjsjTime.returnData();
                                cjsjTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cjsjTime.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }

    private void initXgsj(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        xgsjTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                textView.setText(getTimeYearMonthDay(date));
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                xgsjTime.returnData();
                                xgsjTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                xgsjTime.dismiss();
                            }
                        });
                    }
                })
                .setContentTextSize(18)
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("年", "月", "日", "", "", "")
                .setLineSpacingMultiplier(1.2f)
                .setTextXOffset(0, 0, 0, 40, 0, -40)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(0xFF24AD9D)
                .build();
    }


    private void initLocal() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(false);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
        // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private class MyAMapLocationListener implements AMapLocationListener {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //省信息+城市信息+城区信息+街道信息
            local = aMapLocation.getProvince() + aMapLocation.getCity() + aMapLocation.getDistrict() + aMapLocation.getStreet();
        }
    }

    private void initGridZgh() {
        //设置gridview一行多少个
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        lswt_gridview_zgh.setNumColumns(cols);
        AddImageZgh();
        imagePaths_zgh.add(myCode_zgh);
        gridAdapter_zgh = new CommonlyGridViewAdapter(this, imagePaths_zgh);
        lswt_gridview_zgh.setAdapter(gridAdapter_zgh);
    }

    private void AddImageZgh() {
        //添加多张图片
        lswt_gridview_zgh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isZg = "zgh";
                imgs_zgh = (String) parent.getItemAtPosition(position);
                clickPosition_zgh = position;
                checkCameraPermissionsZgh();
            }
        });
    }

    private void checkCameraPermissionsZgh() {
        /**
         * 检测读写权限
         */
        String[] perms = {CameraPermission, LocationPermission};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
            ClickAddZgh(clickPosition_zgh);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_camera),
                    CAMERA_REQUESTCODE, perms);
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
        ClickAddZgh(clickPosition_zgh);
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            PermissionSettingPage.GoToPermissionSetting(this);
        }
    }


    private void ClickAddZgh(int position) {
        if (PermissionUtil.hasSdcard()) {
            if (myCode_zgh.equals(imgs_zgh)) {
                if (lswt_gridview_zgh.getCount() >= 7) {
                    ShowToast.showShort(this, "最多添加6张图片");
                } else {
                    OpenCameraZgh();
                }
            } else {
                PhotoPreviewIntent intent = new PhotoPreviewIntent(this);
                imagePaths_zgh.remove(imagePaths_zgh.size() - 1);
                intent.setCurrentItem(position);
                intent.setPhotoPaths(imagePaths_zgh);
                startActivityForResult(intent, REQUEST_PREVIEW_CODE_ZGH);
            }
        } else {
            ShowToast.showShort(this, "没有SD卡");
        }
    }


    private void OpenCameraZgh() {
        if (mIsKitKat) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                createWaterFileZgh();
                picture_zgh = new File(savePath_zgh, System.currentTimeMillis() + ".jpg");
                cameraFileUriZgh = FileProvider.getUriForFile(this, "com.project.dimine.ynhj.fileprovider", picture_zgh);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileUriZgh);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE_ZGH);
            } else {//判断是否有相机应用
                ShowToast.showShort(this, "无相机应用");
            }
        } else {
            createWaterFileZgh();
            picture_zgh = new File(savePath_zgh, System.currentTimeMillis() + ".jpg");
            cameraFileUriZgh = Uri.fromFile(picture_zgh);
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileUriZgh);
            startActivityForResult(intent, REQUEST_CAMERA_CODE_ZGH);
        }
    }

    private void createWaterFileZgh() {
        savePath_zgh = PermissionUtil.sdCard + "/YNHJ/";
        File f = new File(savePath_zgh);
        if (!f.exists()) {
            f.mkdir();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择拍照
                case REQUEST_CAMERA_CODE_ZGH:
                    Bitmap bitmap_zgh = BitmapFactory.decodeFile(picture_zgh.getAbsolutePath());
                    bitmap_zgh = saveBitmapZgh(bitmap_zgh);
                    int degree_zgh = WaterImage.getBitmapDegree(picture_zgh.getAbsolutePath());
                    bitmap_zgh = WaterImage.rotateBitmapByDegree(bitmap_zgh, degree_zgh);
                    SimpleDateFormat sdf_zgh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String water_time_zgh = "" + sdf_zgh.format(new Date());

                    bitmap_zgh = WaterImage.drawTextToLeftBottom(this, bitmap_zgh, "拍摄时间：" + water_time_zgh, 8, Color.WHITE, (int) getResources().getDimension(R.dimen.qb_px_2), (int) getResources().getDimension(R.dimen.qb_px_10));
                    bitmap_zgh = WaterImage.drawTextToLeftBottom(this, bitmap_zgh, "拍摄地点：" + local, 8, Color.WHITE, (int) getResources().getDimension(R.dimen.qb_px_2), (int) getResources().getDimension(R.dimen.qb_px_7));
                    bitmap_zgh = WaterImage.drawTextToLeftBottom(this, bitmap_zgh, safeMan + SharedPrefsUtil.getValue(this, "userInfo", "USER_NAME", ""), 8, Color.WHITE, (int) getResources().getDimension(R.dimen.qb_px_2), (int) getResources().getDimension(R.dimen.qb_px_4));

                    try {
                        FileOutputStream baos_zgh = new FileOutputStream(picture_zgh);
                        // 把压缩后的数据存放到baos中  质量压缩并保存一下
                        bitmap_zgh.compress(Bitmap.CompressFormat.JPEG, 90, baos_zgh);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                    String path_zgh = picture_zgh.getAbsolutePath();
                    //保存带水印的图片并添加到集合中展示
                    list_zgh.add(path_zgh);
                    PermissionUtil.refreshAlbum(this, picture_zgh);
                    if (list_zgh.contains(myCode_zgh)) {
                        list_zgh.remove(myCode_zgh);
                    }
                    list_zgh.add(myCode_zgh);
                    imagePaths_zgh = list_zgh;
                    gridAdapter_zgh.DataNotify(imagePaths_zgh);

                    if (bitmap_zgh != null) {
                        bitmap_zgh.recycle();
                        bitmap_zgh = null;
                    }
                    System.gc();
                    break;

                // 预览
                case REQUEST_PREVIEW_CODE_ZGH:
                    if (data != null) {
                        ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                        Log.d(TAG, "ListExtra: " + "ListExtra = " + ListExtra.size());
                        loadAdpaterZgh(ListExtra);
                    }
                    break;
            }
        } else {
            if (data == null) {
                if (!imagePaths_zgh.contains(myCode_zgh)) {
                    imagePaths_zgh.add(myCode_zgh);
                }
                gridAdapter_zgh.notifyDataSetChanged();
            } else {
                ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                if (!ListExtra.contains(myCode_zgh)) {
                    ListExtra.add(myCode_zgh);
                }
                imagePaths_zgh = ListExtra;
                gridAdapter_zgh.DataNotify(imagePaths_zgh);
            }
        }
    }


    private void loadAdpaterZgh(List<String> paths) {
        if (imagePaths_zgh != null && imagePaths_zgh.size() > 0) {
            imagePaths_zgh.clear();
        }
        if (imagePaths_zgh.contains(myCode_zgh)) {
            imagePaths_zgh.remove(myCode_zgh);
        }
        imagePaths_zgh.addAll(paths);
        imagePaths_zgh.add(myCode_zgh);
        gridAdapter_zgh.DataNotify(imagePaths_zgh);
    }

    public Bitmap saveBitmapZgh(Bitmap mBitmap) {
        mBitmap = ImageCompressUtil.compressBitmapTest(mBitmap, picture_zgh);
        return mBitmap;
    }
}
