package com.example.camera.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EnterClassSocket extends Thread {
    public String id,classnum;
    public EnterClassSocket(String id, String classnum){
        this.id = id;
        this.classnum = classnum;
    }

    public void fun() throws IOException {
        System.out.println("运行了EnterClass--fun");
        java.net.Socket s = new Socket("wise-ant.picp.io",37190);
        BufferedReader read_file = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter writer_file = new PrintWriter(s.getOutputStream(),true);

        writer_file.println("41");
        String temp_str=read_file.readLine();
        System.out.println("服务器返回的："+temp_str);

        writer_file.println(this.id);
        writer_file.println(this.classnum);

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
