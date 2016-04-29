package moun.com.wimf.model;

import java.util.Date;

/**
 * Created by maiga mariam on 25/04/2016.
 */
public class WIMF_Friend {

    private int id;
    private String user1;
    private String user2;
    private int state;
    private String creation_date;
    private String update_date;

    public WIMF_Friend(){}

    public WIMF_Friend(String u1,String u2,int stat){
        this.user1 = u1;
        this.user2 = u2;
        this.state = stat;
        this.creation_date = new Date().toString();
        this.update_date = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
        return "Friend{" +
                "id=" + id +
                ", user1='" + user1 + '\'' +
                ", user2='" + user2 + '\'' +
                ", state='" + state + '\'' +
                ", creation_date='" + creation_date + '\'' +
                ", update_date='" + update_date + '\'' +
                '}';
    }
}
