package com.bryan;

import com.bryan.controller.services.FetchFoldersService;
import com.bryan.controller.services.FolderUpdaterService;
import com.bryan.model.EmailAccount;
import com.bryan.model.EmailMessage;
import com.bryan.model.EmailTreeItem;
import com.bryan.view.IconResolver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.mail.Flags;
import javax.mail.Folder;
import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    private FolderUpdaterService folderUpdaterService;

    //folder manager
    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selectedFolder;

    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<>("");
    private ObservableList<EmailAccount> emailAccounts = FXCollections.observableArrayList();
    private IconResolver iconResolver = new IconResolver();

    private List<Folder> folderList = new ArrayList<>();

    public List<Folder> getFolderList() {
        return folderList;
    }

    public EmailManager() {
        folderUpdaterService = new FolderUpdaterService(folderList);
        folderUpdaterService.start();
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }
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
        treeItem.setGraphic(iconResolver.getIconFolder(emailAccount.getAddress()));
        FetchFoldersService fetchFoldersService = new FetchFoldersService(emailAccount.getStore(), treeItem, folderList);
        fetchFoldersService.start();
        foldersRoot.getChildren().add(treeItem);

    }

    public void setRead() {
        try {
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
            selectedFolder.decrementMessagesCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUnRead() {
        try {
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
            selectedFolder.incrementMessagesCount();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSelectedMessage() {
        try {
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
            selectedFolder.getEmailMessages().remove(selectedMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

