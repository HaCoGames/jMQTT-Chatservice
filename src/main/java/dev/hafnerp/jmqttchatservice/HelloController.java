package dev.hafnerp.jmqttchatservice;

import dev.hafnerp.mqttClient.Chat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class HelloController {

    @FXML
    private ListView<Chat> list_chats;

    @FXML
    private TextArea text_chatHistory;

    @FXML
    private TextField text_inputField;

    @FXML
    void sendButtonPressed(ActionEvent event) {
        String message = text_inputField.getText();
        text_chatHistory.appendText("Me - "+ message + System.lineSeparator());
        text_inputField.clear();
    }

    @FXML
    void text_inputFieldKeyHandler(KeyEvent event) {

    }

}
