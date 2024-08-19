package com.carwebguru.demo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.carwebguru.plugins.CWGPlugin;

public class MainActivity extends AppCompatActivity {

    private CWGPlugin cwgPlugin;

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
}