package net.codejava.spring.iDAO;

import java.util.UUID;

import net.codejava.spring.model.Admin_DTO;
import net.codejava.spring.exceptions.*;

public interface Admin_iDao {

    public void insertar (Admin_DTO u) throws dataAccessExceptions;

    public void modificar (Admin_DTO u) throws dataAccessExceptions;

    public void eliminar (Admin_DTO u) throws dataAccessExceptions;
    

    public Admin_DTO obtener (UUID IDReser) throws dataAccessExceptions;
}