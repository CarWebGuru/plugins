package com.carwebguru.plugins.data;

import android.content.Intent;


import java.util.ArrayList;

public abstract class CWGPluginAbstractData {

    public ArrayList<CWGVar> items = new ArrayList<CWGVar>();



    public CWGVar createVar(String key, String value) {
        CWGVar var = new CWGVar(key, value);
        registerVar(var);
        return var;
    }

    public CWGVar createVar(String key, int value) {
        CWGVar var = new CWGVar(key, value);
        registerVar(var);
        return var;
    }

    public CWGVar createVar(String key, float value) {
        CWGVar var = new CWGVar(key, value);
        registerVar(var);
        return var;
    }

    public CWGVar createVar(String key, long value) {
        CWGVar var = new CWGVar(key, value);
        registerVar(var);
        return var;
    }

    public CWGVar createVar(String key, boolean value, boolean defValue) {
        CWGVar var = new CWGVar(key, value, defValue);
        registerVar(var);
        return var;
    }



    public void registerVar(CWGVar var) {
       if(var != null) {
           items.add(var);
       }
    }


    public void reset() {
        for (CWGVar var : items) {
            var.reset();
        }
    }

    public void saveToIntent(Intent intent) {
        for (CWGVar var : items) {
            var.saveToIntent(intent);
        }
    }

    public void loadFromIntent(Intent intent) {
        reset();
        for (CWGVar var : items) {
            var.loadFromIntent(intent);
        }
    }

}