package net.codejava.spring.iDAO;

import net.codejava.spring.exceptions.*;
import java.sql.Date;
import java.util.List;
import net.codejava.spring.model.*;

public interface ClaseSinPista_IDAO {

    public void insertar (ClaseSinPista_DTO u) throws dataAccessExceptions;

    public void eliminar (ClaseSinPista_DTO u) throws dataAccessExceptions;
    
    public List<ClaseSinPista_DTO> listaUsuarios(Date d) throws dataAccessExceptions;
    
    public ClaseSinPista_DTO obtener (ClaseSinPista_DTO d) throws dataAccessExceptions;
}