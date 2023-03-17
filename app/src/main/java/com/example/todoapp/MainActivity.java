package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static String EXTRA_NOTE_TEXT = "extra note text";
    private static String EXTRA_PRIORITY = "extra note priority";

    private NotesAdapter adapter;
    private RecyclerView recyclerViewNotes;
    private FloatingActionButton floatingButton;
    private TextView textEmptyList;
    private DataBase dataBase = DataBase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        recyclerViewNotes.setAdapter(adapter);
        adapter.setOnNoteClickListener(new NotesAdapter.onNoteClickListener() {
            @Override
            public void onNoteClik(Note note) {
                dataBase.removeNote(note.getId());
                showNotes();
            }
        });

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddNoteScreen.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void showNotes() {

        if (dataBase.getNotes().isEmpty()) {
            textEmptyList.setVisibility(View.VISIBLE);
            recyclerViewNotes.setVisibility(View.INVISIBLE);
        } else {
            textEmptyList.setVisibility(View.INVISIBLE);
            recyclerViewNotes.setVisibility(View.VISIBLE);
            adapter.setNotes(dataBase.getNotes());
        }
    }


    private void initViews() {
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        floatingButton = findViewById(R.id.addButton);
        textEmptyList = findViewById(R.id.textViewEmptyList);
        adapter = new NotesAdapter();
    }


}