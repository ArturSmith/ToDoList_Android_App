package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddNoteScreen extends AppCompatActivity {
    private EditText noteText;
    private RadioButton low;
    private RadioButton medium;

    private Button button;

    private DataBase dataBase = DataBase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_screen);
        initViews();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String text = noteText.getText().toString().trim();
        int priority = getPriority();
        int id = dataBase.getNotes().size();
        Note newNote = new Note(id, text, priority);
        dataBase.addNote(newNote);
        finish();
    }

    private int getPriority() {
        int priority;
        if (low.isChecked()) {
            priority = 0;
        } else if (medium.isChecked()) {
            priority = 1;
        } else {
            priority = 2;
        }
        return priority;
    }

    private void initViews() {
        noteText = findViewById(R.id.editTextNoteText);
        button = findViewById(R.id.buttonAddNote);
        low = findViewById(R.id.radioButtonLow);
        medium = findViewById(R.id.radioButtonMedium);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, AddNoteScreen.class);
    }
}