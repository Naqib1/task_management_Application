/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task.management.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static task.management.application.RealDatabaseConnection.DB_PASSWORD;
import static task.management.application.RealDatabaseConnection.DB_URL;
import static task.management.application.RealDatabaseConnection.DB_USER;

/**
 *
 * @author Electronica Care
 */
// SignUp Class
class SingUp extends RealDatabaseConnection{

    public SingUp() {
    // Replace JFrame-based UI with JOptionPane dialogs

    // Prompt for username and password using dialog boxes
    String username = JOptionPane.showInputDialog(null, "Enter Username:", "Signup System", JOptionPane.PLAIN_MESSAGE);
    String password = JOptionPane.showInputDialog(null, "Enter Password:", "Signup System", JOptionPane.PLAIN_MESSAGE);

    if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Please fill out all fields.", "Error", JOptionPane.ERROR_MESSAGE);
    } else {
        User user = new User(username, password);
        User clonedUser = (User) user.clone();

        // Insert into database
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO Users (username, password, role) VALUES (?, ?, 'Employee')";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clonedUser.getUsername());
            preparedStatement.setString(2, clonedUser.getPassword());
            preparedStatement.executeUpdate();

            // Display success message
            JOptionPane.showMessageDialog(null, "User signed up successfully!\nCloned User: " + clonedUser.getUsername(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saving user to database: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

}
