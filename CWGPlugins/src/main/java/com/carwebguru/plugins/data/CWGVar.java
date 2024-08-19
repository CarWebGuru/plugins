package com.carwebguru.plugins.data;

import android.content.Intent;
import android.text.TextUtils;


public class CWGVar {

    public final int VT_EMPTY = 0;
    public final int VT_STRING = 1;
    public final int VT_BOOL = 2;
    public final int VT_INT = 3;
    public final int VT_LONG = 4;
    public final int VT_FLOAT = 5;



    private String key = "";

    private int varType = VT_EMPTY;

    private boolean exists;

    private String valueString = "";

    private int valueInt = 0;

    private float valueFloat = 0;

    private long valueLong = 0;

    private boolean valueBool;

    private boolean valueBoolDef;



    public CWGVar(String key, String valueString) {
        this.key = key;
        this.valueString = valueString;
        varType = VT_STRING;

    }

    public CWGVar(String key, boolean value, boolean defValue) {
        this.key = key;
        this.valueBool = value;
        this.valueBoolDef = defValue;
        varType = VT_BOOL;
    }


    public CWGVar(String key, int value) {
        this.key = key;
        this.valueInt = value;
        varType = VT_INT;
    }


    public CWGVar(String key, float value) {
        this.key = key;
        this.valueFloat = value;
        varType = VT_FLOAT;
    }


    public CWGVar(String key, long value) {
        this.key = key;
        this.valueLong = value;
        varType = VT_LONG;
    }

    public void reset() {
        exists = false;
        valueBool = valueBoolDef;
        valueFloat = 0;
        valueInt = 0;
        valueLong = 0;
        valueString = "";
    }


    public boolean isEmpty() {
        switch (varType) {

            case VT_STRING:
                return TextUtils.isEmpty(getValueString());

            case VT_BOOL:
                return isValueBool() == valueBoolDef;

            case VT_INT:
                return getValueInt() == 0;

            case VT_LONG:
                return getValueLong() == 0;

            case VT_FLOAT:
                return getValueFloat() == 0;

        }

        return true;
    }


    public void saveToIntent(Intent intent) {
      if(intent != null && !isEmpty()) {
          switch (varType) {

              case VT_STRING:
                  intent.putExtra(getKey(), getValueString());
                  break;

              case VT_BOOL:
                  intent.putExtra(getKey(), isValueBool());
                  break;

              case VT_INT:
                  intent.putExtra(getKey(), getValueInt());
                  break;

              case VT_LONG:
                  intent.putExtra(getKey(), getValueLong());
                  break;

              case VT_FLOAT:
                  intent.putExtra(getKey(), getValueFloat());
                  break;

          }
      }
    }

    public void loadFromIntent(Intent intent) {
       if(intent != null && intent.hasExtra(getKey())) {

           switch (varType) {

               case VT_STRING:
                   setValueString(intent.getStringExtra(getKey()));
                   break;

               case VT_BOOL:
                   setValueBool(intent.getBooleanExtra(getKey(), valueBoolDef));
                   break;

               case VT_INT:
                   setValueInt(intent.getIntExtra(getKey(), 0));
                   break;

               case VT_LONG:
                   setValueLong(intent.getLongExtra(getKey(), 0));
                   break;

               case VT_FLOAT:
                   setValueFloat(intent.getFloatExtra(getKey(), 0));
                   break;

           }

       }
    }




    public int getVarType() {
        return varType;
    }

    public boolean isExists() {
        return !isEmpty();
    }




    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public String getValueString() {
        return valueString;
    }

    public void setValueString(String valueString) {
        this.valueString = valueString;
    }

    public int getValueInt() {
        return valueInt;
    }

    public void setValueInt(int valueInt) {
        this.valueInt = valueInt;
    }

    public boolean isValueBool() {
        return valueBool;
    }

    public void setValueBool(boolean valueBool) {
        this.valueBool = valueBool;
    }

    public float getValueFloat() {
        return valueFloat;
    }

    public void setValueFloat(float valueFloat) {
        this.valueFloat = valueFloat;
    }

    public long getValueLong() {
        return valueLong;
    }

    public void setValueLong(long valueLong) {
        this.valueLong = valueLong;
    }

}