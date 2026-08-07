package com.example.gettaskdone;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataManager {

    // Reference to the database
    private SQLiteDatabase dbase;

    // Task table columns
    public static final String TABLE_COLUMN_ID = "_id";
    public static final String TABLE_COLUMN_TITLE = "title";
    public static final String TABLE_COLUMN_DESCRIPTION = "description";
    public static final String TABLE_COLUMN_PRIORITY = "priority";

    // Database information
    private static final String DB_NAME = "task_manager_db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_TASKS = "tasks";

    public DataManager(Context context) {

        // Create the database helper
        myDbHelper helper = new myDbHelper(context);

        // Open the database for writing
        dbase = helper.getWritableDatabase();
    }

    // Helper class for creating the database
    private class myDbHelper extends SQLiteOpenHelper {

        public myDbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase dbase) {

            // Create the tasks table
            String query = "create table " + TABLE_TASKS + " ("
                    + TABLE_COLUMN_ID
                    + " integer primary key autoincrement not null,"
                    + TABLE_COLUMN_TITLE + " text not null,"
                    + TABLE_COLUMN_DESCRIPTION + " text not null,"
                    + TABLE_COLUMN_PRIORITY + " text not null);";

            // Run the create table query
            dbase.execSQL(query);
        }

        @Override
        public void onUpgrade(
                SQLiteDatabase dbase,
                int oldVersion,
                int newVersion) {

            // No database upgrade is needed now
        }
    }

    // Insert a task into the table
    public void insert(
            String title,
            String description,
            String priority) {

        String query = "INSERT INTO " + TABLE_TASKS
                + " (" + TABLE_COLUMN_TITLE
                + ", " + TABLE_COLUMN_DESCRIPTION
                + ", " + TABLE_COLUMN_PRIORITY + ") "
                + "VALUES "
                + "(" + "'" + title + "'"
                + ", " + "'" + description + "'"
                + ", " + "'" + priority + "'" + "); ";

        Log.i("insert() = ", query);

        // Run the insert query
        dbase.execSQL(query);
    }

    // Delete a task using its id
    public void delete(String taskId) {

        String query = "DELETE FROM " + TABLE_TASKS
                + " WHERE " + TABLE_COLUMN_ID
                + " = " + taskId + ";";

        Log.i("delete() = ", query);

        // Run the delete query
        dbase.execSQL(query);
    }

    // Get all tasks from the table
    public Cursor selectAll() {

        Cursor c = dbase.rawQuery(
                "SELECT * from " + TABLE_TASKS,
                null
        );

        return c;
    }

    // Search tasks using part of the title
    public Cursor searchTask(String title) {

        String query = "SELECT "
                + TABLE_COLUMN_ID + ", "
                + TABLE_COLUMN_TITLE + ", "
                + TABLE_COLUMN_DESCRIPTION + ", "
                + TABLE_COLUMN_PRIORITY
                + " FROM " + TABLE_TASKS
                + " WHERE LOWER(" + TABLE_COLUMN_TITLE + ")"
                + " LIKE LOWER(?);";

        Log.i("searchTask() = ", query);

        // Add percent signs for partial search
        String searchValue =
                "%" + title + "%";

        // Return all matching tasks
        Cursor c = dbase.rawQuery(
                query,
                new String[]{searchValue}
        );

        return c;
    }
}