package com.example.bdd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Planete {
    private static int uuid = 1;

    @PrimaryKey
    public int uid;

    @ColumnInfo(name = "name")
    private String nom;

    @ColumnInfo(name = "size")
    private Integer taille;

    Planete(String nom, Integer taille){
        this.uid = uuid;
        uuid += 1;
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