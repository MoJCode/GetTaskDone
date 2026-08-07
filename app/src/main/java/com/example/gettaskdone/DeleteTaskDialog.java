package com.example.gettaskdone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class DeleteTaskDialog extends DialogFragment {

    // Key used to store the task id
    private static final String TASK_ID_KEY = "task_id";

    // Create the dialog with a task id
    public static DeleteTaskDialog newInstance(String taskId) {

        DeleteTaskDialog dialog =
                new DeleteTaskDialog();

        Bundle bundle = new Bundle();

        bundle.putString(
                TASK_ID_KEY,
                taskId
        );

        dialog.setArguments(bundle);

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Get the task id
        String taskId = "";

        if (getArguments() != null) {

            taskId = getArguments()
                    .getString(TASK_ID_KEY, "");
        }

        String finalTaskId = taskId;

        // Create the confirmation dialog
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_message)
                .setIcon(android.R.drawable.ic_menu_delete)

                // Delete button
                .setPositiveButton(
                        R.string.delete,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int id) {

                                // Send the task id to MainActivity
                                if (getActivity()
                                        instanceof DeleteTaskListener) {

                                    DeleteTaskListener listener =
                                            (DeleteTaskListener)
                                                    getActivity();

                                    listener.deleteTask(
                                            finalTaskId
                                    );
                                }
                            }
                        }
                )

                // Cancel button
                .setNegativeButton(
                        R.string.cancel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(
                                    DialogInterface dialog,
                                    int id) {

                                // Close the dialog
                                dialog.dismiss();
                            }
                        }
                );

        return builder.create();
    }

    // MainActivity will implement this interface
    public interface DeleteTaskListener {

        void deleteTask(String taskId);
    }
}