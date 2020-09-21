package com.example.carmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SMS_Activity extends AppCompatActivity {
    static TextView reverseCounterTextView;
    static Button stopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_m_s_);
        Intent intent = getIntent();
        reverseCounterTextView = (TextView)findViewById(R.id.reverseCounterTextView);
        stopButton=(Button)findViewById(R.id.stopButton);
        CountDownTimer countDownTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                reverseCounterTextView.setText(Long.toString(millisUntilFinished/1000));

            }

            @Override
            public void onFinish() {

            }
        };
    }
}