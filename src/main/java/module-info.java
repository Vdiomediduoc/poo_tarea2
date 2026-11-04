module dev.diomedi.poo_tarea2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;

    opens dev.diomedi.poo_tarea2 to javafx.fxml;
    exports dev.diomedi.poo_tarea2;
    exports dev.diomedi.poo_tarea2.controller;
    opens dev.diomedi.poo_tarea2.controller to javafx.fxml;
}