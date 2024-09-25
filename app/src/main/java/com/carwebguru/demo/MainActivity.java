package com.carwebguru.demo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.carwebguru.plugins.CWGPlugin;
import com.carwebguru.plugins.CWGPluginConst;
import com.carwebguru.plugins.ICWGPlugin;
import com.carwebguru.plugins.MultiUpdateHelper;
import com.carwebguru.plugins.data.CWGPluginCallback;
import com.carwebguru.plugins.data.CWGPluginCommand;
import com.carwebguru.plugins.data.CWGPluginExtra;
import com.carwebguru.plugins.data.CWGPluginValues;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private CWGPlugin cwgPlugin;

    private Button btnUpdateP;
    private Button btnUpdateW;

    private int updateStep;
    private boolean updateBusy;

    private int currPlasma = 0;
    private int currWarp = 0;

    private final String UUID_GROUP = "000123-123123123-123123123";
    private final int WIDGET_ID_ACTION_PLASMA = 700000;
    private final int WIDGET_ID_ACTION_WARP = 700001;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initButtons();
        initPlugin();
    }

    private void initButtons() {
        Button btn;

        btn = findViewById(R.id.btnInstall);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                installWidgets();
            }
        });

        btn = findViewById(R.id.btnUninstall);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uninstallWidgets();
            }
        });

        btnUpdateP = findViewById(R.id.btnUpdatePlasma);
        btnUpdateP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSinglePlasma();
            }
        });

        btnUpdateW = findViewById(R.id.btnUpdateWarp);
        btnUpdateW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSingleWarp();
            }
        });


        btn = findViewById(R.id.btnUpdateMulti);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMultiMode();
            }
        });
    }




    private void updateMultiMode() {

        if(updateBusy) {
            return;
        }

        currPlasma = 0;
        currWarp = 0;

        updateStep = 1;
        updateBusy = true;
        new CountDownTimer(30000, 200) {

            public void onTick(long millisUntilFinished) {
                // seconds remaining: " + millisUntilFinished / 1000);

                if(currPlasma > 100) currPlasma = 0;
                if(currWarp > 10) currWarp = 0;

                CWGPluginCommand pCmd = new CWGPluginCommand(CWGPluginConst.Commands.UPDATE_PLUGINS_VALUE);
                MultiUpdateHelper muh = new MultiUpdateHelper();

                muh.add(WIDGET_ID_ACTION_PLASMA,currPlasma + " pl", currPlasma, "");
                muh.add(WIDGET_ID_ACTION_WARP,String.valueOf(currWarp), currWarp, "");

                CWGPluginValues pValue = new CWGPluginValues();
                pValue.setTitle(muh.asUpdateLine());
                cwgPlugin.sendCommand(pCmd, pValue, null, null);

                btnUpdateP.setText("Plasma: " + currPlasma);
                btnUpdateW.setText("Warp: " + currWarp);

                currPlasma++;
                currWarp++;

                toLog("step: " + updateStep);
                updateStep++;
            }


            public void onFinish() {
                updateBusy = false;
                toLog("done");
            }

        }.start();

    }

    private void updateSingleWarp() {
        CWGPluginCommand pCmd = new CWGPluginCommand(CWGPluginConst.Commands.UPDATE_PLUGIN_VALUE);
        pCmd.setAction(WIDGET_ID_ACTION_WARP);

        int newValue = getRandomNumber(0, 10);
        CWGPluginValues pValue = new CWGPluginValues();
        pValue.setTitle(String.valueOf(newValue));
        pValue.setNumber(newValue);

        cwgPlugin.sendCommand(pCmd, pValue, null, null);
        btnUpdateW.setText("Warp speed: " + pValue.getTitle());
    }

    private void updateSinglePlasma() {
        CWGPluginCommand pCmd = new CWGPluginCommand(CWGPluginConst.Commands.UPDATE_PLUGIN_VALUE);
        pCmd.setAction(WIDGET_ID_ACTION_PLASMA);

        int newPlasma = getRandomNumber(0, 100);
        CWGPluginValues pValue = new CWGPluginValues();
        pValue.setTitle(newPlasma + " pl");
        pValue.setNumber(newPlasma);

        cwgPlugin.sendCommand(pCmd, pValue, null, null);
        btnUpdateP.setText("Plasma level: " + pValue.getTitle());
    }

    private void initPlugin() {
        cwgPlugin = new CWGPlugin(getApplicationContext());

        TextView lbl = findViewById(R.id.lblVersion);
        lbl.setText(cwgPlugin.getVersionInfo());

        cwgPlugin.events = new ICWGPlugin() {
            @Override
            public void onCommand(CWGPluginCommand command, CWGPluginValues values, CWGPluginCallback callback, CWGPluginExtra extra, boolean myself) {

                toLog(String.format("onCommand: %d, t: %s, d: %s", command.getCommand(), values.getTitle(), values.getDescription()));

                if( (extra != null) || (extra.getExtra() != null)) {
                    Bundle bundle = extra.getExtra();
                    if(bundle != null) {
                        for (String key : bundle.keySet()) {
                            toLog(" > [extra] " + key + " => " + bundle.get(key) + ";");
                        }
                    }
                }

            }
        };
    }



    private void installWidgets() {
        // Install Widgets Group
        CWGPluginCommand pCmd = new CWGPluginCommand(CWGPluginConst.Commands.SETUP_PLUGIN_GROUP);
        pCmd.setItemUid(UUID_GROUP);
        CWGPluginValues pValue = new CWGPluginValues();
        pValue.setTitle("DEMO Widgets");
        pValue.setDescription("CarWebGuru DEMO widgets");
        cwgPlugin.sendCommand(pCmd, pValue, null, null);

        // Install 2 widgets
        installWidgetPlasma();
        installWidgetWarpSpeed();

    }

    private void installWidgetWarpSpeed() {
        // install widget: "Warp Speed"
        CWGPluginCommand pCmd = new CWGPluginCommand(CWGPluginConst.Commands.SETUP_PLUGIN);
        pCmd.setAction(WIDGET_ID_ACTION_WARP);
        pCmd.setOwnerUid(UUID_GROUP);
        pCmd.setItemUid("DEMO-W-SPEED");

        CWGPluginValues pValue = new CWGPluginValues();
        pValue.setTitle("Warp Speed");
        pValue.setTitleShort("Warp");
        pValue.setDescription("Virtual warp space speed");
        pValue.setUnits("");
        pValue.setValueType(CWGPluginConst.ValueType.INTEGER);
        pValue.setBadType(CWGPluginConst.BadType.BAD_BEGIN);
        pValue.setNumberMin(0);
        pValue.setNumberMax(10);

        pValue.icon.setLibraryCode(57550); // DEC library code from font
        pValue.view.setViewType(0);
        pValue.view.setBigText(true);

        CWGPluginExtra extra = new CWGPluginExtra();
        extra.setSupportedPercent(true);
        extra.setSupportedColorText(false);
        extra.setSupportedColorBackground(false);
        extra.setAuthor("Developer Name");
        extra.setPluginVersion(0);
        extra.setUrl("www.example-sample.com");
        extra.setContacts("");
        extra.setApiKey("QWERTYUIOP");


        cwgPlugin.sendCommand(pCmd, pValue, null, extra);
    }


    private void installWidgetPlasma() {
        // install widget: "Plasma Level"
        CWGPluginCommand pCmd = new CWGPluginCommand(CWGPluginConst.Commands.SETUP_PLUGIN);
        pCmd.setAction(WIDGET_ID_ACTION_PLASMA);
        pCmd.setOwnerUid(UUID_GROUP);
        pCmd.setItemUid("DEMO-PLASMA-A");

        CWGPluginValues pValue = new CWGPluginValues();
        pValue.setTitle("Plasma Level");
        pValue.setTitleShort("Plasma");
        pValue.setDescription("Engine plasma virtual level");
        pValue.setUnits("Pl");
        pValue.setValueType(CWGPluginConst.ValueType.INTEGER);
        pValue.setBadType(CWGPluginConst.BadType.BAD_BEGIN);
        pValue.setNumberMin(0);
        pValue.setNumberMax(100);

        pValue.icon.setLibraryCode(57439); // DEC library code from font
        pValue.view.setViewType(0);
        pValue.view.setBigText(true);

        CWGPluginExtra extra = new CWGPluginExtra();
        extra.setSupportedPercent(true);
        extra.setSupportedColorText(false);
        extra.setSupportedColorBackground(false);
        extra.setAuthor("Denis V");
        extra.setPluginVersion(0);
        extra.setUrl("www.example-sample.com");
        extra.setContacts("");
        extra.setApiKey("QWERTYUIOP");

        cwgPlugin.sendCommand(pCmd, pValue, null, extra);

    }


    private void uninstallWidgets() {
        CWGPluginCommand pCmd = new CWGPluginCommand(CWGPluginConst.Commands.DELETE_PLUGIN_GROUP);
        pCmd.setItemUid(UUID_GROUP);
        cwgPlugin.sendCommand(pCmd, null, null, null);


        pCmd = new CWGPluginCommand(CWGPluginConst.Commands.DELETE_PLUGIN);

        pCmd.setAction(WIDGET_ID_ACTION_PLASMA);
        cwgPlugin.sendCommand(pCmd, null, null, null);

        pCmd.setAction(WIDGET_ID_ACTION_WARP);
        cwgPlugin.sendCommand(pCmd, null, null, null);
    }



    @Override
    protected void onStart() {
        super.onStart();
        cwgPlugin.setEnabled(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        cwgPlugin.setEnabled(true);
    }

    @Override
    protected void onDestroy() {
        cwgPlugin.onDestroy();
        super.onDestroy();
    }



    private int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }


    private void toLog(String txt) {
        Log.d(this.getClass().getSimpleName(), txt);
    }
}