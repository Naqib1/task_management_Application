/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task.management.application;

/**
 *
 * @author A.Diaa
 */
import java.sql.*;

public class DatabaseConnectionProxy implements DatabaseConnection {
    private RealDatabaseConnection realDatabaseConnection;

    @Override
    public Connection getConnection() throws SQLException {
        if (realDatabaseConnection == null) {
            realDatabaseConnection = new RealDatabaseConnection();
        }
        return realDatabaseConnection.getConnection();
    }
}
