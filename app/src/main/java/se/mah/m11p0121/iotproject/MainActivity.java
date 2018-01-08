package se.mah.m11p0121.iotproject;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private Button ledUpOn, ledUpOff, ledDownOn, ledDownOff, ledLeftOn, ledLeftOff, ledRightOn, ledRightOff;
    private PahoMqttClient pahoMqttClient;
    private MqttAndroidClient client;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice;
    private ConnectThread mConnectThread;
    private Classifier classifier;
    public Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        classifier = new Classifier(this);
        pahoMqttClient = new PahoMqttClient();

        ledUpOn = findViewById(R.id.btn_led_up_on);
        ledUpOff = findViewById(R.id.btn_led_up_off);
        ledDownOn = findViewById(R.id.btn_led_down_on);
        ledDownOff = findViewById(R.id.btn_led_down_off);
        ledLeftOn = findViewById(R.id.btn_led_left_on);
        ledLeftOff = findViewById(R.id.btn_led_left_off);
        ledRightOn = findViewById(R.id.btn_led_right_on);
        ledRightOff = findViewById(R.id.btn_led_right_off);

        ledUpOn.setOnClickListener(new ButtonListener());
        ledUpOff.setOnClickListener(new ButtonListener());
        ledDownOn.setOnClickListener(new ButtonListener());
        ledDownOff.setOnClickListener(new ButtonListener());
        ledLeftOn.setOnClickListener(new ButtonListener());
        ledLeftOff.setOnClickListener(new ButtonListener());
        ledRightOn.setOnClickListener(new ButtonListener());
        ledRightOff.setOnClickListener(new ButtonListener());

        client = pahoMqttClient.getMqttClient(getApplicationContext(), Constants.MQTT_BROKER_URL, Constants.CLIENT_ID);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); startActivityForResult(enableBtIntent, 1);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices(); if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mDevice = device;
            } }

        mConnectThread = new ConnectThread(mDevice);
        mConnectThread.start();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                byte[] readBuf = (byte[]) msg.obj;
                int begin = (int)msg.arg1;
                int end = (int)msg.arg2;
                switch(msg.what) {
                    case 1:
                        String readMessage = new String(readBuf);
                        readMessage = readMessage.substring(begin, end);
                        String[]values = readMessage.split(","); //note that values[0] will (usually) be empty after this due to leading empty strings
                        classifier.addValues(values);
                        //Log.d("SPLITSIZE", ""+ values.length);
                        //Log.d("NEWENTRY", readMessage);
                        break;
                }
            }
        };
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btn_led_up_on:
                    try {
                        pahoMqttClient.publishMessage(client, Constants.LED_UP_ON, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_up_off:
                    try {
                        pahoMqttClient.publishMessage(client, Constants.LED_UP_OFF, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_down_on:
                    try {
                        pahoMqttClient.publishMessage(client, Constants.LED_DOWN_ON, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_down_off:
                    try {
                        pahoMqttClient.publishMessage(client, Constants.LED_DOWN_OFF, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_right_on:
                    try {
                        pahoMqttClient.publishMessage(client, Constants.LED_RIGHT_ON, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_right_off:
                    try {
                        pahoMqttClient.publishMessage(client, Constants.LED_RIGHT_OFF, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_left_on:
                    try {
                        pahoMqttClient.publishMessage(client, Constants.LED_LEFT_ON, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_left_off:
                    try {
                        pahoMqttClient.publishMessage(client, Constants.LED_LEFT_OFF, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private final UUID MY_UUID = UUID.fromString(Constants.UUID);
        private ConnectedThread mConnectedThread;
        public ConnectThread(BluetoothDevice device) {
            BluetoothSocket tmp = null;
            mmDevice = device;
            try {
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) { }
            mmSocket = tmp;
        }
        public void run() {
            mBluetoothAdapter.cancelDiscovery();
            try {
                mmSocket.connect();
            } catch (IOException connectException) {
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return; }

            mConnectedThread = new ConnectedThread(mmSocket);
            mConnectedThread.start();
        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
//        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
//            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
//                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }
            mmInStream = tmpIn;
//            mmOutStream = tmpOut;
        }
        public void run() {
            byte[] buffer = new byte[1024];
            int begin = 0;
            int bytes = 0;
            int values = 0;
            while (true) {
                try {
                    bytes += mmInStream.read(buffer, bytes, buffer.length - bytes);
                    for(int i = begin; i < bytes; i++) {
/*                        if(buffer[i] == "h".getBytes()[0]) {
                            Log.d("BTINPUT" , "h");
                            begin += 2;
                        } else*/
                            if (buffer[i] == "\n".getBytes()[0]) {
                            //values++;
                                begin++;
                            //if (values > 5) {
                                mHandler.obtainMessage(1, begin, i, buffer).sendToTarget();
                                begin = i + 1;
                                if(i == bytes - 1) {
                                    bytes = 0;
                                    begin = 0;
                                }
                                values = 0;
                            //}
                        }
                    }
                } catch (IOException e) {
                    break;
                } }
        }
/*        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }*/
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        } }
}
