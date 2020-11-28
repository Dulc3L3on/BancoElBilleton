/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.Manejadores.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author phily
 */
public class ManejadorDB {
   private static Connection connection = null;
    private static ManejadorDB connectionDB;

    private ManejadorDB() {
        String path = "jdbc:mysql://localhost:3306/ElBilleton?useSSL=false&serverTimezone=UTC";
        String usuario = "root";
        String contrasenia = "adminL3on@";

        try {            
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(path, usuario, contrasenia);
        }catch (SQLException ex) {
            System.out.println("Error en la base de datos: " + ex.getMessage());            
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException ex) {
           System.out.println("Error: " + ex.getMessage());            
       } 
    }

    public static Connection darConexion() {
        if (connectionDB == null) {
            connectionDB = new ManejadorDB();
        }
        return connection;
    }        
}
