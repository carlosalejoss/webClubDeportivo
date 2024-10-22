package net.codejava.spring.iDAO;

import java.util.UUID;

import net.codejava.spring.model.*;
import net.codejava.spring.exceptions.*;

public interface Socio_IDAO {

    void insertarOactualizar (Socio_DTO u) throws dataAccessExceptions;

    void eliminar (Socio_DTO u) throws dataAccessExceptions;

    Socio_DTO obtener (UUID IDSocio) throws dataAccessExceptions;
}