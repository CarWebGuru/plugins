package com.carwebguru.plugins.data;

import android.content.Intent;

import com.carwebguru.plugins.CWGPluginConst;


public class CWGPluginValues extends CWGPluginAbstractData {

    private final CWGVar title;

    private final CWGVar titleShort;

    private final CWGVar description;

    private final CWGVar units;

    private final CWGVar percent;

    private final CWGVar number;
    private final CWGVar valueType;
    private final CWGVar badType;

    private final CWGVar numberMin;
    private final CWGVar numberMax;

    private final CWGVar enable;

    public CWGIcon icon;

    public CWGView view;



    public CWGPluginValues() {
        title = createVar(CWGPluginConst.Keys.TITLE, "");
        titleShort = createVar(CWGPluginConst.Keys.TITLE_SHORT, "");
        description = createVar(CWGPluginConst.Keys.DESCR, "");
        units = createVar(CWGPluginConst.Keys.UNITS, "");
        percent = createVar(CWGPluginConst.Keys.PERCENT, 0f);
        enable = createVar(CWGPluginConst.Keys.ENABLE, true, true);

        number = createVar(CWGPluginConst.Keys.NUMBER, 0);
        valueType = createVar(CWGPluginConst.Keys.VALUE_TYPE, 0);
        badType = createVar(CWGPluginConst.Keys.BAD_TYPE, 0);

        numberMin = createVar(CWGPluginConst.Keys.NUMBER_MIN, 0);
        numberMax = createVar(CWGPluginConst.Keys.NUMBER_MAX, 0);

        icon = new CWGIcon();
        view = new CWGView();
    }


    @Override
    public void saveToIntent(Intent intent) {
        super.saveToIntent(intent);
        icon.saveToIntent(intent);
        view.saveToIntent(intent);
    }


    @Override
    public void loadFromIntent(Intent intent) {
        super.loadFromIntent(intent);
        icon.loadFromIntent(intent);
        view.loadFromIntent(intent);
    }

    @Override
    public void reset() {
        super.reset();
        icon.reset();
        view.reset();
    }



    public boolean isIconBitmapExists() {
        return icon.isIconBitmapExists();
    }

    public int getIconType() {
        return icon.getIconType();
    }



    public String getTitle() {
        return title.getValueString();
    }

    public void setTitle(String value) {
        this.title.setValueString(value);
    }

    public String getTitleShort() {
        return titleShort.getValueString();
    }

    public void setTitleShort(String value) {
        this.titleShort.setValueString(value);
    }


    public String getDescription() {
        return description.getValueString();
    }

    public void setDescription(String value) {
        description.setValueString(value);
    }


    public String getUnits() {
        return units.getValueString();
    }

    public void setUnits(String value) {
        this.units.setValueString(value);
    }


    public float getPercent() {
        return percent.getValueFloat();
    }

    public void setPercent(float value) {
        this.percent.setValueFloat(value);
    }

    public int getBadType() {
        return badType.getValueInt();
    }

    public void setBadType(int value) {
        this.badType.setValueInt(value);
    }

    public int getValueType() {
        return valueType.getValueInt();
    }

    public void setValueType(int value) {
        this.valueType.setValueInt(value);
    }

    public int getNumber() {
        return number.getValueInt();
    }

    public void setNumber(int value) {
        this.number.setValueInt(value);
    }


    public int getNumberMin() {
        return numberMin.getValueInt();
    }

    public void setNumberMin(int value) {
        this.numberMin.setValueInt(value);
    }


    public int getNumberMax() {
        return numberMax.getValueInt();
    }

    public void setNumberMax(int value) {
        this.numberMax.setValueInt(value);
    }


    public boolean isEnable() {
        return enable.isValueBool();
    }

    public void setEnable(boolean value) {
        this.enable.setValueBool(value);
    }

}