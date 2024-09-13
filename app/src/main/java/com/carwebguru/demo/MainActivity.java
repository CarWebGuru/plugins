package com.carwebguru.demo;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.carwebguru.plugins.CWGPlugin;
import com.carwebguru.plugins.CWGPluginConst;
import com.carwebguru.plugins.MultiUpdateHelper;

public class MainActivity extends AppCompatActivity {

    private CWGPlugin cwgPlugin;
    private final String LOG_TAG = "MAIN-UI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        cwgPlugin = new CWGPlugin(getApplicationContext());
        //demo001();
        demo002();
    }


    @Override
    protected void onStart() {
        super.onStart();
        cwgPlugin.setEnabled(true);

        TextView lbl = findViewById(R.id.lbl);
        lbl.setText(cwgPlugin.getVersionInfo());
    }

    @Override
    protected void onStop() {
        super.onStop();
        cwgPlugin.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        cwgPlugin.onDestroy();
        super.onDestroy();
    }

    private void log(String value) {
        Log.d(LOG_TAG, value);
    }


    private void demo001() {
        log("main activity: " + this.getClass().getSimpleName());
        log("Plugins SDK version: " + CWGPlugin.VERSION);

        // Multi Update Helper demo
        MultiUpdateHelper muh = new MultiUpdateHelper();
        muh.add(6001, "Title-1");
        muh.add(6002, "Title-2");
        muh.add(6003, "Title-3");
        muh.add(6004, "120 km/h", 120, "010D");
        log("Update line: " + muh.asUpdateLine());
    }

    private void demo002() {
        cwgPlugin.sendCommand("param1 param2 param3");
    }
}