package net.codejava.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import net.codejava.spring.model.*;

import java.sql.SQLException;
import net.codejava.spring.exceptions.*;
import net.codejava.spring.iDAO.*;

public class Normal_DAO implements Normal_IDAO{
	private Connection conexion;
	private static final String URL = "jdbc:postgresql://localhost:5432/SistInf";
    private static final String USUARIO = "diegovalenzuela";
    //private static final String CONTRASEÑA = "tu_contraseña";
	
	public Normal_DAO() throws SQLException {
		    
        this.conexion = getConexion();
    }
    
	private static Connection getConexion() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USUARIO, "");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos PostgreSQL");
            throw e;
        }
    }
	
	@Override
	public void insertar(Normal_DTO u) throws dataAccessExceptions {
		 String sql = "INSERT INTO normal (nombre, apellidos, correo, dni, telefono, contra, iden) VALUES (?, ?, ?, ?, ?, ?, ?)";
		 String sqlUser = "INSERT INTO user (iden) VALUES (?)";
		 
		 try (PreparedStatement stmt = conexion.prepareStatement(sqlUser)) {
		        // Asignamos los valores a los parámetros del PreparedStatement para evitar inyecciones SQl
		        u.setUserid(UUID.randomUUID());
		        stmt.setString(7, u.getUserid().toString()); // Generamos un UUID aleatorio para 'iden'

		        // Ejecutamos la sentencia con executeUpdate(), ya que es un INSERT
		        stmt.executeUpdate();
		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al insertar el usuario: " + e.getMessage(), e);
		    }

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos los valores a los parámetros del PreparedStatement para evitar inyecciones SQL
		        stmt.setString(1, u.getNombre());
		        stmt.setString(2, u.getApellidos());
		        stmt.setString(3, u.getCorreo());
		        stmt.setString(4, u.getDNI());
		        stmt.setInt(5, u.getTel());
		        stmt.setString(6, u.getContra());
		        stmt.setString(7, u.getUserid().toString()); // Generamos un UUID aleatorio para 'iden'

		        // Ejecutamos la sentencia con executeUpdate(), ya que es un INSERT
		        stmt.executeUpdate();
		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al insertar el usuario: " + e.getMessage(), e);
		    }
		}

	@Override
	public void modificar(Normal_DTO u) throws dataAccessExceptions {
		 String sql = "UPDATE normal SET nombre = ?, apellidos = ?, correo = ?, dni = ?, telefono = ?, contra = ? WHERE iden = ?";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos los valores a los parámetros del PreparedStatement de acuerdo a sus tipos
		        stmt.setString(1, u.getNombre());     // String
		        stmt.setString(2, u.getApellidos());  // String
		        stmt.setString(3, u.getCorreo());     // String
		        stmt.setString(4, u.getDNI());        // String
		        stmt.setInt(5, u.getTel());           // Integer
		        stmt.setString(6, u.getContra());    // String
		        stmt.setString(7, u.getUserid().toString());       // String (suponiendo que 'iden' es el identificador único del usuario)

		        // Ejecutamos la sentencia con executeUpdate(), ya que es una modificación (UPDATE)
		        int rowsAffected = stmt.executeUpdate();
		        
		        if (rowsAffected == 0) {
		            throw new dataAccessExceptions("No se encontró el usuario con iden: " + u.getUserid());
		        }

		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al modificar el usuario con iden: " + u.getUserid(), e);
		    }
		
	}

	@Override
	public void eliminar(Normal_DTO u) throws dataAccessExceptions {
		 String sql = "DELETE FROM normal WHERE iden = ?";
		 String sqlUser = "DELETE FROM user WHERE iden = ?";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos los valores a los parámetros del PreparedStatement de acuerdo a sus tipos
		       // String
		        stmt.setString(1, u.getUserid().toString());       // String (suponiendo que 'iden' es el identificador único del usuario)

		        // Ejecutamos la sentencia con executeUpdate(), ya que es una modificación (UPDATE)
		        int rowsAffected = stmt.executeUpdate();
		        
		        if (rowsAffected == 0) {
		            throw new dataAccessExceptions("No se encontró el usuario con iden: " + u.getUserid());
		        }

		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al modificar el usuario con iden: " + u.getUserid(), e);
		    }
		    
		    try (PreparedStatement stmt = conexion.prepareStatement(sqlUser)) {
		        // Asignamos los valores a los parámetros del PreparedStatement de acuerdo a sus tipos
		       // String
		        stmt.setString(1, u.getUserid().toString());       // String (suponiendo que 'iden' es el identificador único del usuario)

		        // Ejecutamos la sentencia con executeUpdate(), ya que es una modificación (UPDATE)
		        int rowsAffected = stmt.executeUpdate();
		        
		        if (rowsAffected == 0) {
		            throw new dataAccessExceptions("No se encontró el usuario con iden: " + u.getUserid());
		        }

		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al modificar el usuario con iden: " + u.getUserid(), e);
		    }
		
	}

	@Override
	public Normal_DTO obtener(UUID IDReser) throws dataAccessExceptions{
		 String sql = "SELECT nombre, apellidos, correo, dni, telefono, contra, iden FROM normal WHERE iden = ?";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos el valor del identificador al PreparedStatement
		        stmt.setString(1, IDReser.toString());  // 'iden' es de tipo long

		        // Ejecutamos la consulta
		        ResultSet rs = stmt.executeQuery();

		        // Si encontramos el resultado, lo convertimos en un objeto Admin_DTO
		        if (rs.next()) {
		            Normal_DTO normal = new Normal_DTO();
		            normal.setNombre(rs.getString("nombre"));
		            normal.setApellidos(rs.getString("apellidos"));
		            normal.setCorreo(rs.getString("correo"));
		            normal.setDNI(rs.getString("dni"));
		            normal.setTel(rs.getInt("telefono"));  // Asumimos que teléfono es un integer
		            normal.setContra(rs.getString("contra"));
		            UUID uuid = UUID.fromString(rs.getString("iden"));
		            normal.setUserid(uuid);  // 'iden' es el identificador único

		            return normal;
		        } else {
		            // Si no encontramos ningún resultado, lanzamos una excepción personalizada
		            throw new dataAccessExceptions("No se encontró el administrador con iden: " + IDReser);
		        }

		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al obtener el administrador con iden: " + IDReser, e);
		    }
	}
	
}