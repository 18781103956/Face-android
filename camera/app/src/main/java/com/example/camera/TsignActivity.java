package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.camera.Socket.BeginSignSocket;
import com.example.camera.Socket.ShowStuSocket;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TsignActivity extends AppCompatActivity {
    public String classname;
    private TextView classtext, refurbish;
    private Button btn_Sign;
    public List<String> stuname ;
    public List<String> sign_result ;
    ListView demoListView;
    SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsign);
        Intent intent = getIntent();
        classname = intent.getStringExtra("classname");


        refurbish = findViewById(R.id.btn_refurbish);  //刷新
        btn_Sign = findViewById(R.id.btn_Sign);  //开始签到按钮
        classtext = findViewById(R.id.text_sign);
        classtext.setText(classname);
        fun();          //传入列表
        ShowList() ;     //列表显示
        BeginSignFun();  //开始签到
        UpdataTable(); // 刷新列表

        /*demoListView = findViewById(R.id.list_stu);
        List<Map<String, ?>>  dataList = new ArrayList<>();
        for (int i = 0; i < stuname.size() ; i++){//stuname.size()
            Map<String, String>map = new HashMap<>();
            map.put("id", "" +  i);
            map.put("name", "name"+stuname.get(i));
            map.put("sex", i%2==0?"男":"女");
            map.put("result", sign_result.get(i));
            dataList.add(map);
        }
        String[] from = {"id", "name","sex", "result"};
        int [] to = {R.id.tv_id, R.id.tv_name, R.id.tv_sex, R.id.tv_result};
        simpleAdapter = new SimpleAdapter(this, dataList,R.layout.tclass_list_items, from, to);
        demoListView.setAdapter(simpleAdapter);*/

        /*for (int i = 0; i<stuname.size(); i++){
            System.out.println("name"+stuname.get(i));
            System.out.println("sign_result"+sign_result.get(i));
        }*/
    }

    public void fun(){
        try{
            ShowStuSocket thread = new ShowStuSocket(classname);
            thread.setName("ShowStu");
            thread.start();
            thread.join();
            stuname = thread.stuname;
            sign_result = thread.sign_result;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void BeginSignFun() {
        try {
            BeginSignSocket thread = new BeginSignSocket(classname);
            thread.setName("BeginSign");
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ShowList(){
        refurbish = findViewById(R.id.btn_refurbish);  //刷新
        btn_Sign = findViewById(R.id.btn_Sign);  //开始签到按钮
        classtext = findViewById(R.id.text_sign);
        classtext.setText(classname);
        fun();          //传入列表
        BeginSignFun();  //开始签到
        UpdataTable(); // 刷新列表

        demoListView = findViewById(R.id.list_stu);
        List<Map<String, ?>>  dataList = new ArrayList<>();
        for (int i = 0; i < stuname.size() ; i++){//stuname.size()
            Map<String, String>map = new HashMap<>();
            map.put("id", "" +  i);
            map.put("name", "name"+stuname.get(i));
            map.put("sex", i%2==0?"男":"女");
            map.put("result", sign_result.get(i));
            dataList.add(map);
        }
        String[] from = {"id", "name","sex", "result"};
        int [] to = {R.id.tv_id, R.id.tv_name, R.id.tv_sex, R.id.tv_result};
        simpleAdapter = new SimpleAdapter(this, dataList,R.layout.tclass_list_items, from, to);
        demoListView.setAdapter(simpleAdapter);
    }

    public void UpdataTable(){
        refurbish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fun();
                ShowList();
            }
        });
    }
}
