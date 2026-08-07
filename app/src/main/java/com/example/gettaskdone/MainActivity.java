package com.example.gettaskdone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements DeleteTaskDialog.DeleteTaskListener {

    // Main screen widgets
    TextView txtGreeting;
    TextView txtRecentTasks;
    EditText editSearch;
    Button btnSearch;
    Button btnShowAll;
    RecyclerView rvTasks;
    FloatingActionButton fabAddTask;

    // Task data
    ArrayList<Task> mAllTaskList;
    ArrayList<Task> mTaskList;
    DataManager dm;

    // Controls which tasks are displayed
    boolean showingAllTasks = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect the activity with the layout
        setContentView(R.layout.activity_main);

        // Connect the widgets
        txtGreeting =
                findViewById(R.id.txtGreeting);

        txtRecentTasks =
                findViewById(R.id.txtRecentTasks);

        editSearch =
                findViewById(R.id.editSearch);

        btnSearch =
                findViewById(R.id.btnSearch);

        btnShowAll =
                findViewById(R.id.btnShowAll);

        rvTasks =
                findViewById(R.id.rvTasks);

        fabAddTask =
                findViewById(R.id.fabAddTask);

        // Open the database
        dm = new DataManager(this);

        // Create the task lists
        mAllTaskList = new ArrayList<>();
        mTaskList = new ArrayList<>();

        // Display tasks vertically
        rvTasks.setLayoutManager(
                new LinearLayoutManager(this)
        );

        // Open the user preferences
        SharedPreferences prefs =
                getSharedPreferences(
                        "UserPrefs",
                        MODE_PRIVATE
                );

        // Get the saved name
        String fullName =
                prefs.getString(
                        "name_key",
                        ""
                );

        // Open register page when there is no name
        if (fullName.isEmpty()) {

            Intent myIntent =
                    new Intent(
                            this,
                            RegisterActivity.class
                    );

            startActivity(myIntent);

        } else {

            // Display the saved name
            txtGreeting.setText(
                    getString(R.string.hello_user)
                            + " "
                            + fullName
            );
        }

        // Open the add task page
        fabAddTask.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Intent myIntent =
                                new Intent(
                                        MainActivity.this,
                                        AddTaskActivity.class
                                );

                        startActivity(myIntent);
                    }
                }
        );

        // Switch between recent and all tasks
        btnShowAll.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        // Change the current list mode
                        showingAllTasks =
                                !showingAllTasks;

                        // Display the selected list
                        displayTasks();
                    }
                }
        );

        // Search for a task
        btnSearch.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        String searchTitle =
                                editSearch.getText()
                                        .toString()
                                        .trim();

                        // Check that a title was entered
                        if (searchTitle.isEmpty()) {

                            Toast.makeText(
                                    MainActivity.this,
                                    R.string.enter_search_title,
                                    Toast.LENGTH_SHORT
                            ).show();

                        } else {

                            // Search SQLite
                            searchTasks(searchTitle);
                        }
                    }
                }
        );
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reload the saved username
        SharedPreferences prefs =
                getSharedPreferences(
                        "UserPrefs",
                        MODE_PRIVATE
                );

        String fullName =
                prefs.getString(
                        "name_key",
                        ""
                );

        // Display the saved name
        if (!fullName.isEmpty()) {
            txtGreeting.setText(getString(R.string.hello_user) + " " + fullName);
        }

        // Reload tasks
        loadTasks();
    }

    // Load all tasks from SQLite
    private void loadTasks() {

        // Remove the old tasks
        mAllTaskList.clear();

        // Get all saved tasks
        Cursor c = dm.selectAll();

        // Read every database row
        while (c.moveToNext()) {

            String taskId =
                    c.getString(0);

            String taskTitle =
                    c.getString(1);

            String taskDescription =
                    c.getString(2);

            String taskPriority =
                    c.getString(3);

            // Create a task object
            Task task = new Task(
                    taskId,
                    taskTitle,
                    taskDescription,
                    taskPriority
            );

            // Add the task to the full list
            mAllTaskList.add(task);
        }

        // Close the Cursor
        c.close();

        // Display recent or all tasks
        displayTasks();
    }

    // Display recent or all tasks
    private void displayTasks() {

        // Remove currently displayed tasks
        mTaskList.clear();

        if (showingAllTasks) {

            // Change the list title
            txtRecentTasks.setText(
                    R.string.all_tasks
            );

            // Change the button text
            btnShowAll.setText(
                    R.string.show_recent_tasks
            );

            // Add all tasks from newest to oldest
            for (int i = mAllTaskList.size() - 1;
                 i >= 0;
                 i--) {

                mTaskList.add(
                        mAllTaskList.get(i)
                );
            }

        } else {

            // Change the list title
            txtRecentTasks.setText(
                    R.string.recent_tasks
            );

            // Change the button text
            btnShowAll.setText(
                    R.string.show_all_tasks
            );

            // Find the latest task
            int firstTask =
                    mAllTaskList.size() - 1;

            // Find where the latest three tasks end
            int lastTask =
                    Math.max(
                            0,
                            mAllTaskList.size() - 3
                    );

            // Add the latest three tasks
            for (int i = firstTask;
                 i >= lastTask;
                 i--) {

                mTaskList.add(
                        mAllTaskList.get(i)
                );
            }
        }

        // Display the selected tasks
        rvTasks.setAdapter(
                new TaskAdapter(
                        this,
                        mTaskList
                )
        );
    }

    // Search for a task by title
    private void searchTasks(String title) {

        // Remove currently displayed tasks
        mTaskList.clear();

        // Search SQLite
        Cursor c = dm.searchTask(title);

        // Read every matching task
        while (c.moveToNext()) {

            String taskId =
                    c.getString(0);

            String taskTitle =
                    c.getString(1);

            String taskDescription =
                    c.getString(2);

            String taskPriority =
                    c.getString(3);

            // Create a task object
            Task task = new Task(
                    taskId,
                    taskTitle,
                    taskDescription,
                    taskPriority
            );

            // Add the matching task
            mTaskList.add(task);
        }

        // Close the Cursor
        c.close();

        // Change the list heading
        txtRecentTasks.setText(
                R.string.search_results
        );

        // Change the button text
        btnShowAll.setText(
                R.string.show_all_tasks
        );

        // Return to recent mode after Show All
        showingAllTasks = false;

        // Display the search results
        rvTasks.setAdapter(
                new TaskAdapter(
                        this,
                        mTaskList
                )
        );

        // Inform the user when nothing matches
        if (mTaskList.size() == 0) {

            Toast.makeText(
                    this,
                    R.string.no_tasks_found,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    // Open the delete confirmation dialog
    public void showDeleteDialog(String taskId) {

        DeleteTaskDialog dialog =
                DeleteTaskDialog.newInstance(
                        taskId
                );

        dialog.show(
                getSupportFragmentManager(),
                "delete_task_dialog"
        );
    }

    // Delete the confirmed task
    @Override
    public void deleteTask(String taskId) {

        // Delete the task from SQLite
        dm.delete(taskId);

        // Open the task preferences
        SharedPreferences prefs =
                getSharedPreferences(
                        "TaskPrefs",
                        MODE_PRIVATE
                );

        SharedPreferences.Editor editor =
                prefs.edit();

        // Delete the task completion value
        editor.remove(
                "completed_" + taskId
        );

        editor.commit();

        // Inform the user
        Toast.makeText(
                this,
                R.string.task_deleted_message,
                Toast.LENGTH_SHORT
        ).show();

        // Reload the task list
        loadTasks();
    }
}