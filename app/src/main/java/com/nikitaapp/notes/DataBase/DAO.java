package com.nikitaapp.notes.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nikitaapp.notes.Models.Notes;

import java.util.List;


@Dao
public interface DAO {

    @Insert(onConflict = REPLACE)
    long insert(Notes notes);

    @Query("SELECT * FROM notes ORDER BY ID DESC")
    List<Notes> getAll();

    @Update
    void update(Notes notes);

    @Delete
    void delete(Notes notes);

    @Query("UPDATE notes SET pinned=:pin WHERE ID= :id")
    void pin(long id, boolean pin);

}
