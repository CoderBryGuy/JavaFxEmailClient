package com.bryan.controller;

import com.bryan.EmailManager;
import com.bryan.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebView;

public class EmailDetailsWindowController extends BaseController {

    @FXML
    private WebView webView;
    @FXML
    private Label subjectView;
    @FXML
    private Label senderView;
    @FXML
    private Label attachmentsLabel;
    @FXML
    private HBox hBocDownLoads;



    public EmailDetailsWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }
}
