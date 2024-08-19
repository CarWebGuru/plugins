package com.carwebguru.plugins.data;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.carwebguru.plugins.CWGPluginConst;


public class CWGPluginExtra extends CWGPluginAbstractData {


    private final CWGVar author;
    private final CWGVar url;
    private final CWGVar contacts;
    private final CWGVar pluginVersion;

    private final CWGVar needStart;

    private final CWGVar apiHash;
    private final CWGVar apiKey;

    private final CWGVar supportedPercent;
    private final CWGVar supportedColorText;
    private final CWGVar supportedColorBackground;

    private Bundle extra;


    public CWGPluginExtra() {
        author = createVar(CWGPluginConst.Keys.PLUGIN_AUTHOR, "");
        url = createVar(CWGPluginConst.Keys.PLUGIN_URL, "");
        contacts = createVar(CWGPluginConst.Keys.PLUGIN_CONTACT, "");
        pluginVersion = createVar(CWGPluginConst.Keys.PLUGIN_VERSION, (int) 0);
        needStart = createVar(CWGPluginConst.Keys.NEED_START, false, false);

        apiHash = createVar(CWGPluginConst.Keys.API_HASH, "");
        apiKey = createVar(CWGPluginConst.Keys.API_KEY, "");

        supportedPercent = createVar(CWGPluginConst.Keys.SUPPORTED_PERCENT, false, false);
        supportedColorText = createVar(CWGPluginConst.Keys.SUPPORTED_COLOR_TEXT, false, false);
        supportedColorBackground = createVar(CWGPluginConst.Keys.SUPPORTED_COLOR_BACKGROUND, false, false);
    }



    @Override
    public void saveToIntent(Intent intent) {
        super.saveToIntent(intent);

        if (extra == null) {
            return;
        }

       Bundle extraValues = new Bundle();

        for (String key : extra.keySet()) {
            Object value = extra.get(key);

            if (value != null) {

                if (value instanceof Integer) {
                    extraValues.putInt(key, extra.getInt(key));
                }

                if (value instanceof String) {
                    extraValues.putString(key, extra.getString(key));
                }

                if (value instanceof Float) {
                    extraValues.putFloat(key, extra.getFloat(key));
                }

                if (value instanceof Boolean) {
                    extraValues.putBoolean(key, extra.getBoolean(key));
                }

                if (value instanceof Bitmap) {
                    extraValues.putBundle(key, extra.getBundle(key));
                }

                if (value instanceof Long) {
                    extraValues.putLong(key, extra.getLong(key));
                }

            }
        }

        if(!extraValues.isEmpty()) {
            intent.putExtra(CWGPluginConst.Keys.EXTRA, extraValues);
        }
    }


    @Override
    public void loadFromIntent(Intent intent) {
        super.loadFromIntent(intent);

        if(intent.hasExtra(CWGPluginConst.Keys.EXTRA)) {
            extra = intent.getBundleExtra(CWGPluginConst.Keys.EXTRA);
        }
    }


    @Override
    public void reset() {
        super.reset();
        if(extra != null) {
            extra.clear();
        }
    }


    public String getAuthor() {
        return author.getValueString();
    }

    public void setAuthor(String value) {
        author.setValueString(value);
    }



    public String getUrl() {
        return url.getValueString();
    }

    public void setUrl(String value) {
        url.setValueString(value);
    }



    public String getContacts() {
        return contacts.getValueString();
    }

    public void setContacts(String value) {
        contacts.setValueString(value);
    }



    public int getPluginVersion() {
        return pluginVersion.getValueInt();
    }

    public void setPluginVersion(int value) {
        pluginVersion.setValueInt(value);
    }


    public boolean getNeedStart() {
        return needStart.isValueBool();
    }

    public void setNeedStart(boolean value) {
        pluginVersion.setValueBool(value);
    }


    public String getApiHash() {
        return apiHash.getValueString();
    }

    public void setApiHash(String value) {
        apiHash.setValueString(value);
    }


    public String getApiKey() {
        return apiKey.getValueString();
    }

    public void setApiKey(String value) {
        apiKey.setValueString(value);
    }




    public boolean isSupportedPercent() {
        return supportedPercent.isValueBool();
    }

    public boolean isSupportedColorText() {
        return supportedColorText.isValueBool();
    }

    public boolean isSupportedColorBackground() {
        return supportedColorBackground.isValueBool();
    }


    public void setSupportedPercent(boolean value) {
        supportedPercent.setValueBool(value);
    }

    public void setSupportedColorText(boolean value) {
        supportedColorText.setValueBool(value);
    }

    public void setSupportedColorBackground(boolean value) {
        supportedColorBackground.setValueBool(value);
    }


    public Bundle getExtra() {
        return extra;
    }

    public void setExtra(Bundle extra) {
        this.extra = extra;
    }
}
