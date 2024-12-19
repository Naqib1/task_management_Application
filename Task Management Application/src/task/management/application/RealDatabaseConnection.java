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

public class RealDatabaseConnection implements DatabaseConnection {
    static final String DB_URL = "jdbc:mysql://localhost:3306/TaskManagementDB";
    static final String DB_USER = "root";
    static final String DB_PASSWORD = "123456";

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
