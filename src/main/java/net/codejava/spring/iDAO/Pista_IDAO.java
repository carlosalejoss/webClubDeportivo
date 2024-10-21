package net.codejava.spring.iDAO;

import java.util.List;
import java.util.UUID;

import net.codejava.spring.model.*;
import net.codejava.spring.exceptions.*;

public interface Pista_IDAO {

    public void insertar (Pista_DTO u) throws  dataAccessExceptions;

    public void eliminar (Pista_DTO u) throws dataAccessExceptions;
    
    public List<Pista_DTO> listaUsuarios() throws dataAccessExceptions;
    
    public Pista_DTO obtener (String s) throws dataAccessExceptions;
}