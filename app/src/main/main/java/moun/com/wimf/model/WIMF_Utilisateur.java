package moun.com.wimf.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WIMF_Utilisateur{


    protected int idU;
    protected String nom;
    protected String tel;
    protected double gps_lat;
    protected double gps_long;
    private String password;
    private String datetimeCrea;
    protected String datetimeMaj;
    private List<WIMF_Ami> amis;

    public WIMF_Utilisateur()
    {
        amis = new ArrayList<WIMF_Ami>();
    }

    public WIMF_Utilisateur(int idU, String nom,String tel,double gps_lat,double gps_long)
    {
        this.idU = idU;
        this.nom = nom;
        this.tel = tel;
        this.gps_lat = gps_lat;
        this.gps_long = gps_long;
    }

    public WIMF_Utilisateur(int idU, String nom,String tel,double gps_lat,double gps_long,String password, String datetimeCrea, String datetimeMaj)
    {
        this.idU = idU;
        this.nom = nom;
        this.tel = tel;
        this.gps_lat = gps_lat;
        this.gps_long = gps_long;
        this.password = password;
        this.datetimeCrea = datetimeCrea;
        this.datetimeMaj = datetimeMaj;
    }

    public int get_idU()
    {
      return idU;
    }
    public String get_nom()
    {
      return nom;
    }
    public String get_tel()
    {
      return tel;
    }
    public double get_gps_lat()
    {
      return gps_lat;
    }
    public double get_gps_long()
    {
      return gps_long;
    }
    public String get_password()
    {
      return password;
    }
    public String get_datetimeCrea()
    {
      return datetimeCrea;
    }
    public String get_datetimeMaj()
    {
      return datetimeMaj;
    }

    public void set_idU(int idU)
    {
      this.idU = idU;
    }
    public void set_nom(String nom)
    {
      this.nom = nom;
    }
    public void set_tel(String tel)
    {
      this.tel = tel;
    }
    public void set_gps_lat(double gps_lat)
    {
      this.gps_lat = gps_lat;
    }
    public void set_gps_long(double gps_long)
    {
      this.gps_long = gps_long;
    }
    public void set_password(String password)
    {
      this.password = password;
    }
    public void set_datetimeCrea(String datetimeCrea)
    {
      this.datetimeCrea = datetimeCrea;
    }
    public void set_datetimeMaj(String datetimeMaj)
    {
      this.datetimeMaj = datetimeMaj;
    }

    @Override
    public String toString() {
        return this.getClass() + " "
                + this.get_idU() + ","
                + this.get_nom() + ","
                + this.get_tel() + ","
                + this.get_gps_lat() + ","
                + this.get_gps_long() + ","
                + this.get_datetimeCrea() + ","
                + this.get_datetimeMaj();

    }
}
