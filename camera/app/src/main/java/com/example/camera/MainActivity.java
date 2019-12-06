package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.camera.Socket.LoginSocket;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText E_user;
    private EditText E_password;
    private RadioGroup radgroup;      //单选框
    //public RadioButton radbtn;        //单选框
    private TextView register; //注册
    public static String result = "0"; //判断密码是否正确
    public String people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //控件声明
        E_user =  findViewById(R.id.user);//账号
        E_password = findViewById(R.id.password);//密码
        login  = findViewById(R.id.btn_begin);  //登录按钮
        // 单选框
        radgroup = (RadioGroup) findViewById(R.id.radioGroup);
        radgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = (RadioButton)findViewById(checkedId);
                Toast.makeText(getApplicationContext(), radbtn.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        //Login();


        //服务器返回处理
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //创建线程
                try {
                    LoginSocket thread = new LoginSocket(E_user.getText().toString(),E_password.getText().toString());
                    thread.setName("Login");
                    thread.start();
                    thread.join();
                    result = thread.getResult();
                    System.out.println("main--------->"+result);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                for (int i = 0; i < radgroup.getChildCount(); i++) {
                    RadioButton rd = (RadioButton) radgroup.getChildAt(i);
                    if (rd.isChecked()) {
                        people = rd.getText().toString();
                        break;
                    }
                }

                if(result.equals("1")){

                    if(people.equals("老师")){
                        Intent intent = new Intent();//MainActivity.this, TeacherActivity.class
                        intent.putExtra("id",E_user.getText().toString());
                        intent.setClass(MainActivity.this, TeacherActivity.class);
                        MainActivity.this.startActivity(intent);
                        /*Intent intent = new Intent(MainActivity.this, TeacherActivity.class );
                        startActivity(intent);*/
                    }
                    else if (people.equals("学生")){
                        Intent intent = new Intent();
                        intent.putExtra("id",E_user.getText().toString());
                        intent.setClass(MainActivity.this, CameraActivity.class);
                        MainActivity.this.startActivity(intent);
                        //Intent intent = new Intent(MainActivity.this,CameraActivity.class ); //StudentClassActivity.class
                                            //startActivity(intent);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "错误", Toast.LENGTH_LONG).show();
                }
            }
        });




        register();
    }

    /*注册*/
    public void register(){
        register = findViewById(R.id.btn_Sign_up);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, registerActivity.class);
                startActivity(intent);
            }
        });
    }

}

