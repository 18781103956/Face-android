package com.example.camera.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShowClassSocket extends Thread {
    public String id;
    public List<String> reList=new ArrayList<>();
    public  ShowClassSocket(String id){
        this.id = id;
    }

    public void fun()throws IOException {
        System.out.println("---运行ShowClassfun---");
        java.net.Socket s = new java.net.Socket("wise-ant.picp.io",37190);

        BufferedReader reader_file = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter writer_file = new PrintWriter(s.getOutputStream(),true);

        writer_file.println("31");
        System.out.println(reader_file.readLine());

        writer_file.println(this.id);
        System.out.println("服务器发送的列表---------->");
        //List<String> reList=new ArrayList<>();
        reList = Collections.singletonList(reader_file.readLine());
        String Str_1 = reList.get(0);
        Str_1 = Str_1.substring(1, Str_1.length()-1);
        reList = Arrays.asList(Str_1.split(", "));
        for(int i=0;i<reList.size();++i){
            System.out.println(reList.get(i));
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
