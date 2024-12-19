/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package task.management.application;

/**
 *
 * @author A.Diaa
 */
import java.sql.*;

public interface DatabaseConnection {
    Connection getConnection() throws SQLException;
}
