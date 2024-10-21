package net.codejava.spring.iDAO;

import java.sql.Date;
import java.util.List;
import net.codejava.spring.model.*;
import net.codejava.spring.exceptions.*;

public interface Reserva_IDAO {

    public void insertar (Reserva_DTO u) throws dataAccessExceptions;

    public void eliminar (Reserva_DTO u) throws dataAccessExceptions;
    
    public List<Reserva_DTO> listaUsuarios(Date u);
    
    public Reserva_DTO obtener (Reserva_DTO u) throws dataAccessExceptions;
}
