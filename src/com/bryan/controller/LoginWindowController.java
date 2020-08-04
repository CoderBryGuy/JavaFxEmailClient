package com.bryan.controller;

import com.bryan.EmailManager;
import com.bryan.controller.services.LoginService;
import com.bryan.model.EmailAccount;
import com.bryan.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static com.bryan.controller.EmailLoginResult.SUCCESS;

public class LoginWindowController extends BaseController {
    @FXML
    private Button loginButton;

    @FXML
    private Label emailLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField emailAddressField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    public void loginButtonAction() {
        if(fieldsAreValid()) {
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(),
                    passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            EmailLoginResult emailLoginResult = loginService.login();

            switch (emailLoginResult){
                case SUCCESS:
                    System.out.println("login successful!!!" + emailAccount);

            }
            System.out.println("loginButtonAction!!!");
            viewFactory.showMainWindow();
            Stage stage = (Stage) errorLabel.getScene().getWindow();
            viewFactory.closeStage(stage);
        }
    }

    private boolean fieldsAreValid() {
        if(emailAddressField.getText().isEmpty()){
            errorLabel.setText("Please fill in email");
            return false;
        }
        if(passwordField.getText().isEmpty()){
            errorLabel.setText("Please fill in password");
            return false;
        }

        return true;
    }
}
