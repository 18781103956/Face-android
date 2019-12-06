package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.camera.Socket.CreateClassSocket;

public class CreateClassActivity extends AppCompatActivity {
    private TextView back = null;  //返回按钮
    private TextView code = null;  //返回的班级码
    private EditText classname = null; //班级名
    private Button createclass = null;
    public String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        Intent intent = getIntent();
        ID = intent.getStringExtra("id");

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateClassActivity.this, ClassActivity.class));
            }
        });

        code = findViewById(R.id.classnum);
        classname = findViewById(R.id.class_name);
        createclass = findViewById(R.id.create);
        createclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CreateClassSocket thread = new CreateClassSocket(ID,classname.getText().toString());
                    thread.setName("CreateClass");
                    thread.start();
                    thread.join();
                    String classcode = thread.classcode;
                    System.out.println("班级码："+classcode);
                    code.setText(classcode);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
