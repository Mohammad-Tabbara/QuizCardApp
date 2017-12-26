package com.tabbara.mohammad.notecards.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tabbara.mohammad.notecards.CardContentActivity;
import com.tabbara.mohammad.notecards.Models.Card;
import com.tabbara.mohammad.notecards.R;

import java.util.List;

/**
 * Created by Espfish on 12/4/2017.
 */

public class RecyclerViewCardAdapter extends RecyclerView.Adapter<RecyclerViewCardAdapter.CardViewHolder>{

    private Context context;
    private List<Card> cards;

    public RecyclerViewCardAdapter(Context context, List<Card> cards){
        this.context = context;
        this.cards = cards;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item,parent,false);
        return new CardViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        final int pos = position;
        holder.getQuestion().setText(cards.get(pos).getQuestion());
        holder.getCardView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CardContentActivity.class);
                intent.putExtra("card",cards.get(pos));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    protected class CardViewHolder extends RecyclerView.ViewHolder{
        private TextView question;
        private CardView cardView;
        public CardViewHolder(View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.tv_question);
            cardView = itemView.findViewById(R.id.card_view);
        }

        public TextView getQuestion() {
            return question;
        }

        public void setQuestion(TextView question) {
            this.question = question;
        }

        public CardView getCardView() {
            return cardView;
        }

        public void setCardView(CardView cardView) {
            this.cardView = cardView;
        }
    }
}
