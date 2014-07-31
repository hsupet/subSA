package com.bnx.subsa;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AdminReceiver extends DeviceAdminReceiver {

    @Override
    public void onEnabled(Context context, Intent intent) {
        Log.e("ADM", intent.getAction());
        // DevicePolicyManager dpm = (DevicePolicyManager) context
        // .getSystemService(Context.DEVICE_POLICY_SERVICE);
        // dpm.lockNow();
        // android.os.Process.killProcess(android.os.Process.myPid());

    }

}
