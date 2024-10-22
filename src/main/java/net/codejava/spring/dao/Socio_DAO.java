package net.codejava.spring.dao;

import java.sql.ResultSet;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.SQLException;
import net.codejava.spring.model.*;
import net.codejava.spring.exceptions.*;
import net.codejava.spring.iDAO.*;

public class Socio_DAO implements Socio_IDAO{
	JdbcTemplate jdbcTemplate;
	
	public Socio_DAO(DataSource dataSource) throws SQLException {
		    
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
	@Override
	public void insertarOactualizar(Socio_DTO u) throws dataAccessExceptions{
		 
		 if(u.getUserid() != null) {
			 String sql = "UPDATE socio SET nombre = ?, apellidos = ?, correo = ?, dni = ?, telefono = ?, contra = ?, iban = ?, cp = ? WHERE iden = ?";
			 jdbcTemplate.update(sql, u.getNombre(), u.getApellidos(), u.getCorreo(), 
					 				u.getDNI(), u.getTel(), u.getContra(), u.getIBAN(),u.getCP(), u.getUserid().toString());
		 }
		 else {
			 String sql = "INSERT INTO socio (nombre, apellidos, correo, dni, telefono, contra, iden, iban, cp) VALUES (?, ?, ?, ?, ?, ?, ?, ? ?)";
			 String sqlUser = "INSERT INTO user (iden) VALUES (?)";
			 
			 u.setUserid(UUID.randomUUID());
			 jdbcTemplate.update(sqlUser, u.getUserid());
			 jdbcTemplate.update(sql, u.getNombre(), u.getApellidos(), u.getCorreo(), 
					 				u.getDNI(), u.getTel(), u.getContra(), u.getUserid().toString(), u.getIBAN(), u.getCP());
		 }
	}
	
	@Override
	public void eliminar(Socio_DTO u) throws dataAccessExceptions {
		 String sql = "DELETE FROM socio WHERE iden = ?";
		 String sqlUser = "DELETE FROM user WHERE iden = ?";
		 
		 jdbcTemplate.update(sql, u.getUserid());
		 jdbcTemplate.update(sqlUser, u.getUserid());
	}

	@Override
	public Socio_DTO obtener(UUID IDReser) throws dataAccessExceptions {
		 String sql = "SELECT nombre, apellidos, correo, dni, telefono, contra, iden, iban, cp FROM socio WHERE iden = ?";
		 
		 return jdbcTemplate.query(sql, new ResultSetExtractor<Socio_DTO>() {

				@Override
				public Socio_DTO extractData(ResultSet rs) throws SQLException, DataAccessException {
					if (rs.next()) {
						Socio_DTO socio = new Socio_DTO();
						UUID uuid = UUID.fromString(rs.getString("iden"));
						socio.setUserid(uuid);
						socio.setNombre(rs.getString("nombre"));
						socio.setApellidos(rs.getString("apellidos"));
						socio.setCorreo(rs.getString("correo"));
						socio.setDNI(rs.getString("dni"));
						socio.setTel(rs.getInt("telefono"));
						socio.setContra(rs.getString("contra"));
						socio.setIBAN(rs.getString("iban"));
			            socio.setCP(rs.getInt("cp"));
						return socio;
					}
					
					return null;
				}
				
			 });
	}
	
}