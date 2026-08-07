package com.example.gettaskdone;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    // Register screen widgets
    EditText editFullName;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connect this activity with the register layout
        setContentView(R.layout.activity_register);

        // Connect the widgets with their ids
        editFullName = findViewById(R.id.editFullName);
        btnRegister = findViewById(R.id.btnRegister);

        // Run when the register button is clicked
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the entered name
                String fullName = editFullName.getText().toString();

                // Check if the name is empty
                if (fullName.equals("")) {
                    Toast.makeText(
                            RegisterActivity.this,
                            R.string.enter_name_message,
                            Toast.LENGTH_SHORT
                    ).show();
                } else {

                    // Open the shared preferences file
                    SharedPreferences prefs =
                            getSharedPreferences("UserPrefs", MODE_PRIVATE);

                    SharedPreferences.Editor editor = prefs.edit();

                    // Save the user name
                    editor.putString("name_key", fullName);
                    editor.commit();

                    // Close the register screen
                    finish();
                }
            }
        });
    }
}