package com.aaronguostudio.diceeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button rollButton = findViewById(R.id.rollButton);
        final ImageView leftDice = findViewById(R.id.image_leftDice);
        final ImageView rightDice = findViewById(R.id.image_rightDice);
        final int[] diceArray = {
            R.drawable.dice1,
            R.drawable.dice2,
            R.drawable.dice3,
            R.drawable.dice4,
            R.drawable.dice5,
            R.drawable.dice6,
        };


        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Dice", "XU");
                Random randomNumberGenerator = new Random();
                int number1 = randomNumberGenerator.nextInt(6);
                int number2 = randomNumberGenerator.nextInt(6);

                leftDice.setImageResource(diceArray[number1]);
                rightDice.setImageResource(diceArray[number2]);
            }
        });

    }
}
