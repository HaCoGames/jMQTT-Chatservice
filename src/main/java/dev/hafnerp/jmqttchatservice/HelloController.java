package dev.hafnerp.jmqttchatservice;

import dev.hafnerp.mqttClient.Chat;
import dev.hafnerp.mqttClient.ChatListMonitor;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;

public class HelloController {

    @FXML
    private ListView<Chat> list_chats;

    @FXML
    private TableColumn<Chat, String> col_names;

    @FXML
    private TextArea text_chatHistory;

    @FXML
    private TextField text_inputField;

    private final ChatListMonitor chatListMonitor = ChatListMonitor.getInstance();

    @FXML
    void sendButtonPressed(ActionEvent event) {
        String message = text_inputField.getText();
        list_chats.getSelectionModel().getSelectedItem().sendMessage(message); //updates the UI with the propertyChangeSupport and the event listener!
        text_inputField.clear();
    }

    @FXML
    void text_inputFieldKeyHandler(KeyEvent event) {

    }

    @FXML
    void onCreateChat(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloController.class.getResource("create-chat.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            CreateChat createChat = fxmlLoader.getController();
            createChat.setOnAddButtonListener(() -> {
                String user = HelloApplication.getMqttConnectOptions().getUserName();
                String currUser = createChat.getUser();
                Chat chat = null;
                try {
                    chat = new Chat(
                            new URI(HelloApplication.getHostName() + user + "/" + currUser),
                            new URI(HelloApplication.getHostName() + currUser + "/" + user)
                    );
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }

                chat.connect(HelloApplication.getMqttConnectOptions());

                list_chats.getItems().add(chat);

                stage.close();
            });

            stage.setScene(scene);
            stage.setTitle("MQTT - add chat");
            stage.show();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onDeleteChat(ActionEvent event) {
        list_chats.getItems().remove(list_chats.getSelectionModel().getSelectedItem());
    }

    public ListView<Chat> getList_chats() {
        return list_chats;
    }

    public void setList_chats(ListView<Chat> list_chats) {
        this.list_chats = list_chats;
    }

    public TextArea getText_chatHistory() {
        return text_chatHistory;
    }

    public void setText_chatHistory(TextArea text_chatHistory) {
        this.text_chatHistory = text_chatHistory;
    }

    public TextField getText_inputField() {
        return text_inputField;
    }

    public void setText_inputField(TextField text_inputField) {
        this.text_inputField = text_inputField;
    }

    public void setHistory(LinkedList<String> history) {
        Platform.runLater(() -> {
            text_chatHistory.clear();
        });
        history.forEach((entry) -> {
            Platform.runLater(() -> {
                text_chatHistory.appendText(entry + System.lineSeparator());
            });
        });
    }

    public void addMessage(String user, String message) {
        String msg = user + " - " + message;
        Chat chat = list_chats.getSelectionModel().getSelectedItem();
        chat.addHistory(msg);
        Platform.runLater(() -> {
            text_chatHistory.appendText(msg + System.lineSeparator());
        });
    }

    public void initializeChatView() {
        Chat.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String currChat = list_chats.getSelectionModel().getSelectedItem().getSubscriber().getUsername();
                String currUser = list_chats.getSelectionModel().getSelectedItem().getPublisher().getUsername();
                if (
                        currChat.equals(evt.getPropertyName()) ||
                        currUser.equals(evt.getPropertyName())
                ) addMessage(evt.getPropertyName(), evt.getNewValue().toString());
            }
        });
        list_chats.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Chat>() {
            @Override
            public void changed(ObservableValue<? extends Chat> observableValue, Chat chat, Chat t1) {
                setHistory(t1.getHistory());
            }
        });
        list_chats.getItems().addAll(chatListMonitor.getChats());
        list_chats.getSelectionModel().selectFirst();
    }

}

