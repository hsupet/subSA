package com.bnx.subsa.common;

public class SSAUtils {

    public static int getHour(String time) {
        return Integer.valueOf(time.substring(0, time.indexOf(Const.COLON)));
    }

    public static int getMinute(String time) {
        return Integer.valueOf(time.substring(time.indexOf(Const.COLON) + 1));
    }

    public static String getTime(int hour, int min) {
        return String.valueOf(hour) + Const.COLON
                + (min < 10 ? ("0" + min) : min);
    }
}
