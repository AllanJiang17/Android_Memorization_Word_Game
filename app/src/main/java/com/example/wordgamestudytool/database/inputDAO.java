package com.example.wordgamestudytool.database;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.wordgamestudytool.model.Input;

import java.util.List;

@Dao
public interface inputDAO {

    @Query("SELECT * FROM input")
    List<Input> getAllInputs();

    @Insert
    void insertInput(Input... inputs);

    @Delete
    void delete(Input input);
}
