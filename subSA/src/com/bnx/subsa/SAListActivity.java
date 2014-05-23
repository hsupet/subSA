package com.bnx.subsa;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bnx.subsa.common.Const;
import com.bnx.subsa.common.SAAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class SAListActivity extends Activity implements OnItemClickListener {

    private static final String TAG = "SAListActivity";
    private static final boolean DBG = BuildConfig.DEBUG;

    private ListView mLVSa;
    private SAAdapter mAdapter;

    ArrayList<HashMap<String, Object>> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sa_list);

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
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stu
        Intent it = new Intent(this, MainActivity.class);
        it.setAction(Const.mActions[arg2]);
        startActivity(it);
    }
}
