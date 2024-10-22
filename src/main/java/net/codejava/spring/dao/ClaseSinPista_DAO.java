package net.codejava.spring.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;


import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import net.codejava.spring.model.*;
import java.sql.SQLException;
import net.codejava.spring.exceptions.*;
import net.codejava.spring.iDAO.*;


public class ClaseSinPista_DAO implements ClaseSinPista_IDAO{
	JdbcTemplate jdbcTemplate;
	
	public ClaseSinPista_DAO(DataSource dataSource) throws SQLException {
		    
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	@Override
	public void insertar(ClaseSinPista_DTO u) throws dataAccessExceptions{
		 String sql = "INSERT INTO clasesinpista (profesor, nombre, sala, fechahora) VALUES (?, ?, ?, ?)";
		 
		 jdbcTemplate.update(sql, u.getProfesor(), u.getNombre(), u.getSala(), u.getSala(), u.getTimeStamp());
		}

	@Override
	public void eliminar(ClaseSinPista_DTO u) throws dataAccessExceptions {
		 String sql = "DELETE FROM clasesinpista WHERE profesor = ? && fechahora = ? && sala = ?";
		 
		 jdbcTemplate.update(sql, u.getProfesor(), u.getTimeStamp(), u.getSala());
	}

	@Override
	public ClaseSinPista_DTO obtener (ClaseSinPista_DTO u) throws dataAccessExceptions {
		 String sql = "SELECT profesor, nombre, fechahora, sala FROM clasesinpista WHERE sala = ? && fechahora = ? && profesor = ?";
		 
		 return jdbcTemplate.query(sql, new ResultSetExtractor<ClaseSinPista_DTO>() {

				@Override
				public ClaseSinPista_DTO extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						ClaseSinPista_DTO clasesinpista = new ClaseSinPista_DTO();
						 clasesinpista.setProfesor(rs.getString("profesor"));
				            clasesinpista.setNombre(rs.getString("nombre"));
				            clasesinpista.setSala(rs.getString("sala"));  // 'iden' es el identificador único
				            clasesinpista.setTimeStamp(rs.getDate("fechahora"));
			            
			            return clasesinpista;
					}
					
					return null;
				}
				
		});
	}

	@Override
	public List<ClaseSinPista_DTO> listaUsuarios(Date ts) throws dataAccessExceptions {
	    String sql = "SELECT * FROM clasesinpista WHERE fechahora = ?";
	    
	    List<ClaseSinPista_DTO> listpistas = jdbcTemplate.query(sql, new Object[]{ts}, new RowMapper<ClaseSinPista_DTO>() {

			@Override
			public ClaseSinPista_DTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ClaseSinPista_DTO clasesinpista = new ClaseSinPista_DTO();
	
				clasesinpista.setProfesor(rs.getString("profesor"));
	            clasesinpista.setNombre(rs.getString("nombre"));
	            clasesinpista.setTimeStamp(rs.getDate("fechahora"));
				clasesinpista.setSala(rs.getString("sala"));
				return clasesinpista;
			}
			
		});
		
		return listpistas;
	}

}

	