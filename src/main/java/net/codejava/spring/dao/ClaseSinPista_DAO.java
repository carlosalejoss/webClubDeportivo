package net.codejava.spring.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.codejava.spring.model.*;
import java.sql.SQLException;
import net.codejava.spring.exceptions.*;
import net.codejava.spring.iDAO.*;


public class ClaseSinPista_DAO implements ClaseSinPista_IDAO{
	private Connection conexion;
	private static final String URL = "jdbc:postgresql://localhost:5432/SistInf";
    private static final String USUARIO = "diegovalenzuela";
    //private static final String CONTRASEÑA = "tu_contraseña";
	
	public ClaseSinPista_DAO() throws SQLException {
		    
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
	public void insertar(ClaseSinPista_DTO u) throws dataAccessExceptions{
		 String sql = "INSERT INTO clasesinpista (profesor, nombre, sala, fechahora) VALUES (?, ?, ?, ?)";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos los valores a los parámetros del PreparedStatement para evitar inyecciones SQL
		    	stmt.setString(1, u.getProfesor());
		    	stmt.setString(2, u.getNombre());
		        stmt.setString(3, u.getSala());
		        stmt.setDate(4, u.getTimeStamp()); 

		        // Ejecutamos la sentencia con executeUpdate(), ya que es un INSERT
		        stmt.executeUpdate();
		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al insertar el usuario: " + e.getMessage(), e);
		    }
		}

	@Override
	public void eliminar(ClaseSinPista_DTO u) throws dataAccessExceptions {
		 String sql = "DELETE FROM clasesinpista WHERE profesor = ? && fechahora = ? && sala = ?";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos los valores a los parámetros del PreparedStatement de acuerdo a sus tipos
		       // String
		    	stmt.setString(1, u.getProfesor());
		        stmt.setDate(2, u.getTimeStamp());
		        stmt.setString(3, u.getSala());

		        // Ejecutamos la sentencia con executeUpdate(), ya que es una modificación (UPDATE)
		        int rowsAffected = stmt.executeUpdate();
		        
		        if (rowsAffected == 0) {
		            throw new dataAccessExceptions("No se encontró el usuario con iden: " + u.getNombre());
		        }

		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al modificar el usuario con iden: " + u.getNombre(), e);
		    }
		
	}

	@Override
	public ClaseSinPista_DTO obtener (ClaseSinPista_DTO u) throws dataAccessExceptions {
		 String sql = "SELECT profesor, nombre, fechahora, sala FROM clasesinpista WHERE sala = ? && fechahora = ? && profesor = ?";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos el valor del identificador al PreparedStatement
		        stmt.setString(1, u.getSala().toString());  // 'iden' es de tipo long
		        stmt.setDate(2, u.getTimeStamp());
		        stmt.setString(3, u.getProfesor().toString());

		        // Ejecutamos la consulta
		        ResultSet rs = stmt.executeQuery();

		        // Si encontramos el resultado, lo convertimos en un objeto Admin_DTO
		        if (rs.next()) {
		            ClaseSinPista_DTO clasesinpista = new ClaseSinPista_DTO();
		            clasesinpista.setProfesor(rs.getString("profesor"));
		            clasesinpista.setNombre(rs.getString("nombre"));
		            clasesinpista.setSala(rs.getString("sala"));  // 'iden' es el identificador único
		            clasesinpista.setTimeStamp(rs.getDate("fechahora"));

		            return clasesinpista;
		        } else {
		            // Si no encontramos ningún resultado, lanzamos una excepción personalizada
		            throw new dataAccessExceptions("No se encontró el administrador con iden: " + u.getNombre());
		        }

		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al obtener el administrador con iden: " + u.getNombre(), e);
		    }
	}

	@Override
	public List<ClaseSinPista_DTO> listaUsuarios(Date ts) throws dataAccessExceptions {
	    String sql = "SELECT * FROM clasesinpista WHERE fechahora = ?";
	    List<ClaseSinPista_DTO> pistas = new ArrayList<>();

	    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
	        // Ejecutamos la consulta
	    	stmt.setDate(1, ts);
	        ResultSet rs = stmt.executeQuery();

	        // Iteramos sobre los resultados
	        while (rs.next()) {
	            ClaseSinPista_DTO clasesinpista = new ClaseSinPista_DTO();
	            clasesinpista.setProfesor(rs.getString("profesor"));
	            clasesinpista.setNombre(rs.getString("nombre"));
	            clasesinpista.setTimeStamp(rs.getDate("fechahora"));  // Obtenemos el tipo de pista
	            
	            
	            // Añadimos el objeto pista a la lista
	            pistas.add(clasesinpista);
	        }

	        // Devolvemos la lista de pistas (puede estar vacía si no hay resultados)
	        return pistas;

	    } catch (SQLException e) {
	        // Manejo de excepciones personalizado
	        throw new dataAccessExceptions("Error al obtener la lista de pistas", e);
	    }
	}

}

	