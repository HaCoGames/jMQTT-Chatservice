package dev.hafnerp.mqttClient;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedList;

public class Chat {

    private Publisher publisher;

    private Subscriber subscriber;

    private URI uri;

    private static final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport("");;

    private LinkedList<String> history = new LinkedList<String>();

    public Chat(URI publisherURI, URI subscriberURI) {
        this.publisher = new Publisher(publisherURI);
        this.subscriber = new Subscriber(subscriberURI);

        propertyChangeSupport.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String username = evt.getPropertyName();
                String message = evt.getPropertyName() + " - " + evt.getNewValue().toString();
                if (username.equals(subscriber.getUsername())) addHistory(message);
            }
        });
    }

    public void sendMessage(String message) {
        propertyChangeSupport.firePropertyChange(publisher.getUsername(), null, message);
        publisher.send(message);
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public LinkedList<String> getHistory() {
        return new LinkedList<String>(history);
    }

    public void setHistory(LinkedList<String> history) {
        this.history = history;
    }

    public void addHistory(String message) {
        history.add(message);
    }

    public void connect(MqttConnectOptions mqttConnectOptions) {
        IMqttMessageListener iMqttMessageListener;
        iMqttMessageListener = new IMqttMessageListener() {
            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                propertyChangeSupport.firePropertyChange(subscriber.getUsername(), null, mqttMessage.toString());
            }
        };
        subscriber.connect(mqttConnectOptions);
        subscriber.addIMqttMessageListener(iMqttMessageListener);
        publisher.connect(mqttConnectOptions);
    }

    public static void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }

    public static void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
    }

    @Override
    public String toString() {
        return Arrays.stream(publisher.getUri().getPath().split("/")).toList().getLast();
    }
}
