package com.londonappbrewery.quizzler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    // TODO: Declare constants here



    // TODO: Declare member variables here:
    Button mTrueButton;
    Button mFalseButton;
    TextView mQuestionTextView;
    TextView mScoreTextView;
    ProgressBar mProgressBar;
    int mScore = 0;
    int mIndex = 0;

    // TODO: Uncomment to create question bank
    private TrueFalse[] mQuestionBank = new TrueFalse[] {
        new TrueFalse(R.string.question_1, true),
        new TrueFalse(R.string.question_2, true),
        new TrueFalse(R.string.question_3, true),
        new TrueFalse(R.string.question_4, true),
        new TrueFalse(R.string.question_5, true),
        new TrueFalse(R.string.question_6, false),
        new TrueFalse(R.string.question_7, true),
        new TrueFalse(R.string.question_8, false),
        new TrueFalse(R.string.question_9, true),
        new TrueFalse(R.string.question_10, true),
        new TrueFalse(R.string.question_11, false),
        new TrueFalse(R.string.question_12, false),
        new TrueFalse(R.string.question_13,true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mScoreTextView = (TextView) findViewById(R.id.score);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mScoreTextView.setText(R.string.initial_score);
        mQuestionTextView.setText(mQuestionBank[mIndex].getQuestionID());

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                updateQuestion();
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                updateQuestion();
            }
        });

        if (savedInstanceState != null) {
            mScore = savedInstanceState.getInt("ScoreKey");
            mIndex = savedInstanceState.getInt("IndexKey");
            mScoreTextView.setText("Score " + Integer.toString(mScore) + "/" + mQuestionBank.length);
        } else {
            mScore = 0;
            mIndex = 0;
        }
    }

    private void updateQuestion () {
        mIndex = (mIndex + 1) % mQuestionBank.length;

        if (mIndex == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Game Over");
            alert.setCancelable(false);
            alert.setMessage("You scored " + mScore + " points!");
            alert.setPositiveButton("Close Application", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.show();
        }
        mQuestionTextView.setText(mQuestionBank[mIndex].getQuestionID());
    }

    private void checkAnswer (boolean userSelection) {
        boolean correctAnswer = mQuestionBank[mIndex].isAnswer();
        Toast myToast;
        if (userSelection == correctAnswer) {
            mScore++;
            int progressNumber = (int) (((double) mScore / (double) mQuestionBank.length) * 100);
            mProgressBar.setProgress(progressNumber);
            mScoreTextView.setText("Score " + Integer.toString(mScore) + "/" + mQuestionBank.length);
            myToast = Toast.makeText(getApplicationContext(), R.string.correct_toast, Toast.LENGTH_SHORT);
        } else {
            myToast = Toast.makeText(getApplicationContext(), R.string.incorrect_toast, Toast.LENGTH_SHORT);
        }
        myToast.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("ScoreKey", mScore);
        outState.putInt("IndexKey", mIndex);

    }
}
