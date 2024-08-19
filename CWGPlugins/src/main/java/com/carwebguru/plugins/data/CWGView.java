package com.carwebguru.plugins.data;

import android.content.Intent;

import com.carwebguru.plugins.CWGPluginConst;


public class CWGView {

    private final CWGVar viewType;

    private final CWGVar bigText;

    private final CWGVar colorText;

    private final CWGVar colorBackground;


    public CWGView() {
        viewType = new CWGVar(CWGPluginConst.Keys.VIEW_TYPE, (int) 0);
        bigText = new CWGVar(CWGPluginConst.Keys.BIG_TEXT, false, false);
        colorText = new CWGVar(CWGPluginConst.Keys.COLOR_TEXT, (int) 0);
        colorBackground = new CWGVar(CWGPluginConst.Keys.COLOR_BACKGROUND, (int) 0);
    }

    public void loadFromIntent(Intent intent) {
        viewType.loadFromIntent(intent);
        bigText.loadFromIntent(intent);
        colorText.loadFromIntent(intent);
        colorBackground.loadFromIntent(intent);
    }

    public void saveToIntent(Intent intent) {
        viewType.saveToIntent(intent);
        bigText.saveToIntent(intent);
        colorText.saveToIntent(intent);
        colorBackground.saveToIntent(intent);
    }

    public void reset() {
        viewType.setValueInt(0);
        bigText.setValueBool(false);
        colorText.setValueInt(0);
        colorBackground.setValueInt(0);
    }



    public int getViewType() {
        return viewType.getValueInt();
    }

    public void setViewType(int value) {
        this.viewType.setValueInt(value);
    }



    public boolean isBigText() {
        return bigText.isValueBool();
    }

    public void setBigText(boolean value) {
        this.bigText.setValueBool(value);
    }



    public int getColorText() {
        return colorText.getValueInt();
    }

    public void setColorText(int value) {
        this.colorText.setValueInt(value);
    }



    public int getColorBackground() {
        return colorBackground.getValueInt();
    }

    public void setColorBackground(int value) {
        this.colorBackground.setValueInt(value);
    }
}
