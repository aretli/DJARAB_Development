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
public class TitleActivity extends Activity {

    private TextView name;
    private ImageView logo;
    private Button configBtn;
    private Button fileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_main);
        name = (TextView) findViewById(R.id.name);
        logo = (ImageView) findViewById(R.id.logo);
    }

    public void configHandler(View v)
    {
        Intent myIntent = new Intent(this, ConfigurationActivity.class);
        startActivity(myIntent);
    }
    public void fileHandler(View v) {
        Intent myIntent = new Intent(this, FileActivity.class);
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
}
