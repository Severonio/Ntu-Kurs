package com.ntu.domain;

import java.io.Serializable;

public class Van  {


    private long id;
    private String title;
    private String customer;
    private int made;
    private int weight;

    public Van() {
        super();
    }

    public Van(String title, String customer, int made, int weight) {
        super();
        this.title = title;
        this.customer = customer;
        this.made = made;
        this.weight = weight;
    }

    public Van(long id, String title, String customer, int made, int weight) {
        super();
        this.id = id;
        this.title = title;
        this.customer = customer;
        this.made = made;
        this.weight = weight;
    }

    //������� �� �������
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public int getMade() {
        return made;
    }
    public void setMade(int made) {
        this.made = made;
    }
    public int getWeight() {
        return weight;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }
    @Override
    public String toString() {
        return "(id=" + id + ") " + title + ", " + customer ;
    }






}
