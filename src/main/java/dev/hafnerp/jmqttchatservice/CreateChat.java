package dev.hafnerp.jmqttchatservice;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateChat {

    private String user;

    private Runnable runnable;

    @FXML
    private TextField text_userName;

    @FXML
    void onAddButton(ActionEvent event) {
        user = text_userName.getText();
        runnable.run();
    }

    public String getUser() {
        return user;
    }

    public void setOnAddButtonListener(Runnable runnable) {
        this.runnable = runnable;
    }
}
