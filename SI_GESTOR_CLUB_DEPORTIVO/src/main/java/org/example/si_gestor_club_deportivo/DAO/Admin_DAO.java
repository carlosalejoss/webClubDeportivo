package org.example.si_gestor_club_deportivo.DAO;

import java.sql.ResultSet;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.SQLException;
import org.example.si_gestor_club_deportivo.DTO.Admin_DTO;
import org.example.si_gestor_club_deportivo.IDAO.Admin_iDao;
import org.springframework.stereotype.Repository;

@Repository
public class Admin_DAO implements Admin_iDao{
	
    private JdbcTemplate jdbcTemplate;
	
	public Admin_DAO(DataSource dataSource) throws SQLException {
		    
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
	
	@Override
	public void insertarOactualizar(Admin_DTO u) {
		 
		 if(u.getUserid() != null) {
			 String sql = "UPDATE Admin SET nombre = ?, apellidos = ?, correo = ?, dni = ?, telefono = ?, contra = ? WHERE iden = ?";
			 jdbcTemplate.update(sql, u.getNombre(), u.getApellidos(), u.getCorreo(), 
					 				u.getDNI(), u.getTel(), u.getContra(), u.getUserid().toString());
		 }
		 else {
			 String sql = "INSERT INTO admin (nombre, apellidos, correo, dni, telefono, contra, iden) VALUES (?, ?, ?, ?, ?, ?, ?)";
			 String sqlUser = "INSERT INTO user (iden) VALUES (?)";
			 
			 u.setUserid(UUID.randomUUID());
			 jdbcTemplate.update(sqlUser, u.getUserid());
			 jdbcTemplate.update(sql, u.getNombre(), u.getApellidos(), u.getCorreo(), 
					 				u.getDNI(), u.getTel(), u.getContra(), u.getUserid().toString());
		 }
		 
	}

	@Override
	public void eliminar(Admin_DTO u) {
		 String sql = "DELETE FROM admin WHERE iden = ?";
		 String sqlUser = "DELETE FROM user WHERE iden = ?";

		 jdbcTemplate.update(sql, u.getUserid().toString());
		 jdbcTemplate.update(sqlUser, u.getUserid().toString());
		
	}
	
	@Override
	public Admin_DTO obtener(UUID IDReser) {
		 String sql = "SELECT nombre, apellidos, correo, dni, telefono, contra, iden FROM Admin WHERE iden = ?";
		 return jdbcTemplate.query(sql, new ResultSetExtractor<Admin_DTO>() {

			@Override
			public Admin_DTO extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					Admin_DTO admin = new Admin_DTO();
					UUID uuid = UUID.fromString(rs.getString("iden"));
					admin.setUserid(uuid);
					admin.setNombre(rs.getString("nombre"));
					admin.setApellidos(rs.getString("apellidos"));
					admin.setCorreo(rs.getString("correo"));
					admin.setDNI(rs.getString("dni"));
					admin.setTel(rs.getInt("telefono"));
					admin.setContra(rs.getString("contra"));
					return admin;
				}
				
				return null;
			}
			
		 });
		   
	}
	
}