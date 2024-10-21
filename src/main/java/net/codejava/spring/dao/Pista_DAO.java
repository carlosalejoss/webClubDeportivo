package net.codejava.spring.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.codejava.spring.model.*;

import java.sql.SQLException;
import net.codejava.spring.exceptions.*;
import net.codejava.spring.iDAO.*;

public class Pista_DAO implements Pista_IDAO{
	private Connection conexion;
	private static final String URL = "jdbc:postgresql://localhost:5432/SistInf";
    private static final String USUARIO = "diegovalenzuela";
    //private static final String CONTRASEÑA = "tu_contraseña";
	
	public Pista_DAO() throws SQLException {
		    
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
	public void insertar(Pista_DTO u) throws dataAccessExceptions {
		 String sql = "INSERT INTO pista (tipo, nombre) VALUES (?, ?)";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos los valores a los parámetros del PreparedStatement para evitar inyecciones SQL
		        stmt.setString(1, u.getTipo());
		        u.setUserid(u.getUserid());
		        stmt.setString(2, u.getUserid().toString()); // Generamos un UUID aleatorio para 'iden'

		        // Ejecutamos la sentencia con executeUpdate(), ya que es un INSERT
		        stmt.executeUpdate();
		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al insertar el usuario: " + e.getMessage(), e);
		    }
		}

	@Override
	public void eliminar(Pista_DTO u) throws dataAccessExceptions {
		 String sql = "DELETE FROM pista WHERE nombre = ?";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos los valores a los parámetros del PreparedStatement de acuerdo a sus tipos
		       // String
		        stmt.setString(1, u.getUserid());       // String (suponiendo que 'iden' es el identificador único del usuario)

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
	public Pista_DTO obtener(String s) throws dataAccessExceptions {
		 String sql = "SELECT tipo, iden FROM pista WHERE nombre = ?";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos el valor del identificador al PreparedStatement
		        stmt.setString(1, s);  // 'iden' es de tipo long

		        // Ejecutamos la consulta
		        ResultSet rs = stmt.executeQuery();

		        // Si encontramos el resultado, lo convertimos en un objeto Admin_DTO
		        if (rs.next()) {
		            Pista_DTO normal = new Pista_DTO();
		            normal.setTipo(rs.getString("tipo"));
		            normal.setUserid(rs.getString("iden"));  // 'iden' es el identificador único

		            return normal;
		        } else {
		            // Si no encontramos ningún resultado, lanzamos una excepción personalizada
		            throw new dataAccessExceptions("No se encontró el administrador con iden: " + s);
		        }

		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al obtener el administrador con iden: " + s, e);
		    }
	}

	@Override
	public List<Pista_DTO> listaUsuarios() throws dataAccessExceptions {
	    String sql = "SELECT * FROM pista";
	    List<Pista_DTO> pistas = new ArrayList<>();

	    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
	        // Ejecutamos la consulta
	        ResultSet rs = stmt.executeQuery();

	        // Iteramos sobre los resultados
	        while (rs.next()) {
	            Pista_DTO pista = new Pista_DTO();
	            pista.setTipo(rs.getString("tipo"));  // Obtenemos el tipo de pista
	            pista.setUserid(rs.getString("iden"));  // Asignamos el UUID

	            // Añadimos el objeto pista a la lista
	            pistas.add(pista);
	        }

	        // Devolvemos la lista de pistas (puede estar vacía si no hay resultados)
	        return pistas;

	    } catch (SQLException e) {
	        // Manejo de excepciones personalizado
	        throw new dataAccessExceptions("Error al obtener la lista de pistas", e);
	    }
	}

}

	