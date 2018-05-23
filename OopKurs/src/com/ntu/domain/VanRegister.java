package com.ntu.domain;


import com.sun.org.apache.bcel.internal.generic.LLOAD;

import java.io.Serializable;
import java.sql.Date;

public class VanRegister implements Serializable {
    private long id;
    private Van van;
    private Date broughtDt;
    private OwnerList ownerList;
    private Date takenDt;

    public VanRegister() {
        super();
    }

    public VanRegister(Van van, Date broughtDt, OwnerList ownerList, Date takenDt) {
        super();
        this.van = van;
        this.broughtDt = broughtDt;
        this.ownerList = ownerList;
        this.takenDt = takenDt;
    }

    public VanRegister(long id, Van van, Date broughtDt, OwnerList ownerList, Date takenDt) {
        super();
        this.id = id;
        this.van = van;
        this.broughtDt = broughtDt;
        this.ownerList = ownerList;
        this.takenDt = takenDt;
    }


    public VanRegister(Van van, Date broughtDt, OwnerList ownerList) {
        super();
        this.van = van;
        this.broughtDt = broughtDt;
        this.ownerList = ownerList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id)  {
        this.id = id;
    }

    public Van getVan() {
        return van;
    }

    public void setVan(Van van) {
        this.van = van;
    }

    public Date getBroughtDt() {
        return broughtDt;
    }

    public void setBroughtDt(Date broughtDt) {
        this.broughtDt = broughtDt;
    }

    public OwnerList getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(OwnerList ownerList) {
        this.ownerList = ownerList;
    }

    public Date getTakenDt() {
        return takenDt;
    }

    public void setTakenDt(Date takenDt) {
        this.takenDt = takenDt;
    }

    @Override
    public String toString() {
        return "VanRegister{" +
                "id=" + id +
                ", van=" + van +
                ", broughtDt=" + broughtDt +
                ", ownerList=" + ownerList +
                ", takenDt=" + takenDt +
                '}';
    }
}
