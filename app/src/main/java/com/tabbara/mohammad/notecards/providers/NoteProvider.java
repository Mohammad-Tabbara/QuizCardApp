package com.tabbara.mohammad.notecards.providers;

import android.arch.persistence.room.Room;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.tabbara.mohammad.notecards.Models.Card;
import com.tabbara.mohammad.notecards.RoomDataBase.AppDatabase;
import com.tabbara.mohammad.notecards.RoomDataBase.CardDAO;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Espfish on 12/21/2017.
 */

public class NoteProvider extends ContentProvider {

    private CardDAO dao;
    private static List<Card> cards;
    public static final String AUTHORITY = "com.mohammad.tabbara";

    @Override
    public boolean onCreate() {
        AppDatabase db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "cards-database").build();
        dao = db.cardDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull final Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        try {
            return new AsyncTask<Void,Void,Cursor>(){

                @Override
                protected Cursor doInBackground(Void... voids) {
                    final Cursor cursor = dao.selectAll();
                    cursor.setNotificationUri(getContext().getContentResolver(), uri);
                    return cursor;
                }
            }.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/" + AUTHORITY + "." + Card.TABLE_NAME;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
