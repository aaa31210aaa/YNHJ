package utils;

import android.content.Context;

public class PortIpAddress {
    public static String CKRY_CODE = "1"; //采矿调度员
    public static String KCSJ_CODE = "2"; //卡车司机
    public static String XKDRY_CODE = "3";//卸矿点人员
    public static String BZZ="4"; //班组长
    public static String KDDDY = "5";//矿堆调度员


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
}
