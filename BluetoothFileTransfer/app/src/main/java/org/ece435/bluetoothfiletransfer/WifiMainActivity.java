package org.ece435.bluetoothfiletransfer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Anthony on 3/20/2015.
 */
public class WifiMainActivity extends Activity {

    private TextView name;
    private ImageView logo;
    private Button configBtn;
    private Button fileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wifi_main);
        name = (TextView) findViewById(R.id.name);
    }

    public void serverHandler(View v)
    {
        Intent myIntent = new Intent(this, WifiServerActivity.class);
        startActivity(myIntent);
    }
    public void clientHandler(View v) {
        Intent myIntent = new Intent(this, WifiClientActivity.class);
        startActivity(myIntent);
    }
    public void wifiConfigHandler(View v) {
        Intent myIntent = new Intent(this, WifiConfigActivity.class);
        startActivity(myIntent);
    }
    public void wifiListNetworkHandler(View v) {
        Intent myIntent = new Intent(this, WifiListNetworkActivity.class);
        startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void returnHomeHandler(View v)
    {
        finish();//Ends activity
    }

}
