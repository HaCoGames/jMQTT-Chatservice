package dev.hafnerp.jmqttchatservice;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginController {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport("");

    private MqttConnectOptions mqttConnectOptions;

    private String hostName;

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
        hostName = text_host.getText();

        propertyChangeSupport.firePropertyChange("BUTTON", false, true);
    }

    public MqttConnectOptions getLastMqttConnectOptions() {
        return mqttConnectOptions;
    }

    public String getLastHostName() {
        return hostName;
    }

    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
    }
}
