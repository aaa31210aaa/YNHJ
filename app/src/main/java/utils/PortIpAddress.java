package utils;

import android.content.Context;

public class PortIpAddress {
    public static String CKRY_CODE = "1"; //采矿调度员
    public static String KCSJ_CODE = "2"; //卡车司机
    public static String XKDRY_CODE = "3";//卸矿点人员
    public static String BZZ = "4"; //班组长
    public static String KDDDY = "5";//矿堆调度员
    public static String SUCCESS_CODE = "0000";
    public static String ERR_CODE = "0001";
    public static String CODE = "code";
    public static String MESSAGE = "message";
    public static String JsonArrName = "data";
    public static String TOKEN_KEY = "access_token";
    public static String USERID_KEY = "userid";


    public static String getUserId(Context context) {
        return SharedPrefsUtil.getValue(context, "userInfo", "userid", "");
    }

    public static String getUserType(Context context) {
        return SharedPrefsUtil.getValue(context, "userInfo", "usertype", "");
    }

    public static String getUserTypeName(Context context) {
        return SharedPrefsUtil.getValue(context, "userInfo", "usertypename", "");
    }

    public static String getUserName(Context context) {
        return SharedPrefsUtil.getValue(context, "userInfo", "USER_NAME", "");
    }

    public static String host = "http://192.168.5.228:8090/DMMES_YGMG/dimine/";

    //登录
    public static String Login() {
        return "http://192.168.5.228:8090/DMMES_YGMG/mobile/login.action";
    }

    //年计划采矿量供矿量
    public static String Njh() {
        return host + "appdata/yearappdata.action";
    }


}
