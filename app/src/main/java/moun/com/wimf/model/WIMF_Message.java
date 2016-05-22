package moun.com.wimf.model;

import java.util.Date;

/**
 * Created by maiga mariam on 25/04/2016.
 */
public class WIMF_Message {


  private int idMsg;
  private String valeur;
  private String tel_snd;
  private String tel_rcv;
  private int etat;
  private Date date_create;
  private Date date_open;


   public WIMF_Message(int idMsg, String valeur, String tel_snd, String tel_rcv, int etat, Date date_create, Date date_open)
  {
    this.idMsg = idMsg;
    this.valeur = valeur;
    this.tel_snd = tel_snd;
    this.tel_rcv = tel_rcv;
    this.etat = etat;
    this.date_create = date_create;
    this.date_open = date_open;
  }


  public int get_idMsg()
  {
    return idMsg;
  }

  public String get_valeur()
  {
    return valeur;
  }

  public String get_tel_snd()
  {
    return tel_snd;
  }

  public String get_tel_rcv()
  {
    return tel_rcv;
  }

  public int get_etat()
  {
    return etat;
  }

  public Date get_date_create()
  {
    return date_create;
  }

  public Date get_date_open()
  {
    return date_open;
  }


  public void set_idMsg(int idMsg)
  {
    this.idMsg = idMsg;
  }

  public void set_valeur(String valeur)
  {
    this.valeur = valeur;
  }

  public void set_tel_snd(String tel_snd)
  {
    this.tel_snd = tel_snd;
  }

  public void set_tel_rcv(String tel_rcv)
  {
    this.tel_rcv = tel_rcv;
  }

  public void set_etat(int etat)
  {
    this.etat = etat;
  }

  public void set_date_create(Date date_create)
  {
    this.date_create = date_create;
  }

  public void set_date_open(Date date_open)
  {
    this.date_open = date_open;
  }



}
