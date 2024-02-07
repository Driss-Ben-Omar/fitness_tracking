package com.fitness_tracking.entities;

import java.util.Date;

public class Workout {

    Long id;

    Long idExercice;

    double weight;

    int serie;

    int repetition;

    Date date;

    Long idUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdExercice() {
        return idExercice;
    }

    public void setIdExercice(Long idExercice) {
        this.idExercice = idExercice;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Workout(Long id, Long idExercice, double weight, int serie, int repetition, Date date, Long idUser) {
        this.id = id;
        this.idExercice = idExercice;
        this.weight = weight;
        this.serie = serie;
        this.repetition = repetition;
        this.date = date;
        this.idUser = idUser;
    }

    public Workout() {
        super();
    }

}
