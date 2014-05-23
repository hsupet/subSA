package com.bnx.subsa.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.bnx.subsa.AlarmReceiver;
import com.bnx.subsa.BuildConfig;
import com.bnx.subsa.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class SAAdapter extends BaseAdapter {

    private static final String TAG = "SAAdapter";
    private static final boolean DBG = BuildConfig.DEBUG;
    private ArrayList<HashMap<String, Object>> data;
    private Context context;

    public SAAdapter(Context c, ArrayList<HashMap<String, Object>> d) {
        context = c;
        data = d;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return data.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    private class ViewHolder {
        // TextView tvName;
        TextView tvStart;
        TextView tvEnd;
        CheckBox cbSW;
    }

    private void enableAlarm(String type, String startTime, String endTime) {
        final AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int amID = 0;

        // on
        Calendar calendeStart = Calendar.getInstance();
        calendeStart.setTimeInMillis(System.currentTimeMillis());
        calendeStart.set(Calendar.HOUR_OF_DAY, SSAUtils.getHour(startTime));
        calendeStart.set(Calendar.MINUTE, SSAUtils.getMinute(startTime));
        calendeStart.set(Calendar.SECOND, 0);
        calendeStart.set(Calendar.MILLISECOND, 0);

        Intent intentStart = new Intent(context, AlarmReceiver.class);
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

        PendingIntent startPi = PendingIntent.getBroadcast(context,
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

        Intent intentEnd = new Intent(context,
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

        PendingIntent endPi = PendingIntent.getBroadcast(context,
                amID, intentEnd, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP,
                calendeEnd.getTimeInMillis(), Const.ONE_DAY, endPi);
    }

    private void cancelAlarm(String type) {
        final AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int amID = 0;

        Intent intentStart = new Intent(context, AlarmReceiver.class);
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

        PendingIntent startPi = PendingIntent.getBroadcast(context,
                amID, intentStart, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(startPi);

        Intent intentEnd = new Intent(context,
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

        PendingIntent endPi = PendingIntent.getBroadcast(context,
                amID, intentEnd, PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(endPi);
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (arg1 == null) {
            arg1 = LayoutInflater.from(context).inflate(R.layout.sa_item, null);

            holder = new ViewHolder();
            holder.cbSW = (CheckBox) arg1.findViewById(R.id.cb_name);
            holder.tvStart = (TextView) arg1.findViewById(R.id.tv_start);
            holder.tvEnd = (TextView) arg1.findViewById(R.id.tv_end);

            holder.cbSW.setOnCheckedChangeListener(new OnCheckedChangeListener(){

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // TODO Auto-generated method stub
                    AlarmManager AM = (AlarmManager) context
                            .getSystemService(Context.ALARM_SERVICE);
                    String type = (String) buttonView.getTag();
                    if (DBG)
                        Log.e(TAG, buttonView.toString() + "checkbox change to " + isChecked);
                    SharedPreferences timeInfo = context.getSharedPreferences(type, 0);
                    timeInfo.edit().putBoolean(Const.SP_KEY_CHECK, isChecked).commit();
                    String startTime = timeInfo.getString(Const.SP_KEY_START, Const.DEFAULT_START);
                    String endTime = timeInfo.getString(Const.SP_KEY_END, Const.DEFAULT_START);
                    int hourOfDay = SSAUtils.getHour(startTime);
                    // Integer.valueOf(startTime.substring(0,
                    // startTime.indexOf(Const.COLON)));
                    int minute = SSAUtils.getMinute(endTime);
                    // Integer.valueOf(startTime.substring(startTime.indexOf(Const.COLON)
                    // +
                    // 1));

                    if (isChecked) {
                        enableAlarm(type, startTime, endTime);
                    } else {
                        cancelAlarm(type);
                    }
                }
                
            });
            arg1.setTag(holder);

        } else {
            holder = (ViewHolder) arg1.getTag();
        }
        holder.cbSW.setTag(Const.mActions[arg0]);
        holder.cbSW.setChecked((Boolean) data.get(arg0).get(Const.SP_KEY_CHECK));
        holder.cbSW.setText((CharSequence) data.get(arg0).get(Const.SP_KEY_NAME));
        holder.tvStart.setText((CharSequence) data.get(arg0).get(Const.SP_KEY_START));
        holder.tvEnd.setText((CharSequence) data.get(arg0).get(Const.SP_KEY_END));
        return arg1;
    }

}
