package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.camera.Socket.EnterClassSocket;

public class AddClassActivity extends AppCompatActivity {

    private Button  btn_back, btn_send;
    private EditText in_classnum;
    public String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class2);
        Intent intent_data = getIntent();
        ID = intent_data.getStringExtra("id");

        in_classnum = findViewById(R.id.in_ClassNum);
        btn_send = findViewById(R.id.btn_ascertain);
        btn_back = findViewById(R.id.Text_back);
        MakeSure();
        BackPage();
    }

    public void BackPage(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddClassActivity.this, CameraActivity.class);
                startActivity(intent);
            }
        });
    }

    public void MakeSure(){
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    EnterClassSocket thread = new EnterClassSocket(ID,in_classnum.getText().toString());
                    thread.setName("AddClass");
                    thread.start();
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}


