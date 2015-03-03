package org.ece435.bluetoothconnect;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    /*CALLED WHEN THE ACTIVITY IS FIRST CREATED*/
    private BluetoothAdapter btAdapter;
    public TextView statusUpdate;
    public Button connect;
    public Button disconnect;
    public ImageView logo;

    BroadcastReceiver bluetoothState = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String prevStateExtra = BluetoothAdapter.EXTRA_PREVIOUS_STATE;
            String stateExtra = BluetoothAdapter.EXTRA_STATE;
            int state = intent.getIntExtra(stateExtra, -1);
            int previousState = intent.getIntExtra(prevStateExtra, -1);
            String toastText = "";
            switch(state){
                case(BluetoothAdapter.STATE_TURNING_ON) :
                {
                    toastText = "Bluetooth turning on";
                    Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();
                    break;
                }
                case(BluetoothAdapter.STATE_ON) :
                {
                    toastText = "Bluetooth on";
                    Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();
                    setupUI();
                    break;
                }
                case(BluetoothAdapter.STATE_TURNING_OFF) :
                {
                    toastText = "Bluetooth turning off";
                    Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();
                    break;
                }
                case(BluetoothAdapter.STATE_OFF) :
                {
                    toastText = "Bluetooth off";
                    Toast.makeText(MainActivity.this, toastText, Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    private void setupUI() {
        //Get references
        final TextView statusUpdate = (TextView) findViewById(R.id.result);
        final Button connect = (Button) findViewById(R.id.connectBtn);
        final Button disconnect = (Button) findViewById(R.id.disconnectBtn);
        final ImageView logo = (ImageView) findViewById(R.id.logo);
        //set Display View
        disconnect.setVisibility(View.GONE);
        logo.setVisibility(View.GONE);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        // show The Image
        if(btAdapter.isEnabled()){
            String address = btAdapter.getAddress();
            String name = btAdapter.getName();
            String statusText = name + " : " + address;
            statusUpdate.setText(statusText);
            disconnect.setVisibility(View.VISIBLE);
            logo.setVisibility(View.VISIBLE);
            connect.setVisibility(View.GONE);
        }
        else{
            connect.setVisibility(View.VISIBLE);
            statusUpdate.setText("Bluetooth is not on");
        }

        connect.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                String actionStateChanged = BluetoothAdapter.ACTION_STATE_CHANGED;
                //Show activity
                String actionRequestEnable = BluetoothAdapter.ACTION_REQUEST_ENABLE;
                //Broadcast actionStateChanged
                IntentFilter filter = new IntentFilter(actionStateChanged);
                //Register receiver
                registerReceiver(bluetoothState, filter);
                //Start activity
                startActivityForResult(new Intent(actionRequestEnable), 0);
            }
        });

        disconnect.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v){
                btAdapter.disable();
                disconnect.setVisibility(View.GONE);
                logo.setVisibility(View.GONE);
                connect.setVisibility(View.VISIBLE);
                statusUpdate.setText("Bluetooth Off");
            }
        });
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
