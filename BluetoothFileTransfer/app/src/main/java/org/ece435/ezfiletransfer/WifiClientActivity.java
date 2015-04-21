package org.ece435.ezfiletransfer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

public class WifiClientActivity extends ActionBarActivity {

    public static String filename;
    EditText editTextAddress;
    EditText editFileName;
    Button buttonConnect;
    TextView textPort;

    static final int SocketServerPORT = 8080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wificlient_main);

        editTextAddress = (EditText) findViewById(R.id.address);
        editFileName = (EditText) findViewById(R.id.filename);
        textPort = (TextView) findViewById(R.id.port);
        textPort.setText("port: " + SocketServerPORT);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonConnect.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                filename = editFileName.getText().toString();
                ClientRxThread clientRxThread =
                        new ClientRxThread(
                                editTextAddress.getText().toString(),editFileName.getText().toString(),
                                SocketServerPORT);

                clientRxThread.start();
            }});
    }
    private class ClientRxThread extends Thread {
        String dstAddress;
        String filename;
        int dstPort;

        ClientRxThread(String address, String name, int port) {
            dstAddress = address;
            dstPort = port;
            filename = name;
        }

        @Override
        public void run() {
            Socket socket = null;
            try {
                socket = new Socket(dstAddress, dstPort);

                File file = new File(
                        Environment.getExternalStorageDirectory(),
                        filename);

                byte[] bytes = new byte[1024];
                InputStream is = socket.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                int bytesRead = is.read(bytes, 0, bytes.length);
                bos.write(bytes, 0, bytesRead);
                bos.close();
                socket.close();

                WifiClientActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(WifiClientActivity.this,
                                "Finished",
                                Toast.LENGTH_LONG).show();
                    }});

            } catch (IOException e) {

                e.printStackTrace();

                final String eMsg = "Something wrong: " + e.getMessage();
                WifiClientActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(WifiClientActivity.this,
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

    public String getEditFilename()
    {
        return editFileName.getText().toString();
    }

}