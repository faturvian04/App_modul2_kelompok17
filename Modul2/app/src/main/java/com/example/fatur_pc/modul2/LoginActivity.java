package com.example.fatur_pc.modul2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Printer;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fatur_pc.modul2.DatabaseHandler;
import com.example.fatur_pc.modul2.User;

import java.security.PrivateKey;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsernameLogin, etPasswdLogin;
    private Button btnSignUpLogin, btnSignInLogin;
    String username, password;
    private static User user;
    private static DatabaseHandler presenter;
    RelativeLayout relativeLayout;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        relativeLayout = (RelativeLayout) findViewById(R.id.scrollView2);
        animationDrawable = (AnimationDrawable) relativeLayout.getBackground();

        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        initView();
        initData();
        login();
        register();

        Intent reg = new Intent(this, RegisterActivity.class);

    }


    @Override
    protected void onPause() {
        super.onPause();

        if (animationDrawable != null && animationDrawable.isRunning()){
            animationDrawable.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (animationDrawable != null && !animationDrawable.isRunning()){
            animationDrawable.start();
        }
    }


    public static User getUser(){
        return user;
    }

    public static DatabaseHandler getPresenter(){
        return presenter;
    }

    private void register() {
        btnSignUpLogin.setOnClickListener(V -> {
            Intent reg = new Intent(this, RegisterActivity.class);
            startActivity(reg);
            finish();}
        );
    }

    private void initData() {
        user = new User();
        presenter = new DatabaseHandler(this);
    }

    private void initView() {
        etUsernameLogin = findViewById(R.id.etUsernameLogin);
        etPasswdLogin = findViewById(R.id.etPasswdLogin);
        btnSignInLogin = findViewById(R.id.btnSignInLogin);
        btnSignUpLogin = findViewById(R.id.btnSignUpLogin);
    }

    private Boolean validation() {
        username = etUsernameLogin.getText().toString();
        password = etPasswdLogin.getText().toString();

        if(username.isEmpty()){
            Toast.makeText(this, "Isikan username", Toast.LENGTH_SHORT).show();
            Log.e("Validation","false");
            return false;
        }

        if(password.isEmpty()){
            Toast.makeText(this, "Isikan Password", Toast.LENGTH_SHORT).show();
            Log.e("Validation","false");
            return false;
        }

        Log.e("Validation","true");
        return true;
    }

    private void login() {
        btnSignInLogin.setOnClickListener(v -> actLogin());
    }


    private void actLogin(){
        if(validation()){
            Log.e("actLogin","true");
            if(loginData()){
                initSharedPreference();
                Intent main = new Intent(this, MainActivity.class);

                startActivity(main);
                finish();
            } else {
                Toast.makeText(this, "Login gagal", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Boolean loginData(){
        user = presenter.getLogin(username);

        if (password.equals(user.getPassword())){
            return true;
        } else {
            return false;
        }
    }

    private void initSharedPreference() {
        SharedPreferences preferences = getSharedPreferences("LoginPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", password);
        editor.putString("username", username);
        editor.commit();
        editor.apply();
    }
}