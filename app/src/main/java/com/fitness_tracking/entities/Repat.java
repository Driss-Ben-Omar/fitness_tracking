package com.fitness_tracking.entities;

import java.util.Date;

public class Repat {

    Long id;

    Long idProduit;

    double weight;

    Date date;

    public Repat(Long id, Long idProduit, double weight, Date date, Long idUser) {
        this.id = id;
        this.idProduit = idProduit;
        this.weight = weight;
        this.date = date;
        this.idUser = idUser;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    Long idUser;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }



    public Repat() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
