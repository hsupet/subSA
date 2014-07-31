package com.bnx.subsa;

import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

public class PSWidget extends AppWidgetProvider {

    private static final String TAG = "PSWidget";

    private static final String CLICK_NAME_ACTION = "com.bnx.subsa.action.widget.click";
    public static boolean isChange = true;
    private static RemoteViews rv;

    @Override
    public void onEnabled(Context context) {
        Log.w(TAG, "onEnabled : ");
        componentName = new ComponentName(context, AdminReceiver.class);
        dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        boolean active = dpm.isAdminActive(componentName);
        if (!active) { // 若无权限
            Log.e(TAG, "没有权限~");
            // activeManage(); // 去获得权限
            // dpm.lockNow(); // 并锁屏
            
        }
    }

    private ComponentName componentName;
    private DevicePolicyManager dpm;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.e(TAG, "onReceive : " + intent.toString());
        if (intent.getAction().equals(CLICK_NAME_ACTION)) {
            componentName = new ComponentName(context, AdminReceiver.class);
            dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            if (dpm.isAdminActive(componentName)) { // 若无权限
                Log.e(TAG, "已经有权限");
                dpm.lockNow(); // 直接锁屏
            } else {
                Log.e(TAG, "没有权限~");
                // activeManage(); // 去获得权限
                // dpm.lockNow(); // 并锁屏
                Intent it = new Intent("com.bnx.subsa.TranshipActivity");
                // Intent it = new
                // Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                // it.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                // componentName);
                // it.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                // "mwy");
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            }
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e(TAG, "onUpdate: ");

        for (int i : appWidgetIds) {
            Log.e(TAG, "onUpdate : " + i);
        }

        rv = new RemoteViews(context.getPackageName(), R.layout.power_bt);
        Intent intentClick = new Intent(CLICK_NAME_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                intentClick, 0);
        rv.setOnClickPendingIntent(R.id.pbt_img, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, rv);
    }

    public void powersave(View v) {
        Log.e(TAG, "powersave()");
    }
}
