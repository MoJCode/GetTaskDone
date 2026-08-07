package com.example.gettaskdone;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TaskViewHolder extends RecyclerView.ViewHolder {

    // Task card widgets
    private TextView mtxtTaskTitle;
    private TextView mtxtTaskDescription;
    private TextView mtxtTaskPriority;
    private TextView mtxtCompletedMessage;
    private CheckBox mcheckCompleted;
    private ImageButton mbtnDeleteTask;

    public TaskViewHolder(View itemView) {
        super(itemView);

        // Connect the task card widgets
        mtxtTaskTitle =
                itemView.findViewById(R.id.txtTaskCardTitle);

        mtxtTaskDescription =
                itemView.findViewById(R.id.txtTaskCardDescription);

        mtxtTaskPriority =
                itemView.findViewById(R.id.txtTaskCardPriority);

        // Connect the completion message
        mtxtCompletedMessage =
                itemView.findViewById(R.id.txt_completed_message);

        mcheckCompleted =
                itemView.findViewById(R.id.checkCompleted);

        mbtnDeleteTask =
                itemView.findViewById(R.id.btnDeleteTask);
    }

    // Display the task title
    public void setTaskTitle(String title) {
        mtxtTaskTitle.setText(title);
    }

    // Display the task description
    public void setTaskDescription(String description) {
        mtxtTaskDescription.setText(description);
    }

    // Display the task priority
    public void setTaskPriority(String priority) {
        mtxtTaskPriority.setText(priority);
    }

    // Change the priority background
    public void setPriorityBackground(int background) {
        mtxtTaskPriority.setBackgroundResource(background);
    }

    // Return the checkbox
    public CheckBox getCheckCompleted() {
        return mcheckCompleted;
    }

    // Return the completion message
    public TextView getCompletedMessage() {
        return mtxtCompletedMessage;
    }

    // Return the delete button
    public ImageButton getDeleteButton() {
        return mbtnDeleteTask;
    }
}