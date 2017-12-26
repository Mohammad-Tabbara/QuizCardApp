package com.tabbara.mohammad.notecards.RoomDataBase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.tabbara.mohammad.notecards.Models.Card;

/**
 * Created by Espfish on 12/4/2017.
 */

@Database(entities = {Card.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CardDAO cardDao();
}
