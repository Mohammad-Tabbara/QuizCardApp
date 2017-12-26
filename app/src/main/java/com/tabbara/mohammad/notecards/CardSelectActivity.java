package com.tabbara.mohammad.notecards;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.tabbara.mohammad.notecards.Adapters.RecyclerViewCardAdapter;
import com.tabbara.mohammad.notecards.Fragments.AddCardDialogFragment;
import com.tabbara.mohammad.notecards.Models.Card;
import com.tabbara.mohammad.notecards.RoomDataBase.AppDatabase;

import java.util.List;

public class CardSelectActivity extends AppCompatActivity implements AddCardDialogFragment.CardDialogListener{

    public AppDatabase db;
    private RecyclerView cardRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_select);

        //Init DataBase
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "cards-database").build();


        //SetupRecyclerView
        cardRecyclerView = findViewById(R.id.card_recycler_view);
        refreshRV(cardRecyclerView);

        //Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup FAB
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new AddCardDialogFragment();
                newFragment.show(getSupportFragmentManager(), "card");
            }
        });
    }

    private void refreshRV(final RecyclerView cardRecyclerView) {
        new AsyncTask<AppDatabase, Void, List<Card>>() {
            @Override
            protected List<Card> doInBackground(AppDatabase... params) {
                return params[0].cardDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Card> cards) {
                RecyclerViewCardAdapter rvCardAdapter = new RecyclerViewCardAdapter(getContext(), cards);
                cardRecyclerView.setAdapter(rvCardAdapter);
                cardRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        }.execute(db);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_select, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog,View view) {
        //TODO: ADD CARD
        final Card card = new Card();
        EditText question = view.findViewById(R.id.question);
        EditText answer = view.findViewById(R.id.answer);
        card.setQuestion(question.getText().toString());
        card.setAnswer(answer.getText().toString());
        new AsyncTask<AppDatabase, Void, Void>() {
            @Override
            protected Void doInBackground(AppDatabase... params) {
                params[0].cardDao().insertAll(card);
                return null;
            }

            @Override
            protected void onPostExecute(Void cards) {
                refreshRV(cardRecyclerView);
            }
        }.execute(db);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog,View view) {

    }

    private Context getContext(){
        return CardSelectActivity.this;
    }
}
