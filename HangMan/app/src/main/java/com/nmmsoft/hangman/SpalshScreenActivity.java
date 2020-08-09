package com.nmmsoft.hangman;
///////////////////////////
//  Designed and Creared //
//     Naser Elziadna    //
//         Thanks♥       //
///////////////////////////

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

public class SpalshScreenActivity extends AppCompatActivity {
    int secondsDelayed=0;
    TextView tv_CountDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_screen);
        tv_CountDown=(TextView)findViewById(R.id.tv_CountDown);
        tv_CountDown.setText(String.valueOf(secondsDelayed));

        CountDownTimer countDownTimer = new CountDownTimer(secondsDelayed*1000, 1000){
            @Override
            public void onTick(long millisUntilFinished) {
                tv_CountDown.setText(String.valueOf(millisUntilFinished/1000));
            }
            @Override
            public void onFinish() {
                tv_CountDown.setText("Go!");
                startActivity(new Intent(SpalshScreenActivity.this, ChoosePlayerActivity.class));
                finish();
            }
        }.start();

    }
}