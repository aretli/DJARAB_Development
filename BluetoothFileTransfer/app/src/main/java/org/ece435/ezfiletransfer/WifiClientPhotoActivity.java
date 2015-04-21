package org.ece435.ezfiletransfer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.os.Environment;

public class WifiClientPhotoActivity extends ActionBarActivity {

    EditText editTextAddress;
    Button buttonConnect;
    TextView textPort;
    EditText editFileName;
    static final int SocketServerPORT = 8080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wificlient_main);
        editFileName = (EditText) findViewById(R.id.filename);
        editTextAddress = (EditText) findViewById(R.id.address);
        textPort = (TextView) findViewById(R.id.port);
        textPort.setText("port: " + SocketServerPORT);
        buttonConnect = (Button) findViewById(R.id.connect);

        buttonConnect.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                ClientRxThread clientRxThread =
                        new ClientRxThread(
                                editTextAddress.getText().toString(),
                                SocketServerPORT);

                clientRxThread.start();
            }});
    }

    private class ClientRxThread extends Thread {
        String dstAddress;
        int dstPort;

        ClientRxThread(String address, int port) {
            dstAddress = address;
            dstPort = port;
        }

        @Override
        public void run() {
            Socket socket = null;

            try {
                socket = new Socket(dstAddress, dstPort);

                File file = new File(
                        Environment.getExternalStorageDirectory(),
                        "photo.png");

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                byte[] bytes;
                FileOutputStream fos = null;
                try {
                    bytes = (byte[])ois.readObject();
                    fos = new FileOutputStream(file);
                    fos.write(bytes);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if(fos!=null){
                        fos.close();
                    }

                }

                socket.close();

                WifiClientPhotoActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(WifiClientPhotoActivity.this,
                                "Finished",
                                Toast.LENGTH_LONG).show();
                    }});

            } catch (IOException e) {

                e.printStackTrace();

                final String eMsg = "Something wrong: " + e.getMessage();
                WifiClientPhotoActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(WifiClientPhotoActivity.this,
                                eMsg,
                                Toast.LENGTH_LONG).show();
                    }});

            } finally {
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}