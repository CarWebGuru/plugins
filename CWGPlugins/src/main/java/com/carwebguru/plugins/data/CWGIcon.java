package com.carwebguru.plugins.data;

import android.content.Intent;
import android.graphics.Bitmap;

import com.carwebguru.plugins.CWGPluginConst;


public class CWGIcon {

    private final CWGVar iconType;
    private final CWGVar group;
    private final CWGVar libraryCode;
    private final CWGVar value;

    private Bitmap bitmap = null;



    public CWGIcon() {
        iconType = new CWGVar(CWGPluginConst.Keys.ICON_TYPE, CWGPluginConst.TYPE_EMPTY);
        libraryCode = new CWGVar(CWGPluginConst.Keys.ICON_CODE, CWGPluginConst.TYPE_EMPTY);
        value = new CWGVar(CWGPluginConst.Keys.ICON_VALUE, "");
        group = new CWGVar(CWGPluginConst.Keys.ICON_GROUP, "");
    }


    public void loadFromIntent(Intent intent) {
        iconType.loadFromIntent(intent);
        value.loadFromIntent(intent);
        group.loadFromIntent(intent);

        switch (getIconType()) {

            case CWGPluginConst.IconType.ICON_CODE:
                libraryCode.loadFromIntent(intent);
                break;

            case CWGPluginConst.IconType.ICON_BITMAP:
                if(intent.hasExtra(CWGPluginConst.Keys.ICON_BITMAP)) {
                    setIconBitmap(intent.getParcelableExtra(CWGPluginConst.Keys.ICON_BITMAP));
                }
                break;
        }
    }

    public void saveToIntent(Intent intent) {
        iconType.saveToIntent(intent);
        value.saveToIntent(intent);
        group.saveToIntent(intent);

        switch (getIconType()) {

            case CWGPluginConst.IconType.ICON_CODE:
                libraryCode.saveToIntent(intent);
                break;

            case CWGPluginConst.IconType.ICON_BITMAP:
                intent.putExtra(CWGPluginConst.Keys.ICON_BITMAP, getBitmap());
                break;
        }
    }


    public void reset() {
        if(bitmap != null) {
            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
            bitmap = null;
        }

        iconType.setValueInt(CWGPluginConst.TYPE_EMPTY);
        libraryCode.setValueInt(0);
        value.setValueString("");
        group.setValueString("");
    }

    public boolean isEmptyIcon() {
      return getIconType() == CWGPluginConst.TYPE_EMPTY;
    }

    public boolean isLibraryIcon() {
        return getIconType() == CWGPluginConst.IconType.ICON_CODE;
    }

    public boolean isBitmapIcon() {
        return getIconType() == CWGPluginConst.IconType.ICON_BITMAP;
    }


    public int getIconType() {
        return iconType.getValueInt();
    }

    public int getLibraryCode() {
        return libraryCode.getValueInt();
    }

    public void setLibraryCode(int iconLibraryCode) {
        this.libraryCode.setValueInt(iconLibraryCode);
        iconType.setValueInt(CWGPluginConst.IconType.ICON_CODE);
    }

    public String getValue() {
        return value.getValueString();
    }

    public void setValue(String iconValue) {
        this.value.setValueString(iconValue);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setIconBitmap(Bitmap bmp) {
        this.bitmap = bmp;
        iconType.setValueInt(CWGPluginConst.IconType.ICON_BITMAP);
    }

    public boolean isIconBitmapExists() {
       return bitmap != null;
    }

    public String getGroup() {
        return group.getValueString();
    }

    public void setGroup(String group) {
        this.group.setValueString(group);
    }

}