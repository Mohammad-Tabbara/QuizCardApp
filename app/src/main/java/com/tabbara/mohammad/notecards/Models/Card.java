package com.tabbara.mohammad.notecards.Models;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Espfish on 12/4/2017.
 */
@Entity
public class Card implements Serializable {

    public static final String TABLE_NAME = "cheeses";

    @PrimaryKey(autoGenerate = true)
    private int cid;

    @ColumnInfo(name = "question")
    private String question;

    @ColumnInfo(name = "answer")
    private String answer;


    public int getCid() {
        return cid;
    }

    public void setCid(int cid){
        this.cid = cid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
