package com.bnx.subsa;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.bnx.subsa.common.Const;
import com.bnx.subsa.common.SAAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class SAListActivity extends Activity implements OnItemClickListener, OnClickListener {

    private static final String TAG = "SAListActivity";
    private static final boolean DBG = BuildConfig.DEBUG;

    private ListView mLVSa;
    private Button mBTSa;
    private SAAdapter mAdapter;

    ArrayList<HashMap<String, Object>> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sa_list);

        Intent intent = getIntent();
        if (intent.getAction().equals("com.bnx.subsa.TranshipActivity")) {
            ComponentName componentName = new ComponentName(this, AdminReceiver.class);
            Intent it = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            it.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
            it.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "mwy");
            startActivityForResult(it, RESULT_CODE);
            // startActivity(it);
            // finish();
        }
        // mData = getData();
        // mAdapter = new SAAdapter(this, mData);
        initView();
    }

    protected void onResume() {
        super.onResume();

        getData();
        if (DBG)
            Log.e(TAG, "data: " + mData.toString());
        if (mAdapter == null) {
            mAdapter = new SAAdapter(this, mData);
            mLVSa.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private ArrayList<HashMap<String, Object>> getData() {
        // TODO Auto-generated method stub
        if (mData == null) {
            mData = new ArrayList<HashMap<String, Object>>();
        } else {
            mData.clear();
        }

        for (int i = 0; i < Const.mActions.length; i++) {
            HashMap<String, Object> tempHashMap = new HashMap<String, Object>();

            SharedPreferences timeInfo = getSharedPreferences(Const.mActions[i], 0);

            String startTime = timeInfo.getString(Const.SP_KEY_START, Const.DEFAULT_START);
            timeInfo.edit().putString(Const.SP_KEY_START, startTime).commit();
            tempHashMap.put(Const.SP_KEY_START, startTime);

            String endTime = timeInfo.getString(Const.SP_KEY_END, Const.DEFAULT_END);
            timeInfo.edit().putString(Const.SP_KEY_END, endTime).commit();
            tempHashMap.put(Const.SP_KEY_END, endTime);

            String swName = timeInfo.getString(Const.SP_KEY_NAME,
                    getResources().getString(Const.showNames[i]));
            timeInfo.edit().putString(Const.SP_KEY_NAME, swName).commit();
            tempHashMap.put(Const.SP_KEY_NAME, swName);

            boolean isChecked = timeInfo.getBoolean(Const.SP_KEY_CHECK, false);
            tempHashMap.put(Const.SP_KEY_CHECK, isChecked);

            mData.add(tempHashMap);
        }
        return mData;
    }

    private void initView() {
        mLVSa = (ListView) findViewById(R.id.sa_list);
        mLVSa.setOnItemClickListener(this);

        mBTSa = (Button) findViewById(R.id.sa_sleep);
        mBTSa.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stu
        Intent it = new Intent(this, MainActivity.class);
        it.setAction(Const.mActions[arg2]);
        startActivity(it);
    }

    private final static int RESULT_CODE = 9991;

    private void activeManage() {
        Log.e(TAG, "activeManage");
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "mwy");
        startActivityForResult(intent, 0);
    }

    private ComponentName componentName;
    private DevicePolicyManager dpm;
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        componentName = new ComponentName(this, AdminReceiver.class);
        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        boolean active = dpm.isAdminActive(componentName);
        if (!active) { // 若无权限
            Log.e(TAG, "没有权限~");
            activeManage(); // 去获得权限
            // dpm.lockNow(); // 并锁屏
        } else {
            Log.e(TAG, "已经有权限");
            dpm.lockNow(); // 直接锁屏
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "request = " + requestCode);
        if (requestCode == RESULT_CODE) {
            finish();
        }
    }
}
