package com.example.android.mp3musicapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.android.mp3musicapp.R;
import com.example.android.mp3musicapp.db.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    EditText txtEmail, txtPwd, txtUsername;
    Button btnRegister;
    TextView lnkLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Ánh xạ đúng với ID trong activity_register.xml
        txtUsername = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPwd = findViewById(R.id.txtPwd);
        btnRegister = findViewById(R.id.btnregister);
        lnkLogin = findViewById(R.id.lnkLogin);
        db = new DatabaseHelper(this);

        btnRegister.setOnClickListener(v -> {
            String email = txtEmail.getText().toString().trim();
            String pwd = txtPwd.getText().toString().trim();
            String username = txtUsername.getText().toString().trim();
            if (!email.isEmpty() && !pwd.isEmpty() && !username.isEmpty()) {
                db.addUser(email, username, pwd);
                Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý nút "Đăng nhập"
        lnkLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }
}