package com.bnx.subsa;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

public class TranshipActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ComponentName componentName = new ComponentName(this, AdminReceiver.class);
        Intent it = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        it.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        it.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "mwy");
        startActivity(it);
        finish();
    }
}
