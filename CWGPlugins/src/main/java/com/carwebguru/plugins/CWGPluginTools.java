package com.carwebguru.plugins;

import android.text.TextUtils;

public class CWGPluginTools {

    public static String buildParams(String uid, String title, float percent, int iconCode, int colorText, int colorBackground) {
        StringBuilder sb = new StringBuilder();
        sb.append(oneParam("uid", uid, !TextUtils.isEmpty(uid)));
        sb.append(oneParam("tit", title, !TextUtils.isEmpty(title)));
        sb.append(oneParam("per", Float.toString(percent), (percent >= 0) && (percent <= 100)));
        sb.append(oneParam("ic", String.valueOf(iconCode), iconCode > 0));
        sb.append(oneParam("ct", String.valueOf(colorText), colorText > 0));
        sb.append(oneParam("cb", String.valueOf(colorBackground), colorBackground > 0));
        return sb.toString();
    }


    public static String oneParam(String pName, String pValue, boolean valid) {
        return valid ? (pName + "=" + pValue + ";") : "";
    }

    public static String buildLines(String... lines) {
        StringBuilder sb = new StringBuilder();
        for (String curr : lines) {
            if(!TextUtils.isEmpty(curr)) {
                sb.append(curr + "::");
            }
        }
        return sb.toString();
    }

}
