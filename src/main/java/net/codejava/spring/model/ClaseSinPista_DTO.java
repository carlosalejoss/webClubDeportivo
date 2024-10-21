package net.codejava.spring.model;

import java.sql.Date;

public class ClaseSinPista_DTO {
    String profesor;
    String nombre;
    String sala;
    Date timestamp;

    public ClaseSinPista_DTO(String p, String n, String s, Date ts){
        this.profesor = p;
        this.nombre = n;
        this.sala = s;
        this.timestamp = ts;
    }
    
    public ClaseSinPista_DTO(){
       
    }

    public void setProfesor(String p){
        this.profesor = p;
    }

    public void setNombre(String n){
        this.nombre = n;
    }

    public void setSala(String s){
        this.sala = s;
    }

    public void setTimeStamp(Date ts){
        this.timestamp = ts;
    }
    
    public String getProfesor(){
        return profesor;
    }

    public String getNombre(){
       return nombre;
    }

    public String getSala(){
        return sala;
    }

    public Date getTimeStamp(){
        return timestamp;
    }
}

