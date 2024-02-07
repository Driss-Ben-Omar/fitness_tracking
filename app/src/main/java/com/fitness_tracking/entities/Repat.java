package com.fitness_tracking.entities;

import java.util.Date;

public class Repat {

    Long id;

    Long idProduit;

    Date date;

    public Repat(Long id, Long idProduit, Date date) {
        this.id = id;
        this.idProduit = idProduit;
        this.date = date;
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
