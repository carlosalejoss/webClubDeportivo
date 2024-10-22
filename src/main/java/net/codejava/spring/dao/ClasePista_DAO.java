package net.codejava.spring.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import net.codejava.spring.model.*;

import java.sql.SQLException;
import net.codejava.spring.exceptions.*;
import net.codejava.spring.iDAO.*;


public class ClasePista_DAO implements ClasePista_IDAO{
	 private JdbcTemplate jdbcTemplate;
	
	public ClasePista_DAO(DataSource dataSource) throws SQLException {
		    
      this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	@Override
	public void insertar(ClasePista_DTO u) throws dataAccessExceptions {
		 String sql = "INSERT INTO clasepista (profesor, nombre, useriden, horafecha, pistaiden) VALUES (?, ?, ?, ?, ?)";
		 
		 jdbcTemplate.update(sql, u.getProfesor(), u.getNombre(), u.getIdenUser().toString(), u.getTimeStamp(), u.getPista());    
	}

	@Override
	public void eliminar(ClasePista_DTO u) throws dataAccessExceptions {
		 String sql = "DELETE FROM clasepista WHERE profesor = ? && useriden = ? && horafecha = ? && pistaiden = ?";
		 
		 jdbcTemplate.update(sql, u.getProfesor(), u.getIdenUser().toString(), u.getTimeStamp(), u.getPista());
	}

	@Override
	public ClasePista_DTO obtener (ClasePista_DTO u) throws dataAccessExceptions {
		 String sql = "SELECT profesor, nombre, useriden, horafecha, pistaiden FROM clasepista WHERE useriden = ? && horafecha = ? && pistaiden = ?";
		 
		 return jdbcTemplate.query(sql, new ResultSetExtractor<ClasePista_DTO>() {

				@Override
				public ClasePista_DTO extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						ClasePista_DTO clasepista = new ClasePista_DTO();
						clasepista.setProfesor(rs.getString("profesor"));
			            clasepista.setNombre(rs.getString("nombre"));
			            UUID uuid = UUID.fromString(rs.getString("useriden"));
			            clasepista.setIdenUser(uuid);  // 'iden' es el identificador único
			            clasepista.setTimeStamp(rs.getDate("horafecha"));
			            UUID uuid2 = UUID.fromString(rs.getString("pistaiden"));
			            clasepista.setIdenUser(uuid2);
			            
			            return clasepista;
					}
					
					return null;
				}
				
		});
	}

	
	@Override
	public List<ClasePista_DTO> listaUsuarios(Date ts) throws dataAccessExceptions {
	    String sql = "SELECT * FROM reserva WHERE horafecha = ?";
	    
	    List<ClasePista_DTO> listpistas = jdbcTemplate.query(sql, new Object[]{ts}, new RowMapper<ClasePista_DTO>() {

			@Override
			public ClasePista_DTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClasePista_DTO clasepista = new ClasePista_DTO();
	
				clasepista.setProfesor(rs.getString("profesor"));
	            clasepista.setNombre(rs.getString("nombre"));
	            clasepista.setTimeStamp(rs.getDate("horafecha"));  // Obtenemos el tipo de pista
	            UUID uuid = UUID.fromString(rs.getString("useriden"));  // Convertimos el iden a UUID
	            clasepista.setIdenUser(uuid);  // Asignamos el UUID
	            uuid = UUID.fromString(rs.getString("pistaiden"));  // Convertimos el iden a UUID
	            clasepista.setPista(uuid);  // Asignamos el UUID
				
				return clasepista;
			}
			
		});
		
		return listpistas;
	}

}

	