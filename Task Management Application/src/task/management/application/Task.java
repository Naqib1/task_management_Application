/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package task.management.application;

/**
 *
 * @author A.Diaa
 */
public interface Task {
    String getTask();
    String getAssignee();
    String getDeadline();
    boolean isCompleted();
    void setCompleted(boolean completed);
}
