package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.camera.Socket.GetTeacherSocket;
import com.example.camera.Socket.LoginSocket;

import java.util.Timer;
import java.util.TimerTask;


public class TeacherActivity extends AppCompatActivity {
    private ImageView image; //照片
    private TextView text_1; //工号
    private TextView text_2; //姓名
    private TextView text_3; //院系
    public  String ID; //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        Intent intent_data = getIntent();
        ID=intent_data.getStringExtra("id");

        //设置text
        text_1 = findViewById(R.id.id);
        //text_1.setText("工号：");
        text_2 = findViewById(R.id.name);
        //text_2.setText("姓名：");
        text_3 = findViewById(R.id.academy);
        //text_3.setText("院系：");

        socket_data();  //创建线程
        //
        final Intent  intent = new Intent();//TeacherActivity.this, ClassActivity.class
        intent.putExtra("id", ID);//设置参数,""
        //暂停2s 跳转至下一页面
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                intent.setClass(TeacherActivity.this, ClassActivity.class);//从哪里跳到哪里
                TeacherActivity.this.startActivity(intent);
            }
        };
        timer.schedule(task,2000);
    }

    public void socket_data(){
        try {
            GetTeacherSocket thread = new GetTeacherSocket();
            thread.setId(ID);
            thread.setName("Getdata");
            thread.start();
            thread.join();
            String job_num = thread.num;
            String job_name = thread.name;
            String job_academy = thread.academy;
            System.out.println("--------->"+job_num);
            System.out.println("--------->"+job_name);
            System.out.println("--------->"+job_academy);
            text_1.setText(job_num);
            text_2.setText(job_name);
            text_3.setText(job_academy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
