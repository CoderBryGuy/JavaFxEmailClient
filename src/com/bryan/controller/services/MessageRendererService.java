package com.bryan.controller.services;

import com.bryan.model.EmailMessage;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;

public class MessageRendererService extends Service {

    private EmailMessage emailMessage;
    private WebEngine webEngine;
    private StringBuffer stringBuffer;

    public MessageRendererService(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.stringBuffer = new StringBuffer();
        this.setOnSucceeded(e->{
            displayMessage();
        });
    }

    public void setEmailMessage(EmailMessage emailMessage){
        this.emailMessage = emailMessage;
    }

    private void displayMessage(){
        webEngine.loadContent(stringBuffer.toString());
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try{
                    loadMessages();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    private void loadMessages() throws MessagingException, IOException {
        stringBuffer.setLength(0);
        Message message = emailMessage.getMessage();
        String contentType = message.getContentType();
        if(isSimpleType(contentType)){
            stringBuffer.append(message.getContent().toString());
        } else if(isMultipartType(contentType)){
            Multipart multipart = (Multipart) message.getContent();
            loadMultiPart(multipart, stringBuffer);
            }
        }

        private void loadMultiPart(Multipart multipart, StringBuffer stringBuffer) throws MessagingException, IOException{
            for (int i = multipart.getCount() - 1; i >=0 ; i--) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String contentType = bodyPart.getContentType();
                if(isSimpleType(contentType)){
                    stringBuffer.append(bodyPart.getContent().toString());
                }else if(isMultipartType(contentType)){
                    Multipart multipart2 = (Multipart)bodyPart.getContent();
                    loadMultiPart(multipart2, stringBuffer);
                } else if(!isTextPlain(contentType)){
                    MimeBodyPart mbp = (MimeBodyPart) bodyPart;
                    emailMessage.addAttachment(mbp);
                }
            }

        }

        private boolean isTextPlain(String contentType){
            return contentType.contains("TEXT/PLAIN");
        }


    private boolean isSimpleType(String contentType){
        if(contentType.contains("TEXT/HTML")||
        contentType.contains("mixed")||
        contentType.contains("text")){
            return true;
        }else{
            return false;
        }
    }

    private boolean isMultipartType(String contentType){
        if(contentType.contains("multipart")){
            return true;
        }else {
            return false;
        }
    }
}
