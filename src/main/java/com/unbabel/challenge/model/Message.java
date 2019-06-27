package com.unbabel.challenge.model;

import javax.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 100)
    private String msg;
    private String lan;
    private String con;
    private String status;
    private String oriLan;
    
    public Message() {
    }

    public Message(String msg, String lan, String con, String status, String oriLan) {
        this.msg = msg;
        this.lan = lan;
        this.con = con;
        this.status = status;
        this.oriLan = oriLan;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public String getCon() {
        return con;
    }

    public void setCon(String con) {
        this.con = con;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOriLan() {
        return oriLan;
    }

    public void setOriLan(String oriLan) {
        this.oriLan = oriLan;
    }
}
