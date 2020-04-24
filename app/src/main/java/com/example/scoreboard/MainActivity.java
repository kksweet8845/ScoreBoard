package com.example.scoreboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button rightBtn, leftBtn;
    TextView[] rightTexts, leftTexts, gameTexts;
    static final String STATE_RIGHT = "scoreRight";
    static final String STATE_LEFT = "scoreLeft";
    static final String STATE_GAMESET = "gameSet";

    static final String SCORE_FRAGMENT = "scoreboard";
    static final String HISTORY_FRAGMENT = "history";
    static final String SETTINGS_FRAGMENT = "settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // load fragment
//        if(savedInstanceState == null)
        loadFragment(savedInstanceState, SCORE_FRAGMENT);
//        if(savedInstanceState != null){
//            // restoration
//            restoreUI(savedInstanceState);
//        }else{
//            // initialization
//            initUI();
//        }
    }


    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // TODO restore isntance state
    }

    public void loadFragment(Bundle savedInstanceState, String fragmentName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch(fragmentName) {
            case SCORE_FRAGMENT:
                ScoreboardFragment fragment = ScoreboardFragment.newInstance(savedInstanceState);
                fragmentTransaction.add(R.id.main_layout, fragment);
                break;
            case HISTORY_FRAGMENT:
                // TODO HISTORY fragment
                break;
            case SETTINGS_FRAGMENT:
                // TODO SETTINGS fragment
                break;
        }
        fragmentTransaction.commit();
    }

//    private void restoreUI(Bundle savedInstanceState) {
//        initUI();
//        char[] right = savedInstanceState.getString(STATE_RIGHT).toCharArray();
//        char[] left = savedInstanceState.getString(STATE_LEFT).toCharArray();
//        char[] game = savedInstanceState.getString(STATE_GAMESET).toCharArray();
//        for(int i=0;i<2;i++){
//            Log.i("Log",  Integer.toString(i) + " times");
//            rightTexts[i].setText(right, i, 1);
//            leftTexts[i].setText(left, i, 1);
//            gameTexts[i].setText(game, i, 1);
//        }
//
//    }

//    private void initUI() {
//        /* Initialization */
//        rightTexts = new TextView[] { (TextView) findViewById(R.id.scoreBoard_right_digits),
//                (TextView) findViewById(R.id.scoreBoard_right_tenDigits)};
//        leftTexts = new TextView[] { (TextView) findViewById(R.id.scoreBoard_left_digits),
//                (TextView) findViewById(R.id.scoreBoard_left_tenDigits)};
//        gameTexts = new TextView[] { findViewById(R.id.scoreBoard_left_set), findViewById(R.id.scoreBoard_right_set)};
//
//        rightBtn = (Button) findViewById(R.id.right_plus_btn);
//        leftBtn = (Button) findViewById(R.id.left_plus_btn);
//
//        // set button onClickListener
//        rightBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scoreBoardPlusOne(rightTexts);
//            }
//        });
//
//        leftBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                scoreBoardPlusOne(leftTexts);
//            }
//        });
//    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        System.out.println("OnSaveInstanceState");
//        // right textViews
//        StringBuilder rightStr = new StringBuilder(); // in order to save memory
//        StringBuilder leftStr= new StringBuilder();
//        StringBuilder gameSet = new StringBuilder();
//        for(int i=0;i<2;i++){
//            rightStr.append(rightTexts[i].getText().toString());
//            leftStr.append(leftTexts[i].getText().toString());
//            gameSet.append(gameTexts[i].getText().toString());
//        }
//        System.out.println("right" + rightStr.toString());
//        System.out.println( "left" + leftStr.toString());
//        System.out.println( "gameset" + gameSet.toString());
//        outState.putString(STATE_RIGHT, rightStr.toString());
//        outState.putString(STATE_LEFT, leftStr.toString());
//        outState.putString(STATE_GAMESET, gameSet.toString());
        super.onSaveInstanceState(outState);
    }

//    private void scoreBoardPlusOne(TextView[] texts) {
//        int digits, tenDigits;
//        char[] digits_char, tenDigits_char;
//        digits = Integer.parseInt(texts[0].getText().toString());
//        tenDigits = Integer.parseInt(texts[1].getText().toString());
//        digits += 1;
//        if(digits >= 10){
//            digits = 0;
//            tenDigits += 1;
//        }
//        digits_char = Integer.toString(digits).toCharArray();
//        tenDigits_char = Integer.toString(tenDigits).toCharArray();
//        texts[0].setText(digits_char, 0, digits_char.length);
//        texts[1].setText(tenDigits_char, 0, tenDigits_char.length);
//    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            setContentView(R.layout.activity_main);
//        }else{
//            setContentView(R.layout.activity_main_l);
//        }
//    }


    @Override
    protected void onDestroy() {
        FragmentManager mana = getSupportFragmentManager();
        Fragment fragment = mana.findFragmentById(R.id.scoreboard_fragment);
        FragmentTransaction fragmentTransaction = mana.beginTransaction();
        if(fragment != null)
            fragmentTransaction.remove(fragment);
        super.onDestroy();
    }
}
