package com.jackwelter.eggtimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button controllerButton;
    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controllerButton = (Button) findViewById(R.id.controllerButton);
        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
        public void controlTimer(View view){

            if(counterIsActive == false) {
                counterIsActive = true;
                timerSeekBar.setEnabled(false);
                controllerButton.setText("Stop!");

                countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        updateTimer((int) millisUntilFinished / 1000);
                        timerSeekBar.setProgress((int) millisUntilFinished / 1000);
                    }
                    @Override
                    public void onFinish() {
                        timerTextView.setText("0:00");
                        controllerButton.setText("Reset");
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                        mediaPlayer.start();
                    }
                }.start();
            }else if (controllerButton.getText() == "Reset"){
                counterIsActive = false;
                timerSeekBar.setProgress(30);
                timerSeekBar.setEnabled(true);
                countDownTimer.cancel();
                controllerButton.setText("G0!");
            }else{
                counterIsActive = false;
                updateTimer(timerSeekBar.getProgress());
                timerSeekBar.setEnabled(true);
                countDownTimer.cancel();
                controllerButton.setText("Go!");
            }
    }
        public void updateTimer(int secLeft){
            int min = secLeft / 60;
            int sec = secLeft % 60;
            timerTextView.setText(Integer.toString(min)+":"+String.format("%02d",sec));
        }
}

