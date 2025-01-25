package com.example.attendenacemanager;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

public class StudentEditor extends AppCompatActivity {
    private int classID;
    private int studentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_editor);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Initialize views
        TextView id = findViewById(R.id.id);
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText email = findViewById(R.id.email);
        EditText tel = findViewById(R.id.tel);
        Button submit = findViewById(R.id.submit);
        TextView delete = findViewById(R.id.delete);

        // Get classID from intent
        classID = getIntent().getIntExtra("class ID", 0);

        Database database = new Database();

        // Check if editing an existing student
        if (getIntent().hasExtra("Student ID")) {
            delete.setVisibility(View.VISIBLE);
            studentID = getIntent().getIntExtra("Student ID", 0);

            // Fetch student details
            Student student = database.getStudent(classID, studentID);
            id.setText(String.valueOf(student.getID()));
            firstName.setText(student.getFirstName());
            lastName.setText(student.getLastName());
            email.setText(student.getEmail());
            tel.setText(student.getTel()); // Fixed: Properly set tel value

            // Set submit button click listener for updating student
            submit.setOnClickListener(v -> {
                student.setFirstName(firstName.getText().toString());
                student.setLastName(lastName.getText().toString());
                student.setEmail(email.getText().toString());
                student.setTel(tel.getText().toString()); // Fixed: Correct tel handling

                database.updateStudent(classID, student);
                Toast.makeText(this, "Student Updated Successfully", Toast.LENGTH_LONG).show();
                finish();
            });

            // Set delete button click listener for deleting student
            delete.setOnClickListener(v -> {
                try {
                    database.deleteStudent(classID, studentID);
                    Toast.makeText(this, "Student Deleted Successfully", Toast.LENGTH_LONG).show();
                    finish();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            // Hide delete button for new student
            studentID = database.getNextStudentID(classID);
            id.setText(String.valueOf(studentID));
            submit.setOnClickListener(v->{
                Student student = new Student();
                student.setID(studentID);
                student.setFirstName(firstName.getText().toString());
                student.setLastName(lastName.getText().toString());
                student.setEmail(email.getText().toString());
                student.setTel(tel.getText().toString());
                database.addStudent(classID,student);
                Toast.makeText(this, "Student Add Successfully", Toast.LENGTH_LONG).show();
                finish();
            });
        }
    }
}
