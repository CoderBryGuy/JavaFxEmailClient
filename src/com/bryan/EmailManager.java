package com.bryan;

import com.bryan.controller.services.FetchFoldersService;
import com.bryan.model.EmailAccount;
import com.bryan.model.EmailTreeItem;
import javafx.scene.control.TreeItem;

public class EmailManager {

    //folder manager

    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");

   public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);

    }
}
