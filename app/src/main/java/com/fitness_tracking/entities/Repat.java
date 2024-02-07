package com.fitness_tracking.entities;

import java.util.Date;

public class Repat {

    Long id;

    Long idProduit;

    Date date;

    Long idUser;

    public Repat(Long id, Long idProduit, Date date, Long idUser) {
        this.id = id;
        this.idProduit = idProduit;
        this.date = date;
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
