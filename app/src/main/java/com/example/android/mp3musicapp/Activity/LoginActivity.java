package com.example.android.mp3musicapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.android.mp3musicapp.R;
import com.example.android.mp3musicapp.RegisterAndLogin.Constants;
import com.example.android.mp3musicapp.db.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    EditText txtEmail, txtPwd;
    Button btnLogin;
    TextView lnkRegister;
    DatabaseHelper db;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtloginEmail);
        txtPwd = findViewById(R.id.txtloginPwd);
        btnLogin = findViewById(R.id.btnlogin);
        lnkRegister = findViewById(R.id.lnkRegister);
        db = new DatabaseHelper(this);
        preferences = getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE);

        // Kiểm tra đã đăng nhập chưa
        if (preferences.getBoolean(Constants.KEY_ISE_LOGGED_IN, false)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        btnLogin.setOnClickListener(v -> {
            String email = txtEmail.getText().toString().trim();
            String pwd = txtPwd.getText().toString().trim();
            if (db.checkUser(email, pwd)) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean(Constants.KEY_ISE_LOGGED_IN, true);
                editor.putString(Constants.KEY_EMAIL, email);
                editor.apply();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Sai email hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            }
        });

        lnkRegister.setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));
    }
}