package moun.com.wimf.model;


import java.util.Date;

public class WIMF_User {

    private int id;
    private String userName;
    private String phone;
    private String password;
    private String GPS;
    private String creation_date;
    private String update_date;

    public WIMF_User() {
     }

    public WIMF_User(String name,String ph,String pwd,String gps,String dateCrea,String dateUpdate) {
        this.userName = name;
        this.phone = ph;
        this.password = pwd;
        this.GPS = gps;
        this.creation_date = new Date().toString();
        this.update_date = null;

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



    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

   /* public String getEmail() {
        return email;
    }

    public String getAddress() {
        return Address;
    }*/

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

   /* public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        Address = address;
    }*/
   public String getGPS() {
       return this.GPS;
   }

    public void setGPS(String gps) {
        this.GPS = gps;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", gps='" + GPS + '\'' +
                '}';
    }
}
