package moun.com.wimf.model;

import java.util.Date;

/**
 * Created by maiga mariam on 25/04/2016.
 */
public class WIMF_Message {
    private int id;
    private String message;
    private int state;
    private String sender;
    private String receiver;
    private String creation_date;
    private String update_date;

    public WIMF_Message()
    {}

    public WIMF_Message(String mess,int etat,String send,String receiv) {
        this.message = mess;
        this.state = etat;
        this.sender = send;
        this.receiver = receiv;
        this.creation_date = new Date().toString();
        this.update_date = null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", state=" + state +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", creation_date='" + creation_date + '\'' +
                ", update_date='" + update_date + '\'' +
                '}';
    }

}
