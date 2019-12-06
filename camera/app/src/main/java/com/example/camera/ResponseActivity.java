package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.camera.Socket.StudentResultSocket;

import java.util.Calendar;

public class ResponseActivity extends AppCompatActivity {

    private TextView text_school,stu_id,name,major,result,now_time;
    public String ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        Intent intent_data = getIntent();
        ID = intent_data.getStringExtra("id"); //账号
        fun();

        text_school = findViewById(R.id.college);
        stu_id = findViewById(R.id.stu_id);
        name = findViewById(R.id.name);
        major = findViewById(R.id.major);
        result = findViewById(R.id.Sign_response);
        now_time = findViewById(R.id.Sign_time);
    }

    public void fun(){
        try{
            StudentResultSocket thread = new StudentResultSocket(ID);
            thread.setName("StudentResult");
            thread.start();
            thread.join();
            text_school.setText(thread.school);
            stu_id.setText(thread.student_id);
            name.setText(thread.studentname);
            major.setText(thread.major);
            result.setText(thread.result);
            getTime();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void getTime(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH)+1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //获取系统时间
//小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //分钟
        int minute = calendar.get(Calendar.MINUTE);
        //秒
        int second = calendar.get(Calendar.SECOND);
        String now = year+"/"+ month+"/"+day+"/"+ hour + ":"+minute+":"+second;
        now_time.setText(now);
    }
}
