package com.example.rk0509;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText number,pwd;
    private Button but_login,but_register;
    private String Login_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number = findViewById(R.id.editText_number);
        pwd = findViewById(R.id.editText_pwd);
        but_login = findViewById(R.id.button_login);
        but_register = findViewById(R.id.button_register);

        but_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numbers = number.getText().toString();
                String pwds = pwd.getText().toString();
               Login_url = "http://120.27.23.105/user/login?mobile="+numbers+"&password="+pwds;
               getData();
            }
        });



    }

    private void getData() {

    }

    public void register_event(View view){
        Intent it = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(it);
    }
}
