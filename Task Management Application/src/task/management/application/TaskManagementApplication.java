/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package task.management.application;

/**
 *
 * @author A.Diaa
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Main Application Class
public class TaskManagementApplication extends JFrame implements ActionListener {

    private static TaskManagementApplication instance;
    private JTextField taskField, assigneeField, deadlineField;
    private JButton addButton, deleteButton, markIncompleteButton, switchButton, signupButton;
    private JComboBox<String> sortComboBox;
    private JTable taskTable;
    private DefaultTableModel tableModel;
    private boolean isAdmin;
    private boolean isEmployee;
    private List<Task> tasks;

    // Singleton Constructor
    private TaskManagementApplication() {
        setTitle("Task Management Application");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tasks = new ArrayList<>();

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("Task:"));
        taskField = new JTextField();
        inputPanel.add(taskField);
        inputPanel.add(new JLabel("Employee Name:"));
        assigneeField = new JTextField();
        inputPanel.add(assigneeField);
        inputPanel.add(new JLabel("Deadline Date (YYYY-MM-DD):"));
        deadlineField = new JTextField();
        inputPanel.add(deadlineField);
        addButton = new JButton("Add Task");
        addButton.addActionListener(this);
        inputPanel.add(addButton);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Control Panel
        sortComboBox = new JComboBox<>(new String[]{"Sort by Task", "Sort by Employee", "Sort by Deadline"});
        sortComboBox.addActionListener(this);
        deleteButton = new JButton("Delete Selected Task");
        deleteButton.addActionListener(this);
        markIncompleteButton = new JButton("Mark Incomplete Tasks as Completed");
        markIncompleteButton.addActionListener(this);

        controlPanel.add(sortComboBox);
        controlPanel.add(deleteButton);
        controlPanel.add(markIncompleteButton);

        // Switch User Panel
        switchButton = new JButton("Switch to Admin/Employee");
        switchButton.addActionListener(this);
        signupButton = new JButton("Sign Up User"); // New button for sign-up page
        signupButton.addActionListener(this);
        signupButton.setVisible(false); // Initially hidden; visible only for admin

        JPanel switchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        switchPanel.add(switchButton);
        switchPanel.add(signupButton);

        // Table Panel
        tableModel = new DefaultTableModel(new Object[]{"Task", "Assignee", "Deadline", "Completed"}, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        taskTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(taskTable);

        // Layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(tableScrollPane, BorderLayout.CENTER);
        getContentPane().add(controlPanel, BorderLayout.SOUTH);
        getContentPane().add(switchPanel, BorderLayout.WEST);

        login();
    }
// Singleton Instance Getter
    public static TaskManagementApplication getInstance() {
        if (instance == null) {
            instance = new TaskManagementApplication();
        }
        return instance;
    }

    private void login() {
        String username = JOptionPane.showInputDialog(this, "Enter Username:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");

        String role = validateLogin(username, password);
        if (role == null) {
            JOptionPane.showMessageDialog(this, "Invalid login. Please try again.");
            System.exit(0);
        }

        isAdmin = role.equals("Admin");
        isEmployee = role.equals("Employee");
        JOptionPane.showMessageDialog(this, "Welcome, " + username + "!");

        taskField.setEditable(isAdmin);
        assigneeField.setEditable(isAdmin);
        deadlineField.setEditable(isAdmin);
        addButton.setEnabled(isAdmin);
        deleteButton.setEnabled(isAdmin);
        markIncompleteButton.setEnabled(isAdmin||isEmployee);
        signupButton.setVisible(isAdmin); // Show sign-up button for admin only

        refreshTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            addTask();
        } else if (e.getSource() == deleteButton) {
            deleteSelectedTask();
        } else if (e.getSource() == sortComboBox) {
            sortTasks();
        } else if (e.getSource() == markIncompleteButton) {
            markSelectedTaskAsIncomplete();
        } else if (e.getSource() == switchButton) {
            switchUser();
        } else if (e.getSource() == signupButton) {
            openSignupPage();
        }
    }

    private void addTask() {
        if (!isAdmin) {
            JOptionPane.showMessageDialog(this, "Only admin can add tasks.");
            return;
        }
        String taskType = JOptionPane.showInputDialog(this, "Enter Task Type (Bug/Feature):");
        String task = taskField.getText().trim();
        String assignee = assigneeField.getText().trim();
        String deadline = deadlineField.getText().trim();
        boolean completed = false;

        if (!task.isEmpty() && !assignee.isEmpty() && !deadline.isEmpty()) {
            // Using Factory to create tasks
            Task newTask = TaskFactory.createTask(taskType, task, assignee, deadline, completed);
            tasks.add(newTask);
            refreshTable();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }
    }

    private void deleteSelectedTask() {
        if (!isAdmin) {
            JOptionPane.showMessageDialog(this, "Only admin can delete tasks.");
            return;
        }
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow != -1) {
            tasks.remove(selectedRow);
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.");
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Task task : tasks) {
            tableModel.addRow(new Object[]{task.getTask(), task.getAssignee(), task.getDeadline(), task.isCompleted()});
        }
    }

    private void clearFields() {
        taskField.setText("");
        assigneeField.setText("");
        deadlineField.setText("");
    }

    private void sortTasks() {
        int selectedIndex = sortComboBox.getSelectedIndex();
        switch (selectedIndex) {
            case 0:
                tasks.sort((t1, t2) -> t1.getTask().compareToIgnoreCase(t2.getTask()));
                break;
            case 1:
                tasks.sort((t1, t2) -> t1.getAssignee().compareToIgnoreCase(t2.getAssignee()));
                break;
            case 2:
                tasks.sort((t1, t2) -> t1.getDeadline().compareTo(t2.getDeadline()));
                break;
        }
        refreshTable();
    }

    private void markSelectedTaskAsIncomplete() {
        int selectedRow = taskTable.getSelectedRow();
        if (selectedRow != -1) {
            Task task = tasks.get(selectedRow);
            task.setCompleted(!task.isCompleted()); // Toggle completion status
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to mark as incomplete.");
        }
    }

    private String validateLogin(String username, String password) {
        String query = "SELECT role FROM Users WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/TaskManagementDB", "root", "123456");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error.");
            System.exit(1);
            return null;
        }
    }

    private void switchUser() {
        login();
        taskField.setEditable(isAdmin);
        assigneeField.setEditable(isAdmin);
        deadlineField.setEditable(isAdmin);
        addButton.setEnabled(isAdmin);
        deleteButton.setEnabled(isAdmin);
        markIncompleteButton.setEnabled(isAdmin||isEmployee);
        signupButton.setVisible(isAdmin);
    }

    private void openSignupPage() {
        new SingUp(); // Open the signup page
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> TaskManagementApplication.getInstance().setVisible(true));
    }
}