package com.carwebguru.plugins.data;

import com.carwebguru.plugins.CWGPluginConst;

public class CWGPluginCallback extends CWGPluginAbstractData {


    private final CWGVar callbackType;

    private final CWGVar appPackage;

    private final CWGVar appActivity;

    private final CWGVar messageCaption;

    private final CWGVar messageText;

    private final CWGVar callbackAction;



    public CWGPluginCallback(int callbackType) {
        this.callbackType = createVar(CWGPluginConst.Keys.CALLBACK_TYPE, callbackType);
        callbackAction = createVar(CWGPluginConst.Keys.CALLBACK_ACTION, "");

        appPackage = createVar(CWGPluginConst.Keys.APP_PACKAGE, "");
        appActivity = createVar(CWGPluginConst.Keys.APP_ACTIVITY, "");

        messageCaption = createVar(CWGPluginConst.Keys.MESSAGE_CAPTION, "");
        messageText = createVar(CWGPluginConst.Keys.MESSAGE_TEXT, "");
    }


   public int getCallbackType() {
        return callbackType.getValueInt();
   }

   public void setCallbackType(int value) {
        callbackType.setValueInt(value);
   }


    public int getCallbackAction() {
        return callbackAction.getValueInt();
    }

    public void setCallbackAction(int value) {
        callbackAction.setValueInt(value);
    }


    public String getAppPackage() {
        return appPackage.getValueString();
    }

    public void setAppPackage(String appPackage) {
        this.appPackage.setValueString(appPackage);
    }



    public String getAppActivity() {
        return appActivity.getValueString();
    }

    public void setAppActivity(String appActivity) {
        this.appActivity.setValueString(appActivity);
    }



    public String getMessageCaption() {
        return messageCaption.getValueString();
    }

    public void setMessageCaption(String messageCaption) {
        this.messageCaption.setValueString(messageCaption);
    }



    public String getMessageText() {
        return messageText.getValueString();
    }

    public void setMessageText(String messageText) {
        this.messageText.setValueString(messageText);
    }

}
