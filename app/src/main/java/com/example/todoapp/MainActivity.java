package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private static final String EXTRA_NOTE_TEXT = "extra note text";
    private static final String EXTRA_PRIORITY = "extra note priority";

    private NotesAdapter adapter;
    private RecyclerView recyclerViewNotes;
    private FloatingActionButton floatingButton;
    private TextView textEmptyList;
    private ItemTouchHelper itemTouchHelper;

    private NoteDatabase noteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        noteDatabase = NoteDatabase.getInstance(getApplication());
        recyclerViewNotes.setAdapter(adapter);
        itemTouchHelper.attachToRecyclerView(recyclerViewNotes);
        floatingButtonClickListener();

//      adapter.setOnNoteClickListener(new NotesAdapter.onNoteClickListener() {
//            @Override
//            public void onNoteCliсk(Note note) {
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNotes();
    }

    private void floatingButtonClickListener() {
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddNoteScreen.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private ItemTouchHelper itemTouchHelper() {

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT
                ) {
                    @Override
                    public boolean onMove(
                            @NonNull RecyclerView recyclerView,
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            @NonNull RecyclerView.ViewHolder target
                    ) {
                        return false;
                    }

                    @Override
                    public void onSwiped(
                            @NonNull RecyclerView.ViewHolder viewHolder,
                            int direction
                    ) {
                        int position = viewHolder.getAdapterPosition();
                        Note note = adapter.getNotes().get(position);
                        noteDatabase.notesDao().remove(note.getId());
                        showNotes();
                    }
                });

        return itemTouchHelper;
    }

    private void showNotes() {

        if (noteDatabase.notesDao().getNotes().isEmpty()) {
            textEmptyList.setVisibility(View.VISIBLE);
            recyclerViewNotes.setVisibility(View.INVISIBLE);
        } else {
            textEmptyList.setVisibility(View.INVISIBLE);
            recyclerViewNotes.setVisibility(View.VISIBLE);
            adapter.setNotes(noteDatabase.notesDao().getNotes());
        }
    }

    private void initViews() {
        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        floatingButton = findViewById(R.id.addButton);
        textEmptyList = findViewById(R.id.textViewEmptyList);
        adapter = new NotesAdapter();
        itemTouchHelper = itemTouchHelper();
    }


}