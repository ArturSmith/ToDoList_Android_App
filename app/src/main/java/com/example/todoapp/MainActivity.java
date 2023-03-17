package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static String EXTRA_NOTE_TEXT = "extra note text";
    private static String EXTRA_PRIORITY = "extra note priority";

    private LinearLayout linearLayout;
    private FloatingActionButton floatingButton;
    private TextView textEmptyList;
    private DataBase dataBase = DataBase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

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
        linearLayout.removeAllViews();
        if (dataBase.getNotes().isEmpty()){
            textEmptyList.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.INVISIBLE);
        } else {
            textEmptyList.setVisibility(View.INVISIBLE);
            linearLayout.setVisibility(View.VISIBLE);
            for (Note note : dataBase.getNotes()) {
                View view = getLayoutInflater().inflate(
                        R.layout.note_view,
                        linearLayout,
                        false);
                TextView textViewNote = view.findViewById(R.id.textViewNote);
                textViewNote.setText(note.getText());

                int colorResId;
                switch (note.getPriority()){
                    case 0:
                        colorResId = android.R.color.holo_green_light;
                        break;
                    case 1:
                        colorResId = android.R.color.holo_orange_light;
                        break;
                    default:
                        colorResId = android.R.color.holo_red_light;
                }

                int color = ContextCompat.getColor(this, colorResId);
                textViewNote.setBackgroundColor(color);
                linearLayout.addView(view);
            }
        }

    }

    private void initViews() {
        linearLayout = findViewById(R.id.linear_layout_notes);
        floatingButton = findViewById(R.id.addButton);
        textEmptyList = findViewById(R.id.textViewEmptyList);
    }







}