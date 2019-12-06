package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class StudentClassActivity extends AppCompatActivity {
    private Button  btn_add_class;
    private ListView list;
    private String data[] = {"软件1班","软件2班","软件3班","软件4班","软件5班","软件6班","软件7班","软件8班","软件9班","软件10班","软件11班","软件12班","软件13班"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class);
        btn_add_class = findViewById(R.id.add_class);
        btn_add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentClassActivity.this,AddClassActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        list = findViewById(R.id.have_class);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(StudentClassActivity.this,"点击了" + id,Toast.LENGTH_SHORT).show();
                startActivity(new Intent(StudentClassActivity.this,CameraActivity.class));
            }
        });
    }
}
