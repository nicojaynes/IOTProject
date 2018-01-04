package se.mah.m11p0121.iotproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private Button ledUpOn, ledUpOff, ledDownOn, ledDownOff, ledLeftOn, ledLeftOff, ledRightOn, ledRightOff;
    private PahoMqttClient pahoMqttClient;
    private MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }

    private class ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.btn_led_up_on:
                    try {
                        String msg = "LED UP ON";
                        pahoMqttClient.publishMessage(client, msg, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_up_off:
                    try {
                        String msg = "LED UP OFF";
                        pahoMqttClient.publishMessage(client, msg, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_down_on:
                    try {
                        String msg = "LED DOWN ON";
                        pahoMqttClient.publishMessage(client, msg, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_down_off:
                    try {
                        String msg = "LED DOWN OFF";
                        pahoMqttClient.publishMessage(client, msg, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_right_on:
                    try {
                        String msg = "LED RIGHT ON";
                        pahoMqttClient.publishMessage(client, msg, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_right_off:
                    try {
                        String msg = "LED RIGHT OFF";
                        pahoMqttClient.publishMessage(client, msg, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_left_on:
                    try {
                        String msg = "LED LEFT ON";
                        pahoMqttClient.publishMessage(client, msg, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.btn_led_left_off:
                    try {
                        String msg = "LED LEFT OFF";
                        pahoMqttClient.publishMessage(client, msg, 1, Constants.PUBLISH_TOPIC);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
