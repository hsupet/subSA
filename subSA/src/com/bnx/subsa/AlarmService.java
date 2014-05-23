package com.bnx.subsa;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service {

    private static final String TAG = "AlarmService";
    private static final boolean DBG = BuildConfig.DEBUG;
    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        String action = arg0.getAction();
        if (DBG)
            Log.e(TAG, "intent action: " + action);
        return null;
    }


}
