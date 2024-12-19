/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task.management.application;

/**
 *
 * @author A.Diaa
 */
public class FeatureTask implements Task {
    private String task, assignee, deadline;
    private boolean completed;

    public FeatureTask(String task, String assignee, String deadline, boolean completed) {
        this.task = task;
        this.assignee = assignee;
        this.deadline = deadline;
        this.completed = completed;
    }

    @Override
    public String getTask() { return task; }

    @Override
    public String getAssignee() { return assignee; }

    @Override
    public String getDeadline() { return deadline; }

    @Override
    public boolean isCompleted() { return completed; }

    @Override
    public void setCompleted(boolean completed) { this.completed = completed; }
}
