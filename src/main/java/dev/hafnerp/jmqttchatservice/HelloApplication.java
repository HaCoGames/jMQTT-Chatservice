package dev.hafnerp.jmqttchatservice;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class HelloApplication extends Application {

    private static MqttConnectOptions mqttConnectOptions;

    private static String hostName;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoaderLogin = new FXMLLoader(HelloApplication.class.getResource("connection-form.fxml"));

        Parent rootLogin = fxmlLoaderLogin.load();
        LoginController loginController = fxmlLoaderLogin.getController();

        loginController.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                stage.hide();
                try {
                    mqttConnectOptions = loginController.getLastMqttConnectOptions();
                    hostName = loginController.getLastHostName();
                    stage.setScene(loadMainScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.show();
            }
        });

        Scene login = new Scene(rootLogin);

        stage.setTitle("MQTT - Login");
        stage.setScene(login);
        stage.setResizable(false);
        stage.show();

    }

    private Scene loadMainScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        HelloController helloController = fxmlLoader.getController();
        helloController.initializeChatView();

        System.out.println(mqttConnectOptions.toString());

        return new Scene(root, 700, 430);
    }

    public static void main(String[] args) {
        launch();
    }

    public static MqttConnectOptions getMqttConnectOptions() {
        return mqttConnectOptions;
    }

    public static String getHostName() {
        return hostName;
    }
}