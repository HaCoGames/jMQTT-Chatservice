package dev.hafnerp.mqttClient;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Subscriber {
    private Exception exception = null;

    private MqttClient mqttClient;

    private MqttConnectOptions mqttConnectOptions;

    private URI uri;

    public Subscriber(URI uri) {
        try {
            this.uri = uri;
            String host = uri.getScheme() + "://" +  uri.getHost();
            mqttClient = new MqttClient(
                    host,
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
            this.mqttConnectOptions = mqttConnectOptions;
            mqttClient.connect(mqttConnectOptions);
        }
        catch (MqttException mqttException) {
            exception = mqttException;
        }
    }

    public void addIMqttMessageListener(IMqttMessageListener iMqttMessageListener) {
        try {
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

    public URI getURI() throws URISyntaxException {
        return new URI(uri.getRawSchemeSpecificPart());
    }

    public String getUsername() {
        return Arrays.stream(uri.getPath().split("/")).toList().get(1);
    }
}
