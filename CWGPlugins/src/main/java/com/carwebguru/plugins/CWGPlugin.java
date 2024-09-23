package com.carwebguru.plugins;

import static android.content.Context.RECEIVER_EXPORTED;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.carwebguru.plugins.data.CWGPluginCallback;
import com.carwebguru.plugins.data.CWGPluginCommand;
import com.carwebguru.plugins.data.CWGPluginExtra;
import com.carwebguru.plugins.data.CWGPluginValues;

import java.util.ArrayList;


public class CWGPlugin extends BroadcastReceiver {


    public ICWGPlugin events = null;

    public static final int VERSION = 3;
    public static final String ACTION = "CWG_PLUGINS_CLIENT";

    public static final String SERVER_PACKAGE = "com.softartstudio.carwebguru";

    private final int SEND_MODE_COMMAND = 0;

    private final int SEND_MODE_ANSWER = 1;

    private boolean debug = false;
    private final String LOG_TAG = "CWGPlugin";

    private Context ctx = null;
    private boolean receiverEnabled = false;
    private String pluginsPackage;

    private final ArrayList<CWGPluginCommand> listCommands;
    private int stepId = 0;


    /**
     * CWG Plugin constructor
     * @param ctx
     */
    public CWGPlugin(Context ctx) {
        this.ctx = ctx;
        if(ctx != null) {
            pluginsPackage = ctx.getPackageName();
        }

        listCommands = new ArrayList<CWGPluginCommand>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(events == null) {
            Log.w(LOG_TAG, "Empty plugin events!");
            return;
        }

        if(intent == null) {
            Log.w(LOG_TAG, "Empty onReceive intent");
            return;
        }

        CWGPluginCommand command = new CWGPluginCommand(0);
        command.loadFromIntent(intent);

        CWGPluginValues values = new CWGPluginValues();
        values.loadFromIntent(intent);

        CWGPluginCallback callback = new CWGPluginCallback(0);
        callback.loadFromIntent(intent);

        CWGPluginExtra extra = new CWGPluginExtra();
        extra.loadFromIntent(intent);

        boolean myself = false;
        if(ctx != null && !TextUtils.isEmpty(ctx.getPackageName())) {
            myself = ctx.getPackageName().equals(command.getSenderPackage());
        }

        if( (command.getCommand() == CWGPluginConst.Commands.GET_CWG_VERSION) && (listCommands.size() > 0) ) {
           for(CWGPluginCommand currCmd : listCommands) {
              if(currCmd.getStepId() == command.getStepId()) {
                  listCommands.remove(currCmd);
                  break;
              }
           }
        }

        events.onCommand(command, values, callback, extra, myself);
    }


    public void sendCommand(final String txt) {
        CWGPluginCommand pCmd = new CWGPluginCommand(CWGPluginConst.Commands.PARAMS);
        pCmd.setParams(txt);
        sendRaw(pCmd, null, null, null, SEND_MODE_COMMAND, null);
    }


    public void sendCommand(final CWGPluginCommand command, CWGPluginValues values, CWGPluginCallback callback, CWGPluginExtra extra) {
        sendRaw(command, values, callback, extra, SEND_MODE_COMMAND, null);
    }


    public void sendAnswer(final String toPackage, final CWGPluginCommand command, CWGPluginValues values, CWGPluginCallback callback, CWGPluginExtra extra) {
        sendRaw(command, values, callback, extra, SEND_MODE_ANSWER, toPackage);
    }


    private void sendRaw(final CWGPluginCommand command, CWGPluginValues values, CWGPluginCallback callback, CWGPluginExtra extra, int sendMode, String toPackage) {
        if(ctx == null) {
            Log.e(LOG_TAG, "context is null!");
            return;
        }

        if(command == null) {
            Log.e(LOG_TAG, "send command is null!");
            return;
        }

        if((sendMode == SEND_MODE_ANSWER) && TextUtils.isEmpty(toPackage)) {
            Log.e(LOG_TAG, "answer package is empty!");
            return;
        }

        if(debug) {
            Log.d(LOG_TAG, "sendCommand for package:"  + SERVER_PACKAGE + ", cmd: " + command.getCommand());
        }

        Intent intent = new Intent();
        intent.setAction(ACTION);
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);

        switch (sendMode) {

            case SEND_MODE_COMMAND:
                intent.setPackage(SERVER_PACKAGE);
                break;

            case SEND_MODE_ANSWER:
                intent.setPackage(toPackage);
                break;
        }

        command.setSenderPackage(ctx.getPackageName());
        command.setTimestamp(System.currentTimeMillis());
        command.saveToIntent(intent);


        if(values != null) {
            values.saveToIntent(intent);
        }

        if(callback != null) {
            callback.saveToIntent(intent);
        }

        if(extra != null) {
            extra.saveToIntent(intent);
        }

        ctx.sendBroadcast(intent);
    }



    public void startIfClosed(String appPackage, String appActivity) {
        CWGPluginCommand cmd = new CWGPluginCommand(CWGPluginConst.Commands.GET_CWG_VERSION);
        final int waitStepID = getStepId();
        cmd.setStepId(getStepId());
        incStepId();
        listCommands.add(cmd);
        sendAnswer(appPackage, cmd, null, null, null);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ctx == null) {
                    return;
                }
                boolean needStart = false;

                for(CWGPluginCommand currCmd : listCommands) {
                    if(currCmd.getStepId() == waitStepID) {
                        listCommands.remove(currCmd);
                        needStart = true;
                        break;
                    }
                }

                if(needStart) {
                    Intent launchIntent = null;
                    if (TextUtils.isEmpty(appActivity)) {
                        launchIntent = ctx.getPackageManager().getLaunchIntentForPackage(appPackage);
                    } else {
                        launchIntent = new Intent(Intent.ACTION_MAIN);
                        launchIntent.setComponent(new ComponentName(appPackage, appActivity));
                    }

                    if(launchIntent != null) {
                        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        ctx.startActivity(launchIntent);
                    }
                }
            }
        }, 500);
    }




    public void setEnabled(boolean enabled) {
        if(enabled == receiverEnabled) {
            return;
        }

        if(debug) {
            Log.d(LOG_TAG, "setEnabled: " + enabled);
        }

        if(ctx == null) {
            return;
        }

        if(enabled) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ctx.registerReceiver(this, new IntentFilter(ACTION), RECEIVER_EXPORTED);
            } else {
                ctx.registerReceiver(this, new IntentFilter(ACTION));
            }
            receiverEnabled = true;
        } else {
            ctx.unregisterReceiver(this);
            receiverEnabled = false;
        }
    }

    public void onDestroy() {
        setEnabled(false);
        ctx = null;
        events = null;
    }

    public String getVersionInfo() {
        return "CWGPlugins SDK V" + VERSION;
    }




    public String getPluginsPackage() {
        return pluginsPackage;
    }

    public void setPluginsPackage(String pluginsPackage) {
        this.pluginsPackage = pluginsPackage;
    }


    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public void incStepId() {
        stepId++;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
