package com.bryan;

import com.bryan.view.ColorTheme;
import com.bryan.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        ViewFactory viewFactory = new ViewFactory(new EmailManager());
//        viewFactory.showLoginWindow();

        viewFactory.showOptionsWindow();
        viewFactory.updateStyles();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
