package com.doubleslash.playground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.doubleslash.playground.R;
import com.doubleslash.playground.Retrofit_pakage.My_Retrofit;
import com.doubleslash.playground.register.RegisterActivity1;
import com.doubleslash.playground.register.RegisterActivity4;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEdit;
    private EditText passwordEdit;
    private Button loginBtn;
    private TextView findIdPwBtn;
    private TextView registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
    }

    private void initUI() {
        emailEdit = findViewById(R.id.email_edit);
        passwordEdit = findViewById(R.id.password_edit);
        final My_Retrofit my_retrofit=new My_Retrofit();

        loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailEdit.getText().toString()!=null&&passwordEdit.getText().toString()!=null){
                    int result=my_retrofit.post_login(emailEdit.getText().toString(),passwordEdit.getText().toString());
                    if(result==1) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        findIdPwBtn = findViewById(R.id.find_IdPw_btn);
        findIdPwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 할 일
            }
        });
        registerBtn = findViewById(R.id.register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity1.class);
                startActivity(intent);
            }
        });
    }
}