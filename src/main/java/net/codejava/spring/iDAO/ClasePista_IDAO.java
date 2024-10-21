package net.codejava.spring.iDAO;

import net.codejava.spring.exceptions.*;
import java.sql.Date;
import java.util.List;

import net.codejava.spring.model.*;

public interface ClasePista_IDAO {

    public void insertar (ClasePista_DTO u) throws dataAccessExceptions;

    public void eliminar (ClasePista_DTO u) throws dataAccessExceptions;
    
    public List<ClasePista_DTO> listaUsuarios(Date d) throws dataAccessExceptions;
    
    public ClasePista_DTO obtener (ClasePista_DTO d) throws dataAccessExceptions;
}