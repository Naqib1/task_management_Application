/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task.management.application;

/**
 *
 * @author A.Diaa
 */
public class TaskFactory {
    public static Task createTask(String taskType, String task, String assignee, String deadline, boolean completed) {
        switch (taskType) {
            case "Bug","bug":
                return new BugTask(task, assignee, deadline, completed);
            case "Feature","feature":
                return new FeatureTask(task, assignee, deadline, completed);       
            default:
                throw new IllegalArgumentException("Unknown task type: " + taskType);
        }
    }
}
