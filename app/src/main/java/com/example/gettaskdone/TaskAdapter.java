package com.example.gettaskdone;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter
        extends RecyclerView.Adapter<TaskViewHolder> {

    // Adapter information
    private Context mContext;
    private ArrayList<Task> mTaskList;

    // Create the adapter
    public TaskAdapter(
            Context context,
            ArrayList<Task> taskList) {

        mContext = context;
        mTaskList = taskList;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(
            ViewGroup parent,
            int viewType) {

        // Create one task card
        View view = LayoutInflater.from(mContext)
                .inflate(
                        R.layout.row_task,
                        parent,
                        false
                );

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            TaskViewHolder holder,
            int position) {

        // Get the task in this position
        Task task = mTaskList.get(position);

        // Display the task information
        holder.setTaskTitle(
                task.getTaskTitle()
        );

        holder.setTaskDescription(
                task.getTaskDescription()
        );

        holder.setTaskPriority(
                task.getTaskPriority()
        );

        // Change the priority color
        if (task.getTaskPriority().equals("HIGH")) {

            holder.setPriorityBackground(
                    R.drawable.priority_high_background
            );

        } else if (task.getTaskPriority().equals("MEDIUM")) {

            holder.setPriorityBackground(
                    R.drawable.priority_medium_background
            );

        } else {

            holder.setPriorityBackground(
                    R.drawable.priority_low_background
            );
        }

        // Open the task preferences
        SharedPreferences prefs =
                mContext.getSharedPreferences(
                        "TaskPrefs",
                        Context.MODE_PRIVATE
                );

        // Create a different key for each task
        String completedKey =
                "completed_" + task.getTaskId();

        // Get the saved completion value
        boolean isCompleted =
                prefs.getBoolean(
                        completedKey,
                        false
                );

        // Remove the old checkbox listener
        holder.getCheckCompleted()
                .setOnCheckedChangeListener(null);

        // Display the saved checkbox value
        holder.getCheckCompleted()
                .setChecked(isCompleted);

        // Show or hide the completion message
        if (isCompleted) {

            holder.getCompletedMessage()
                    .setVisibility(View.VISIBLE);

        } else {

            holder.getCompletedMessage()
                    .setVisibility(View.GONE);
        }

        // Handle checkbox changes
        holder.getCheckCompleted()
                .setOnCheckedChangeListener(
                        new CompoundButton.OnCheckedChangeListener() {

                            @Override
                            public void onCheckedChanged(
                                    CompoundButton buttonView,
                                    boolean isChecked) {

                                // Save the completion value
                                SharedPreferences.Editor editor =
                                        prefs.edit();

                                editor.putBoolean(
                                        completedKey,
                                        isChecked
                                );

                                editor.commit();

                                // Show or hide the message
                                if (isChecked) {

                                    holder.getCompletedMessage()
                                            .setVisibility(
                                                    View.VISIBLE
                                            );

                                } else {

                                    holder.getCompletedMessage()
                                            .setVisibility(
                                                    View.GONE
                                            );
                                }
                            }
                        }
                );

        // Open the delete confirmation dialog
        holder.getDeleteButton()
                .setOnClickListener(
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                // Send the task id to MainActivity
                                if (mContext instanceof MainActivity) {

                                    MainActivity mainActivity =
                                            (MainActivity) mContext;

                                    mainActivity.showDeleteDialog(
                                            task.getTaskId()
                                    );
                                }
                            }
                        }
                );
    }

    @Override
    public int getItemCount() {

        // Return the number of tasks
        return mTaskList.size();
    }
}