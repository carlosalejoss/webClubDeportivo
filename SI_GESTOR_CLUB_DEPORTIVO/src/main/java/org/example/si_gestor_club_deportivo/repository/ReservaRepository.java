package org.example.si_gestor_club_deportivo.repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import org.example.si_gestor_club_deportivo.dto.ReservaDTO;

import java.sql.SQLException;

public class ReservaRepository implements ReservaRepository {
	JdbcTemplate jdbcTemplate;
	
	public ReservaRepository(DataSource dataSource) throws SQLException {
		    
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Override
	public void insertar(ReservaDTO u) {
		 String sql = "INSERT INTO reserva (useriden, horafecha, pistaiden, precio) VALUES (?, ?, ?, ?)";
		 
		 jdbcTemplate.update(sql, u.getUserId().toString(), u.getTimeStamp(), u.getPista(), u.getPrecio());
	}

	@Override
	public void eliminar(ReservaDTO u)  {
		 String sql = "DELETE FROM reserva WHERE useriden = ? && horafecha = ? && pistaiden = ?";
		 
		 jdbcTemplate.update(sql, u.getUserId().toString(), u.getTimeStamp(), u.getPista());
	}

	@Override
	public ReservaDTO obtener(ReservaDTO u) {
		 String sql = "SELECT useriden, horafecha, pistaiden, precio FROM reserva WHERE useriden = ? && horafecha = ? && pistaiden = ?";
		 
		 return jdbcTemplate.query(sql, new ResultSetExtractor<ReservaDTO>() {

				@Override
				public ReservaDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						ReservaDTO reserva = new ReservaDTO();
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
	public List<ReservaDTO> listaUsuarios(Date ts) {
	    String sql = "SELECT * FROM reserva WHERE fechahora = ?";
	    
	    List<ReservaDTO> listpistas = jdbcTemplate.query(sql, new Object[]{ts}, new RowMapper<ReservaDTO>() {

			@Override
			public ReservaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ReservaDTO reserva = new ReservaDTO();
	
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

	