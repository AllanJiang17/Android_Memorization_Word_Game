package com.example.wordgamestudytool.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wordgamestudytool.model.Input;

@Database(entities = {Input.class}, version = 1)
public abstract class DB extends RoomDatabase {

    public abstract inputDAO inputDAO();

    private static DB INSTANCE;

    public static DB getInstance(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DB.class, "input")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
