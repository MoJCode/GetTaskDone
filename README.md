# GetTaskDone

GetTaskDone is an Android task and to-do management application developed for the IT487 Mobile Application Development course.

The application helps users organize their daily tasks by allowing them to create tasks, assign priorities, search for tasks, mark tasks as completed, and delete tasks.

## Main Features

- Register and save the user's full name
- Add new tasks
- Add a task title and description
- Assign HIGH, MEDIUM, or LOW priority
- Display the three most recent tasks
- Display all saved tasks
- Search tasks using part of the task title
- Case-insensitive task search
- Mark tasks as completed
- Save task completion status
- Delete tasks
- Display a confirmation dialog before deleting a task

## Technologies Used

- Java
- Android Studio
- SQLite
- SharedPreferences
- RecyclerView
- CardView
- DialogFragment
- Material Components
- XML layouts

## Project Structure

The main Java classes used in the application are:

- `MainActivity.java`
- `RegisterActivity.java`
- `AddTaskActivity.java`
- `DataManager.java`
- `Task.java`
- `TaskAdapter.java`
- `TaskViewHolder.java`
- `DeleteTaskDialog.java`

The main layout files are:

- `activity_main.xml`
- `activity_register.xml`
- `activity_add_task.xml`
- `row_task.xml`

## Database

The application uses a local SQLite database named:

`task_manager_db`

The database contains a `tasks` table with the following fields:

- Task ID
- Task title
- Task description
- Task priority

The application performs the following SQLite operations:

- Insert
- Select All
- Search
- Delete

## Course

**Course:** IT487 – Mobile Application Development  
**Project:** GetTaskDone  
**Platform:** Android  
**Programming Language:** Java
