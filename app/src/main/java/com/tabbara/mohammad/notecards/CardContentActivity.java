package com.tabbara.mohammad.notecards;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.tabbara.mohammad.notecards.Models.Card;

public class CardContentActivity extends AppCompatActivity {

    private Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_content);

        //Get Selected Card
        Intent intent = getIntent();
        card = (Card) intent.getSerializableExtra("card");

        //Init Views
        TextView question = findViewById(R.id.question);
        TextView answer = findViewById(R.id.answer);

        //View Card Info
        question.setText(card.getQuestion());
        answer.setText(card.getAnswer());
    }
}
