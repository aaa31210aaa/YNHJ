package com.project.dimine.ynhj;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpParams;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import utils.JpushNotifictionUtil;

public class MyApplication extends Application {
    public final static long CONNECT_TIMEOUT = 30000;
    public final static long READ_TIMEOUT = 30000;
    public final static long WRITE_TIMEOUT = 30000;
    public static SQLiteDatabase sqldb;
    private static Context context;
    public static int CUSTOM_PUSH_NOTIFI_STYLE = 1;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5bee5f6e");
        initOkGo();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(context);
        JpushNotifictionUtil.customPushNotification(this, CUSTOM_PUSH_NOTIFI_STYLE, R.layout.custom_push_notification, R.drawable.jpush_notification_icon, R.drawable.jpush_notification_icon);

    }

    //获取应用上下文环境
    public static Context getContext() {
        return context;
    }

    private void initOkGo() {
        HttpParams params = new HttpParams();//param支持中文,直接传,不要自己编码

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //log相关
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setColorLevel(Level.INFO);                               //log颜色级别，决定了log在控制台显示的颜色
        builder.addInterceptor(loggingInterceptor);                                 //添加OkGo默认debug日志

        //超时时间设置，默认60秒
        builder.readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS);      //全局的读取超时时间
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS);     //全局的写入超时时间
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);   //全局的连接超时时间

        //自动管理cookie（或者叫session的保持），以下几种任选其一就行
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));            //使用sp保持cookie，如果cookie不过期，则一直有效
//        builder.cookieJar(new CookieJarImpl(new DBCookieStore(this)));              //使用数据库保持cookie，如果cookie不过期，则一直有效
        //builder.cookieJar(new CookieJarImpl(new MemoryCookieStore()));            //使用内存保持cookie，app退出后，cookie消失

        //https相关设置，以下几种方案根据需要自己设置
        //方法一：信任所有证书,不安全有风险
//        HttpsUtils.SSLParams sslParams1 = HttpsUtils.getSslSocketFactory();
        //方法二：自定义信任规则，校验服务端证书
//        HttpsUtils.SSLParams sslParams2 = HttpsUtils.getSslSocketFactory(new SafeTrustManager());
        //方法三：使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams3 = HttpsUtils.getSslSocketFactory(getAssets().open("srca.cer"));
        //方法四：使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
        //HttpsUtils.SSLParams sslParams4 = HttpsUtils.getSslSocketFactory(getAssets().open("xxx.bks"), "123456", getAssets().open("yyy.cer"));
//        builder.sslSocketFactory(sslParams1.sSLSocketFactory, sslParams1.trustManager);
        //配置https的域名匹配规则，详细看demo的初始化介绍，不需要就不要加入，使用不当会导致https握手失败
//        builder.hostnameVerifier(new SafeHostnameVerifier());


        // 其他统一的配置
        // 详细说明看GitHub文档：https://github.com/jeasonlzy/
        OkGo
                .getInstance()
                .init(this)                           //必须调用初始化
                .setOkHttpClient(builder.build())               //必须设置OkHttpClient
//                .setCacheMode(CacheMode.NO_CACHE)     //全局统一缓存模式，默认不使用缓存，可以不传
//                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                              //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);                       //全局公共参数

    }
}
