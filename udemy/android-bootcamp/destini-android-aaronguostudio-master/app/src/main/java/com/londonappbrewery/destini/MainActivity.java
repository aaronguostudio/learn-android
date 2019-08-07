package com.londonappbrewery.destini;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // TODO: Steps 4 & 8 - Declare member variables here:
    TextView mTextView;
    Button mButtonTop;
    Button mButtonBottom;
    int mStoryIndex = 0;
    private Story[] mStoryBank = new Story[] {
        new Story(R.string.T1_Story, R.string.T1_Ans1, R.string.T1_Ans2),
        new Story(R.string.T2_Story, R.string.T2_Ans1, R.string.T2_Ans2),
        new Story(R.string.T3_Story, R.string.T3_Ans1, R.string.T3_Ans2)
    };

    private Story[] mEndBank = new Story[] {
        new Story(R.string.T4_End),
        new Story(R.string.T5_End),
        new Story(R.string.T6_End)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Step 5 - Wire up the 3 views from the layout to the member variables:
        mTextView = findViewById(R.id.storyTextView);
        mButtonTop = findViewById(R.id.buttonTop);
        mButtonBottom = findViewById(R.id.buttonBottom);

        // TODO: Steps 6, 7, & 9 - Set a listener on the top button:
        mButtonTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStory(1);
            }
        });

        // TODO: Steps 6, 7, & 9 - Set a listener on the bottom button:

        mButtonBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateStory(2);
            }
        });
    }

    private void updateUI () {
        mTextView.setText(mStoryBank[mStoryIndex].getStory());

        mButtonTop.setAlpha(1);
        mButtonBottom.setAlpha(1);
        mButtonTop.setText(mStoryBank[mStoryIndex].getAnswer1());
        mButtonBottom.setText(mStoryBank[mStoryIndex].getAnswer2());
    }

    private void showEnd (int index) {
        mTextView.setText(mEndBank[index].getStory());
        mButtonTop.setAlpha(0);
        mButtonBottom.setAlpha(0);
    }

    private void updateStory (int answer) {
        if ((mStoryIndex + 1) % mStoryBank.length == 0) {
            showEnd(1);
        } else {
            mStoryIndex++;
            updateUI();
        }
    }
}
