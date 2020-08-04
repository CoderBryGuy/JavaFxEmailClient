module JavaFxEmailClient {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;
    requires activation;
    requires java.mail;


    opens com.bryan;
    opens com.bryan.view;
    opens com.bryan.controller;
}