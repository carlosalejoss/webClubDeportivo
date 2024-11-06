package org.example.si_gestor_club_deportivo.IDAO;

import java.sql.Date;
import java.util.List;
import org.example.si_gestor_club_deportivo.DTO.Reserva_DTO;

public interface Reserva_IDAO {

    public void insertar (Reserva_DTO u);

    public void eliminar (Reserva_DTO u);
    
    public List<Reserva_DTO> listaUsuarios(Date u);
    
    public Reserva_DTO obtener (Reserva_DTO u);
}
