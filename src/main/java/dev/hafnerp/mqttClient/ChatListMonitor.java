package dev.hafnerp.mqttClient;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.net.URI;
import java.util.ArrayList;

public class ChatListMonitor {

    private static final ChatListMonitor chatListMonitor = new ChatListMonitor();

    public static ChatListMonitor getInstance() {
        return chatListMonitor;
    }

    private ChatListMonitor() {
        try {
            Chat pridnik = new Chat(new URI("tcp://localhost/peter/pridnik"), new URI("tcp://localhost/pridnik/peter"));
            MqttConnectOptions mqttConnectOptionsPeter = new MqttConnectOptions();
            mqttConnectOptionsPeter.setUserName("peter");
            mqttConnectOptionsPeter.setPassword("peter".toCharArray());
            pridnik.connect(mqttConnectOptionsPeter);
            chats.add(pridnik);

            Chat david = new Chat(new URI("tcp://localhost/peter/david"), new URI("tcp://localhost/david/peter"));
            david.connect(mqttConnectOptionsPeter);
            chats.add(david);
        }
        catch (Exception ignore){}
    }

    private final ArrayList<Chat> chats = new ArrayList<>();

    public synchronized void addChat(Chat chat) {
        chats.add(chat);
    }

    public synchronized ArrayList<Chat> getChats() {
        return new ArrayList<>(chats);
    }
}
