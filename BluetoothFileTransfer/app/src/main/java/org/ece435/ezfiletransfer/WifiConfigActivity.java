package org.ece435.ezfiletransfer;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class WifiConfigActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wificonfig_main);

        Button en = (Button) findViewById(R.id.en);
        Button dis = (Button) findViewById(R.id.dis);
        final WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        en.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(true);
                    Toast.makeText(WifiConfigActivity.this, "Wifi enabled", Toast.LENGTH_LONG).show();
                }

            }
        });

        dis.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if(wifiManager.isWifiEnabled()) {
                    wifiManager.setWifiEnabled(false);
                    Toast.makeText(WifiConfigActivity.this, "Wifi disabled", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void returnHomeHandler2(View v)
    {
        finish();//Ends activity
    }

}