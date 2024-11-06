package org.example.si_gestor_club_deportivo.DAO;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import org.example.si_gestor_club_deportivo.IDAO.Reserva_IDAO;
import org.example.si_gestor_club_deportivo.DTO.Reserva_DTO;

import java.sql.SQLException;

public class Reserva_DAO implements Reserva_IDAO{
	JdbcTemplate jdbcTemplate;
	
	public Reserva_DAO(DataSource dataSource) throws SQLException {
		    
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Override
	public void insertar(Reserva_DTO u) {
		 String sql = "INSERT INTO reserva (useriden, horafecha, pistaiden, precio) VALUES (?, ?, ?, ?)";
		 
		 jdbcTemplate.update(sql, u.getUserId().toString(), u.getTimeStamp(), u.getPista(), u.getPrecio());
	}

	@Override
	public void eliminar(Reserva_DTO u)  {
		 String sql = "DELETE FROM reserva WHERE useriden = ? && horafecha = ? && pistaiden = ?";
		 
		 jdbcTemplate.update(sql, u.getUserId().toString(), u.getTimeStamp(), u.getPista());
	}

	@Override
	public Reserva_DTO obtener(Reserva_DTO u) {
		 String sql = "SELECT useriden, horafecha, pistaiden, precio FROM reserva WHERE useriden = ? && horafecha = ? && pistaiden = ?";
		 
		 return jdbcTemplate.query(sql, new ResultSetExtractor<Reserva_DTO>() {

				@Override
				public Reserva_DTO extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						Reserva_DTO reserva = new Reserva_DTO();
						UUID uuid = UUID.fromString(rs.getString("useriden"));
			            reserva.setUserId(uuid);
			            reserva.setTimeStamp(rs.getDate("horafecha"));
			            reserva.setPista(rs.getString("pistaiden"));
			            reserva.setPrecio(rs.getDouble("precio"));
			            
			            return reserva;
					}
					
					return null;
				}
				
		});
	}

	@Override
	public List<Reserva_DTO> listaUsuarios(Date ts) {
	    String sql = "SELECT * FROM reserva WHERE fechahora = ?";
	    
	    List<Reserva_DTO> listpistas = jdbcTemplate.query(sql, new Object[]{ts}, new RowMapper<Reserva_DTO>() {

			@Override
			public Reserva_DTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				Reserva_DTO reserva = new Reserva_DTO();
	
				    reserva.setTimeStamp(rs.getDate("fechahora"));  // Obtenemos el tipo de pista
		            UUID uuid = UUID.fromString(rs.getString("useriden"));  // Convertimos el iden a UUID
		            reserva.setUserId(uuid);  // Asignamos el UUID
		            reserva.setPista(rs.getString("pistaiden"));  // Asignamos el UUID
		            reserva.setPrecio(rs.getDouble("precio"));
	            
	            return reserva;
			}
			
		});
	    
	    return listpistas;
	}

}

	