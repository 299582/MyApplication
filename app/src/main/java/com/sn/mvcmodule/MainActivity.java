package com.sn.mvcmodule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sn.mvcmodule.bean.User;

/**
 * 流程:
 * 1.界面展示
 * 2.用户输入
 * 3.按钮点击
 * 4.判断用户输入
 * 5.显示滚动条
 * 6.一系列的耗时操作
 * 7.隐藏
 * 8.提示用户
 *
 */
public class MainActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mPassWord;
    private ProgressDialog mProgressDialog;
    private String mUserNameString;
    private String mPassWordString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //进行控件的初始化
        mUserName = (EditText) findViewById(R.id.username);
        mPassWord = (EditText) findViewById(R.id.password);
        //进度条对象
        mProgressDialog = new ProgressDialog(this);
    }

    /**
     * 登录按钮的点击事件执行逻辑
     * @param view
     */
    public void login(View view){
        //获取用户输入信息
        mUserNameString = mUserName.getText().toString().trim();
        mPassWordString = mPassWord.getText().toString().trim();
        //把用户输入信息放入bean类里
        User user = new User();
        user.password= mPassWordString;
        user.username= mUserNameString;
        //检查返回值的类型
        boolean result = checkUserInfo(user);

        //用户输入格式是否正确
        if (result){
            //进度条显示
            mProgressDialog.show();
            new Thread(){
                public void run() {
                    try {
                        sleep(3000);
                        //用户信息是否填写正确
                        if("ycf".equals(mUserNameString) && "ycf".equals(mPassWordString)){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //进度条消失
                                    mProgressDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "登录成功"+mUserNameString, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                };
            }.start();
        }
        else {
            Toast.makeText(MainActivity.this, "用户名或密码不能为null", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 判断用户输入是否为null
     * @param user
     * @return
     */
    private boolean checkUserInfo(User user){
        //判断用户的密码账号是否输入为null,为null,返回false
        if(TextUtils.isEmpty(user.username) || TextUtils.isEmpty(user.password)){
            return false;
        }

        return true;
    }

}
