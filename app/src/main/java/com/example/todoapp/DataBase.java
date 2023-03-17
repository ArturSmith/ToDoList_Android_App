package com.example.todoapp;

import java.util.ArrayList;
import java.util.Random;

public class DataBase {

    private ArrayList<Note> notes = new ArrayList<>();

    private static DataBase instance = null;
    public static DataBase getInstance(){
        if (instance==null){
            instance = new DataBase();
        }
        return instance;
    }
    public DataBase(){

    }

    public ArrayList<Note> getNotes() {
        return new ArrayList<>(notes);
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public void removeNote(int id) {
        for (Note note : notes) {
            if (note.getId()==id){
                notes.remove(note);
                break;
            }
        }
    }


}
