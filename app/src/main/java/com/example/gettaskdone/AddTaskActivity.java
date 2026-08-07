package com.example.gettaskdone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    // Add task screen widgets
    EditText editTaskTitle;
    EditText editDescription;
    Spinner spinnerPriority;
    Button btnAddTask;
    ImageButton btnBack;

    // Database object
    DataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect the activity with the layout
        setContentView(R.layout.activity_add_task);

        // Open the database
        dm = new DataManager(this);

        // Connect the widgets with their ids
        editTaskTitle = findViewById(R.id.editTaskTitle);
        editDescription = findViewById(R.id.editDescription);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        btnAddTask = findViewById(R.id.btnAddTask);
        btnBack = findViewById(R.id.btnBack);

        // Return to the previous page
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Add a task to the database
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the entered task details
                String title =
                        editTaskTitle.getText().toString();

                String description =
                        editDescription.getText().toString();

                String priority =
                        spinnerPriority.getSelectedItem().toString();

                // Check the title and description
                if (title.equals("") || description.equals("")) {

                    Toast.makeText(
                            AddTaskActivity.this,
                            R.string.enter_task_details,
                            Toast.LENGTH_SHORT
                    ).show();

                } else if (spinnerPriority.getSelectedItemPosition() == 0) {

                    // Check the selected priority
                    Toast.makeText(
                            AddTaskActivity.this,
                            R.string.select_priority_message,
                            Toast.LENGTH_SHORT
                    ).show();

                } else {

                    // Insert the task
                    dm.insert(title, description, priority);

                    Toast.makeText(
                            AddTaskActivity.this,
                            R.string.task_added_message,
                            Toast.LENGTH_SHORT
                    ).show();

                    // Return to the main page
                    finish();
                }
            }
        });
    }
}