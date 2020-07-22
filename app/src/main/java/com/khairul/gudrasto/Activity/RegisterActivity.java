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
import com.khairul.gudrasto.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private TextInputLayout textInputEmail, textInputName, textInputPassword;
    private Button btn_register;
    private TextView login;

    private DatabaseHelperUser db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelperUser(this);
        textInputEmail = findViewById(R.id.text_input_email);
        textInputPassword =findViewById(R.id.text_input_password);
        textInputName = findViewById(R.id.text_input_name);
        btn_register = findViewById(R.id.btn_register);
        login = findViewById(R.id.tv_login);

        btn_register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    private boolean validateEmail(){
        String emailInput = textInputEmail.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()){
            textInputEmail.setError("Field can't be empty");
            return false;
        } else {
            textInputEmail.setError(null);
//            textInputEmail.setErrorEnabled(false);
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
            return true;
        }
    }

    private boolean validateName(){
        String passwordName = textInputName.getEditText().getText().toString().trim();

        if (passwordName.isEmpty()){
            textInputName.setError("Field can't be empty");
            return false;
        } else {
            textInputName.setError(null);
            return true;
        }
    }


    @Override
    public void onClick(View view) {
        Intent move = new Intent(RegisterActivity.this, MainActivity.class);
        move.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        switch (view.getId()){

            case R.id.btn_register:
                if (!validateEmail() || !validatePassword() || !validateName()){
                    return;
                }

                String email = textInputEmail.getEditText().getText().toString().trim();
                String name = textInputName.getEditText().getText().toString().trim();
                String password = textInputPassword.getEditText().getText().toString().trim();

                long cek = db.addUser(email, name, password);
                if (cek > 0){
                    Toast.makeText(this, "Success..!", Toast.LENGTH_SHORT).show();
                }
                startActivity(move);
                fileList();
                break;

            case R.id.tv_login:
                startActivity(move);
                finish();
                break;

        }

    }
}
