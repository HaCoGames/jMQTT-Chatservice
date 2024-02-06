package dev.hafnerp.jmqttchatservice;

import dev.hafnerp.mqttClient.Chat;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class HelloApplication extends Application {

    private HelloController helloController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        helloController = fxmlLoader.getController();
        helloController.initializeChatView();

        FXMLLoader fxmlLoaderLogin = new FXMLLoader(HelloApplication.class.getResource("connection-form.fxml"));
        Parent rootLogin = fxmlLoaderLogin.load();

        Scene login = new Scene(rootLogin);
        Scene scene = new Scene(root, 700, 430);
        stage.setTitle("MQTT - Chat");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}