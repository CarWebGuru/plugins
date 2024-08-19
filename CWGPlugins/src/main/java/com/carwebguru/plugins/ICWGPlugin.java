package com.carwebguru.plugins;


import com.carwebguru.plugins.data.CWGPluginCallback;
import com.carwebguru.plugins.data.CWGPluginCommand;
import com.carwebguru.plugins.data.CWGPluginExtra;
import com.carwebguru.plugins.data.CWGPluginValues;

public interface ICWGPlugin {

    void onCommand(CWGPluginCommand command, CWGPluginValues values, CWGPluginCallback callback, CWGPluginExtra extra, boolean myself);

}
