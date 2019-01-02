package utils;

import android.app.Notification;
import android.content.Context;

import com.project.dimine.ynhj.R;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class JpushNotifictionUtil {
    /**
     * 自定义通知栏
     *
     * @param context
     * @param number     自定义样式编号
     * @param layoutId   布局Id
     * @param iconTipId  指定最顶层状态栏小图标
     * @param iconShowId 指定下拉状态栏时显示的通知图标
     */
    public static void customPushNotification(Context context, int number,
                                              int layoutId, int iconTipId, int iconShowId) {

        // 指定定制的 Notification Layout
        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(
                context, layoutId, R.id.custom_push_notification_icon,
                R.id.custom_push_notification_title,
                R.id.custom_push_notification_text);

        // 指定最顶层状态栏小图标
        builder.statusBarDrawable = iconTipId;

        // 指定下拉状态栏时显示的通知图标
        builder.layoutIconDrawable = iconShowId;

        JPushInterface.setPushNotificationBuilder(number, builder);
    }


    public static void customPushNotification2(Context context) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
        builder.statusBarDrawable = R.drawable.jpush_notification_icon;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_SHOW_LIGHTS;  //设置为自动消失和呼吸灯闪烁
        builder.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE
                | Notification.DEFAULT_LIGHTS;  // 设置为铃声、震动、呼吸灯闪烁都要
        JPushInterface.setPushNotificationBuilder(2, builder);
    }
}
