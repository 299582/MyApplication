package com.example.rk0509;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RegisterActivity extends AppCompatActivity {

    private EditText number2,pwd2;
    private String register_url;
    private PhoneBean phoneBean;
    private String num;
    private String pwd;
    private Button register_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        number2 = findViewById(R.id.editText_number2);
        pwd2 = findViewById(R.id.editText_pwd2);
        register_login = findViewById(R.id.button_register_atonce);




        register_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = number2.getText().toString();
                pwd = pwd2.getText().toString();
                register_url = "http://120.27.23.105/user/reg?mobile="+num+"&password="+pwd;
                getData();
            }
        });

    }

    private void getData() {
        MyTast tast = new MyTast();
        tast.execute(register_url);
    }

    class MyTast extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(5000);
                urlConnection.setConnectTimeout(5000);
                if(urlConnection.getResponseCode() == 200){
                    InputStream inputStream = urlConnection.getInputStream();
                    String s = steamtoString(inputStream);
                    return s;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            Gson gson = new Gson();
            phoneBean = gson.fromJson(s, PhoneBean.class);

                    String msg = phoneBean.getMsg();
                    String code = phoneBean.getCode();
                    if(msg.equals("注册成功")){
                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                        Intent it = new Intent(RegisterActivity.this,MainActivity.class);
                        startActivity(it);
                    }else{
                        Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }


        }
    }

    private String steamtoString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int len;
        byte[] b = new byte[1024] ;
        while((len = inputStream.read(b))!= -1){
            outputStream.write(b,0,len);
        }
        return outputStream.toString();
    }


//    public void register_login(View view){
//
//        String msg = phoneBean.getMsg();
//        String code = phoneBean.getCode();
//        if(code == "0"){
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//            Intent it = new Intent(RegisterActivity.this,MainActivity.class);
//            startActivity(it);
//        }else{
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//        }
//
//    }
    public void register_back(View view){
        Intent it = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(it);
    }
}
