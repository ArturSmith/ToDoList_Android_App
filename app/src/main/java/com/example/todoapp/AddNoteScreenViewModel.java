package com.example.todoapp;
import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
public class AddNoteScreenViewModel extends AndroidViewModel {
    private NotesDao notesDao;
    private MutableLiveData<Boolean> shouldClose = new MutableLiveData<>();

    public AddNoteScreenViewModel(@NonNull Application application) {
        super(application);
        notesDao = NoteDatabase.getInstance(application).notesDao();
    }
    public void saveNote(Note note) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                notesDao.add(note);
                shouldClose.postValue(true);
            }
        });
        thread.start();
    }
    public LiveData<Boolean> getShouldClose() {
        return shouldClose;
    }
}
