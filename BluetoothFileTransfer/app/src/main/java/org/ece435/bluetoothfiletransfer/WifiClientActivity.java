package org.ece435.bluetoothfiletransfer;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WifiClientActivity extends Activity {

    TextView textResponse;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;

    EditText welcomeMsg;

    byte[] aByte = new byte[1];
    int bytesRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wificlient_main);

        editTextAddress = (EditText) findViewById(R.id.address);
        editTextPort = (EditText) findViewById(R.id.port);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonClear = (Button) findViewById(R.id.clear);
        textResponse = (TextView) findViewById(R.id.response);

        welcomeMsg = (EditText)findViewById(R.id.welcomemsg);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);

        buttonClear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                textResponse.setText("");
            }
        });
    }

    OnClickListener buttonConnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View arg0) {

            String tMsg = welcomeMsg.getText().toString();
            if(tMsg.equals("")){
                tMsg = null;
                Toast.makeText(WifiClientActivity.this, "No Welcome Msg sent", Toast.LENGTH_SHORT).show();
            }

            MyClientTask myClientTask = new MyClientTask(editTextAddress
                    .getText().toString(), Integer.parseInt(editTextPort
                    .getText().toString()),
                    tMsg);
            myClientTask.execute();
        }
    };

    public class MyClientTask extends AsyncTask<Void, Void, Void> {

        String dstAddress;
        int dstPort;
        String response = "";
        String msgToServer;

        MyClientTask(String addr, int port, String msgTo) {
            dstAddress = addr;
            dstPort = port;
            msgToServer = msgTo;
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;
            try {
                socket = new Socket(dstAddress, dstPort);
                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                InputStream is = null;
                is = socket.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                if(msgToServer != null && is != null)
                {
                    FileOutputStream fos = null;
                    BufferedOutputStream bos = null;
                    try {
                        fos = new FileOutputStream( msgToServer );
                        bos = new BufferedOutputStream(fos);
                        bytesRead = is.read(aByte, 0, aByte.length);

                        do {
                            baos.write(aByte);
                            bytesRead = is.read(aByte);
                        } while (bytesRead != -1);

                        bos.write(baos.toByteArray());
                        bos.flush();
                        bos.close();
                        socket.close();
                    } catch (IOException ex) {
                        // Do exception handling
                    }
                }
                /*if(msgToServer != null){
                    dataOutputStream.writeUTF(msgToServer);
                }

                response = dataInputStream.readUTF();*/



            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            textResponse.setText(response);
            super.onPostExecute(result);
        }

    }

    public void returnHomeHandler(View v)
    {
        finish();//Ends activity
    }


}