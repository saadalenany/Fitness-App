package com.example.root.fitnessapp.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by root on 09/06/17.
 */

public class Messages implements Serializable{

    String messageID;
    String message_content;
    String sender;
    String receiver;
    String message_date;
    boolean seen;

    public Messages() {
    }

    public Messages(String messageID, String message_content, String sender, String receiver, String message_date, boolean seen) {
        this.messageID = messageID;
        this.message_content = message_content;
        this.sender = sender;
        this.receiver = receiver;
        this.message_date = message_date;
        this.seen = seen;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage_date() {
        return message_date;
    }

    public void setMessage_date(String message_date) {
        this.message_date = message_date;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "messageID='" + messageID + '\'' +
                ", message_content='" + message_content + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", message_date='" + message_date + '\'' +
                ", seen=" + seen +
                '}';
    }
}
