package com.bryan;

import com.bryan.controller.persistence.PersistenceAccess;
import com.bryan.controller.persistence.ValidAccount;
import com.bryan.controller.services.LoginService;
import com.bryan.model.EmailAccount;
import com.bryan.view.ViewFactory;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Launcher extends Application {

    private PersistenceAccess persistenceAccess = new PersistenceAccess();
    private EmailManager emailManager = new EmailManager();

    @Override
    public void start(Stage stage) throws Exception{

        ViewFactory viewFactory = new ViewFactory(emailManager);
        List<ValidAccount> validAccountList = persistenceAccess.loadFromPersistence();
        if(validAccountList.size() > 0){
            viewFactory.showMainWindow();
            for (ValidAccount validAccount : validAccountList
                 ) {
                EmailAccount emailAccount = new EmailAccount(validAccount.getAddress(), validAccount.getPassword());
                LoginService loginService = new LoginService(emailAccount, emailManager);
                loginService.start();
            }
        }else {
            viewFactory.showLoginWindow();
        }

        viewFactory.updateStyles();
    }


    @Override
    public void stop() throws Exception {
        List<ValidAccount> validAccounts = new ArrayList<>();
        for (EmailAccount emailAccount : emailManager.getEmailAccounts()
             ) {
            validAccounts.add(new ValidAccount(emailAccount.getAddress(), emailAccount.getPassword()));
        }
        persistenceAccess.saveToPersistence(validAccounts);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
