package com.example.Examen_PMDM_3T_Raul_Villodres;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mascota_table")
public class Mascota {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String raza;
    private String edad;
    private String rutaParcial;

    //Constructor

    public Mascota(String nombre, String raza, String edad, String rutaParcial) {
        this.nombre = nombre;
        this.raza = raza;
        this.edad = edad;
        this.rutaParcial = rutaParcial;
    }


    //Getters


    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRaza() {
        return raza;
    }

    public String getEdad() {
        return edad;
    }

    public String getRutaParcial() {
        return rutaParcial;
    }

    //Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public void setRutaParcial(String rutaParcial) {
        this.rutaParcial = rutaParcial;
    }
}
