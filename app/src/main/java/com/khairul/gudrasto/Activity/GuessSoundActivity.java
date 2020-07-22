package com.khairul.gudrasto.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khairul.gudrasto.Helper.DatabaseHelperUser;
import com.khairul.gudrasto.Helper.SharePrefManager;
import com.khairul.gudrasto.Model.DataGuess;
import com.khairul.gudrasto.Model.GuessModels;
import com.khairul.gudrasto.R;

import java.util.ArrayList;
import java.util.Collections;

public class GuessSoundActivity extends AppCompatActivity {

    private DatabaseHelperUser databaseHelperUser;
    private SharePrefManager sharePrefManager;

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;
    private GuessModels currentQuestion;

    private ImageView sound;

    private Handler handler = new Handler();

    private TextView textViewQuestions;
    private TextView textViewScore;
    private TextView textViewCorrext, textViewWrong;

    private ArrayList<GuessModels> questionsList;
    private int questionCounter = 0;
    private int totalGuess = 0;
    private int questionTotalCount;
    private boolean answerd;

    private int corectAns = 0, wrongAns = 0, point = 0;
    private int idSound;

    RadioButton radioButton;

    MediaPlayer player;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_sound);
        radioButton = findViewById(R.id.radioButton1Guess);

        setupUI();
        fetchDB();

    }

    private void setupUI() {

        databaseHelperUser = new DatabaseHelperUser(this);
        sharePrefManager = new SharePrefManager(this);

        textViewCorrext = findViewById(R.id.correctGuess);
        textViewWrong = findViewById(R.id.wrongGuess);
        textViewQuestions = findViewById(R.id.totalGuess);
        textViewScore = findViewById(R.id.pointGuess);

        rbGroup = findViewById(R.id.radioGroupGuess);
        rb1 = findViewById(R.id.radioButton1Guess);
        rb2 = findViewById(R.id.radioButton2Guess);
        rb3 = findViewById(R.id.radioButton3Guess);
        rb4 = findViewById(R.id.radioButton4Guess);

        buttonConfirmNext = findViewById(R.id.btn_confirm);

        sound = findViewById(R.id.sound);

        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.isPlaying() == true) {
                    player.pause();
                }else{
                    player.start();
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void fetchDB() {
        questionsList = DataGuess.getDataGuess();

        startGuess();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void startGuess() {
        questionTotalCount = questionsList.size();
        Collections.shuffle(questionsList);

        showGuess();

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                switch (checked){
                    case R.id.radioButton1Guess:
                        rb1.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_selected));
                        rb2.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
                        rb3.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
                        rb4.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
                        break;

                    case R.id.radioButton2Guess:
                        rb1.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
                        rb2.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_selected));
                        rb3.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
                        rb4.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
                        break;

                    case R.id.radioButton3Guess:
                        rb1.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
                        rb2.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
                        rb3.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_selected));
                        rb4.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
                        break;

                    case R.id.radioButton4Guess:
                        rb1.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
                        rb2.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
                        rb3.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
                        rb4.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_selected));

                        break;

                }
            }
        });

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answerd){
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){

                        guessOperations();
                    }else{
                        Toast.makeText(GuessSoundActivity.this, "Please Select Options", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showGuess() {
        rbGroup.clearCheck();

        rb1.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
        rb2.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
        rb3.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));
        rb4.setForeground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.guess_sound_no_selected));

        if (questionCounter < questionTotalCount){
            currentQuestion = questionsList.get(questionCounter);

            idSound = getResources().getIdentifier(currentQuestion.getSound(), "raw", getPackageName());
            player = MediaPlayer.create(this, idSound);
            player.setLooping(false);
            player.setVolume(1.0f, 1.0f);

            rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(), currentQuestion.getOption1()));
            rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(), currentQuestion.getOption2()));
            rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(), currentQuestion.getOption3()));
            rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(), currentQuestion.getOption4()));

            totalGuess++;
            questionCounter++;
            answerd =false;

            textViewQuestions.setText("Tebak Suara: " + totalGuess);
        }else{
            questionCounter = 0;
            Collections.shuffle(questionsList);
            showGuess();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void guessOperations() {
        answerd = true;

        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbselected) + 1;

        checkSolution(answerNr, rbselected);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkSolution(int answerNr, RadioButton rbselected) {

        switch (currentQuestion.getAnswerNr()){
            case 1:
                if (currentQuestion.getAnswerNr() == answerNr){
                    rb1.setForeground(ContextCompat.getDrawable(this, R.drawable.guess_sound_correct));

                    corectAns++;
                    point += 50;
                    textViewScore.setText("Point: " + point);
                    textViewCorrext.setText("Benar: " + String.valueOf(corectAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showGuess();
                        }
                    },1000);

                }else{
                    changeIncorrecColor(rbselected);
                    wrongAns++;
                    textViewWrong.setText("Salah: "+ String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showGuess();
                        }
                    },1000);
                }
                break;
            case 2:
                if (currentQuestion.getAnswerNr() == answerNr){
                    rb2.setForeground(ContextCompat.getDrawable(this, R.drawable.guess_sound_correct));
                    corectAns++;
                    point += 50;
                    textViewScore.setText("Point: " + point);
                    textViewCorrext.setText("Benar: " + String.valueOf(corectAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showGuess();
                        }
                    },1000);
                }else{
                    changeIncorrecColor(rbselected);
                    wrongAns++;
                    textViewWrong.setText("Salah: "+ String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showGuess();
                        }
                    },1000);
                }
                break;

            case 3:
                if (currentQuestion.getAnswerNr() == answerNr){
                    rb3.setForeground(ContextCompat.getDrawable(this, R.drawable.guess_sound_correct));

                    corectAns++;
                    point += 50;
                    textViewScore.setText("Point: " + point);
                    textViewCorrext.setText("Benar: " + String.valueOf(corectAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showGuess();
                        }
                    },1000);
                }else{
                    changeIncorrecColor(rbselected);
                    wrongAns++;
                    textViewWrong.setText("Salah: "+ String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showGuess();
                        }
                    },1000);
                }
                break;
            case 4:
                if (currentQuestion.getAnswerNr() == answerNr){
                    rb4.setForeground(ContextCompat.getDrawable(this, R.drawable.guess_sound_correct));

                    corectAns++;
                    point += 50;
                    textViewScore.setText("Point: " + point);
                    textViewCorrext.setText("Benar: " + String.valueOf(corectAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showGuess();
                        }
                    },1000);
                }else{
                    changeIncorrecColor(rbselected);
                    wrongAns++;
                    textViewWrong.setText("Salah: "+ String.valueOf(wrongAns));
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            showGuess();
                        }
                    },1000);
                }
                break;

        } // end of switch statement

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void changeIncorrecColor(RadioButton rbselected) {
        rbselected.setForeground(ContextCompat.getDrawable(this, R.drawable.guess_sound_wrong));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (Integer.parseInt(sharePrefManager.getSpGuessBest()) < point){
            databaseHelperUser.updateBestPointGuess(Integer.toString(point), Integer.toString(sharePrefManager.getSPId()));
            sharePrefManager.saveSPString(SharePrefManager.SP_GUESS_BEST, Integer.toString(point));
        }
        databaseHelperUser.updateLastPointGuess(Integer.toString(point), Integer.toString(sharePrefManager.getSPId()));
        sharePrefManager.saveSPString(SharePrefManager.SP_GUESS_WEEK, Integer.toString(point));
    }

}
