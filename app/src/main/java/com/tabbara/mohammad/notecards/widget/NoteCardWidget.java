package com.tabbara.mohammad.notecards.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.UserDictionary;
import android.widget.RemoteViews;

import com.tabbara.mohammad.notecards.Models.Card;
import com.tabbara.mohammad.notecards.R;
import com.tabbara.mohammad.notecards.RoomDataBase.AppDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class NoteCardWidget extends AppWidgetProvider {

    private static int i = 0;
    private static List<Card> cards;
    private static boolean showingQuestion = true;

    private static AppDatabase db;
    private static RemoteViews views;

    public static String ACTION_WIDGET_CLICK_NEXT = "Action_next";
    public static String ACTION_WIDGET_CLICK_FLIP = "Action_flip";

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId,final int[] appWidgetIds) {
        // Construct the RemoteViews object
//        db = Room.databaseBuilder(context,
//                AppDatabase.class, "cards-database").build();
//        new AsyncTask<AppDatabase, Void, List<Card>>() {
//            @Override
//            protected List<Card> doInBackground(AppDatabase... params) {
//                cards = params[0].cardDao().getAll();
//                return cards;
//            }
//
//            @Override
//            protected void onPostExecute(List<Card> cards) {
//                // Instruct the widget manager to update the widget
//                Intent intent = new Intent(context,NoteCardWidget.class);
////                intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
//
////                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
////                views.setOnClickPendingIntent(R.id.next,pendingIntent);
//                views.setTextViewText(R.id.appwidget_text, cards.get(0).getQuestion());
//                appWidgetManager.updateAppWidget(appWidgetId, views);
//            }
//        }.execute(db);
        readDBCards(context);
//        Intent intent = new Intent(context, NoteCardWidget.class);
//        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        views.setOnClickPendingIntent(R.id.next, pendingIntent);
        views.setTextViewText(R.id.appwidget_text, cards.get(0).getQuestion());
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    public static void readDBCards(Context context){
//        db = Room.databaseBuilder(context,
//                AppDatabase.class, "cards-database").build();
//        new AsyncTask<AppDatabase, Void, List<Card>>() {
//            @Override
//            protected List<Card> doInBackground(AppDatabase... params) {
//                cards = params[0].cardDao().getAll();
//                return cards;
//            }
//        }.execute(db);
        Uri uri = Uri.parse("content://"+"com.mohammad.tabbara");
        uri.buildUpon().appendPath("card");
        Cursor cursor = context.getContentResolver().query(
//                UserDictionary.Words.CONTENT_URI,
                uri,
                null,
                null,
                null,
                null
        );
        cards = new ArrayList<>();
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            String question = cursor.getString(cursor.getColumnIndex("question"));
            String answer = cursor.getString(cursor.getColumnIndex("answer"));
            Card card = new Card();
            card.setQuestion(question);
            card.setAnswer(answer);
            cards.add(card);
            cursor.moveToNext();
        }
        cursor.close();
    }

    static void updateWidgetState(Context paramContext, String paramString)
    {
        RemoteViews localRemoteViews = buildUpdate(paramContext, paramString);
        ComponentName localComponentName = new ComponentName(paramContext, NoteCardWidget.class);
        AppWidgetManager.getInstance(paramContext).updateAppWidget(localComponentName, localRemoteViews);
    }

    private static RemoteViews buildUpdate(Context paramContext, String paramString)
    {

        RemoteViews views = new RemoteViews(paramContext.getPackageName(), R.layout.note_card_widget);
        Intent activebtnnext = new Intent(paramContext, NoteCardWidget.class);
        activebtnnext.setAction(ACTION_WIDGET_CLICK_NEXT);
        PendingIntent configPendingIntentnext = PendingIntent.getBroadcast(paramContext, 0, activebtnnext , 0);
        views.setOnClickPendingIntent(R.id.next, configPendingIntentnext);

        Intent activeflipbtn = new Intent(paramContext, NoteCardWidget.class);
        activeflipbtn.setAction(ACTION_WIDGET_CLICK_FLIP);
        PendingIntent configPendingIntentprev = PendingIntent.getBroadcast(paramContext, 0, activeflipbtn , 0);
        views.setOnClickPendingIntent(R.id.flip, configPendingIntentprev);
        readDBCards(paramContext);
        if(cards != null && cards.size()!= 0) {
            if (paramString.equals(ACTION_WIDGET_CLICK_NEXT)) {
                i++;
                i %= cards.size();
                views.setTextViewText(R.id.appwidget_text, cards.get(i).getQuestion());
                showingQuestion = true;
            }
            if (paramString.equals(ACTION_WIDGET_CLICK_FLIP)) {
                if (showingQuestion) {
                    views.setTextViewText(R.id.appwidget_text, cards.get(i).getAnswer());
                    showingQuestion = false;
                } else {
                    views.setTextViewText(R.id.appwidget_text, cards.get(i).getQuestion());
                    showingQuestion = true;
                }
            }
        }
        return views;
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context,intent);
        String str = intent.getAction();
        if (intent.getAction().equals(ACTION_WIDGET_CLICK_NEXT)) {
            updateWidgetState(context, str);
        }
        if (intent.getAction().equals(ACTION_WIDGET_CLICK_FLIP)) {
            updateWidgetState(context, str);
        }
//        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
//        appWidgetManager.updateAppWidget(new ComponentName(context, NoteCardWidget.class), views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        views = new RemoteViews(context.getPackageName(), R.layout.note_card_widget);
        for (int appWidgetId : appWidgetIds) {
            views.setOnClickPendingIntent(R.id.next,
                    getPendingSelfIntent(context, ACTION_WIDGET_CLICK_NEXT));
            views.setOnClickPendingIntent(R.id.flip,
                    getPendingSelfIntent(context, ACTION_WIDGET_CLICK_FLIP));
            updateAppWidget(context, appWidgetManager, appWidgetId,appWidgetIds);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

