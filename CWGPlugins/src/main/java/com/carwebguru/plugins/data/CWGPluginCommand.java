package com.carwebguru.plugins.data;


import com.carwebguru.plugins.CWGPluginConst;

public class CWGPluginCommand extends CWGPluginAbstractData {

    private final CWGVar command;

    private final CWGVar action;

    private final CWGVar params;

    private final CWGVar logic;

    private final CWGVar viewStyle;

    private final CWGVar module;

    private final CWGVar itemUid;

    private final CWGVar ownerUid;

    private final CWGVar senderPackage;

    private final CWGVar stepId;

    private final CWGVar timestamp;

    private final CWGVar debug;




    public CWGPluginCommand(int value) {
        command = createVar(CWGPluginConst.Keys.COMMAND, value);
        action = createVar(CWGPluginConst.Keys.ACTION, 0);
        params = createVar(CWGPluginConst.Keys.PARAMS, "");
        logic = createVar(CWGPluginConst.Keys.LOGIC, 0);
        module = createVar(CWGPluginConst.Keys.MODULE, 0);
        viewStyle = createVar(CWGPluginConst.Keys.VIEW_STYLE, 0);

        itemUid = createVar(CWGPluginConst.Keys.ITEM_UID, "");
        ownerUid = createVar(CWGPluginConst.Keys.OWNER_UID, "");

        senderPackage = createVar(CWGPluginConst.Keys.SENDER_PACKAGE, "");
        stepId = createVar(CWGPluginConst.Keys.STEP_ID, (int) 0);
        timestamp = createVar(CWGPluginConst.Keys.TIMESTAMP, (long) 0L);
        debug = createVar(CWGPluginConst.Keys.DEBUG, false, false);
    }


    public int getCommand() {
        return command.getValueInt();
    }

    public void setCommand(int value) {
        command.setValueInt(value);
    }


    public String getItemUid() {
        return itemUid.getValueString();
    }

    public void setItemUid(String value) {
        itemUid.setValueString(value);
    }


    public String getParams() {
        return params.getValueString();
    }

    public void setParams(String value) {
        params.setValueString(value);
    }


    public String getOwnerUid() {
        return ownerUid.getValueString();
    }

    public void setOwnerUid(String value) {
        ownerUid.setValueString(value);
    }


    public String getSenderPackage() {
        return senderPackage.getValueString();
    }

    public void setSenderPackage(String value) {
        senderPackage.setValueString(value);
    }


    public int getStepId() {
        return stepId.getValueInt();
    }

    public void setStepId(int value) {
        stepId.setValueInt(value);
    }


    public long getTimestamp() {
        return timestamp.getValueLong();
    }

    public void setTimestamp(long value) {
        timestamp.setValueLong(value);
    }


    public boolean getDebug() {
        return debug.isValueBool();
    }

    public void setDebug(boolean value) {
        debug.setValueBool(value);
    }


    public int getAction() {
        return action.getValueInt();
    }

    public void setAction(int value) {
        action.setValueInt(value);
    }


    public int getModule() {
        return module.getValueInt();
    }

    public void setModule(int value) {
        module.setValueInt(value);
    }


    public int getLogic() {
        return logic.getValueInt();
    }

    public void setLogic(int value) {
        logic.setValueInt(value);
    }


    public int getViewStyle() {
        return viewStyle.getValueInt();
    }

    public void setViewStyle(int value) {
        this.viewStyle.setValueInt(value);
    }
}
