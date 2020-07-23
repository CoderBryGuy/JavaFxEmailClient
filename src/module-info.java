module JavaFxEmailClient {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;


    opens com.bryan;
    opens com.bryan.view;
    opens com.bryan.controller;
}