package com.example.aplicacionmqtt;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MQTT";
    private static final String MQTT_BROKER_URL = "tcp://10.0.2.2:1883";
    private MqttAndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeMQTTClient();
    }

    private void initializeMQTTClient() {
        String clientId = "MQTT_FX_Client"; // Client ID definido
        client = new MqttAndroidClient(getApplicationContext(), MQTT_BROKER_URL, clientId);

        try {
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);

            client.connect(options, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG, "Conexión exitosa al broker MQTT");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.e(TAG, "Error al conectar con el broker MQTT", exception);
                }
            });
        } catch (MqttException e) {
            Log.e(TAG, "Error durante la conexión al broker MQTT", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (client != null && client.isConnected()) {
                client.disconnect();
                Log.d(TAG, "Desconectado del broker MQTT");
            }
        } catch (MqttException e) {
            Log.e(TAG, "Error al desconectarse del broker MQTT", e);
        }
    }
}