package com.carwebguru.plugins;

import android.text.TextUtils;

import java.util.ArrayList;

public class MultiUpdateHelper {

    private ArrayList<String> items;
    private StringBuilder sBuffer;

    public MultiUpdateHelper() {
        items = new ArrayList<String>();
        sBuffer = new StringBuilder();
    }

    // act, title, num, pid, icn-code

    public void add(int action, String title) {
        bufferClear();
        bufferAdd(CWGPluginConst.Keys.ACTION, String.valueOf(action));
        bufferAdd(CWGPluginConst.Keys.TITLE, title);
        addFromBuffer();
    }

    public void add(int action, String title, int iconCode) {
        bufferClear();
        bufferAdd(CWGPluginConst.Keys.ACTION, String.valueOf(action));
        bufferAdd(CWGPluginConst.Keys.TITLE, title);
        bufferAdd(CWGPluginConst.Keys.ICON_CODE, String.valueOf(iconCode));
        addFromBuffer();
    }

    public void add(int action, String title, int iconCode, int numValue) {
        bufferClear();
        bufferAdd(CWGPluginConst.Keys.ACTION, String.valueOf(action));
        bufferAdd(CWGPluginConst.Keys.TITLE, title);
        bufferAdd(CWGPluginConst.Keys.ICON_CODE, String.valueOf(iconCode));
        bufferAdd(CWGPluginConst.Keys.NUMBER, String.valueOf(numValue));
        addFromBuffer();
    }

    public void add(int action, String title, int iconCode, int numValue, String pid) {
        bufferClear();
        bufferAdd(CWGPluginConst.Keys.ACTION, String.valueOf(action));
        bufferAdd(CWGPluginConst.Keys.TITLE, title);
        bufferAdd(CWGPluginConst.Keys.ICON_CODE, String.valueOf(iconCode));
        bufferAdd(CWGPluginConst.Keys.NUMBER, String.valueOf(numValue));
        bufferAdd(CWGPluginConst.Keys.PID, pid);
        addFromBuffer();
    }

    public void add(int action, String title, int numValue, String pid) {
        bufferClear();
        bufferAdd(CWGPluginConst.Keys.ACTION, String.valueOf(action));
        bufferAdd(CWGPluginConst.Keys.TITLE, title);
        bufferAdd(CWGPluginConst.Keys.NUMBER, String.valueOf(numValue));
        bufferAdd(CWGPluginConst.Keys.PID, pid);
        addFromBuffer();
    }


    public void addRaw(String values) {
        if(!TextUtils.isEmpty(values)) {
            items.add(values);
        }
    }

    public void addFromBuffer() {
        if(sBuffer.length() > 0) {
            items.add(sBuffer.toString());
        }
    }


    public String filterValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return "";
        }

        if (value.contains("\n"))
            value = value.replace("\n", " ");

        if (value.contains(";"))
            value = value.replace(";", ",");

        if (value.contains("::"))
            value = value.replace("::", " ");

        return value;
    }

    public void bufferClear() {
        sBuffer.setLength(0);
    }

    public void bufferAdd(String pName, String value) {
        if (TextUtils.isEmpty(value)) {
            return;
        }

        value = filterValue(value);

        if (sBuffer.length() > 0) {
            sBuffer.append(";");
        }

        sBuffer.append(pName);
        sBuffer.append("=");
        sBuffer.append(value);
    }


    public String bufferToString() {
        return sBuffer.toString();
    }


    public String asUpdateLine() {
        StringBuilder sb = new StringBuilder();

        for(String s : items) {
            if(sb.length() > 0) {
                sb.append("::");
            }
            sb.append(s);
        }

        return sb.toString();
    }

    public void clear() {
        items.clear();
        bufferClear();
    }
}
