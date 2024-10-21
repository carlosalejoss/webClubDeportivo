package net.codejava.spring.iDAO;

import java.util.UUID;

import net.codejava.spring.model.*;
import net.codejava.spring.exceptions.*;

public interface Normal_IDAO {

    public void insertar (Normal_DTO u) throws dataAccessExceptions;

    public void modificar (Normal_DTO u) throws dataAccessExceptions;

    public void eliminar (Normal_DTO u) throws dataAccessExceptions;
    

    public Normal_DTO obtener (UUID IDReser) throws dataAccessExceptions;
}