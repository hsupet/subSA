package com.bnx.subsa;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.bnx.subsa.common.Const;
import com.bnx.subsa.common.SSAUtils;

import java.util.Calendar;

public class BootupReceiver extends BroadcastReceiver {

    private Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        mContext = context;

        for (int i = 0; i < Const.mActions.length; i++) {
            SharedPreferences timeInfo = mContext.getSharedPreferences(Const.mActions[i], 0);

            String startTime = timeInfo.getString(Const.SP_KEY_START, Const.DEFAULT_START);

            String endTime = timeInfo.getString(Const.SP_KEY_END, Const.DEFAULT_END);

            String swName = timeInfo.getString(Const.SP_KEY_NAME,
                    mContext.getResources().getString(Const.showNames[i]));

            boolean isChecked = timeInfo.getBoolean(Const.SP_KEY_CHECK, false);
            if (isChecked) {
                enableAlarm(Const.mActions[i], startTime, endTime);
            }
        }
    }

    private void enableAlarm(String type, String startTime, String endTime) {
        final AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        int amID = 0;

        // on
        Calendar calendeStart = Calendar.getInstance();
        calendeStart.setTimeInMillis(System.currentTimeMillis());
        calendeStart.set(Calendar.HOUR_OF_DAY, SSAUtils.getHour(startTime));
        calendeStart.set(Calendar.MINUTE, SSAUtils.getMinute(startTime));
        calendeStart.set(Calendar.SECOND, 0);
        calendeStart.set(Calendar.MILLISECOND, 0);

        Intent intentStart = new Intent(mContext, AlarmReceiver.class);
        if (type.equals(Const.INTENT_AP)) {
            intentStart.setAction(Const.INTENT_ACT_AP_START);
            amID = 0;
        } else if (type.equals(Const.INTENT_BT)) {
            intentStart.setAction(Const.INTENT_ACT_BT_START);
            amID = 1;
        } else if (type.equals(Const.INTENT_SL)) {
            intentStart.setAction(Const.INTENT_ACT_SL_START);
            amID = 2;
        }
        // intentStart.putExtra(Const.INTENT_TYPE_KEY, mType);

        PendingIntent startPi = PendingIntent.getBroadcast(mContext,
                amID, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP,
                calendeStart.getTimeInMillis(), Const.ONE_DAY, startPi);

        // off
        Calendar calendeEnd = Calendar.getInstance();
        calendeEnd.setTimeInMillis(System.currentTimeMillis());
        calendeEnd.set(Calendar.HOUR_OF_DAY, SSAUtils.getHour(endTime));
        calendeEnd.set(Calendar.MINUTE, SSAUtils.getMinute(endTime));
        calendeEnd.set(Calendar.SECOND, 0);
        calendeEnd.set(Calendar.MILLISECOND, 0);

        Intent intentEnd = new Intent(mContext,
                AlarmReceiver.class);
        if (type.equals(Const.INTENT_AP)) {
            intentEnd.setAction(Const.INTENT_ACT_AP_END);
            amID = 3;
        } else if (type.equals(Const.INTENT_BT)) {
            intentEnd.setAction(Const.INTENT_ACT_BT_END);
            amID = 4;
        } else if (type.equals(Const.INTENT_SL)) {
            intentEnd.setAction(Const.INTENT_ACT_SL_END);
            amID = 5;
        }
        // intentEnd.putExtra(Const.INTENT_TYPE_KEY, mType);

        PendingIntent endPi = PendingIntent.getBroadcast(mContext,
                amID, intentEnd, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP,
                calendeEnd.getTimeInMillis(), Const.ONE_DAY, endPi);
    }
}
