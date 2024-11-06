package org.example.si_gestor_club_deportivo.IDAO;

import java.util.List;
import org.example.si_gestor_club_deportivo.DTO.Pista_DTO;

public interface Pista_IDAO {

    public void insertar (Pista_DTO u);

    public void eliminar (Pista_DTO u);
    
    public List<Pista_DTO> listaUsuarios();
    
    public Pista_DTO obtener (String s);
}