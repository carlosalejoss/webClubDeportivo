package net.codejava.spring.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.codejava.spring.model.*;

import java.sql.SQLException;
import net.codejava.spring.exceptions.*;
import net.codejava.spring.iDAO.*;


public class ClasePista_DAO implements ClasePista_IDAO{
	private Connection conexion;
	private static final String URL = "jdbc:postgresql://localhost:5432/SistInf";
    private static final String USUARIO = "diegovalenzuela";
    //private static final String CONTRASEÑA = "tu_contraseña";
	
	public ClasePista_DAO() throws SQLException {
		    
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
	public void insertar(ClasePista_DTO u) throws dataAccessExceptions {
		 String sql = "INSERT INTO clasepista (profesor, nombre, useriden, horafecha, pistaiden) VALUES (?, ?, ?, ?, ?)";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos los valores a los parámetros del PreparedStatement para evitar inyecciones SQL
		    	stmt.setString(1, u.getProfesor());
		    	stmt.setString(2, u.getNombre());
		        stmt.setString(3, u.getIdenUser().toString());
		        stmt.setDate(4, u.getTimeStamp());
		        stmt.setString(5, u.getPista().toString()); 

		        // Ejecutamos la sentencia con executeUpdate(), ya que es un INSERT
		        stmt.executeUpdate();
		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al insertar el usuario: " + e.getMessage(), e);
		    }
		}

	@Override
	public void eliminar(ClasePista_DTO u) throws dataAccessExceptions {
		 String sql = "DELETE FROM clasepista WHERE profesor = ? && useriden = ? && horafecha = ? && pistaiden = ?";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos los valores a los parámetros del PreparedStatement de acuerdo a sus tipos
		       // String
		    	stmt.setString(1, u.getProfesor());
		        stmt.setString(2, u.getIdenUser().toString());
		        stmt.setDate(3, u.getTimeStamp());
		        stmt.setString(4, u.getPista().toString());

		        // Ejecutamos la sentencia con executeUpdate(), ya que es una modificación (UPDATE)
		        int rowsAffected = stmt.executeUpdate();
		        
		        if (rowsAffected == 0) {
		            throw new dataAccessExceptions("No se encontró el usuario con iden: " + u.getIdenUser());
		        }

		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al modificar el usuario con iden: " + u.getIdenUser(), e);
		    }
		
	}

	@Override
	public ClasePista_DTO obtener (ClasePista_DTO u) throws dataAccessExceptions {
		 String sql = "SELECT profesor, nombre, useriden, horafecha, pistaiden FROM clasepista WHERE useriden = ? && horafecha = ? && pistaiden = ?";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos el valor del identificador al PreparedStatement
		        stmt.setString(1, u.getIdenUser().toString());  // 'iden' es de tipo long
		        stmt.setDate(2, u.getTimeStamp());
		        stmt.setString(3, u.getPista().toString());

		        // Ejecutamos la consulta
		        ResultSet rs = stmt.executeQuery();

		        // Si encontramos el resultado, lo convertimos en un objeto Admin_DTO
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
		        } else {
		            // Si no encontramos ningún resultado, lanzamos una excepción personalizada
		            throw new dataAccessExceptions("No se encontró el administrador con iden: " + u.getIdenUser());
		        }

		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al obtener el administrador con iden: " + u.getIdenUser(), e);
		    }
	}

	@Override
	public List<ClasePista_DTO> listaUsuarios(Date ts) throws dataAccessExceptions {
	    String sql = "SELECT * FROM reserva WHERE horafecha = ?";
	    List<ClasePista_DTO> pistas = new ArrayList<>();

	    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
	        // Ejecutamos la consulta
	    	stmt.setDate(1, ts);
	        ResultSet rs = stmt.executeQuery();

	        // Iteramos sobre los resultados
	        while (rs.next()) {
	            ClasePista_DTO clasepista = new ClasePista_DTO();
	            clasepista.setProfesor(rs.getString("profesor"));
	            clasepista.setNombre(rs.getString("nombre"));
	            clasepista.setTimeStamp(rs.getDate("horafecha"));  // Obtenemos el tipo de pista
	            UUID uuid = UUID.fromString(rs.getString("useriden"));  // Convertimos el iden a UUID
	            clasepista.setIdenUser(uuid);  // Asignamos el UUID
	            uuid = UUID.fromString(rs.getString("pistaiden"));  // Convertimos el iden a UUID
	            clasepista.setPista(uuid);  // Asignamos el UUID
	            
	            
	            // Añadimos el objeto pista a la lista
	            pistas.add(clasepista);
	        }

	        // Devolvemos la lista de pistas (puede estar vacía si no hay resultados)
	        return pistas;

	    } catch (SQLException e) {
	        // Manejo de excepciones personalizado
	        throw new dataAccessExceptions("Error al obtener la lista de pistas", e);
	    }
	}

}

	