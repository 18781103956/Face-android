package com.example.camera.Socket;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GetTeacherSocket extends Thread{
    public String num, name, academy, id;

    public void setId(String id) {
        this.id = id;
    }

    public void fun() throws IOException{
        System.out.println("运行了GetTeacherSocket--fun");
        java.net.Socket s = new Socket("wise-ant.picp.io",37190);
        BufferedReader read_file = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter writer_file = new PrintWriter(s.getOutputStream(),true);

        writer_file.println("33");
        String return_code =read_file.readLine();  //读一个返回码
        System.out.println("服务器返回的："+return_code);

        writer_file.println(this.id);
        this.num = read_file.readLine();
        this.name = read_file.readLine();
        this.academy = read_file.readLine();
        System.out.println("服务器返回num："+num);
        System.out.println("服务器返回name："+name);
        System.out.println("服务器返回academy："+academy);

        read_file.close();
        writer_file.close();
        s.close();
    }

    @Override
    public void run() {
        super.run();
        try {
            fun();
            Thread.sleep(200);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
