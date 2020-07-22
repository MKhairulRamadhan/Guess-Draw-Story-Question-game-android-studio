package com.khairul.gudrasto.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.khairul.gudrasto.Helper.DatabaseHelperUser;
import com.khairul.gudrasto.Helper.SharePrefManager;
import com.khairul.gudrasto.Model.UserModels;
import com.khairul.gudrasto.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //shared preferences
    SharePrefManager sharedPrefManager;

    // database user
    DatabaseHelperUser db;

    private TextInputLayout textInputEmail, textInputPassword;
    Button btn_login;
    TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //shared prefences
        sharedPrefManager = new SharePrefManager(this);
        if (sharedPrefManager.getSPSudahLogin()){
            startActivity(new Intent(MainActivity.this, MenuActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }

        //db
        db = new DatabaseHelperUser(this);

        //txt data
        textInputEmail = findViewById(R.id.text_input_email);
        textInputPassword =findViewById(R.id.text_input_password);
        btn_login = findViewById(R.id.btn_login);
        signup = findViewById(R.id.tv_signup);


        btn_login.setOnClickListener(this);
        signup.setOnClickListener(this);
    }

    private boolean validateEmail(){
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()){
            textInputEmail.setError("Field can't be empty");
            return false;
        } else {
            textInputEmail.setError(null);
            textInputEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassword(){
        String passwordInput = textInputPassword.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()){
            textInputPassword.setError("Field can't be empty");
            return false;
        } else {
            textInputPassword.setError(null);
            textInputPassword.setErrorEnabled(false);
            return true;
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_login:
                if (!validateEmail() || !validatePassword()){
                  return;
                }

                String email = textInputEmail.getEditText().getText().toString().trim();
                String password = textInputPassword.getEditText().getText().toString().trim();

                UserModels user = db.checkUser(email, password);
                if (user == null){
                    Toast.makeText(this, "Email atau password salah!", Toast.LENGTH_SHORT).show();
                }else{
                    //shared preferences
                    sharedPrefManager.saveSPBoolean(SharePrefManager.SP_LOGIN_DONE, true);
                    sharedPrefManager.saveSPInt(SharePrefManager.SP_ID, user.getId());
                    sharedPrefManager.saveSPString(SharePrefManager.SP_NAME, user.getName());
                    sharedPrefManager.saveSPString(SharePrefManager.SP_STORY_BEST, user.getBest_score_Story());
                    sharedPrefManager.saveSPString(SharePrefManager.SP_GUESS_BEST, user.getBest_score_Guess());
                    sharedPrefManager.saveSPString(SharePrefManager.SP_STORY_WEEK, user.getWeek_score_Story());
                    sharedPrefManager.saveSPString(SharePrefManager.SP_GUESS_WEEK, user.getWeek_score_Guess());

                    Intent menu = new Intent(MainActivity.this, MenuActivity.class);
                    menu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(menu);
                    finish();
                }

                break;

            case R.id.tv_signup:
                Intent move = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(move);
                break;

        }

    }
}
