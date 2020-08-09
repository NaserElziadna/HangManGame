package com.nmmsoft.hangman;
///////////////////////////
//  Designed and Creared //
//     Naser Elziadna    //
//         Thanks♥       //
///////////////////////////

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nmmsoft.hangman.logic.DatabaseHelper;
import com.nmmsoft.hangman.logic.MyApp;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper db;

    Button newGame_btn;
    Button continueGame_btn;
    Button aboutGame_btn;
    Button exitGame_btn;
    TextView playerName_tv;

    private String pName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db=new DatabaseHelper(MainActivity.this);

        this.playerName_tv=(TextView) findViewById(R.id.player_Name);
        this.continueGame_btn=(Button) findViewById(R.id.btn_continueGame);

        Intent intent = getIntent();
        pName = intent.getStringExtra("Player");
        this.playerName_tv.setText(pName);
        changeContinueButtonBg();
    }

    private void changeContinueButtonBg(){
        if (MyApp.getInstance().getIsContinue()){
            continueGame_btn.setEnabled(true);
        }
        else {
            continueGame_btn.setEnabled(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeContinueButtonBg();
    }

    public void gotoNewGame(View view) {
        Intent intent = new Intent(this, BoardGame.class);
        String text = playerName_tv.getText().toString();
        intent.putExtra("Player", text);
        intent.putExtra("Score", db.getPlayerScore(pName));
        intent.putExtra("isContinue", false);
        startActivity(intent);
    }

    public void gotoAbout(View view) {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
    }

    public void Exit(View view) {
        finish();
        System.exit(0);
    }

    public void gotoContinue(View view) {
        Intent intent = new Intent(this, BoardGame.class);
        intent.putExtra("Player", MyApp.getInstance().getCurrentPlayer());
        intent.putExtra("Score",MyApp.getInstance().getCurrentScore() );
        intent.putExtra("isContinue",true);
        startActivity(intent);

    }
}