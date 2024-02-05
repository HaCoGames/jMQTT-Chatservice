package dev.hafnerp.mqttClient;

import java.net.URI;

public class Chat {

    private Publisher publisher;
    private Subscriber subscriber;


    /**
     *
     * @param publisher Requires an already connected publisher!
     * @param subscriber Requires an already connected subscriber!
     */
    public Chat(Publisher publisher, Subscriber subscriber) {
        this.publisher = publisher;
        this.subscriber = subscriber;
    }

    public void sendMessage(String message) {
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
}
