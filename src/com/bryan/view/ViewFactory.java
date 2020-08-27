package com.bryan.view;

import com.bryan.EmailManager;
import com.bryan.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {

    private EmailManager emailManager;
    private ArrayList<Stage> activeStages;
    private boolean mainViewInitialized = false;

    //View options handling:
    private ColorTheme colorTheme = ColorTheme.DEFAULT;
    private FontSize fontSize = FontSize.MEDIUM;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        activeStages = new ArrayList<Stage>();
    }

    public boolean isMainViewInitialized() {
        return mainViewInitialized;
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public FontSize getFontSize() {
        return fontSize;
    }

    public void showLoginWindow(){
        System.out.println("Show login window called");

        BaseController controller = new LoginWindowController(emailManager, this, "LoginWindow.fxml");
        initializeStage(controller);

    }

    public void showMainWindow(){
        System.out.println("Show main window called");

        BaseController controller = new MainWindowController(emailManager, this, "MainWindow.fxml");
        initializeStage(controller);
        mainViewInitialized = true;
    }

    public void showComposeMessageWindow(){
        System.out.println("Show compose message window called");

        BaseController controller = new ComposeMessageController(emailManager, this, "ComposeMessageWindow.fxml");
        initializeStage(controller);
        mainViewInitialized = true;
    }


    public void showOptionsWindow(){
        System.out.println("Show options window called");

        BaseController controller = new OptionsWindowController(emailManager, this, "OptionsWindow.fxml");
        initializeStage(controller);

    }

    public void showEmailDetailsWindow(){
        BaseController controller = new EmailDetailsWindowController(emailManager, this, "EmailDetailsWindow.fxml");
        initializeStage(controller);
    }

    private void initializeStage(BaseController baseController){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;
        try{
            parent = fxmlLoader.load();
        }catch (IOException e){
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        activeStages.add(stage);
    }

    public void closeStage(Stage stageToClose){
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    public void setFontSize(FontSize fontSize) {
        this.fontSize = fontSize;
    }

    public void setColorTheme(ColorTheme value) {
       this.colorTheme = value;
    }

    public void updateStyles() {
        for (Stage stage : activeStages
             ) {
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();

            System.out.println(ColorTheme.getCssPath(colorTheme).toString());

            scene.getStylesheets().add(getClass().getResource(ColorTheme.getCssPath(colorTheme)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSize.getCssPath(fontSize)).toExternalForm());
        }
    }
}
