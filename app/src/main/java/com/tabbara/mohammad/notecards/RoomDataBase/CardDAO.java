package com.tabbara.mohammad.notecards.RoomDataBase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import com.tabbara.mohammad.notecards.Models.Card;

import java.util.List;

/**
 * Created by Espfish on 12/4/2017.
 */

@Dao
public interface CardDAO {
    @Query("SELECT * FROM card")
    List<Card> getAll();

    @Query("SELECT * FROM card")
    Cursor selectAll();

    @Insert
    void insertAll(Card... users);

    @Update
    void updateUsers(Card... users);

    @Delete
    void delete(Card user);
}
