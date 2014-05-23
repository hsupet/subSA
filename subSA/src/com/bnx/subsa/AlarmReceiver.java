package com.bnx.subsa;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import com.bnx.subsa.common.Const;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = "AlarmReceiver";
    private static final boolean DBG = BuildConfig.DEBUG;

    private Context mContext;
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        // TODO Auto-generated method stub
        mContext = arg0;
        String action = arg1.getAction();
        Bundle b = arg1.getExtras();
        String type = b.getString(Const.INTENT_TYPE_KEY);
        if (DBG)
            Log.e(TAG, "intent action: " + action);
        if (action.equals(Const.INTENT_ACT_BT_START)) {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
                    .getDefaultAdapter();
            if (!mBluetoothAdapter.isEnabled()) {
                if (DBG)
                    Log.e(TAG, "BT NOT enable " + type);
                mBluetoothAdapter.enable();
            }
            if (DBG)
                Log.e(TAG, "BT on " + type);
        } else if (action.equals(Const.INTENT_ACT_BT_END)) {
            BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
                    .getDefaultAdapter();
            if (mBluetoothAdapter.isEnabled()) {
                if (DBG)
                    Log.e(TAG, "BT enable " + type);
                mBluetoothAdapter.disable();
            }
            if (DBG)
                Log.e(TAG, "BT off " + type);
        } else if (action.equals(Const.INTENT_ACT_AP_END)) {
            setAirplaneMode(false);
        } else if (action.equals(Const.INTENT_ACT_AP_START)) {
            setAirplaneMode(true);

        } else if (action.equals(Const.INTENT_ACT_SL_END)) {
            AudioManager am = (AudioManager) arg0.getSystemService(arg0.AUDIO_SERVICE);
            SharedPreferences sp = mContext.getSharedPreferences(Const.INTENT_SL, 0);
            int ringMode = sp.getInt(Const.KEY_RING_MODE, AudioManager.RINGER_MODE_NORMAL);
            if (DBG)
                Log.e(TAG, "sl off mode = " + ringMode);
            am.setRingerMode(ringMode);
        } else if (action.equals(Const.INTENT_ACT_SL_START)) {
            AudioManager am = (AudioManager) arg0.getSystemService(arg0.AUDIO_SERVICE);
            SharedPreferences sp = mContext.getSharedPreferences(Const.INTENT_SL, 0);
            Editor ed = sp.edit();
            ed.putInt(Const.KEY_RING_MODE, am.getRingerMode());
            ed.commit();
            am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
        }

    }

    private void setAirplaneMode(boolean setAirPlane) {

        // if (android.os.Build.VERSION.SDK_INT >= 18) {
        // Settings.Global.putInt(mContext.getContentResolver(),
        // Settings.Global.AIRPLANE_MODE_ON,
        // setAirPlane ? 1 : 0);
        // } else {
        // Settings.System.putInt(mContext.getContentResolver(),
        // Settings.System.AIRPLANE_MODE_ON,
        // setAirPlane ? 1 : 0);
        // Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        //
        // intent.putExtra("state", setAirPlane);
        // mContext.sendBroadcast(intent);
        // }

        if (DBG)
            Log.e(TAG, "sendBroadcast");
    }
}
