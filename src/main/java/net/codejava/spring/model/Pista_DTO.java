package net.codejava.spring.model;

public class Pista_DTO {
    private String idenPista;
    private String tipo;
    
    public Pista_DTO() {
    	
    }

	public Pista_DTO(String t, String d){
        this.tipo = t;
        this.idenPista = d;
    }

    public void setUserid(String s) {
		this.idenPista = s;
		
	}

    public void setTipo(String t){
        this.tipo = t;
    }
    
    public String getUserid() {
		return idenPista;
		
	}

    public String getTipo(){
       return tipo;
    }

    public String toString(){
        return "Pista:\n" + "iden pista: " + idenPista +
                "\n Tipo: " + tipo;
    }
     
}
