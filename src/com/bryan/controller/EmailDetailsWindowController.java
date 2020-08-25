package com.bryan.controller;

import com.bryan.EmailManager;
import com.bryan.controller.services.MessageRendererService;
import com.bryan.model.EmailMessage;
import com.bryan.view.ViewFactory;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

import javax.mail.MessagingException;

import javax.mail.internet.MimeBodyPart;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmailDetailsWindowController extends BaseController implements Initializable  {
    
    private String LOCATION_OF_DOWNLOADS = System.getProperty("user.home") + "/Downloads/";

    @FXML
    private WebView webView;
    @FXML
    private Label subjectLabel;
    @FXML
    private Label senderLabel;
    @FXML
    private Label attachmentsLabel;
    @FXML
    private HBox hBoxDownLoads;



    public EmailDetailsWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EmailMessage emailMessage = emailManager.getSelectedMessage();
        subjectLabel.setText(emailMessage.getSubject());
        senderLabel.setText(emailMessage.getSender());
        loadAttachments(emailMessage);

        MessageRendererService messageRendererService = new MessageRendererService(webView.getEngine());
        messageRendererService.setEmailMessage(emailMessage);
        messageRendererService.restart();
    }

    private void loadAttachments(EmailMessage emailMessage) {
        if(emailMessage.isHasAttachments()){
            for(MimeBodyPart mimeBodyPart: emailMessage.getAttachmentList()){
                try {
                    AttachmentsButton button = new AttachmentsButton(mimeBodyPart);
                    hBoxDownLoads.getChildren().add(button);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }
        }else{
            attachmentsLabel.setText("");
        }
    }

    private class AttachmentsButton extends Button{

        private MimeBodyPart mimeBodyPart;
        private String downloadedFilePath;

        public AttachmentsButton(MimeBodyPart mimeBodyPart) throws MessagingException {
            this.mimeBodyPart = mimeBodyPart;
            this.setText(mimeBodyPart.getFileName());
            this.downloadedFilePath = LOCATION_OF_DOWNLOADS + mimeBodyPart.getFileName();

            this.setOnAction( e -> downloadAttachments());
        }

        private void downloadAttachments() {
            colorBlue();
            Service service = new Service() {
                @Override
                protected Task<Object> createTask() {
                    return new Task<Object>() {
                        @Override
                        protected Object call() throws Exception {
                            mimeBodyPart.saveFile(downloadedFilePath);
                            return null;
                        }
                    };
                }
            };

            service.restart();
            service.setOnSucceeded(e->{
                colorGreen();
                this.setOnAction(e2->{
                    File file = new File(downloadedFilePath);
                    Desktop desktop = Desktop.getDesktop();
                    if(file.exists()){
                        try {
                            desktop.open(file);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
            });
        }


        private void colorBlue(){
            this.setStyle("-fx-background-color: Blue");
        }

        private void colorGreen(){
            this.setStyle("-fx-background-color: Green");
        }
    }


}
