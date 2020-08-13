package com.bryan;

import com.bryan.controller.services.FetchFoldersService;
import com.bryan.model.EmailAccount;
import com.bryan.model.EmailMessage;
import com.bryan.model.EmailTreeItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class EmailManager {

    //folder manager
    private EmailMessage selectedMessage;
    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");
    private ObservableList<EmailAccount> emailAccounts = FXCollections.observableArrayList();

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailTreeItem<String> getFoldersRoot() {
        return foldersRoot;
    }

    public ObservableList<EmailAccount> getEmailAccounts() {
        return emailAccounts;
    }

    public void setEmailAccounts(ObservableList<EmailAccount> emailAccounts) {
        this.emailAccounts = emailAccounts;
    }

    public void addEmailAccount(EmailAccount emailAccount){
        emailAccounts.add(emailAccount);
        EmailTreeItem<String> treeItem = new EmailTreeItem<>(emailAccount.getAddress());
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);

    }
}
