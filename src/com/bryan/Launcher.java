package com.bryan;

import com.bryan.view.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception{
//        Parent parent = FXMLLoader.load(getClass().getResource("view/LoginWindow.fxml"));
//        Scene scene = new Scene(parent, 600, 351);
//
//        Parent parent = FXMLLoader.load(getClass().getResource("view/MainWindow.fxml"));
//        Scene scene = new Scene(parent);
//
//        stage.setScene(scene);
//        stage.show();

        ViewFactory viewFactory = new ViewFactory(new EmailManager());
        viewFactory.showLoginWindow();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
