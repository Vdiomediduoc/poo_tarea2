module dev.diomedi.poo_tarea2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    opens dev.diomedi.poo_tarea2.model to com.google.gson;
    opens dev.diomedi.poo_tarea2.controller to javafx.fxml;
    exports dev.diomedi.poo_tarea2;
    exports dev.diomedi.poo_tarea2.controller;
    exports dev.diomedi.poo_tarea2.model;}