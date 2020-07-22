package com.khairul.gudrasto.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.khairul.gudrasto.Adapter.FinalScoreDialog;
import com.khairul.gudrasto.Helper.DatabaseHelperUser;
import com.khairul.gudrasto.Helper.SharePrefManager;
import com.khairul.gudrasto.Model.Questions;
import com.khairul.gudrasto.Helper.QuizDbHelper;
import com.khairul.gudrasto.R;

import java.util.ArrayList;
import java.util.Collections;

public class StoryNQuestionActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    private FinalScoreDialog finalScoreDialog;

    private DatabaseHelperUser databaseHelperUser;
    private SharePrefManager sharePrefManager;

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;
    private NestedScrollView scrollView;

    private TextView textViewQuestions;
    private TextView textViewScore;
    private TextView textViewQuestionCount;
    private TextView textViewCorrext, textViewWrong;

    private TextView title_story;
    private TextView story_story;
    private ImageView img_story;

    private int questionCounter = 0;
    private int totalQustionsAns = 0;

    private ArrayList<Questions> questionsList;
    private Questions currentQuestion;

    private int questionTotalCount = 0;
    private int corectAns = 0, wrongAns = 0, point = 0;
    private boolean answerd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_n_question);

        setupUI();
        fetchDB();

        finalScoreDialog = new FinalScoreDialog(this);
    }

    private void setupUI() {
        databaseHelperUser = new DatabaseHelperUser(this);
        sharePrefManager = new SharePrefManager(this);

        title_story = findViewById(R.id.title_story);
        story_story = findViewById(R.id.story_story);
        img_story = findViewById(R.id.img_story);

        textViewQuestions = findViewById(R.id.txtQuestion);
        textViewScore = findViewById(R.id.txtPoint);
        textViewCorrext = findViewById(R.id.txtCorrect);
        textViewWrong = findViewById(R.id.txtWrong);
        textViewQuestionCount = findViewById(R.id.txtTotalQuestion);
        buttonConfirmNext = findViewById(R.id.btnComfirm);
        scrollView = findViewById(R.id.scrollviewparent);

        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radioButton1);
        rb2 = findViewById(R.id.radioButton2);
        rb3 = findViewById(R.id.radioButton3);
        rb4 = findViewById(R.id.radioButton4);

    }

    private void fetchDB() {
        QuizDbHelper dbHelper = new QuizDbHelper(this);
        questionsList = dbHelper.getAllQuestions();

//        title_story.setText(questionsList.get(questionCounter).getTitle());
//        story_story.setText(questionsList.get(questionCounter).getStory());
//        img_story.setImageResource(questionsList.get(questionCounter).getImg());
//
//        textViewQuestions.setText(questionsList.get(questionCounter).getQuestion());
//        rb1.setText(questionsList.get(questionCounter).getOption1());
//        rb2.setText(questionsList.get(questionCounter).getOption2());
//        rb3.setText(questionsList.get(questionCounter).getOption3());
//        rb4.setText(questionsList.get(questionCounter).getOption4());
        startQuiz();
    }

    private void startQuiz() {
        questionTotalCount = questionsList.size();
        Collections.shuffle(questionsList);

        showQuestions();

        rbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {
                switch (checked){
                    case R.id.radioButton1:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

                        rb1.setTextColor(Color.WHITE);
                        rb2.setTextColor(Color.BLACK);
                        rb3.setTextColor(Color.BLACK);
                        rb4.setTextColor(Color.BLACK);
                        break;

                    case R.id.radioButton2:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

                        rb1.setTextColor(Color.BLACK);
                        rb2.setTextColor(Color.WHITE);
                        rb3.setTextColor(Color.BLACK);
                        rb4.setTextColor(Color.BLACK);
                        break;

                    case R.id.radioButton3:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb1.setTextColor(Color.BLACK);
                        rb2.setTextColor(Color.BLACK);
                        rb3.setTextColor(Color.WHITE);
                        rb4.setTextColor(Color.BLACK);
                        break;

                    case R.id.radioButton4:
                        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
                        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.when_option_selected));
                        rb1.setTextColor(Color.BLACK);
                        rb2.setTextColor(Color.BLACK);
                        rb3.setTextColor(Color.BLACK);
                        rb4.setTextColor(Color.WHITE);
                        break;
                }
            }
        });

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!answerd){
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()){
                        quizOperations();
                    }else{
                        Toast.makeText(StoryNQuestionActivity.this, "Please Select Options", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void showQuestions() {
        rbGroup.clearCheck();
        scrollView.fullScroll(NestedScrollView.FOCUS_UP);

        rb1.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
        rb2.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
        rb3.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));
        rb4.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buttons_background));

        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);

        if (questionCounter < questionTotalCount){
            currentQuestion = questionsList.get(questionCounter);

            title_story.setText(currentQuestion.getTitle());
            story_story.setText(currentQuestion.getStory());
            img_story.setImageResource(currentQuestion.getImg());
            textViewQuestions.setText(currentQuestion.getQuestion());
            textViewQuestions.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());

            questionCounter++;
            totalQustionsAns++;
            answerd = false;

            buttonConfirmNext.setText("Confirm");

            textViewQuestionCount.setText("Questions : " +totalQustionsAns);

        }else{
            questionCounter = 0;
            startQuiz();
        }

    }

    private void quizOperations() {
        answerd = true;

        RadioButton rbselected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbselected) + 1;

        checkSolution(answerNr, rbselected);
    }

    private void checkSolution(int answerNr, RadioButton rbselected) {
        switch (currentQuestion.getAnswerNr()){
            case 1:
                if (currentQuestion.getAnswerNr() == answerNr){
                    rb1.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));

                    corectAns++;
                    point += 50;
                    textViewCorrext.setText("Correct: " + String.valueOf(corectAns));
                    textViewScore.setText("Point: " + String.valueOf(point));
                    refreshQuestions();

                }else{
                    changeIncorrecColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: "+ String.valueOf(wrongAns));
                    refreshQuestions();
                }
                break;
            case 2:
                if (currentQuestion.getAnswerNr() == answerNr){
                    rb2.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));

                    corectAns++;
                    point += 50;
                    textViewCorrext.setText("Correct: " + String.valueOf(corectAns));
                    textViewScore.setText("Point: " + String.valueOf(point));
                    refreshQuestions();
                }else{
                    changeIncorrecColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: "+ String.valueOf(wrongAns));
                    refreshQuestions();
                }
                break;

            case 3:
                if (currentQuestion.getAnswerNr() == answerNr){
                    rb3.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));

                    corectAns++;
                    point += 50;
                    textViewCorrext.setText("Correct: " + String.valueOf(corectAns));
                    textViewScore.setText("Point: " + String.valueOf(point));
                    refreshQuestions();
                }else{
                    changeIncorrecColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: "+ String.valueOf(wrongAns));
                    refreshQuestions();
                }
                break;
            case 4:
                if (currentQuestion.getAnswerNr() == answerNr){
                    rb4.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_correct));

                    corectAns++;
                    point += 50;
                    textViewCorrext.setText("Correct: " + String.valueOf(corectAns));
                    textViewScore.setText("Point: " + String.valueOf(point));
                    refreshQuestions();
                }else{
                    changeIncorrecColor(rbselected);

                    wrongAns++;
                    textViewWrong.setText("Wrong: "+ String.valueOf(wrongAns));
                    refreshQuestions();
                }
                break;

        } // end of switch statement

    }

    public void refreshQuestions(){
        if (point % 250 == 0 && point != 0) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finalScoreDialog.finalScoreDialog(point);
                }
            }, 1000);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showQuestions();
            }}, 1000);

    }

    private void changeIncorrecColor(RadioButton rbselected) {
        rbselected.setBackground(ContextCompat.getDrawable(this, R.drawable.when_answer_wrong));
        rbselected.setTextColor(Color.WHITE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Toast.makeText(this, Integer.toString(point), Toast.LENGTH_SHORT).show();
        if (Integer.parseInt(sharePrefManager.getSpStoryBest()) < point){
            databaseHelperUser.updateBestPointStory(Integer.toString(point), Integer.toString(sharePrefManager.getSPId()));
            sharePrefManager.saveSPString(SharePrefManager.SP_STORY_BEST, Integer.toString(point));
        }
        databaseHelperUser.updateLastPointStory(Integer.toString(point), Integer.toString(sharePrefManager.getSPId()));
        sharePrefManager.saveSPString(SharePrefManager.SP_STORY_WEEK, Integer.toString(point));
    }
}
