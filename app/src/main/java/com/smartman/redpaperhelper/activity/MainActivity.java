package com.smartman.redpaperhelper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartman.redpaperhelper.R;
import com.smartman.redpaperhelper.ui.ServiceAlertDialog;
import com.smartman.redpaperhelper.utils.AccessibilityServiceUtil;
import com.smartman.redpaperhelper.utils.PrefsUtil;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    private Button startButton;
    private ImageView setView;
    private TextView helpView;
    private TextView recordView;
    private ServiceAlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        startButton = (Button) findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        });

        setView = (ImageView) findViewById(R.id.setting);
        setView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

        helpView = (TextView) findViewById(R.id.help);
        helpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ServiceAlertDialog(MainActivity.this);
            }
        });

        recordView = (TextView) findViewById(R.id.record);
        recordView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,RecordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isOnService = AccessibilityServiceUtil.isAccessibilitySettingsOn(getApplicationContext());
        if (!isOnService) {
            startButton.setText("启动服务");
            startButton.setEnabled(true);
        } else {
            startButton.setText("服务正在运行");
            startButton.setEnabled(false);
        }
        Boolean isFirstIn = PrefsUtil.loadPrefBoolean("FIRST_IN", true);
        if(isFirstIn)
        {
            dialog = new ServiceAlertDialog(MainActivity.this);
            PrefsUtil.savePrefBoolean("FIRST_IN",false);
        }

    }
}
