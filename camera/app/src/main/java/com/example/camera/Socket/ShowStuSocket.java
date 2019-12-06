package com.example.camera.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShowStuSocket extends Thread {
    public String classname;
    public List<String> stuname = new ArrayList<>();
    public List<String> sign_result = new ArrayList<>();

    public ShowStuSocket(String classname){
        this.classname = classname;
    }

    public void fun() throws IOException{
        System.out.println("---运行ShowStufun---");
        java.net.Socket s = new java.net.Socket("wise-ant.picp.io",37190);

        BufferedReader reader_file = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter writer_file = new PrintWriter(s.getOutputStream(),true);

        writer_file.println("50");
        System.out.println(reader_file.readLine());

        writer_file.println(this.classname);
        System.out.println("服务器发送的列表---------->");
        stuname = Collections.singletonList(reader_file.readLine());
        sign_result = Collections.singletonList(reader_file.readLine());
        String Str_1 = stuname.get(0);
        Str_1 = Str_1.substring(1, Str_1.length()-1);
        stuname = Arrays.asList(Str_1.split(", "));

        String Str_2 = sign_result.get(0);
        Str_2 = Str_2.substring(1, Str_2.length()-1);
        sign_result = Arrays.asList(Str_2.split(","));

        for(int i=0;i<stuname.size();++i){
            System.out.println("name:"+stuname.get(i));
        }
        for (int i=0; i<sign_result.size(); ++i){
            System.out.println("签到情况"+sign_result.get(i));
        }


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
