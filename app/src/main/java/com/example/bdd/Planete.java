package com.example.bdd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Planete {
    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "name")
    private String nom;

    @ColumnInfo(name = "size")
    private Integer taille;

    Planete(int uid, String nom, Integer taille){
        this.uid = uid;
        this.nom = nom;
        this.taille = taille;
    }

    public int getUid() {
        return uid;
    }

    public String getNom() {
        return nom;
    }

    public Integer getTaille() {
        return taille;
    }

}