package net.codejava.spring.model;

import java.sql.Date;
import java.util.UUID;

public class ClasePista_DTO {
    String profesor;
    String nombre;
    UUID idenUser;
    Date timestamp;
    UUID pista;

    public void ClasePista(String p, String n, UUID idenU, Date ts, UUID pi){
        this.profesor = p;
        this.nombre = n;
        this.idenUser = idenU;
        this.timestamp = ts;
        this.pista = pi;
    }

    public void setProfesor(String p){
        this.profesor = p;
    }

    public void setNombre(String n){
        this.nombre = n;
    }

    public void setIdenUser(UUID iu){
        this.idenUser = iu;
    }

    public void setTimeStamp(Date ts){
        this.timestamp = ts;
    }

    public void setPista(UUID p){
        this.pista = p;
    }
    
    public String getProfesor(){
        return profesor;
    }

    public String getNombre(){
       return nombre;
    }

    public UUID getIdenUser(){
        return idenUser;
    }

    public Date getTimeStamp(){
        return timestamp;
    }

    public UUID getPista(){
        return pista;
    }

    @Override
    public String toString(){
        return "Clase:\n" + "Profesor: " + profesor +
                "\nNombre: " + nombre +
                "\nIden User: " + idenUser +
                "\n Fecha y hora: " + timestamp +
                "\nPista: " + pista;
    }
}
