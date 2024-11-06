package org.example.si_gestor_club_deportivo.DTO;

import org.springframework.stereotype.Repository;

import java.util.UUID;

public class Socio_DTO {
	private UUID userId;
    private String nombre;
    private String apellidos;
    private String contra;
    private int tel;
    private String DNI;
    private String correo;
    private String IBAN;
    private int CP;
    

    public Socio_DTO(String n, String a, String c, Integer t, String DNI, String correo, String iban, int cp){
        this.nombre = n;
        this.apellidos = a;
        this.contra = c;
        this.tel = t;
        this.DNI = DNI;
        this.correo = correo;
        this.IBAN = iban;
        this.CP = cp;
        this.userId = null;
    }
    public Socio_DTO(){}

    public void setNombre(String n){
        this.nombre = n;
    }

    public void setApellidos(String a){
        this.apellidos = a;
    }

    public void setContra(String c){
        this.contra = c;
    }

    public void setTel(Integer t){
        this.tel = t;
    }

    public void setDNI(String DNI){
        this.DNI = DNI;
    }

    public void setCorreo(String c){
        this.correo = c;
    }
    
    public void setUserid(UUID c) {
    	this.userId = c;
    }
    
    public void setIBAN(String i) {
    	this.IBAN = i;
    }
    
    public void setCP(int c) {
    	this.CP = c;
    }
    
    public String getNombre(){
        return nombre;
    }

    public String getApellidos(){
       return apellidos;
    }

    public String getContra(){
        return contra;
    }

    public int getTel(){
        return tel;
    }

    public String getDNI(){
        return DNI;
    }

    public String getCorreo(){
        return correo;
    }
    
    public UUID getUserid() {
    	return userId;
    }
    
    public String getIBAN() {
    	return IBAN;
    }
    
    public int getCP() {
    	return CP;
    }

    public String toString(){
        return "Administrador:\n" + "Nombre: " + nombre + 
                "\nApellidos: " + apellidos +
                "\nTelefono: " + tel +
                "\nDNI: " + DNI +
                "\nCorreo: " + correo;
    }
}