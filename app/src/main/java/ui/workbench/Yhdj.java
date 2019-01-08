package ui.workbench;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
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

public class Yhdj extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.title_name_right)
    TextView title_name_right;
    @BindView(R.id.yhdj_yhmc)
    EditText yhdj_yhmc;
    @BindView(R.id.yhdj_yhlx)
    EditText yhdj_yhlx;
    @BindView(R.id.yhdj_pcrq)
    TextView yhdj_pcrq;
    @BindView(R.id.yhdj_zglx)
    EditText yhdj_zglx;
    @BindView(R.id.yhdj_zgqx)
    EditText yhdj_zgqx;
    @BindView(R.id.yhdj_yhdd)
    EditText yhdj_yhdd;
    @BindView(R.id.yhdj_zgzrr)
    EditText yhdj_zgzrr;
    @BindView(R.id.yhdj_zrrfgld)
    EditText yhdj_zrrfgld;
    @BindView(R.id.yhdj_jdr)
    EditText yhdj_jdr;
    @BindView(R.id.yhdj_yhms)
    EditText yhdj_yhms;
    @BindView(R.id.yhdj_yhbgzy)
    EditText yhdj_yhbgzy;
    @BindView(R.id.yhdj_lrsj)
    TextView yhdj_lrsj;
    @BindView(R.id.yhdj_lrr)
    EditText yhdj_lrr;
    @BindView(R.id.yhdj_yhlevel)
    EditText yhdj_yhlevel;
    @BindView(R.id.yhdj_xasj)
    TextView yhdj_xasj;
    @BindView(R.id.yhdj_xaqkzs)
    EditText yhdj_xaqkzs;
    @BindView(R.id.yhdj_xabz)
    EditText yhdj_xabz;
    @BindView(R.id.yhdj_xgr)
    EditText yhdj_xgr;
    @BindView(R.id.yhdj_xgsj)
    TextView yhdj_xgsj;
    @BindView(R.id.yhdj_img)
    MyGridView yhdj_img;
    private TimePickerView pcrqTime;
    private TimePickerView lrsjTime;
    private TimePickerView xasjTime;
    private TimePickerView xgsjTime;
    private String tag = "";
    //当前位置的信息
    private String local;
    //默认字符
    private static final String myCode = "000000";
    //选择的图片的集合
    private ArrayList<String> imagePaths;

    //返回码
    private static final int REQUEST_CAMERA_CODE = 10;  //相册
    private static final int REQUEST_PREVIEW_CODE = 20; //预览
    private CommonlyGridViewAdapter gridAdapter;
    private Uri cameraFileUri;
    private String savePath;
    private File picture;
    private String safeMan = "";
    //加水印后的图片地址
    private String waterPath;
    private File waterf;
    private ArrayList<String> list;

    //定位需要的声明
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String imgs;

    private int clickPosition;
    //版本比较：是否是4.4及以上版本
    private final boolean mIsKitKat = Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT;


    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_yhdj;
    }

    @Override
    protected void initView() {
        initLocal();
        CameraUtil.init(this);
    }

    @Override
    protected void initData() {
        tag = getIntent().getStringExtra("tag");
        title_name.setText(R.string.yhdj);
        title_name_right.setText(R.string.submit);
        yhdj_pcrq.setText(DateUtils.getStringDateShort());
        initPcrq(yhdj_pcrq);
        yhdj_lrsj.setText(DateUtils.getStringDateShort());
        initLrsj(yhdj_lrsj);
        yhdj_xasj.setText(DateUtils.getStringDateShort());
        initXasj(yhdj_xasj);
        yhdj_xgsj.setText(DateUtils.getStringDateShort());
        initXgsj(yhdj_xgsj);
        imagePaths = new ArrayList<String>();
        list = new ArrayList<>();
        initGrid();

    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    /**
     * 排查日期
     */
    @OnClick(R.id.yhdj_pcrq)
    void Pcrq() {
        if (pcrqTime != null)
            pcrqTime.show();
    }

    /**
     * 录入时间
     */
    @OnClick(R.id.yhdj_lrsj)
    void Lrsj() {
        if (lrsjTime != null)
            lrsjTime.show();
    }

    /**
     * 销案时间
     */
    @OnClick(R.id.yhdj_xasj)
    void Xasj() {
        if (xasjTime != null)
            xasjTime.show();
    }

    /**
     * 修改时间
     */
    @OnClick(R.id.yhdj_xgsj)
    void Xgsj() {
        if (xgsjTime != null)
            xgsjTime.show();
    }


    private void initPcrq(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        pcrqTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                                pcrqTime.returnData();
                                pcrqTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pcrqTime.dismiss();
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

    private void initLrsj(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        lrsjTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                                lrsjTime.returnData();
                                lrsjTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                lrsjTime.dismiss();
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

    private void initXasj(final TextView textView) {
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(START_YEAR, START_MONTH, START_DAY);
        Calendar endDate = Calendar.getInstance();
        endDate.set(END_YEAR, END_MONTH, END_DAY);
        //时间选择器 ，自定义布局
        xasjTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
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
                                xasjTime.returnData();
                                xasjTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                xasjTime.dismiss();
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


    private class MyAMapLocationListener implements AMapLocationListener {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            //省信息+城市信息+城区信息+街道信息
            local = aMapLocation.getProvince() + aMapLocation.getCity() + aMapLocation.getDistrict() + aMapLocation.getStreet();
        }
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

    private void initGrid() {
        //设置gridview一行多少个
        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        yhdj_img.setNumColumns(cols);
        AddImage();
        imagePaths.add(myCode);
        gridAdapter = new CommonlyGridViewAdapter(this, imagePaths);
        yhdj_img.setAdapter(gridAdapter);
    }

    private void AddImage() {
        //添加多张图片
        yhdj_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                imgs = (String) parent.getItemAtPosition(position);
                clickPosition = position;
                checkCameraPermissions();
            }
        });
    }

    private void checkCameraPermissions() {
        /**
         * 检测读写权限
         */
        String[] perms = {CameraPermission, LocationPermission};
        if (EasyPermissions.hasPermissions(this, perms)) {//有权限
            ClickAdd(clickPosition);
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.permission_camera),
                    CAMERA_REQUESTCODE, perms);
        }
    }


    private void ClickAdd(int position) {
        if (PermissionUtil.hasSdcard()) {
            if (myCode.equals(imgs)) {
                if (yhdj_img.getCount() >= 7) {
                    ShowToast.showShort(this, "最多添加6张图片");
                } else {
                    OpenCamera();
                }
            } else {
                PhotoPreviewIntent intent = new PhotoPreviewIntent(this);
                imagePaths.remove(imagePaths.size() - 1);
                intent.setCurrentItem(position);
                intent.setPhotoPaths(imagePaths);
                startActivityForResult(intent, REQUEST_PREVIEW_CODE);
            }
        } else {
            ShowToast.showShort(this, "没有SD卡");
        }
    }

    /**
     * 打开相机
     */
    private void OpenCamera() {
        if (mIsKitKat) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                createWaterFile();
                picture = new File(savePath, System.currentTimeMillis() + ".jpg");
                cameraFileUri = FileProvider.getUriForFile(this, "com.project.dimine.ynhj.fileprovider", picture);
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileUri);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE);
            } else {//判断是否有相机应用
                ShowToast.showShort(this, "无相机应用");
            }
        } else {
            createWaterFile();
            picture = new File(savePath, System.currentTimeMillis() + ".jpg");
            cameraFileUri = Uri.fromFile(picture);
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileUri);
            startActivityForResult(intent, REQUEST_CAMERA_CODE);
        }
    }


    /**
     * 创建加水印后的图片文件夹
     */
    private void createWaterFile() {
        savePath = PermissionUtil.sdCard + "/YNHJ/";
        File f = new File(savePath);
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
                case REQUEST_CAMERA_CODE:
                    Bitmap bitmap = BitmapFactory.decodeFile(picture.getAbsolutePath());
                    bitmap = saveBitmap(bitmap);

                    Log.e(TAG, "宽：" + bitmap.getWidth() + "----" + "高：" + bitmap.getHeight() + "大小：" + bitmap.getByteCount());

                    //获得旋转角度
                    int degree = WaterImage.getBitmapDegree(picture.getAbsolutePath());
                    //矫正照片被旋转
                    bitmap = WaterImage.rotateBitmapByDegree(bitmap, degree);
                    //添加水印
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String water_time = "" + sdf.format(new Date());

                    bitmap = WaterImage.drawTextToLeftBottom(this, bitmap, "拍摄时间：" + water_time, 8, Color.WHITE, (int) getResources().getDimension(R.dimen.qb_px_2), (int) getResources().getDimension(R.dimen.qb_px_10));
                    bitmap = WaterImage.drawTextToLeftBottom(this, bitmap, "拍摄地点：" + local, 8, Color.WHITE, (int) getResources().getDimension(R.dimen.qb_px_2), (int) getResources().getDimension(R.dimen.qb_px_7));
                    bitmap = WaterImage.drawTextToLeftBottom(this, bitmap, safeMan + SharedPrefsUtil.getValue(this, "userInfo", "USER_NAME", ""), 8, Color.WHITE, (int) getResources().getDimension(R.dimen.qb_px_2), (int) getResources().getDimension(R.dimen.qb_px_4));

                    try {
                        FileOutputStream baos = new FileOutputStream(picture);
                        // 把压缩后的数据存放到baos中  质量压缩并保存一下
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    String path = picture.getAbsolutePath();
                    //保存带水印的图片并添加到集合中展示
                    list.add(path);
                    PermissionUtil.refreshAlbum(this, picture);
                    if (list.contains(myCode)) {
                        list.remove(myCode);
                    }
                    list.add(myCode);
                    imagePaths = list;
                    gridAdapter.DataNotify(imagePaths);

                    if (bitmap != null) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                    System.gc();
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    if (data != null) {
                        ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                        Log.d(TAG, "ListExtra: " + "ListExtra = " + ListExtra.size());
                        loadAdpater(ListExtra);
                    }
                    break;
            }
        } else {
            if (data == null) {
                if (!imagePaths.contains(myCode)) {
                    imagePaths.add(myCode);
                }
                gridAdapter.notifyDataSetChanged();
            } else {
                ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
                if (!ListExtra.contains(myCode)) {
                    ListExtra.add(myCode);
                }
                imagePaths = ListExtra;
                gridAdapter.DataNotify(imagePaths);
            }
        }
    }

    //加载图片
    private void loadAdpater(List<String> paths) {
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.clear();
        }
        if (imagePaths.contains(myCode)) {
            imagePaths.remove(myCode);
        }
        imagePaths.addAll(paths);
        imagePaths.add(myCode);
        gridAdapter.DataNotify(imagePaths);
    }

    /**
     * 保存照片
     */
    public Bitmap saveBitmap(Bitmap mBitmap) {
        mBitmap = ImageCompressUtil.compressBitmapTest(mBitmap, picture);
        return mBitmap;
    }
}
