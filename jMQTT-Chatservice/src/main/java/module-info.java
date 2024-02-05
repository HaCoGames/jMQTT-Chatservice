module dev.hafnerp.jmqttchatservice {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens dev.hafnerp.jmqttchatservice to javafx.fxml;
    exports dev.hafnerp.jmqttchatservice;
}