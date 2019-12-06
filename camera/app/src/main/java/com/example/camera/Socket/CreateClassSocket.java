package com.example.camera.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CreateClassSocket extends Thread{
    public String id,class_name, classcode;

    public CreateClassSocket(String id, String class_name){
        this.id = id;
        this.class_name = class_name;
    }

    public void fun()  throws IOException {
        System.out.println("运行了CreateClassSocket--fun");
        java.net.Socket s = new Socket("wise-ant.picp.io",37190);
        BufferedReader read_file = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter writer_file = new PrintWriter(s.getOutputStream(),true);

        writer_file.println("30");
        String return_code =read_file.readLine();  //读一个返回码
        System.out.println("服务器返回的："+return_code);

        writer_file.println(this.id);
        writer_file.println(this.class_name);
        this.classcode = read_file.readLine();
        System.out.println("服务器返回name："+classcode);


        read_file.close();
        writer_file.close();
        s.close();
    }

    @Override
    public void run() {
        super.run();
        try{
            fun();
            Thread.sleep(200);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
