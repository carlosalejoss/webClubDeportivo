package org.example.si_gestor_club_deportivo.DAO;

import java.sql.ResultSet;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import org.example.si_gestor_club_deportivo.DTO.Pista_DTO;
import org.example.si_gestor_club_deportivo.IDAO.Pista_IDAO;

import java.sql.SQLException;
import org.springframework.stereotype.Repository;

@Repository
public class Pista_DAO implements Pista_IDAO{
	JdbcTemplate jdbcTemplate;
	
	public Pista_DAO(DataSource dataSource) throws SQLException {
		    
       this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	
	@Override
	public void insertar(Pista_DTO u) {
		 String sql = "INSERT INTO pista (tipo, nombre) VALUES (?, ?)";
		 
		 jdbcTemplate.update(sql, u.getTipo(), u.getUserid().toString());
		}

	@Override
	public void eliminar(Pista_DTO u)  {
		 String sql = "DELETE FROM pista WHERE nombre = ?";
		 
		 jdbcTemplate.update(sql, u.getUserid());
	}

	@Override
	public Pista_DTO obtener(String s)  {
		 String sql = "SELECT tipo, nombre FROM pista WHERE nombre = ?";
		 
		 return jdbcTemplate.query(sql, new ResultSetExtractor<Pista_DTO>() {

				@Override
				public Pista_DTO extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						Pista_DTO pista = new Pista_DTO();
						pista.setTipo(rs.getString("tipo"));
			            pista.setUserid(rs.getString("nombre"));  // 'iden' es el identificador único
			            
			            return pista;
					}
					
					return null;
				}
				
		});
	}

	@Override
	public List<Pista_DTO> listaUsuarios() {
	    String sql = "SELECT * FROM pista";
	    
	    List<Pista_DTO> listpistas = jdbcTemplate.query(sql, new RowMapper<Pista_DTO>() {

			@Override
			public Pista_DTO mapRow(ResultSet rs, int rowNum) throws SQLException {
				Pista_DTO pista = new Pista_DTO();
	
				pista.setTipo(rs.getString("tipo"));  // Obtenemos el tipo de pista
	            pista.setUserid(rs.getString("nombre"));  // Asignamos el UUID
	            
	            return pista;
			}
			
		});
	    
	    return listpistas;
	}

}

	