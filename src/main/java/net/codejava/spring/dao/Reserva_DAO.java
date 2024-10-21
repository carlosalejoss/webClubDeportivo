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

public class Reserva_DAO implements Reserva_IDAO{
	private Connection conexion;
	private static final String URL = "jdbc:postgresql://localhost:5432/SistInf";
    private static final String USUARIO = "diegovalenzuela";
    //private static final String CONTRASEÑA = "tu_contraseña";
	
	public Reserva_DAO() throws SQLException {
		    
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
	public void insertar(Reserva_DTO u) throws dataAccessExceptions {
		 String sql = "INSERT INTO reserva (useriden, horafecha, pistaiden, precio) VALUES (?, ?, ?, ?)";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos los valores a los parámetros del PreparedStatement para evitar inyecciones SQL
		        stmt.setString(1, u.getUserId().toString());
		        stmt.setDate(2, u.getTimeStamp());
		        stmt.setString(3, u.getPista()); 
		        stmt.setDouble(4, u.getPrecio());

		        // Ejecutamos la sentencia con executeUpdate(), ya que es un INSERT
		        stmt.executeUpdate();
		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al insertar el usuario: " + e.getMessage(), e);
		    }
		}

	@Override
	public void eliminar(Reserva_DTO u) throws dataAccessExceptions {
		 String sql = "DELETE FROM reserva WHERE useriden = ? && horafecha = ? && pistaiden = ?";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos los valores a los parámetros del PreparedStatement de acuerdo a sus tipos
		       // String
		        stmt.setString(1, u.getUserId().toString());
		        stmt.setDate(2, u.getTimeStamp());
		        stmt.setString(3, u.getPista().toString());

		        // Ejecutamos la sentencia con executeUpdate(), ya que es una modificación (UPDATE)
		        int rowsAffected = stmt.executeUpdate();
		        
		        if (rowsAffected == 0) {
		            throw new dataAccessExceptions("No se encontró el usuario con iden: " + u.getUserId());
		        }

		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al modificar el usuario con iden: " + u.getUserId(), e);
		    }
		
	}

	@Override
	public Reserva_DTO obtener(Reserva_DTO u) throws dataAccessExceptions{
		 String sql = "SELECT useriden, horafecha, pistaiden, precio FROM reserva WHERE useriden = ? && horafecha = ? && pistaiden = ?";

		    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
		        // Asignamos el valor del identificador al PreparedStatement
		        stmt.setString(1, u.getUserId().toString());  // 'iden' es de tipo long
		        stmt.setDate(2, u.getTimeStamp());
		        stmt.setString(3, u.getPista().toString());

		        // Ejecutamos la consulta
		        ResultSet rs = stmt.executeQuery();

		        // Si encontramos el resultado, lo convertimos en un objeto Admin_DTO
		        if (rs.next()) {
		            Reserva_DTO reserva = new Reserva_DTO();
		            UUID uuid = UUID.fromString(rs.getString("useriden"));
		            reserva.setUserId(uuid);  // 'iden' es el identificador único
		            reserva.setTimeStamp(rs.getDate("horafecha"));
		            reserva.setPista(rs.getString("pistaiden"));
		            reserva.setPrecio(rs.getDouble("precio"));

		            return reserva;
		        } else {
		            // Si no encontramos ningún resultado, lanzamos una excepción personalizada
		            throw new dataAccessExceptions("No se encontró el administrador con iden: " + u.getUserId());
		        }

		    } catch (SQLException e) {
		        // Manejo de excepciones personalizado
		        throw new dataAccessExceptions("Error al obtener el administrador con iden: " + u.getUserId(), e);
		    }
	}

	@Override
	public List<Reserva_DTO> listaUsuarios(Date ts) throws dataAccessExceptions {
	    String sql = "SELECT * FROM reserva WHERE fechahora = ?";
	    List<Reserva_DTO> pistas = new ArrayList<>();

	    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
	        // Ejecutamos la consulta
	    	stmt.setDate(1, ts);
	        ResultSet rs = stmt.executeQuery();

	        // Iteramos sobre los resultados
	        while (rs.next()) {
	            Reserva_DTO reserva = new Reserva_DTO();
	            reserva.setTimeStamp(rs.getDate("fechahora"));  // Obtenemos el tipo de pista
	            UUID uuid = UUID.fromString(rs.getString("useriden"));  // Convertimos el iden a UUID
	            reserva.setUserId(uuid);  // Asignamos el UUID
	            reserva.setPista(rs.getString("pistaiden"));  // Asignamos el UUID
	            reserva.setPrecio(rs.getDouble("precio"));
	            
	            // Añadimos el objeto pista a la lista
	            pistas.add(reserva);
	        }

	        // Devolvemos la lista de pistas (puede estar vacía si no hay resultados)
	        return pistas;

	    } catch (SQLException e) {
	        // Manejo de excepciones personalizado
	        throw new dataAccessExceptions("Error al obtener la lista de pistas", e);
	    }
	}

}

	