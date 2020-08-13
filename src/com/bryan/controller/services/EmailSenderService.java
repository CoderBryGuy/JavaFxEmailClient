package com.bryan.controller.services;

import com.bryan.controller.EmailSendingResult;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class EmailSenderService extends Service<EmailSendingResult> {
    @Override
    protected Task<EmailSendingResult> createTask() {
        return null;
    }
}
