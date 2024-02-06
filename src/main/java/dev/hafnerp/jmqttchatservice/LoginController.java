package dev.hafnerp.jmqttchatservice;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class LoginController {

    private MqttConnectOptions mqttConnectOptions;

    @FXML
    private Button button_login;


    @FXML
    private TextField text_host;

    @FXML
    private TextField text_password;

    @FXML
    private TextField text_username;

    @FXML
    void onLogInPressed(ActionEvent event) {
        mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setUserName(text_username.getText());
        mqttConnectOptions.setPassword(text_password.getText().toCharArray());
        String hostName = text_host.getText();
    }

    public MqttConnectOptions getLastMqttConnectOptions() {
        return mqttConnectOptions;
    }
}
