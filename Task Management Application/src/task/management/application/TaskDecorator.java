/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package task.management.application;

/**
 *
 * @author A.Diaa
 */
public class TaskDecorator implements Task {
    private Task wrappedTask;

    public TaskDecorator(Task wrappedTask) {
        this.wrappedTask = wrappedTask;
    }

    @Override
    public String getTask() { return wrappedTask.getTask(); }

    @Override
    public String getAssignee() { return wrappedTask.getAssignee(); }

    @Override
    public String getDeadline() { return wrappedTask.getDeadline(); }

    @Override
    public boolean isCompleted() { return wrappedTask.isCompleted(); }

    @Override
    public void setCompleted(boolean completed) {
        System.out.println("Logging task completion: " + wrappedTask.getTask());
        wrappedTask.setCompleted(completed);
    }
}
