package com.bnx.subsa.common;

import com.bnx.subsa.R;

public class Const {

    public static final String TIME_FORMAT = "HH:mm";
    public static final long ONE_HOUR = 3600000l;
    public static final long ONE_DAY = 24 * 60 * 60 * 1000l;

    // public static final String SP_BT_NAME = "bluetooth_time";
    // public static final String SP_AP_NAME = "airplane_time";
    // public static final String SP_SL_NAME = "silent_time";
    public static final String DEFAULT_START = "21:00";
    public static final String DEFAULT_END = "8:00";

    public static final String SP_KEY_START = "start_time";
    public static final String SP_KEY_END = "end_time";
    public static final String SP_KEY_NAME = "sw_name";
    public static final String SP_KEY_CHECK = "check_bit";

    public static final String INTENT_AP = "airplane_mode";
    public static final String INTENT_BT = "bluetooth_on";
    public static final String INTENT_SL = "silence";
    public static final String INTENT_ACT_AP_START = "start.ap";
    public static final String INTENT_ACT_AP_END = "end.ap";
    public static final String INTENT_ACT_BT_START = "start.bt";
    public static final String INTENT_ACT_BT_END = "end.bt";
    public static final String INTENT_ACT_SL_START = "start.sl";
    public static final String INTENT_ACT_SL_END = "end.sl";
    public static final String INTENT_TYPE_KEY = "type.key";

    public static final int INTENT_FLAG_AP = 1;
    public static final int INTENT_FLAG_BT = 2;
    public static final int INTENT_FLAG_SL = 4;

    public static final String KEY_RING_MODE = "Ringer.Mode";

    public static final char COLON = ':';
    public static String[] mActions = {
            Const.INTENT_AP, Const.INTENT_BT, Const.INTENT_SL
    };
    // len must same as mActions
    public static int[] showNames = {
            R.string.airplane_mode, R.string.bluetooth, R.string.silent_mode
    };
}
