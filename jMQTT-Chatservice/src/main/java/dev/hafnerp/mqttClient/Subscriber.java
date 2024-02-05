package dev.hafnerp.mqttClient;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.URI;

public class Subscriber {
    private Exception exception = null;

    private MqttClient mqttClient;

    private URI uri;

    public Subscriber(URI uri) {
        try {
            System.out.println(uri.getHost());
            this.uri = uri;
            mqttClient = new MqttClient(
                    uri.getHost(),
                    MqttClient.generateClientId(),
                    new MemoryPersistence()
            );
        }
        catch (MqttException mqttException) {
            exception = mqttException;
        }

    }

    public void connect(MqttConnectOptions mqttConnectOptions) {
        try {
            mqttClient.connect(mqttConnectOptions);
        }
        catch (MqttException mqttException) {
            exception = mqttException;
        }
    }

    public void addIMqttMessageListener(IMqttMessageListener iMqttMessageListener) {
        try {
            System.out.println(uri.getPath());
            mqttClient.subscribe(uri.getPath(), iMqttMessageListener);
        }
        catch (Exception e) {
            exception = e;
        }
    }

    public void disconnect() {
        try {
            mqttClient.disconnect();
        }
        catch (Exception e) {
            exception = e;
        }
    }

    public Exception getException() {
        return exception;
    }
}
