package dev.hafnerp.mqttClient;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.URI;

public class Publisher {

    private Exception exception = null;

    private MqttClient mqttClient;

    private URI uri;

    public Publisher(URI uri) {
        try {
            this.uri = uri;
            System.out.println(uri.getHost());
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

    public void send(String message) {
        try {
            mqttClient.publish(uri.getPath(), message.getBytes(), 2, true);
        }
        catch (MqttException mqttException) {
            exception = mqttException;
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