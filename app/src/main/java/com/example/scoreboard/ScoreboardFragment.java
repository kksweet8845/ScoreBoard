package com.example.scoreboard;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScoreboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScoreboardFragment extends Fragment {

    private static final String STATE_RIGHT = "scoreRight";
    private static final String STATE_LEFT = "scoreLeft";
    private static final String STATE_GAMESET = "gameSet";

    private static final String SCORE_FRAGMENT = "scoreboard";
    private static final String HISTORY_FRAGMENT = "history";
    private static final String SETTINGS_FRAGMENT = "settings";

    private View rootView = null;

    Button rightBtn, leftBtn;
    TextView[] rightTexts, leftTexts, gameTexts;
    ImageButton changeScoreBtn;

    int totalScore = 11;


    public ScoreboardFragment() {
        // Required empty public constructor
    }

    public static ScoreboardFragment newInstance(Bundle state) {
        ScoreboardFragment fragment = new ScoreboardFragment();
        if(state == null)
            state = new Bundle();
            fragment.initState(state);

        fragment.setArguments(state);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scoreboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUI(view);
        super.onViewCreated(view, savedInstanceState);
    }


    private void initUI(View view) {
        /* Initialization */
        rootView = view;
        rightTexts = new TextView[] { (TextView) view.findViewById(R.id.scoreBoard_right_digits),
                (TextView) view.findViewById(R.id.scoreBoard_right_tenDigits)};
        leftTexts = new TextView[] { (TextView) view.findViewById(R.id.scoreBoard_left_digits),
                (TextView) view.findViewById(R.id.scoreBoard_left_tenDigits)};
        gameTexts = new TextView[] { view.findViewById(R.id.scoreBoard_left_set), view.findViewById(R.id.scoreBoard_right_set)};

        rightBtn = (Button) view.findViewById(R.id.right_plus_btn);
        leftBtn = (Button) view.findViewById(R.id.left_plus_btn);
        changeScoreBtn = (ImageButton) view.findViewById(R.id.score_change);
        // set button onClickListener
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameFlow(rightTexts, gameTexts[1]);
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameFlow(leftTexts, gameTexts[0]);
            }
        });

        changeScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                changeScore();
            }
        });
    }

    private Bundle initState(@NonNull Bundle bundle){
        bundle.putString(STATE_RIGHT, "00");
        bundle.putString(STATE_LEFT, "00");
        bundle.putString(STATE_GAMESET, "00");
        return bundle;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        System.out.println("OnSaveInstanceState");
        // right textViews
        StringBuilder rightStr = new StringBuilder(); // in order to save memory
        StringBuilder leftStr= new StringBuilder();
        StringBuilder gameSet = new StringBuilder();
        for(int i=0;i<2;i++){
            rightStr.append(rightTexts[i].getText().toString());
            leftStr.append(leftTexts[i].getText().toString());
            gameSet.append(gameTexts[i].getText().toString());
        }
        System.out.println("right" + rightStr.toString());
        System.out.println( "left" + leftStr.toString());
        System.out.println( "gameset" + gameSet.toString());
        outState.putString(STATE_RIGHT, rightStr.toString());
        outState.putString(STATE_LEFT, leftStr.toString());
        outState.putString(STATE_GAMESET, gameSet.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null){
            restoreUI(savedInstanceState);
        }
        super.onActivityCreated(savedInstanceState);
    }

    private void restoreUI(Bundle savedInstanceState) {
        initUI(rootView);
        char[] right = savedInstanceState.getString(STATE_RIGHT).toCharArray();
        char[] left = savedInstanceState.getString(STATE_LEFT).toCharArray();
        char[] game = savedInstanceState.getString(STATE_GAMESET).toCharArray();
        for(int i=0;i<2;i++){
            Log.i("Log",  Integer.toString(i) + " times");
            rightTexts[i].setText(right, i, 1);
            leftTexts[i].setText(left, i, 1);
            gameTexts[i].setText(game, i, 1);
        }

    }

    private void gameFlow(TextView[] board, TextView gameSetBoard) {
        int curScore = 0;
        // Pluse one score
        curScore = scoreBoardPlusOne(board, gameSetBoard);
        // check whether is finished
        if(curScore >= totalScore){
            gameSetFinished(board, gameSetBoard);
        }

    }

    private void gameSetFinished(TextView[] board, TextView gameSetBoard) {
        // start alert dialog
        FinishDialogFragment fra = new FinishDialogFragment(gameTexts);
        fra.show(getFragmentManager(), "dialog");

        // reset score
    }


    private int scoreBoardPlusOne(TextView[] texts, TextView gameSetBoard) {
        int digits, tenDigits, score;
        digits = Integer.parseInt(texts[0].getText().toString());
        tenDigits = Integer.parseInt(texts[1].getText().toString());
        digits += 1;
        score = tenDigits*10 + digits;
        if(digits >= 10){
            digits = 0;
            tenDigits += 1;
        }
        texts[0].setText(Integer.toString(digits));
        texts[1].setText(Integer.toString(tenDigits));
        return tenDigits*10+digits;
    }

    private void gameSetPlusOne(TextView gameSetBoard) {
        int set = Integer.parseInt(gameSetBoard.getText().toString());
        set += 1;
        gameSetBoard.setText(Integer.toString(set));
    }

    private void changeScore() {
        for(int i=0;i<2;i++){
            String tmp;
            tmp = rightTexts[i].getText().toString();
            rightTexts[i].setText(leftTexts[i].getText().toString());
            leftTexts[i].setText(tmp);
        }
    }

    public static class FinishDialogFragment extends DialogFragment {


        public FinishDialogFragment() {
            // Required constructor
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.finishedGameDialog)
                   .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           /**
                            * TODO Store the game history and restart a new game
                            */
                       }
                   })
                   .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id){
                           /**
                            * TODO Do not store the game history and restart a new game
                            */
                       }
                   });
            return builder.create();
        }
    }
}
