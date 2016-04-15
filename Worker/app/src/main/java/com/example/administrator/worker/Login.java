package com.example.administrator.worker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.worker.alei.untils.HttpURLConnectionUntils;
import com.example.administrator.worker.alei.untils.getAESstring;
import com.example.administrator.worker.jsonuser.until.userlogin;
import com.google.gson.Gson;

public class Login extends AppCompatActivity {

    private String URL = "http://221.2.165.50:814/AjaxBase.ashx?callback=?&sMode=LOGIN&sKey=?&sExec=?";
    private String STR="{\"result\": \"登录成功！\",\"status\":\"1\",\"reason\": \"登录成功！\",\"data\": {\"LOGIN\":[{\"skey\": \"8a918c5e-67e4-4cae-9782-9f4db0bdaa76\",\"userid\": \"00058602\",\"ismanager\": \"N\",\"username\":\"王阿磊\",\"manager\":\"冯元华\",\"grpname\":\"讯通信息科技公司\",\"usergh\":\"00058602\",\"grpid\":\"4\",\"usertype\":\"研发工程师\",\"dept\":\"010102\",\"deptname\":\"产品研发部\",\"str1\":\"\"}]}}";
    private EditText editUser;
    private EditText editPassword;
    private CheckBox cbxRemember;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*onCreat函数，在activity创建时调用*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        System.out.println("onCreat函数");
        editUser = (EditText) findViewById(R.id.UserEdittext);
        editPassword = (EditText) findViewById(R.id.PasswordEdittext);
        cbxRemember = (CheckBox) findViewById(R.id.rememberUserPwd);
        btnStart = (Button) findViewById(R.id.start);
        btnStart.setOnClickListener(listener);
        SharedPreferences cmt = Login.this.getSharedPreferences("AutoLogin", Context.MODE_APPEND);
        if(cmt.getBoolean("auto",false))
        {
            if(cmt.getBoolean("success",true))
            {
                editUser.setText(cmt.getString("user", null));
                editPassword.setText(getAESstring.decrypt("19910418", cmt.getString("pwd", null)));
                cbxRemember.setChecked(cmt.getBoolean("auto",true));
            }
            else
            {
                editUser.setText(cmt.getString("user", null));
                editPassword.setText(cmt.getString("pwd", null));
                cbxRemember.setChecked(cmt.getBoolean("auto",true));
            }
            btnStart.performClick();
        }
    }
    @Override
    protected void onStart() {
        /*onStart函数，在activity创建时,紧接着onCreate调用*/
        super.onStart();
        System.out.println("onStart函数");
    }

    @Override
    protected void onResume() {
        /*onResume函数，在activity运行时调用*/
        super.onResume();
        System.out.println("onResume函数");
    }

    @Override
    protected void onPause() {
        /*onPause函数，在activity停止时调用*/
        super.onPause();
        System.out.println("onPause函数");
    }

    @Override
    protected void onStop() {
        /*onStop函数，在activity停止变为不可见状态时调用
        在此状态下如果activity重新被调用，将会跳转到onRestart函数
        * */
        super.onStop();
        System.out.println("onStop函数");
    }

    @Override
    protected void onRestart() {
        /*onRestart函数，在activity在没有被释放内储存时，由不可见状态重新被调用时
        * */
        super.onRestart();
        System.out.println("onRestart函数");
    }

    @Override
    protected void onDestroy() {
        /*onDestroy函数，当内存被彻底释放掉，会最后调用一次函数*/
        super.onDestroy();
        System.out.println("onDestroy函数");
    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start:
                    System.out.println("监听成功");
                    Logining();
                    break;
            }
        }
    };
    public void Logining(){
        //region 动作：登录的主要操作
        String url=URL+"&var1="+editUser.getText().toString()+"&var2="+editPassword.getText().toString();
        System.out.println("URL拼写完成:" + url);

        HttpURLConnectionUntils.doGetAsyn(url, new HttpURLConnectionUntils.CallBack() {
            @Override
            public void onRequestComplete(String result) {
                System.out.println("json回调成功" + result);
                Gson gson = new Gson();
                userlogin user =gson.fromJson(result,userlogin.class);
                if(user.getStatus()==1)
                {
                    System.out.println("登陆成功");
                    //region 动作：密码记录到本地，若加密失败记录危险性密码。（含记录有效自检，已注释！）
                    if (cbxRemember.isChecked()) {
                        SharedPreferences cmt = Login.this.getSharedPreferences("AutoLogin", Context.MODE_APPEND);
                        SharedPreferences.Editor editor = cmt.edit();
                        String userx;
                        String pwdx;
                        Boolean success;
                        try {
                            userx = editUser.getText().toString();
                            pwdx = getAESstring.encrypt("19910418", editPassword.getText().toString());
                            success = true;

                        } catch (Exception ex) {
                            userx = editUser.getText().toString();
                            pwdx = editPassword.getText().toString();
                            success = false;
                            System.out.println("本地用户名密码存储异常，执行记录了带有危险的用户名密码。");
                        }
                        editor.putString("logining", editUser.getText().toString());
                        editor.putString("user", userx);
                        editor.putString("pwd", pwdx);
                        editor.putBoolean("auto", cbxRemember.isChecked());
                        editor.putBoolean("success", success);
                        editor.commit();
                        System.out.println("本地数据存储完毕，自测验证有效性");
                        //region 动作：程序员本地记录数据自检
                        try {
                            if (cmt.getBoolean("success", false)) {
                                System.out.println("用户名：" + cmt.getString("user", null));
                                System.out.println("密码：" + getAESstring.decrypt("19910418", cmt.getString("pwd", null)));
                                System.out.println(cmt.getAll());
                            } else {
                                System.out.println("前一次本地用户信息记录存在异常！");
                                System.out.println(cmt.getAll());
                            }
                        } catch (Exception ex) {
                            System.out.println("本地用户信息记录存在异常！");
                            System.out.println(cmt.getAll());
                        }
                        //endregion
                        System.out.println("用户名密码保存完成");
                    } else {
                        SharedPreferences cmt = Login.this.getSharedPreferences("AutoLogin", Context.MODE_APPEND);
                        SharedPreferences.Editor editor = cmt.edit();
                        editor.putString("logining", editUser.getText().toString());
                        editor.putString("user", "");
                        editor.putString("pwd", "");
                        editor.putBoolean("auto", false);
                        editor.putBoolean("success", false);
                        editor.commit();
                        //region 动作：程序员本地记录数据自检
                        try {
                            if (cmt.getBoolean("success", false)) {
                                System.out.println("用户名：" + cmt.getString("user", null));
                                System.out.println("密码：" + getAESstring.decrypt("19910418", cmt.getString("pwd", null)));
                                System.out.println(cmt.getAll());
                            } else {
                                System.out.println("前一次本地用户信息记录存在异常！");
                                System.out.println(cmt.getAll());
                            }
                        } catch (Exception ex) {
                            System.out.println("本地用户信息记录存在异常！");
                            System.out.println(cmt.getAll());
                        }
                        //endregion
                    }
                    //endregion
                    startActivity(new Intent(Login.this, TabPage.class));
                }
                else
                {
                    System.out.println("登陆失败");
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(),"登录失败。",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

                /*JsonReader reader = new JsonReader(new StringReader(result));
                try {
                    reader.beginObject();
                    System.out.println("开始解析Json对象");
                    while (reader.hasNext()) {
                        System.out.println("解析。。。。。。");
                        String tagName = reader.nextName();
                        System.out.println("获取Json中下一个键。。。。。");
                        if (tagName.equals("result")) {
                            System.out.println("寻找登陆成功信息的键。。。。。。");
                            if (reader.nextString().equals("登录成功！")) {
                                System.out.println("已经找到标记键，匹配值。。。。");
                                System.out.println("成功。。。。。。");
                                //region 动作：密码记录到本地，若加密失败记录危险性密码。（含记录有效自检，已注释！）
                                if (cbxRemember.isChecked()) {
                                    SharedPreferences cmt = Login.this.getSharedPreferences("AutoLogin", Context.MODE_APPEND);
                                    SharedPreferences.Editor editor = cmt.edit();
                                    String userx;
                                    String pwdx;
                                    Boolean success;
                                    try {
                                        userx = editUser.getText().toString();
                                        pwdx = getAESstring.encrypt("19910418", editPassword.getText().toString());
                                        success = true;

                                    } catch (Exception ex) {
                                        userx = editUser.getText().toString();
                                        pwdx = editPassword.getText().toString();
                                        success = false;
                                        System.out.println("本地用户名密码存储异常，执行记录了带有危险的用户名密码。");
                                    }
                                    editor.putString("logining", editUser.getText().toString());
                                    editor.putString("user", userx);
                                    editor.putString("pwd", pwdx);
                                    editor.putBoolean("auto", cbxRemember.isChecked());
                                    editor.putBoolean("success", success);
                                    editor.commit();
                                    System.out.println("本地数据存储完毕，自测验证有效性");
                                    //region 动作：程序员本地记录数据自检
                                    try {
                                        if (cmt.getBoolean("success", false)) {
                                            System.out.println("用户名：" + cmt.getString("user", null));
                                            System.out.println("密码：" + getAESstring.decrypt("19910418", cmt.getString("pwd", null)));
                                            System.out.println(cmt.getAll());
                                        } else {
                                            System.out.println("前一次本地用户信息记录存在异常！");
                                            System.out.println(cmt.getAll());
                                        }
                                    } catch (Exception ex) {
                                        System.out.println("本地用户信息记录存在异常！");
                                        System.out.println(cmt.getAll());
                                    }
                                    //endregion
                                    System.out.println("用户名密码保存完成");
                                } else {
                                    SharedPreferences cmt = Login.this.getSharedPreferences("AutoLogin", Context.MODE_APPEND);
                                    SharedPreferences.Editor editor = cmt.edit();
                                    editor.putString("logining", editUser.getText().toString());
                                    editor.putString("user", "");
                                    editor.putString("pwd", "");
                                    editor.putBoolean("auto", false);
                                    editor.putBoolean("success", false);
                                    editor.commit();
                                    //region 动作：程序员本地记录数据自检
                                    try {
                                        if (cmt.getBoolean("success", false)) {
                                            System.out.println("用户名：" + cmt.getString("user", null));
                                            System.out.println("密码：" + getAESstring.decrypt("19910418", cmt.getString("pwd", null)));
                                            System.out.println(cmt.getAll());
                                        } else {
                                            System.out.println("前一次本地用户信息记录存在异常！");
                                            System.out.println(cmt.getAll());
                                        }
                                    } catch (Exception ex) {
                                        System.out.println("本地用户信息记录存在异常！");
                                        System.out.println(cmt.getAll());
                                    }
                                    //endregion
                                }
                                //endregion
                                startActivity(new Intent(Login.this, TabPage.class));
                                break;
                            } else {
                                System.out.println("已经找到标记键，匹配值。。。。");
                                System.out.println("失败。。。。。。");
                                break;
                            }
                        }
                    }
                    reader.endObject();
                    System.out.println("结束对象解析");
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });

    }

}
