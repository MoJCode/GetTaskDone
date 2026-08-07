package com.example.gettaskdone;

public class Task {

    // Task information
    private String mTaskId;
    private String mTaskTitle;
    private String mTaskDescription;
    private String mTaskPriority;

    // Create a task object
    public Task(
            String taskId,
            String taskTitle,
            String taskDescription,
            String taskPriority) {

        mTaskId = taskId;
        mTaskTitle = taskTitle;
        mTaskDescription = taskDescription;
        mTaskPriority = taskPriority;
    }

    // Return the task id
    public String getTaskId() {
        return mTaskId;
    }

    // Return the task title
    public String getTaskTitle() {
        return mTaskTitle;
    }

    // Return the task description
    public String getTaskDescription() {
        return mTaskDescription;
    }

    // Return the task priority
    public String getTaskPriority() {
        return mTaskPriority;
    }
}