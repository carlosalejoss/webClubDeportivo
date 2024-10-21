package net.codejava.spring.model;

import java.sql.Date;
import java.util.UUID;

public class Reserva_DTO {
    UUID userId;
    Date timestamp;
    String idenPista;
    Double precio;

	public Reserva_DTO(UUID u, Date ts, String iP, Double p){
        this.userId = u;
        this.timestamp = ts;
        this.idenPista = iP;
        this.precio = p;
    }

    public Reserva_DTO() {
		
	}

	public void setUserId(UUID u){
        this.userId = u;
    }

    public void setTimeStamp(Date ts){
        this.timestamp = ts;
    }

    public void setPrecio(Double p){
        this.precio = p;
    }
    
    public void setPista(String u){
        this.idenPista = u;
    }
    
    public UUID getUserId(){
        return userId;
    }

    public Date getTimeStamp(){
       return timestamp;
    }

    public Double getPrecio(){
        return precio;
    }
    
    public String getPista(){
        return idenPista;
    }

    @Override
    public String toString(){
        return "Reserva: \n" +
                "Iden Usuario: " + userId +
                "\n Fecha y hora: " + timestamp +
                "\n Pista: " + idenPista +
                "\n Precio: " + precio;
    }

}
