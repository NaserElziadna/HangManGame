package com.nmmsoft.hangman;
///////////////////////////
//  Designed and Creared //
//     Naser Elziadna    //
//         Thanks♥       //
///////////////////////////

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nmmsoft.hangman.logic.DatabaseHelper;
import com.nmmsoft.hangman.logic.Game;
import com.nmmsoft.hangman.logic.MyApp;
import com.nmmsoft.hangman.logic.MyData;
import com.nmmsoft.hangman.logic.Player;
import com.nmmsoft.hangman.logic.Scoring;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BoardGame extends AppCompatActivity {
    DatabaseHelper db;

    private ImageView img;
    private TextView wordTv;
    private TextView livesTv;
    private TextView nameTv;
    private TextView scoreTv;
    private TextView wordToFindTv;

    Game hangman;
    Player player;
    Scoring scoring;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_game);

        db=new DatabaseHelper(BoardGame.this);

        hangman=new Game();
        scoring=new Scoring();
        player=new Player();

        this.img = findViewById(R.id.img_hangman);
        this.wordTv = findViewById(R.id.wordTv);
        this.wordToFindTv = findViewById(R.id.wordToFindTv);
        this.livesTv = findViewById(R.id.lives);
        this.nameTv =findViewById(R.id.player_Name_BoardGame);
        this.scoreTv =findViewById(R.id.player_Score_BoardGame);

        Intent intent = getIntent();

        //initialing the game status [new game / continue game]
        if (intent.getBooleanExtra("isContinue",false)){
            nameTv.setText(MyApp.getInstance().getCurrentPlayer());
            scoreTv.setText(String.valueOf(MyApp.getInstance().getCurrentScore()));
            continueGame();
        }
        else {
            nameTv.setText(intent.getStringExtra("Player"));
            scoreTv.setText(intent.getStringExtra("Score"));

            Toast.makeText(this, scoreTv.getText(), Toast.LENGTH_SHORT).show();

            scoring.setScore(Integer.parseInt(intent.getStringExtra("Score")));
            player.setName(String.valueOf(nameTv.getText()));
            player.setScore(Integer.parseInt(scoreTv.getText().toString()));
            newGame();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {//active when i ever press the back button of the phone
            saveGame();
        }
        return super.onKeyDown(keyCode, event);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void updateActivityViews() {
        String currentWord = MyApp.getInstance().getCurrentWord().toUpperCase();
        ArrayList<String> allLetters = new ArrayList<>(MyApp.getInstance().getLettersEntered());

        ArrayList<String> wrong_Letters = new ArrayList<>();
        ArrayList<String> correct_Letters = new ArrayList<>();


        for (String l : allLetters) {
            if (currentWord.contains(l)) {
                correct_Letters.add(l);
            } else {
                wrong_Letters.add(l);
            }
        }

        updateImg(wrong_Letters.size());//update the image according to the wrong letters count
        livesTv.setText(String.valueOf(7-wrong_Letters.size()));
        nameTv.setText(MyApp.getInstance().getCurrentPlayer());

        LinearLayout layout = findViewById(R.id.linearLayout_keyboard);
        for (int a = 0; a < layout.getChildCount(); a++) {
            LinearLayout child1 = (LinearLayout) layout.getChildAt(a);

            int count = child1.getChildCount();
            Button v = null;

            for (int i = 0; i < count; i++) {
                v = (Button) child1.getChildAt(i);
                if (correct_Letters.contains(v.getText()))
                {
                    v.setBackground(getResources().getDrawable(R.mipmap.correct_letter_bg));
                    v.setEnabled(false);
                }
                else if (wrong_Letters.contains(v.getText())){
                    v.setBackground(getResources().getDrawable(R.mipmap.wrong_letter_bg));
                    v.setEnabled(false);
                }
                else {
                    v.setBackgroundColor(Color.GRAY);
                    v.setEnabled(true);
                }
            }
        }

    }

    //method for save a instance of the game for continuing purpose
    private void saveGame() {
        if (hangman.getCurrentLive() < 1)//the player lose [no lives lift "♥"] - cant continue
        {
            hangman.setIsContinue(false);
            MyApp.getInstance().setIsContinue(hangman.isContinue());
        }
        else { //if game not finish - [have life to continue]
            MyApp.getInstance().setCurrentWord(hangman.getWordToFind());
            Set<String> stringSet = new HashSet<>(hangman.getLetters());
            MyApp.getInstance().setLettersEntered(stringSet);
            MyApp.getInstance().setCurrentPlayer(player.getName());
            MyApp.getInstance().setCurrentScore(player.getScore());
            hangman.setIsContinue(true);
            MyApp.getInstance().setIsContinue(hangman.isContinue());
        }
    }

    // Method for starting a new game
    public void newGame() {
        LinearLayout layout = findViewById(R.id.linearLayout_keyboard);
        for (int a=0;a<layout.getChildCount();a++) {
            LinearLayout child1 = (LinearLayout) layout.getChildAt(a);

            int count = child1.getChildCount();
            View v = null;

            for (int i = 0; i < count; i++) {
                v = (Button) child1.getChildAt(i);
                v.setBackgroundColor(Color.GRAY);
                //do something with your child element
            }
        }

        hangman.setNbErrors(-1);
        hangman.getLetters().clear();
        hangman.setWordToFind(hangman.nextWordToFind());
        livesTv.setText(String.valueOf(hangman.getCurrentLive()));

        // word found initialization
        hangman.setWordFound(new char[hangman.getWordToFind().length()]);

        for (int i = 0; i < hangman.getWordFound().length; i++) {
            hangman.getWordFound()[i] = '_';
        }

        updateImg(hangman.getNbErrors());
        wordTv.setText(hangman.wordFoundContent());
        wordToFindTv.setText("");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void continueGame(){
        updateActivityViews();
    }

    // Method updating the word found after user entered a character
    private boolean enter(String c) {
        // we update only if c has not already been entered
        if (!hangman.getLetters().contains(c)) {
            // we check if word to find contains c
            if (hangman.getWordToFind().contains(c)) {
                // if so, we replace _ by the character c
                int index = hangman.getWordToFind().indexOf(c);

                while (index >= 0) {
                    hangman.getWordFound()[index] = c.charAt(0);
                    index = hangman.getWordToFind().indexOf(c, index + 1);
                }
               scoringAndUpdate(true);
                // c is now a letter entered
                hangman.getLetters().add(c);
                return true;
            } else {
                // c not in the word => error
                hangman.add1toNbErrors();
                Toast.makeText(this, R.string.try_an_other, Toast.LENGTH_SHORT).show();
                scoringAndUpdate(false);

            }

            // c is now a letter entered
            hangman.getLetters().add(c);
        } else {
            Toast.makeText(this, R.string.letter_already_entered, Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    //update the current player Score in the database and in real game life
    private void scoringAndUpdate(boolean a){
        if (a){
            scoreTv.setText(String.valueOf(scoring.getandaddScore()));
        }
        else {
            scoreTv.setText(String.valueOf(scoring.getandtakeScore()));
        }
        player.setScore(scoring.getScore());
        scoring.updateDatabase(db,player);
    }

    //update the status of my imageview in my board
    private void updateImg(int play) {
        int resImg = getResources().getIdentifier("hang" + play, "drawable", getPackageName());
        img.setImageResource(resImg);
    }

    //method activate when i ever press any button of the
    //english alphabet
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void touchLetter(View v) {
        if (hangman.getNbErrors() < Game.getMaxErrors()
                && !getString(R.string.you_win).equals(wordToFindTv.getText())) {
            String letter = ((Button) v).getText().toString();
            if(enter(letter)){
                v.setBackground(getResources().getDrawable(R.mipmap.correct_letter_bg));
                v.setEnabled(false);
            }
            else{
                v.setBackground(getResources().getDrawable(R.mipmap.wrong_letter_bg));
                v.setEnabled(false);
                hangman.takeLive();
                livesTv.setText(String.valueOf(hangman.getCurrentLive()));
            }
            wordTv.setText(hangman.wordFoundContent());
            updateImg(hangman.getNbErrors());


            // check if word is found
            if (hangman.wordFounded()) {
                Toast.makeText(this, R.string.you_win, Toast.LENGTH_SHORT).
                        show();
                wordToFindTv.setText(R.string.you_win);
            }
            else if (hangman.wordFounded()&&hangman.getCurrentLive()>0){newGame();}
                else {
                if (hangman.getNbErrors() >= Game.getMaxErrors()) {
                    Toast.makeText(this, R.string.you_lose, Toast.LENGTH_SHORT).show();
                    wordToFindTv.setText(getString(R.string.word_to_find).
                            replace("#word#", hangman.getWordToFind()));
                }
            }
        } else {
            Toast.makeText(this, R.string.game_is_ended, Toast.LENGTH_SHORT).show();
        }
    }
}