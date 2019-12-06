package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.example.camera.Socket.ShowClassSocket;
import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity {
    private Button btn_NewClass = null;
    private ListView list_class = null;
    private String ID;
    private String data[] = {"软件1班","软件2班","软件3班","软件4班","软件5班","软件6班","软件7班","软件8班","软件9班","软件10班","软件11班","软件12班","软件13班"};
    public List<String> classlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        Intent intent = getIntent();
        ID = intent.getStringExtra("id");
        classSocket();

        btn_NewClass = findViewById(R.id.btn_newClass);
        btn_NewClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("id", ID);//设置参数,""
                intent.setClass(ClassActivity.this, CreateClassActivity.class);//从哪里跳到哪里
                ClassActivity.this.startActivity(intent);
                //startActivity(new Intent(ClassActivity.this, CreateClassActivity.class));
            }
        });

        list_class = findViewById(R.id.list_class);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, classlist);
        list_class.setAdapter(adapter);
        list_class.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ClassActivity.this,"pos:"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("classname",classlist.get(position));//设置参数,""
                intent.setClass(ClassActivity.this, TsignActivity.class);//从哪里跳到哪里
                ClassActivity.this.startActivity(intent);
                //startActivity(new Intent(ClassActivity.this, TsignActivity.class));
            }
        });

        for (int i = 0; i < classlist.size();i++){
            System.out.println("班级"+classlist.get(i));
        }

    }

    public  void  classSocket(){
        try {
            ShowClassSocket thread = new ShowClassSocket(ID);
            thread.setName("ShowClass");
            thread.start();
            thread.join();
             classlist = thread.reList;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
