package dev.hafnerp.mqttClient;

import java.util.ArrayList;

public class ChatListMonitor {

    private static final ChatListMonitor chatListMonitor = new ChatListMonitor();

    public static ChatListMonitor getInstance() {
        return chatListMonitor;
    }

    private ChatListMonitor() {
    }

    private final ArrayList<Chat> chats = new ArrayList<>();

    public synchronized void addChat(Chat chat) {
        chats.add(chat);
    }

    public synchronized ArrayList<Chat> getChats() {
        return new ArrayList<>(chats);
    }
}
