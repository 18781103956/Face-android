package com.example.camera.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class StudentResultSocket extends Thread {

    public String school, student_id, studentname, major, classnum, result,  username/*账号*/;

    public StudentResultSocket(String username){
        this.username = username;
    }

    public void fun() throws IOException{
        System.out.println("---StudentResultSocketfun---");
        java.net.Socket s = new java.net.Socket("wise-ant.picp.io",37190);

        BufferedReader reader_file = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter writer_file = new PrintWriter(s.getOutputStream(),true);

        writer_file.println("60");
        System.out.println(reader_file.readLine());

        writer_file.println("60");
        System.out.println("服务器发送的列表---------->");
        this.school = reader_file.readLine();
        this.student_id = reader_file.readLine();
        this.studentname = reader_file.readLine();
        this.major = reader_file.readLine();
        this.classnum = reader_file.readLine();
        this.result = reader_file.readLine();

        reader_file.close();
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
