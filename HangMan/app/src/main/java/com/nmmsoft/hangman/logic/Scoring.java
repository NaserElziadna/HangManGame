package com.nmmsoft.hangman.logic;
///////////////////////////
//  Designed and Creared //
//     Naser Elziadna    //
//         Thanks♥       //
///////////////////////////

import androidx.appcompat.app.AppCompatActivity;

public class Scoring extends AppCompatActivity {
    private int score;

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score=score;

    }

    public int getandaddScore() {
        this.score+=15;
        return getScore();
    }

    public int getandtakeScore() {
        this.score-=3;
        return getScore();
    }

    public  void updateDatabase(DatabaseHelper db,Player player){
        db.updatePlayerScoreData(player.getName(),player.getScore());
    }


}
