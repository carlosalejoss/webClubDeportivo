package org.example.si_gestor_club_deportivo.repository;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import org.example.si_gestor_club_deportivo.dto.PistaDTO;

import java.sql.SQLException;
import org.springframework.stereotype.Repository;

@Repository
public class PistaRepository implements PistaRepository {
	JdbcTemplate jdbcTemplate;
	
	public PistaRepository(DataSource dataSource) throws SQLException {
		    
       this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	
	@Override
	public void insertar(PistaDTO u) {
		 String sql = "INSERT INTO pista (tipo, nombre) VALUES (?, ?)";
		 
		 jdbcTemplate.update(sql, u.getTipo(), u.getUserid().toString());
		}

	@Override
	public void eliminar(PistaDTO u)  {
		 String sql = "DELETE FROM pista WHERE nombre = ?";
		 
		 jdbcTemplate.update(sql, u.getUserid());
	}

	@Override
	public PistaDTO obtener(String s)  {
		 String sql = "SELECT tipo, nombre FROM pista WHERE nombre = ?";
		 
		 return jdbcTemplate.query(sql, new ResultSetExtractor<PistaDTO>() {

				@Override
				public PistaDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						PistaDTO pista = new PistaDTO();
						pista.setTipo(rs.getString("tipo"));
			            pista.setUserid(rs.getString("nombre"));  // 'iden' es el identificador único
			            
			            return pista;
					}
					
					return null;
				}
				
		});
	}

	@Override
	public List<PistaDTO> listaUsuarios() {
	    String sql = "SELECT * FROM pista";
	    
	    List<PistaDTO> listpistas = jdbcTemplate.query(sql, new RowMapper<PistaDTO>() {

			@Override
			public PistaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				PistaDTO pista = new PistaDTO();
	
				pista.setTipo(rs.getString("tipo"));  // Obtenemos el tipo de pista
	            pista.setUserid(rs.getString("nombre"));  // Asignamos el UUID
	            
	            return pista;
			}
			
		});
	    
	    return listpistas;
	}

}

	