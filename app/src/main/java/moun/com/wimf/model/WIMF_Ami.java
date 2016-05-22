package moun.com.wimf.model;

import java.util.Date;

/**
 * Created by maiga mariam on 25/04/2016.
 */
public class WIMF_Ami extends WIMF_Utilisateur{

    private int idU_snd;
    private int idU_rcv;
    private int etat;
    private Date date_request;
    private Date date_response;

    public WIMF_Ami(int idU_snd, int idU_rcv, int etat, Date date_request, Date date_response)
    {
        super();
      this.idU_snd = idU_snd;
      this.idU_rcv = idU_rcv;
      this.etat = etat;
      this.date_request = date_request;
      this.date_response = date_response;
    }

    public WIMF_Ami(){}

    public WIMF_Ami(int idU, String nom,String tel,double gps_lat,double gps_long, int idU_snd, int idU_rcv, int etat, Date datetimeCrea, Date datetimeMaj)
    {
        this(idU_snd, idU_rcv, etat, datetimeCrea, datetimeMaj);
        this.idU = idU;
        this.nom = nom;
        this.tel = tel;
        this.gps_lat = gps_lat;
        this.gps_long = gps_long;
    }

    public int get_idU_snd()
    {
      return idU_snd;
    }
    public int get_idU_rcv()
    {
      return idU_rcv;
    }
    public int get_etat()
    {
      return etat;
    }
    public Date get_date_request()
    {
      return date_request;
    }
    public Date get_date_response()
    {
      return date_response;
    }

    public void set_idU_snd(int idU_snd)
    {
      this.idU_snd = idU_snd ;
    }
    
    public void set_idU_rcv(int idU_rcv)
    {
      this.idU_rcv = idU_rcv ;
    }
    
    public void set_etat(int etat)
    {
      this.etat = etat ;
    }
    
    public void set_date_request(Date date_request)
    {
      this.date_request = date_request ;
    }
    
    public void set_date_response(Date date_response)
    {
      this.date_response = date_response ;
    }
    


}
